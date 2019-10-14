package com.group4.togolist.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.group4.togolist.R;

public class ProfileActivity extends AppCompatActivity  implements View.OnClickListener {

    private EditText eTxtUserName , eTxtEmail  , eTxtPassword , eTxtConfirmPassword;
    private Button btnEdit , btnLogout , btnUpdate;
    private TextView txtHopeComeBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initComponent();
    }

    private void initComponent() {

        eTxtUserName = findViewById(R.id.editTextPofileUsername);
        eTxtEmail = findViewById(R.id.editTextPofileEmail);
        eTxtPassword = findViewById(R.id.editTextPofilePassword);
        eTxtConfirmPassword = findViewById(R.id.editTextPofileConfirmPassword);
        txtHopeComeBack = findViewById(R.id.textView10);

        btnEdit = findViewById(R.id.btnEdit);
        btnLogout = findViewById(R.id.btnLogout);
        btnUpdate = findViewById(R.id.btnProfileUpdate);

        btnEdit.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnLogout.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnEdit:
                eTxtPassword.setVisibility(View.VISIBLE);
                eTxtConfirmPassword.setVisibility(View.VISIBLE);
                btnUpdate.setVisibility(View.VISIBLE);
                btnEdit.setVisibility(View.GONE);
                btnLogout.setVisibility(View.GONE);
                txtHopeComeBack.setVisibility(View.GONE);
                break;
            case R.id.btnProfileUpdate:
                // updte code
                afterUpdate();
                break;
            case R.id.btnLogout:
                // Logout code
                break;
        }
    }
   private void afterUpdate(){
       eTxtPassword.setVisibility(View.GONE);
       eTxtConfirmPassword.setVisibility(View.GONE);
       btnUpdate.setVisibility(View.GONE);

       btnEdit.setVisibility(View.VISIBLE);
       btnLogout.setVisibility(View.VISIBLE);
       txtHopeComeBack.setVisibility(View.VISIBLE);
    }

    public void setUserName(String userName){
        eTxtUserName.setText(userName);
    }

    public void setEmail(String email){
        eTxtEmail.setText(email);
    }
    public void setPassword(String password){
        eTxtPassword.setText(password);
    }

    public void setConfirmedPassword(String confirmedPassword){
        eTxtConfirmPassword.setText(confirmedPassword);
    }
}
