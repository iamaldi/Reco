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
import com.reco.util.Utilities;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


public class SettingsFragment extends Fragment {
    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            return;
        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button mChangePasswordButton = view.findViewById(R.id.fragment_settings_button_change_password);
        TextView mName = view.findViewById(R.id.fragment_settings_editText_name);
        TextView mMessengerURL = view.findViewById(R.id.fragment_settings_editText_messenger_url);

        UserProfileModel user = Utilities.getLocalUser((AppCompatActivity) Objects.requireNonNull(getActivity()));

        if (user != null) {
            mName.setText(user.getDisplayName());
            mMessengerURL.setText(user.getMessengerUrl());
        }

        mChangePasswordButton.setOnClickListener(mView -> {
            Toast.makeText(mView.getContext(), "Change Password", Toast.LENGTH_SHORT).show();

            MainActivity.changeToFragment((AppCompatActivity) getActivity(),
                    new ChangePasswordFragment(), true,
                    "change-password-from-settings");
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setings, container, false);
    }
}