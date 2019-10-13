package com.group4.togolist.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.group4.togolist.R;
import com.group4.togolist.viewmodel.FirstViewModel;



import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

public class FirstActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * Class do :
     * Created by Group 4 ITI (Eng/Bassen - Eng Fatma - Eng Ali)
     */
    private Button btnSignIN, btnCreateAccount , buttontest;
    private FirstViewModel firstViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        firstViewModel = ViewModelProviders.of(this , new MyViewModelFactory(FirstActivity.this)).get(FirstViewModel.class);
        initComponent();
    }

    /**
     *    set up of  sign in and sign up Buttons
     */
    private void initComponent() {
        btnSignIN = findViewById(R.id.btnSignIn);
        btnCreateAccount = findViewById(R.id.btnSignUp);
        buttontest = findViewById(R.id.buttontest);

        // assigning Events to Buttons
        btnSignIN.setOnClickListener(this);
        btnCreateAccount.setOnClickListener(this);

        /**
         * الكود ده علشان اعمل تست على صفحة add note
         * هنمسحها اما صفحة الرجستر تشتغل
         */
        buttontest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstActivity.this, AddFormActivity.class);
                startActivity(intent);
            }
        });
    }



    /**
     *
     *    Handling events to sign in and sign up Buttons
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSignIn:

               firstViewModel.signIn();
                break;

            case R.id.btnSignUp:

              firstViewModel.createAccount();
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
            return (T) new FirstViewModel(mActivity);
        }
    }
}
