package com.reco.view.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.reco.R;
import com.reco.service.model.UserProfileModel;
import com.reco.service.model.UserProfileUpdateModel;
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

public class UpdateProfileFragment extends Fragment {
    private APIService apiService;

    public UpdateProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            return;
        }
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.API_URL))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = mRetrofit.create(APIService.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageButton cancelButton, applyChangesButton;
        Button changePhotoButton;
        EditText displayName, messengerURL;

        cancelButton = view.findViewById(R.id.fragment_update_profile_cancel_button);
        applyChangesButton = view.findViewById(R.id.fragment_update_profile_apply_changes_button);
        changePhotoButton = view.findViewById(R.id.fragment_update_profile_change_photo_button);
        displayName = view.findViewById(R.id.fragment_update_profile_name_edit_text);
        messengerURL = view.findViewById(R.id.fragment_update_profile_messenger_url_edit_text);

        NavController navController = Navigation.findNavController(view);

        // populate fields with local user profile data
        UserProfileModel user = Utilities.getLocalUser((AppCompatActivity) Objects.requireNonNull(getActivity()));

        if (user != null) {
            displayName.setText(user.getDisplayName());
            messengerURL.setText(user.getMessengerUrl());
        }

        cancelButton.setOnClickListener(view2 -> {
            cancelButton.setEnabled(false);
            navController.navigateUp();
        });

        applyChangesButton.setOnClickListener(view3 -> {
            // check for empty fields
            String userDisplayName = displayName.getText().toString();
            String userMessengerURL = messengerURL.getText().toString();

            if (userDisplayName.isEmpty()) {
                displayName.setError(getResources().getString(R.string.field_required));
            } else {
                // disable the button once passed checks - fixes null pointer exception
                applyChangesButton.setEnabled(false);
                UserProfileUpdateModel updateUser = new UserProfileUpdateModel(userDisplayName, null, userMessengerURL);
                // call api to update user
                apiService.updateUserProfile(updateUser).enqueue(new Callback<UserProfileModel>() {
                    @Override
                    public void onResponse(@NotNull Call<UserProfileModel> call, @NotNull Response<UserProfileModel> response) {
                        if (response.isSuccessful()) {
                            if (getActivity() != null) {
                                UserProfileModel userProfile = response.body();
                                // update current local user
                                Utilities.saveLocalUser((AppCompatActivity) Objects.requireNonNull(getActivity()), userProfile);
                                Toast.makeText(getContext(), R.string.profile_updated_successfully, Toast.LENGTH_SHORT).show();
                                navController.navigateUp();
                            }
                        } else {
                            // enable button if server returned an error
                            applyChangesButton.setEnabled(true);
                            Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<UserProfileModel> call, @NotNull Throwable t) {
                        // enable button if server returned an error
                        applyChangesButton.setEnabled(true);
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        changePhotoButton.setOnClickListener(view4 -> {
            Toast.makeText(getContext(), "Change photo", Toast.LENGTH_SHORT).show();
        });
    }
}