package com.reco.view.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.reco.R;
import com.reco.service.model.UserLoginModel;
import com.reco.service.repository.APIService;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginFragment extends Fragment {

    private final Retrofit mRetrofit;
    private final APIService mAPIService;
    private TextView mUsername, mPassword;
    private Button mLoginButton;

    public LoginFragment() {
        // we have to do this on every fragment -
        // alternatively we could use DI but that takes a LOT more time
        this.mRetrofit = new Retrofit.Builder()
                .baseUrl("https://api.url")
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
        mUsername = view.findViewById(R.id.fragment_login_username);
        mPassword = view.findViewById(R.id.fragment_login_password);
        mLoginButton = view.findViewById(R.id.fragment_login_login_button);

        mLoginButton.setOnClickListener(mView -> {
            String username = mUsername.getText().toString();
            String password = mPassword.getText().toString();

            // debug
            Toast.makeText(getContext(), "LOGIN Button Clicked", Toast.LENGTH_SHORT).show();
            Toast.makeText(getContext(), username, Toast.LENGTH_SHORT).show();
            Toast.makeText(getContext(), password, Toast.LENGTH_SHORT).show();

            // call the api to login the user
            mAPIService.userLogin(new UserLoginModel(username, password)).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    // if successful login show home fragment
                    // otherwise show error message
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
        });

    }
}