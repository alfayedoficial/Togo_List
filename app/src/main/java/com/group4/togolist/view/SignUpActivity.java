package com.group4.togolist.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.group4.togolist.R;
import com.group4.togolist.viewmodel.LoginViewModel;
import com.group4.togolist.viewmodel.RegisterViewModel;

public class SignUpActivity extends AppCompatActivity  implements View.OnClickListener {
    /**
     * Class do :
     * Created by Group 4 ITI (Eng/Bassen - Eng Fatma - Eng Ali)
     */

    private RegisterViewModel registerViewModel;
    private Button btnSignUp , btnSignUpWithGoogle  , btnTermsAndConditions , btnPrivacyPolicy;
    private EditText eTxtUserName , eTxtEmail , eTxtPassword , eTxtConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        registerViewModel = ViewModelProviders.of(this, new MyViewModelFactory(SignUpActivity.this)).get(RegisterViewModel.class);

        initComponent();

    }

    /**
     *  getting button and editText objects and assigning Listenter to buttons
     */
    private void initComponent() {

        btnSignUp = findViewById(R.id.btn_sign_up);
        btnSignUpWithGoogle = findViewById(R.id.btn_sign_up_with_google);
        btnTermsAndConditions = findViewById(R.id.btn_Terms_And_Conditions);
        btnPrivacyPolicy = findViewById(R.id.btn_Privacy_Policy);

        eTxtUserName = findViewById(R.id.editText_username_SignUp);
        eTxtEmail = findViewById(R.id.editText_email_Signup);
        eTxtPassword = findViewById(R.id.editText_password_Signup);
        eTxtConfirmPassword = findViewById(R.id.editText_confirmPassword);

        btnSignUp.setOnClickListener(this);
        btnSignUpWithGoogle.setOnClickListener(this);
        btnTermsAndConditions.setOnClickListener(this);
        btnPrivacyPolicy.setOnClickListener(this);
    }

    /**
     *
     *  Handling events to sign up , sign up with google , cancel , terms and conditions , privacy policy Buttons
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_sign_up:

                registerViewModel.signUp(eTxtUserName.getText().toString(), eTxtEmail.getText().toString(),eTxtPassword.getText().toString(),eTxtConfirmPassword.getText().toString());
                break;

            case R.id.btn_sign_up_with_google:

                break;


            case R.id.btn_Terms_And_Conditions:

                break;

            case R.id.btn_Privacy_Policy:

                break;
        }
    }

    /**
     *  to get an Object from RegisterViewModel
     */

    class MyViewModelFactory implements ViewModelProvider.Factory {
        private Activity mActivity;


        public MyViewModelFactory(Activity activity) {
            mActivity = activity;
        }


        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new RegisterViewModel(mActivity);
        }
    }
}
