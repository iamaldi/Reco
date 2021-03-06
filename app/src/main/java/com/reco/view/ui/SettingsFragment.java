package com.reco.view.ui;

import android.content.Intent;
import android.net.Uri;
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
import com.reco.service.repository.APIService;
import com.reco.util.Utilities;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import de.hdodenhof.circleimageview.CircleImageView;
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
        CircleImageView profileImage = view.findViewById(R.id.fragment_settings_profile_circleimage);
        TextView displayName = view.findViewById(R.id.fragment_settings_name_edit_text);
        TextView messengerURL = view.findViewById(R.id.fragment_settings_messenger_url_edit_text);
        Button changePasswordButton = view.findViewById(R.id.fragment_settings_change_password_button);
        AppCompatImageButton backButton = view.findViewById(R.id.fragment_settings_back_button);
        AppCompatImageButton editProfileButton = view.findViewById(R.id.fragment_settings_edit_profile_button);
        Button deleteAccountButton = view.findViewById(R.id.fragment_settings_delete_account_button);
        Button logoutButton = view.findViewById(R.id.fragment_settings_logout_button);

        BottomNavigationView bottomNavigationMenu = Objects.requireNonNull(getActivity()).findViewById(R.id.activity_main_bottomNavigationView);
        NavController navController = Navigation.findNavController(view);

        // hide bottom navigation menu
        bottomNavigationMenu.setVisibility(View.GONE);

        // check if we have a copy of our data locally
        UserProfileModel user = Utilities.getLocalUser((AppCompatActivity) Objects.requireNonNull(getActivity()));
        if (user != null) {
            if (user.getImgUrl() != null)
                profileImage.setImageURI(Uri.parse(user.getImgUrl()));
            displayName.setText(user.getDisplayName());
            messengerURL.setText(user.getMessengerUrl());
        } else {
            // get user from api
            apiService.getUserProfile().enqueue(new Callback<UserProfileModel>() {
                @Override
                public void onResponse(@NotNull Call<UserProfileModel> call, @NotNull Response<UserProfileModel> response) {
                    if (response.isSuccessful()) {
                        UserProfileModel userProfile = response.body();
                        if (userProfile != null) {
                            if (getActivity() != null) {
                                // save user locally
                                Utilities.saveLocalUser((AppCompatActivity) Objects.requireNonNull(getActivity()), userProfile);
                                // update the fields
                                displayName.setText(userProfile.getDisplayName());
                                messengerURL.setText(userProfile.getMessengerUrl());
                            }
                        }
                    } else {
                        Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<UserProfileModel> call, @NotNull Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        backButton.setOnClickListener(view1 -> {
            backButton.setEnabled(false);
            navController.navigateUp();
        });
        editProfileButton.setOnClickListener(view2 -> {
            editProfileButton.setEnabled(false);
            navController.navigate(R.id.action_settingsFragment_to_updateProfileFragment2);
        });

        changePasswordButton.setOnClickListener(view3 -> {
            changePasswordButton.setEnabled(false);
            navController.navigate(R.id.action_settingsFragment_to_changePasswordFragment);
        });

        deleteAccountButton.setOnClickListener(view4 -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.delete_account_dialog_title)
                    .setMessage(R.string.delete_account_dialog_message)
                    .setPositiveButton(R.string.delete_account_dialog_option_cancel, (dialog1, id) -> {
                        // cancel - do nothing
                    }).setNegativeButton(R.string.delete_account_dialog_option_delete, (dialog2, id) -> {
                // call api to delete user
                apiService.deleteUserProfile().enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(@NotNull Call<Void> call, @NotNull Response<Void> response) {
                        if (response.isSuccessful()) {
                            if (getActivity() != null) {
                                Utilities.setLoggedInStatus((AppCompatActivity) getActivity(), false);
                                // delete every bit of local data
                                Utilities.clearLocalData((AppCompatActivity) Objects.requireNonNull(getActivity()));
                                // show message then main activity / login
                                Toast.makeText(getContext(), R.string.account_deleted, Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getActivity(), MainActivity.class));
                                getActivity().finish();
                            }
                        } else {
                            // couldn't delete account for some reason
                            Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<Void> call, @NotNull Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }).create().show();
        });

        logoutButton.setOnClickListener(view4 -> {
            logoutButton.setEnabled(false);
            // call api to logout
            apiService.logoutUser().enqueue(new Callback<Void>() {
                @Override
                public void onResponse(@NotNull Call<Void> call, @NotNull Response<Void> response) {
                    if (response.isSuccessful()) {
                        if (getActivity() != null) {
                            Utilities.setLoggedInStatus((AppCompatActivity) getActivity(), false);
                            // start main activity / login
                            startActivity(new Intent(getActivity(), MainActivity.class));
                            getActivity().finish();
                        }
                    } else {
                        logoutButton.setEnabled(true);
                        Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<Void> call, @NotNull Throwable t) {
                    logoutButton.setEnabled(true);
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