package com.reco.view.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.reco.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    final boolean LOGGED_IN = true; // test

    public static void changeToFragment(AppCompatActivity activity, Fragment fragment, boolean addToBackStack, String fragmentStackName) {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main_fragment_container, fragment);
        if (addToBackStack) {
            transaction.addToBackStack(fragmentStackName);
        }
        transaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BottomNavigationView bottomNav = findViewById(R.id.activity_main_bottomNavigationView);

        bottomNav.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navbar_home:
                    changeToFragment(this, new HomeFragment(),
                            true, "home-from-nav");
                    break;
                case R.id.navbar_search:
                    changeToFragment(this, new SearchFragment(),
                            true, "search-from-nav");
                    break;
                case R.id.navbar_library:
                    changeToFragment(this, new LibraryFragment(),
                            true, "library-from-nav");
                    break;
                case R.id.navbar_settings:
                    changeToFragment(this, new RecommendationsFragment(),
                            true, "recommendations-from-nav");
                    break;
            }

            return true;
        });

        if (findViewById(R.id.activity_main_fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }
            if (LOGGED_IN) {
                changeToFragment(this, new HomeFragment(), false, null);
            } else {
                changeToFragment(this, new LoginFragment(), false, null);
            }
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