package com.reco.view.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.reco.R;
import com.reco.service.model.UserProfileModel;
import com.reco.service.repository.APIService;
import com.reco.util.Utilities;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SettingsFragment extends Fragment {
    private APIService apiService;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            return;
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.API_URL))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(APIService.class);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView mName = view.findViewById(R.id.fragment_settings_editText_name);
        TextView mMessengerURL = view.findViewById(R.id.fragment_settings_editText_messenger_url);
        Button mChangePasswordButton = view.findViewById(R.id.fragment_settings_button_change_password);
        Button mLogoutButton = view.findViewById(R.id.fragment_settings_logout_button);

        UserProfileModel user = Utilities.getLocalUser((AppCompatActivity) Objects.requireNonNull(getActivity()));

        // check if we have a copy of our data locally
        if (user != null) {
            mName.setText(user.getDisplayName());
            mMessengerURL.setText(user.getMessengerUrl());
        } else {
            // get user from api
            apiService.getUserProfile().enqueue(new Callback<UserProfileModel>() {
                @Override
                public void onResponse(@NotNull Call<UserProfileModel> call, @NotNull Response<UserProfileModel> response) {
                    if (response.isSuccessful()) {
                        UserProfileModel userProfile = response.body();
                        if (userProfile != null) {
                            // save user locally
                            Utilities.saveLocalUser((AppCompatActivity) Objects.requireNonNull(getActivity()), userProfile);
                            // update the fields
                            mName.setText(userProfile.getDisplayName());
                            mMessengerURL.setText(userProfile.getMessengerUrl());
                        }
                    } else {
                        Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<UserProfileModel> call, Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        mChangePasswordButton.setOnClickListener(mView -> {
            MainActivity.changeToFragment((AppCompatActivity) getActivity(),
                    new ChangePasswordFragment(), true,
                    "change-password-from-settings");
        });

        mLogoutButton.setOnClickListener(view2 -> {
            // call api to logout
            apiService.logoutUser().enqueue(new Callback<Void>() {
                @Override
                public void onResponse(@NotNull Call<Void> call, @NotNull Response<Void> response) {
                    if (response.isSuccessful()) {
                        // clear user object locally
                        Utilities.deleteLocalUser((AppCompatActivity) Objects.requireNonNull(getActivity()));
                        // TODO: clear all previous fragments
                        MainActivity.changeToFragment((AppCompatActivity) getActivity(),
                                new LoginFragment(), false,
                                "login-from-settings-logout");
                    } else {
                        Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<Void> call, @NotNull Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setings, container, false);
    }
}