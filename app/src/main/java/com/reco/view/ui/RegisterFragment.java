package com.reco.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.reco.R;
import com.reco.service.model.UserProfileModel;
import com.reco.service.model.UserRegisterModel;
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

public class RegisterFragment extends Fragment {
    private APIService mAPIService;

    public RegisterFragment() {
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView mDisplayName = view.findViewById(R.id.fragment_register_displayName);
        TextView mUsername = view.findViewById(R.id.fragment_register_username);
        TextView mMessengerUrl = view.findViewById(R.id.fragment_register_messengerURL);
        TextView mPassword = view.findViewById(R.id.fragment_register_password);
        TextView mRepeatPassword = view.findViewById(R.id.fragment_register_repeat_password);
        TextView mLoginInstead = view.findViewById(R.id.fragment_register_go_to_login);
        Button mRegisterButton = view.findViewById(R.id.fragment_register_signup_button);
        NavController navController = Navigation.findNavController(view);

        mRegisterButton.setOnClickListener(mView -> {
            String username = mUsername.getText().toString();
            String displayName = mDisplayName.getText().toString();
            String messengerUrl = mMessengerUrl.getText().toString();
            String password = mPassword.getText().toString();
            String repeatPassword = mRepeatPassword.getText().toString();

            // check if input fields are not empty
            if (displayName.isEmpty()) {
                mDisplayName.setError(getString(R.string.field_required));
            } else if (username.isEmpty()) {
                mUsername.setError(getString(R.string.field_required));
            } else if (password.isEmpty()) {
                mPassword.setError(getString(R.string.field_required));
            } else if (repeatPassword.isEmpty()) {
                mRepeatPassword.setError(getString(R.string.field_required));
            } else {
                // check if passwords match
                if (password.equals(repeatPassword)) {
                    // call the api to register
                    mAPIService.userRegister(new UserRegisterModel(username, displayName, messengerUrl, password, repeatPassword)).enqueue(new Callback<UserProfileModel>() {
                        @Override
                        public void onResponse(@NotNull Call<UserProfileModel> call, @NotNull Response<UserProfileModel> response) {
                            if (response.isSuccessful()) {
                                UserProfileModel user = response.body();
                                // delete any previous we might have saved data locally
                                Utilities.clearLocalData((AppCompatActivity) Objects.requireNonNull(getActivity()));
                                // save user to shared preferences
                                Utilities.saveLocalUser((AppCompatActivity) Objects.requireNonNull(getActivity()), user);
                                // start home activity
                                startActivity(new Intent(getActivity(), HomeActivity.class));
                                getActivity().finish();
                            } else {
                                Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(@NotNull Call<UserProfileModel> call, @NotNull Throwable t) {
                            Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    mRepeatPassword.setError(getString(R.string.passwords_do_not_match));
                }
            }
        });

        mLoginInstead.setOnClickListener(view2 -> {
            navController.navigate(R.id.action_registerFragment_to_loginFragment);
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }
}