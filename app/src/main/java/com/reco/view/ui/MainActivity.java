package com.reco.view.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.widget.TextView;

import com.reco.R;

public class MainActivity extends AppCompatActivity {

    final boolean TEST_LOGGED_IN = true;
    final boolean TEST_LOGGED_OUT = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // test - remove anytime
//        TextView testTextView = findViewById(R.id.textView);
//        testTextView.setText(R.string.app_name);

        // start splash screen

        // - if not authenticated
        // start login fragment
        // -- if has not account
        // start register fragment

        // - if authenticated
        // start main activity

        Fragment mSearchFragment = null;
        mSearchFragment = new SearchFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frameLayout, mSearchFragment).commit();
    }
}