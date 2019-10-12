package com.group4.togolist.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.group4.togolist.R;

import androidx.appcompat.app.AppCompatActivity;

public class FirstActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnSignIN, btnCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initComponent();
    }

    private void initComponent() {
        btnSignIN = findViewById(R.id.btn_sign_in);
        btnCreateAccount = findViewById(R.id.btn_create_account);

        // assigning Events to Buttons
        btnSignIN.setOnClickListener(this);
        btnCreateAccount.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_sign_in:
                Intent SignIN = new Intent(FirstActivity.this, SignInActivity.class);
                startActivity(SignIN);
                break;
            case R.id.btn_create_account:
                Intent SignUP = new Intent(FirstActivity.this, SignUpActivity.class);
                startActivity(SignUP);
                break;
        }
    }
}
