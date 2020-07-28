package com.reco.view.ui;

import android.os.Bundle;

import com.reco.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    final boolean LOGGED_IN = true; // test

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // test - use fragment manager with back-stack
        if (LOGGED_IN) {
            RecommendationsFragment mHomeFragment = new RecommendationsFragment();

            transaction.replace(R.id.fragment_container, mHomeFragment);
//            transaction.addToBackStack(null);

            transaction.commit(); // not very effective
        } else {
            RegisterFragment mLoginFragment = new RegisterFragment();

            transaction.replace(R.id.fragment_container, mLoginFragment);
            transaction.addToBackStack(null);

            transaction.commit(); // not very effective
        }


        // start splash screen

        // - if not authenticated
        // start login fragment
        // -- if has not account
        // start register fragment

        // - if authenticated
        // start main activity
    }
}