package com.reco.view.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.reco.R;


public class SetingsFragment extends Fragment {

    private Button mChangePasswordButton;


    public SetingsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );

        mChangePasswordButton=view.findViewById( R.id.fragment_settings_button_change_password );


        mChangePasswordButton.setOnClickListener(mView -> {
            Toast.makeText(mView.getContext(), "Change Password", Toast.LENGTH_SHORT).show();

            MainActivity.changeToFragment((AppCompatActivity) getActivity(),
                    new ChangePasswordFragment(), true,
                    "change password-from-settings");

        });

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate( R.layout.fragment_setings, container, false );
    }
}