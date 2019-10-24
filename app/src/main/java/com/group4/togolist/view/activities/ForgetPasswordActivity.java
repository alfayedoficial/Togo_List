package com.group4.togolist.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.group4.togolist.R;
import com.group4.togolist.viewmodel.ForgetPassViewModel;

public class ForgetPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * Class do :
     * Created by Group 4 ITI (Eng/Bassem - Eng Fatma - Eng Ali)
     */

    private ForgetPassViewModel forgetPassViewModel;
    private Button btnResetPassword , btnCancel;
    private TextView eTxtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        initComponent();
        forgetPassViewModel = ViewModelProviders.of(this , new MyViewModelFactory(ForgetPasswordActivity.this)).get(ForgetPassViewModel.class);


    }

    private void initComponent() {

        btnResetPassword = findViewById(R.id.btn_reset_password);
        btnCancel = findViewById(R.id.btn_Cancel_forgetPass);

        eTxtPassword = findViewById(R.id.editText_email_forgetPass);

        btnResetPassword.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btn_reset_password:

                forgetPassViewModel.sendEmail(eTxtPassword.getText().toString());
                break;

            case R.id.btn_Cancel_forgetPass:

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
            return (T) new ForgetPassViewModel(mActivity);
        }
    }
}
