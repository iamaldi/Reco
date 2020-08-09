package com.reco.view.ui;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.reco.R;
import com.reco.service.model.UserProfileModel;
import com.reco.util.Utilities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    public static void changeToFragment(AppCompatActivity appCompatActivity, Fragment fragment, boolean addToBackStack, String fragmentTag) {
        FragmentTransaction transaction = appCompatActivity.getSupportFragmentManager().beginTransaction();
        // check if the calling fragment is the same as the one already visible
        // fragment already on backStack
        if (appCompatActivity.getSupportFragmentManager().findFragmentByTag(fragmentTag) != null) {
            // pop it
            appCompatActivity.getSupportFragmentManager().popBackStack();
        } else {
            transaction.replace(R.id.activity_main_fragment_container, fragment, fragmentTag);
            if (addToBackStack) {
                transaction.addToBackStack(fragmentTag);
            }
            transaction.commit();
        }
    }

    public static void removeAllFragments(AppCompatActivity appCompatActivity) {
        appCompatActivity.getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // reset to original theme after splash screen
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: check if this is necessary - and if so- where is best to be placed
        if (findViewById(R.id.activity_main_fragment_container) != null) {
            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }
        }

        // check if user is logged in
        UserProfileModel user = Utilities.getLocalUser(this);

        // we have a local copy of the user
        if (user != null) {
            // add navigation menu
            BottomNavigationView bottomNav = findViewById(R.id.activity_main_bottomNavigationView);
            bottomNav.setOnNavigationItemSelectedListener(item -> {
                switch (item.getItemId()) {
                    case R.id.navbar_home:
                        changeToFragment(this, new HomeFragment(),
                                true, "home-fragment");
                        break;
                    case R.id.navbar_search:
                        changeToFragment(this, new SearchFragment(),
                                true, "search-fragment");
                        break;
                    case R.id.navbar_library:
                        changeToFragment(this, new LibraryFragment(),
                                true, "library-fragment");
                        break;
                    case R.id.navbar_recommendations:
                        changeToFragment(this, new RecommendationsFragment(),
                                true, "recommendations-fragment");
                        break;
                }

                return true;
            });

            // show home fragment
            changeToFragment(this, new HomeFragment(),
                    false, "home-fragment");
        } else {
            // point to login fragment
            changeToFragment(this, new LoginFragment(), false, "login-fragment");
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