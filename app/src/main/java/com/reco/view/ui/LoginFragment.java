package com.reco.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.reco.R;
import com.reco.service.model.UserLoginModel;
import com.reco.service.model.UserProfileModel;
import com.reco.service.repository.APIService;
import com.reco.util.Utilities;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginFragment extends Fragment {
    private APIService mAPIService;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.API_URL))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mAPIService = mRetrofit.create(APIService.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView mUsername = view.findViewById(R.id.fragment_login_username);
        TextView mPassword = view.findViewById(R.id.fragment_login_password);
        Button mLoginButton = view.findViewById(R.id.fragment_login_login_button);
        TextView mRegisterInstead = view.findViewById(R.id.fragment_login_register_instead_button);
        ImageButton mPasswordVisibility = view.findViewById(R.id.fragment_login_password_visibility);


        NavController navController = Navigation.findNavController(view);

        mPasswordVisibility.setOnClickListener(view12 -> {
            if (mPassword.getTransformationMethod() == PasswordTransformationMethod.getInstance()) {
                mPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                mPasswordVisibility.setImageResource(R.drawable.ic_baseline_visibility_off_24);
            } else {
                mPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                mPasswordVisibility.setImageResource(R.drawable.ic_baseline_remove_red_eye_24);
            }
        });

        mPassword.setOnTouchListener((view13, motionEvent) -> {
            view13.performClick();
            mPassword.setError(null);
            mPasswordVisibility.setVisibility(View.VISIBLE);
            return false;
        });

        mLoginButton.setOnClickListener(view1 -> {
            String username = mUsername.getText().toString();
            String password = mPassword.getText().toString();

            UserProfileModel localUser = Utilities.getLocalUser((AppCompatActivity) Objects.requireNonNull(getActivity()));
            // check if fields are empty
            if (username.isEmpty()) {
                mUsername.setError(getString(R.string.field_required));
                mUsername.requestFocus();
            } else if (password.isEmpty()) {
                mPasswordVisibility.setVisibility(View.INVISIBLE);
                mPassword.setError(getString(R.string.field_required));
                mPassword.requestFocus();
            } else {
                // disable the button
                mLoginButton.setEnabled(false);
                // call the api to login
                mAPIService.userLogin(new UserLoginModel(username, password)).enqueue(new Callback<UserProfileModel>() {
                    @Override
                    public void onResponse(@NotNull Call<UserProfileModel> call, @NotNull Response<UserProfileModel> response) {
                        if (response.isSuccessful()) {
                            if (getActivity() != null) {
                                UserProfileModel user = response.body();
                                // set flag
                                Utilities.setLoggedInStatus((AppCompatActivity) getActivity(), true);
                                if (localUser == null) {
                                    // save user to shared preferences
                                    Utilities.saveLocalUser((AppCompatActivity) Objects.requireNonNull(getActivity()), user);
                                }
                                // start home activity
                                startActivity(new Intent(getActivity(), HomeActivity.class));
                                getActivity().finish();
                            }
                        } else {
                            // enable the button
                            mLoginButton.setEnabled(true);
                            Toast.makeText(getContext(), R.string.invalid_credentials, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<UserProfileModel> call, @NotNull Throwable t) {
                        // enable the button
                        mLoginButton.setEnabled(true);
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });

        mRegisterInstead.setOnClickListener(view2 -> {
            // disable the button
            mRegisterInstead.setEnabled(false);
            navController.navigate(R.id.action_loginFragment_to_registerFragment);
        });
    }
}