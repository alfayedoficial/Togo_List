package com.group4.togolist.view.activities;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.group4.togolist.R;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

     /**
     * Class do : run animation plane
     * duration of wait 2 sec until animation finished
     * Created by Group 4 ITI (Eng/Bassem - Eng Fatma - Eng Ali)
     */

    ObjectAnimator objectAnimator;
    ImageView id_splashIcon;

     // Duration of wait
    private final int SPLASH_DISPLAY_LENGTH = 2300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        animatorPlane();
        durationOfWait();
    }

    private void animatorPlane() {
        id_splashIcon = findViewById(R.id.id_splashIcon);
        // code objectAnimator
        objectAnimator = ObjectAnimator.ofFloat(id_splashIcon, "x", 2000);
        objectAnimator.setDuration(4000);
    }

    @Override
    protected void onStart() {
        super.onStart();
        objectAnimator.start();
    }

    private void durationOfWait() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(SplashActivity.this, FirstActivity.class);
                startActivity(mainIntent);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

}
