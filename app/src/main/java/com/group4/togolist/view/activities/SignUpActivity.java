package com.group4.togolist.view.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.material.textfield.TextInputLayout;
import com.group4.togolist.R;
import com.group4.togolist.viewmodel.RegisterViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * Class do :
     * Created by Group 4 ITI (Eng/Bassem - Eng Fatma - Eng Ali)
     */

    private RegisterViewModel registerViewModel;
    private Button btnSignUp, btnTermsAndConditions, btnPrivacyPolicy;
    private TextInputLayout eTxtUserName, eTxtEmail, eTxtPassword, eTxtConfirmPassword;
    private ProgressBar progressBar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initComponent();
        registerViewModel = ViewModelProviders.of(this, new MyViewModelFactory(SignUpActivity.this)).get(RegisterViewModel.class);
        SharedPreferences loadDataSetting = getSharedPreferences(SplashActivity.PREFF_NAME,0);
        SharedPreferences.Editor editor = loadDataSetting.edit();
        editor.putBoolean(SplashActivity.TAG_LOAD_DATA,true);
        editor.commit();
    }

    /**
     * getting button and editText objects and assigning Listenter to buttons
     */
    private void initComponent() {

        btnSignUp = findViewById(R.id.btn_sign_up);
        btnTermsAndConditions = findViewById(R.id.btn_Terms_And_Conditions);
        btnPrivacyPolicy = findViewById(R.id.btn_Privacy_Policy);

        eTxtUserName = findViewById(R.id.editText_username_SignUp);
        eTxtEmail = findViewById(R.id.editText_email_Signup);
        eTxtPassword = findViewById(R.id.editText_password_Signup);
        eTxtConfirmPassword = findViewById(R.id.editText_confirmPassword);

        progressBar2 = findViewById(R.id.progressBar2);

        btnSignUp.setOnClickListener(this);
        btnTermsAndConditions.setOnClickListener(this);
        btnPrivacyPolicy.setOnClickListener(this);
    }

//    Cheack if editText Empty

    private boolean validateUsername() {
        String usenameInput = eTxtUserName.getEditText().getText().toString().trim();
        if (usenameInput.isEmpty()) {
            eTxtUserName.setError(getString(R.string.messageempty));
            System.out.println(Integer.toString(R.string.errormessage).trim());
            return false;
        } else if (usenameInput.length() > 15) {
            eTxtUserName.setError(getString(R.string.messagelong));
            return false;
        }else if (usenameInput.length() < 6) {
            eTxtUserName.setError(getString(R.string.messageshort));
            return false;
        } else {
            eTxtUserName.setError(null);
            return true;
        }
    }

    private boolean validateEmail() {
        String emailInput = eTxtEmail.getEditText().getText().toString().trim();
        if (emailInput.isEmpty()) {
            eTxtEmail.setError(getString(R.string.messageempty));
            System.out.println(Integer.toString(R.string.errormessage).trim());
            return false;
        } else {
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

    private boolean validateConfirmPassword() {
        String passwordInput = eTxtConfirmPassword.getEditText().getText().toString().trim();

        if (passwordInput.isEmpty()) {
            eTxtConfirmPassword.setError(getString(R.string.messageempty));
            return false;
        } else {
            eTxtConfirmPassword.setError(null);
            return true;
        }
    }

    /**
     * Handling events to sign up , sign up with google , cancel , terms and conditions , privacy policy Buttons
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sign_up:
                if (!validateUsername() | !validateEmail() | !validatePassword() | !validateConfirmPassword()){
                    return;
                }
                String usename = eTxtUserName.getEditText().getText().toString();
                String email = eTxtEmail.getEditText().getText().toString();
                String password = eTxtPassword.getEditText().getText().toString();
                String confirmPassword = eTxtConfirmPassword.getEditText().getText().toString();
                registerViewModel.signUp(usename,email,password,confirmPassword);
                break;


            case R.id.btn_Terms_And_Conditions:
                registerViewModel.termsAndConditions();

                break;

            case R.id.btn_Privacy_Policy:
                registerViewModel.termsAndConditions();
                break;
        }
    }

    /**
     * to get an Object from RegisterViewModel
     */

    class MyViewModelFactory implements ViewModelProvider.Factory {
        private SignUpActivity mActivity;


        public MyViewModelFactory(SignUpActivity activity) {
            mActivity = activity;
        }


        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new RegisterViewModel(mActivity);
        }
    }
    /**
     *  close edit text and sign up activity
     */
    public void disableLayout(boolean status){
        eTxtUserName.setEnabled(status);
        eTxtEmail.setEnabled(status);
        eTxtPassword.setEnabled(status);
        eTxtConfirmPassword.setEnabled(status);
        btnSignUp.setEnabled(status);
        btnTermsAndConditions.setEnabled(status);
        btnPrivacyPolicy.setEnabled(status);
        if(!status){
            progressBar2.setVisibility(View.VISIBLE);
        }else {progressBar2.setVisibility(View.GONE);}
    }
}
