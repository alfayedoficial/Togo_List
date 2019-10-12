package com.group4.togolist.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.group4.togolist.R;

import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity implements View.OnClickListener {
    Button btn_SignIN, btn_createaccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initComponent();
    }

    private void initComponent() {
        btn_SignIN = findViewById(R.id.btn_SignIN);
        btn_createaccount = findViewById(R.id.btn_createaccount);

        // assigning Events to Buttons
        btn_SignIN.setOnClickListener(this);
        btn_createaccount.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_SignIN:
                Intent SignIN = new Intent(Home.this, com.group4.togolist.view.SignIN.class);
                startActivity(SignIN);
                break;
            case R.id.btn_createaccount:
                Intent SignUP = new Intent(Home.this, com.group4.togolist.view.SignUP.class);
                startActivity(SignUP);
                break;
        }
    }
}
