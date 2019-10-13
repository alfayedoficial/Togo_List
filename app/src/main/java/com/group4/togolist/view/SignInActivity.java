package com.group4.togolist.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.group4.togolist.R;
import com.group4.togolist.viewmodel.LoginViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;


public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * Class do :
     * Created by Group 4 ITI (Eng/Bassen - Eng Fatma - Eng Ali)
     */

    private LoginViewModel loginViewModel;
    private Button btnCancel, btnForgetPassword, btnSignIn, btnSignInWithGoogle, btn_forget_password;
    private EditText eTxtEmail, eTxtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        loginViewModel = ViewModelProviders.of(this, new MyViewModelFactory(SignInActivity.this)).get(LoginViewModel.class);

        initComponent();
    }

    /**
     * setup sign in , cancel , forgot password , sign in with google buttons and email , password edittext
     */
    private void initComponent() {

        btnSignIn = findViewById(R.id.btn_sign_up);
        btnCancel = findViewById(R.id.btn_Cancel_Signin);
        btnSignInWithGoogle = findViewById(R.id.btn_sign_up_with_google);
        btnForgetPassword = findViewById(R.id.btn_forget_password);

        eTxtEmail = findViewById(R.id.editText_email_Signin);
        eTxtPassword = findViewById(R.id.editText_password_Signin);

        btnSignIn.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnSignInWithGoogle.setOnClickListener(this);
        btnForgetPassword.setOnClickListener(this);


    }


    /**
     * Handling Button events
     */

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_sign_up:

                loginViewModel.signIn(eTxtEmail.getText().toString(), eTxtPassword.getText().toString());
                break;

            case R.id.btn_Cancel_Signin:

                loginViewModel.cancel();
                break;

            case R.id.btn_sign_up_with_google:
                loginViewModel.signInWithGoogle();
                break;

            case R.id.btn_forget_password:
                loginViewModel.forgetPassword();
                break;
        }


    }

    class MyViewModelFactory implements ViewModelProvider.Factory {
        private Activity mActivity;


        public MyViewModelFactory(Activity activity) {
            mActivity = activity;
        }


        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new LoginViewModel(mActivity);
        }
    }
}
