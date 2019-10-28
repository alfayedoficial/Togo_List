package com.group4.togolist.view.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.group4.togolist.R;
import com.group4.togolist.viewmodel.LoginViewModel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import static com.group4.togolist.repository.FirebaseHandler.GOOGLE_SIGNIN;


public class SignInActivity extends AppCompatActivity implements View.OnClickListener {


    /**
     * Class do :
     * Created by Group 4 ITI (Eng/Bassem - Eng Fatma - Eng Ali)
     */

    private LoginViewModel loginViewModel;
    private Button btnForgetPassword, btnSignIn, btnSignInWithGoogle;
    private TextInputLayout eTxtEmail, eTxtPassword;
    private static final String TAG = "SignInActivity";
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initComponent();
        loginViewModel = ViewModelProviders.of(this, new MyViewModelFactory(SignInActivity.this)).get(LoginViewModel.class);
        SharedPreferences loadDataSetting = getSharedPreferences(SplashActivity.PREFF_NAME,0);
        SharedPreferences.Editor editor = loadDataSetting.edit();
        editor.putBoolean(SplashActivity.TAG_LOAD_DATA,true);
        editor.commit();
    }

    /**
     * setup sign in , cancel , forgot password , sign in with google buttons and email , password edittext
     */
    private void initComponent() {

        btnSignIn = findViewById(R.id.btn_sign_in);
        btnSignInWithGoogle = findViewById(R.id.btn_sign_in_with_google);
        btnForgetPassword = findViewById(R.id.btn_forget_password);

        eTxtEmail = findViewById(R.id.editText_email_Signin);
        eTxtPassword = findViewById(R.id.editText_password_Signin);
        progressBar = findViewById(R.id.progressBar);

        btnSignIn.setOnClickListener(this);
        btnSignInWithGoogle.setOnClickListener(this);
        btnForgetPassword.setOnClickListener(this);


    }

//    Cheack if editText Empty
    private boolean validateEmail(){
        String emailInput = eTxtEmail.getEditText().getText().toString().trim();
        if(emailInput.isEmpty()){
            eTxtEmail.setError(getString(R.string.messageempty));
            System.out.println(Integer.toString(R.string.errormessage).trim());
            return false;
        }else {
            eTxtEmail.setError(null);
            return true;
        }
    }
    private boolean validatePassword() {
        String passwordInput = eTxtPassword.getEditText().getText().toString().trim();

        if (passwordInput.isEmpty()) {
            eTxtPassword.setError(getString(R.string.messageempty));
            return false;
        } else {
            eTxtPassword.setError(null);
            return true;
        }
    }


    /**
     * Handling Button events
     */

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_sign_in:
                if (!validateEmail() | !validatePassword()){
                    return;
                }
                String email = eTxtEmail.getEditText().getText().toString();
                String password = eTxtPassword.getEditText().getText().toString();
                loginViewModel.signIn(email,password );
                break;


            case R.id.btn_sign_in_with_google:
                loginViewModel.signInWithGoogle();

                break;

            case R.id.btn_forget_password:
                loginViewModel.forgetPassword();
                break;
        }


    }

    class MyViewModelFactory implements ViewModelProvider.Factory {
        private SignInActivity mActivity;


        public MyViewModelFactory(SignInActivity activity) {
            mActivity = activity;
        }


        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new LoginViewModel(mActivity);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (resultCode == GOOGLE_SIGNIN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null){
                    loginViewModel.firebaseSignInWithGoogle(account);                }
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);

            }
        }
    }
    public void disableLayout(boolean status){
        eTxtEmail.setEnabled(status);
        eTxtPassword.setEnabled(status);
        btnSignIn.setEnabled(status);
        btnSignInWithGoogle.setEnabled(status);
        btnForgetPassword.setEnabled(status);
        if(!status){
            progressBar.setVisibility(View.VISIBLE);
        }else {progressBar.setVisibility(View.GONE);}
    }
}
