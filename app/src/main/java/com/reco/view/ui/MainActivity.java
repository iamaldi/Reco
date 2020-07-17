package com.reco.view.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.reco.R;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // test - remove anytime
        TextView testTextView = findViewById(R.id.textView);
        testTextView.setText(R.string.app_name);

        // start splash screen

        // - if not authenticated
        // start login fragment
        // -- if has not account
        // start register fragment

        // - if authenticated
        // start main activity
    }
}