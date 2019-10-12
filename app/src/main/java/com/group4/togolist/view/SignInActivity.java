package com.group4.togolist.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.group4.togolist.R;
import com.group4.togolist.viewmodel.LoginViewModel;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private LoginViewModel loginViewModel;
    private Button btnCancel , btnForgetPassword , btnSignIn , btnSignInWithGoogle;
    private EditText eTxtEmail , eTxtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        loginViewModel = new LoginViewModel(this);

        initComponent();
    }

    /**
     *  setup sign in , cancel , forgot password , sign in with google buttons and email , password edittext
     */
    private void initComponent(){

        btnSignIn = (Button) findViewById(R.id.btn_sign_in);
        btnCancel = (Button) findViewById(R.id.btn_Cancel);
        btnSignInWithGoogle = (Button) findViewById(R.id.btn_sign_in_with_google);
        btnForgetPassword = (Button) findViewById(R.id.btn_forget_password);

        eTxtEmail = (EditText) findViewById(R.id.editText_email);
        eTxtPassword = (EditText) findViewById(R.id.editText_password);

        btnSignIn.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnSignInWithGoogle.setOnClickListener(this);
        btnForgetPassword.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_sign_in:

                loginViewModel.signIn(eTxtEmail.getText().toString() , eTxtPassword.getText().toString());
                break;

            case R.id.btn_Cancel:

                    loginViewModel.cancel();
                     break;

            case R.id.btn_sign_in_with_google:

                break;

            case R.id.btn_forget_password:

                break;
        }


    }
}
