package com.reco.view.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.reco.R;
import com.reco.service.model.UserPasswordChangeModel;
import com.reco.service.repository.APIService;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChangePasswordFragment extends Fragment {
    private APIService apiService;

    public ChangePasswordFragment() {
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView oldPassword = view.findViewById(R.id.fragment_change_password_current_password);
        TextView newPassword = view.findViewById(R.id.fragment_change_password_new_password);
        TextView repeatPassword = view.findViewById(R.id.fragment_change_password_repeat_password);
        AppCompatButton changePasswordButton = view.findViewById(R.id.fragment_change_password_change_password_button);
        NavController navController = Navigation.findNavController(view);

        changePasswordButton.setOnClickListener(view1 -> {
            String oldPass, newPass, repeatPass;
            oldPass = oldPassword.getText().toString();
            newPass = newPassword.getText().toString();
            repeatPass = repeatPassword.getText().toString();

            if (oldPass.isEmpty()) {
                oldPassword.setError(getResources().getString(R.string.field_required));
            } else if (newPass.isEmpty()) {
                newPassword.setError(getResources().getString(R.string.field_required));
            } else if (repeatPass.isEmpty()) {
                repeatPassword.setError(getResources().getString(R.string.field_required));
            } else {
                if (oldPass.equals(newPass)) {
                    Toast.makeText(getContext(), R.string.passwords_cannot_be_same, Toast.LENGTH_SHORT).show();
                } else if (!newPass.equals(repeatPass)) {
                    repeatPassword.setError(getResources().getString(R.string.passwords_do_not_match));
                } else {
                    apiService.changeUserPassword(new UserPasswordChangeModel(oldPass, newPass)).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(@NotNull Call<Void> call, @NotNull Response<Void> response) {
                            if (response.isSuccessful()) {
                                navController.navigateUp();
                                Toast.makeText(getContext(), R.string.password_changed_successfully, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(@NotNull Call<Void> call, @NotNull Throwable t) {
                            Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_password, container, false);
    }
}