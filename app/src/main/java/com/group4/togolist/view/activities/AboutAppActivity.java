package com.group4.togolist.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.group4.togolist.R;

public class AboutAppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutapp);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
