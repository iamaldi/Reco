package com.reco.view.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.reco.R;
import com.reco.service.model.UserProfileModel;
import com.reco.service.model.UserRegisterModel;
import com.reco.service.repository.APIService;
import com.reco.util.Utilities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterFragment extends Fragment {
    private Retrofit mRetrofit;
    private APIService mAPIService;
    private TextView mName, mUsername, mPassword, mRepeatPassword;
    private Button mRegisterButton;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRetrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.API_URL))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mAPIService = mRetrofit.create(APIService.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mName = view.findViewById(R.id.fragment_register_name);
        mUsername = view.findViewById(R.id.fragment_register_username);
        mPassword = view.findViewById(R.id.fragment_register_password);
        mRepeatPassword = view.findViewById(R.id.fragment_register_repeat_password);
        mRegisterButton = view.findViewById(R.id.fragment_register_signup_button);

        // hide bottom navigation menu
        // getActivity because the navbar belongs to MainActivity and
        // it is not accessible from any other fragment as it is initialized in MainActivity
        BottomNavigationView mBottomNav = getActivity().findViewById(R.id.activity_main_bottomNavigationView);
        mBottomNav.setVisibility(View.GONE);

        String username = mUsername.getText().toString();
        String name = mName.getText().toString();

        String messengerUrl = ""; // get this from fragment

        String password = mPassword.getText().toString();
        String repeatPassword = mRepeatPassword.getText().toString();

        mRegisterButton.setOnClickListener(mView -> {
            // check if input fields are not empty

            // call the api to register
            mAPIService.userRegister(new UserRegisterModel(username, name, messengerUrl, password, repeatPassword)).enqueue(new Callback<UserProfileModel>() {
                @Override
                public void onResponse(Call<UserProfileModel> call, Response<UserProfileModel> response) {
                    if (response.isSuccessful()) {
                        UserProfileModel user = response.body();
                        // save user to shared preferences
                        Utilities.saveLocalUser((AppCompatActivity) getActivity(), user);
                        // launch home fragment
                        MainActivity.changeToFragment((AppCompatActivity) getActivity(),
                                new HomeFragment(), false,
                                "home-from-register");
                    } else {
                        Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<UserProfileModel> call, Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }
}