package com.group4.togolist.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.group4.togolist.R;

public class PrivacyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
