package com.group4.togolist.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.group4.togolist.R;

import androidx.appcompat.app.AppCompatActivity;

public class FirstActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * Class do :
     * Created by Group 4 ITI (Eng/Bassen - Eng Fatma - Eng Ali)
     */
    Button btnSignIN, btnCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        initComponent();
    }

    /**
     *    set up of  sign in and sign up Buttons
     */
    private void initComponent() {
        btnSignIN = findViewById(R.id.btnSignIn);
        btnCreateAccount = findViewById(R.id.btnSignUp);

        // assigning Events to Buttons
        btnSignIN.setOnClickListener(this);
        btnCreateAccount.setOnClickListener(this);
    }

    /**
     *
     *    Handling events to sign in and sign up Buttons
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSignIn:
                Intent SignIN = new Intent(FirstActivity.this, SignInActivity.class);
                startActivity(SignIN);
                break;
            case R.id.btnSignUp:
                Intent SignUP = new Intent(FirstActivity.this, SignUpActivity.class);
                startActivity(SignUP);
                break;
        }
    }
}
