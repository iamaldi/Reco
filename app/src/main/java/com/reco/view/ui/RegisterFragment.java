package com.reco.view.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.reco.R;
import com.reco.service.model.UserRegisterModel;
import com.reco.service.repository.APIService;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterFragment extends Fragment {
    private final Retrofit mRetrofit;
    private final APIService mAPIService;
    private TextView mName, mUsername, mPassword, mRepeatPassword;
    private Button mRegisterButton;

    public RegisterFragment() {
        // we have to do this on every fragment -
        // alternatively we could use DI but that takes a LOT more time
        this.mRetrofit = new Retrofit.Builder()
                .baseUrl("https://api.url")
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

        String name = mName.getText().toString();
        String username = mUsername.getText().toString();
        String password = mPassword.getText().toString();
        String repeatPassword = mRepeatPassword.getText().toString();

        mRegisterButton.setOnClickListener(mView -> {
            Toast.makeText(getContext(), "SIGN UP", Toast.LENGTH_SHORT).show();

            // call the api to register the user
            mAPIService.userRegister(new UserRegisterModel(name, username, password, repeatPassword)).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    // if user register successful launch home fragment
                    // otherwise display error
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    // no internet or things went South
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