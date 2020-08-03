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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav=findViewById( R.id.activity_main_bottomNavigationView );


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // test - use fragment manager with back-stack
        if (LOGGED_IN) {
            HomeFragment mHomeFragment = new HomeFragment();

            transaction.replace(R.id.activity_main_fragment_container, mHomeFragment);
//            transaction.addToBackStack(null);

            transaction.commit(); // not very effective
        } else {
            RegisterFragment mRegisterFragment = new RegisterFragment();

            transaction.replace(R.id.activity_main_fragment_container, mRegisterFragment);
//            transaction.addToBackStack(null);

            transaction.commit(); // not very effective
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