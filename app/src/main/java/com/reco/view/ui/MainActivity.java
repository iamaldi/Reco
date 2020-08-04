package com.reco.view.ui;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.reco.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    final boolean LOGGED_IN = true; // test

    public static void changeToFragment(AppCompatActivity activity, Fragment fragment, boolean addToBackStack, String fragmentStackName) {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        if (addToBackStack) {
            transaction.addToBackStack(fragmentStackName);
        }
        transaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav=findViewById( R.id.activity_main_bottomNavigationView );

        // test - use fragment manager with back-stack
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }
            if (LOGGED_IN) {
  //           changeToFragment(this, new SearchFragment(), false, "search-from-home");
                changeToFragment(this, new HomeFragment(), false, null);
            } else {
                changeToFragment(this, new RegisterFragment(), false, null);
            }
        }


        bottomNav.setOnNavigationItemSelectedListener( new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selected=null;

                switch (item.getItemId()){
                    case R.id.navbar_home:
                        selected=new HomeFragment();
                        break;
                    case R.id.navbar_search:
                        selected=new SearchFragment();
                        break;
                    case R.id.navbar_library:
                        selected=new LibraryFragment();
                        break;
                    case R.id.navbar_settings:
                        selected=new RecommendationsFragment();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace( R.id.activity_main_fragment_container,selected ).commit();

                return true;
            }
        } );

        // start splash screen

        // - if not authenticated
        // start login fragment
        // -- if has not account
        // start register fragment

        // - if authenticated
        // start main activity
    }



}