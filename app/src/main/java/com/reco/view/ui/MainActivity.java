package com.reco.view.ui;

import android.content.Intent;
import android.os.Bundle;

import com.reco.R;
import com.reco.util.Utilities;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // reset to original theme after splash screen
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // check if user is logged in
        if (Utilities.isUserLoggedIn(this)) {
            // start home activity
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }
    }
}