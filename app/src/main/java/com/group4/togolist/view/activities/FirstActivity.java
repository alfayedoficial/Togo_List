package com.group4.togolist.view.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.group4.togolist.R;
import com.group4.togolist.view.adapters.MyViewPagerHowToUseAdapeter;
import com.group4.togolist.viewmodel.FirstViewModel;



import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class FirstActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * Class do :
     * Created by Group 4 ITI (Eng/Bassem - Eng Fatma - Eng Ali)
     */
    private Button btnSignIN, btnCreateAccount  ;
    private FirstViewModel firstViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        initComponent();
        firstViewModel = ViewModelProviders.of(this , new MyViewModelFactory(FirstActivity.this)).get(FirstViewModel.class);
        firstViewModel.checkFirebaseUser();


        // SharedPreferences method
        firstAppStartOnlyMethod();

    }
    /**
     * this method doing switch method viewPagerMethod when Start App First Once
     */

    private void firstAppStartOnlyMethod() {
        SharedPreferences preferences = getSharedPreferences("prefs",MODE_PRIVATE);
        Boolean firstStart = preferences.getBoolean("firstStart",true);
        if (firstStart){
            viewPagerMethod();
        }
    }

    /**
     * this method doing viewPager How To Use App
     */

    private void viewPagerMethod() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
//        CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.indicator);

        PagerAdapter adapter = new MyViewPagerHowToUseAdapeter(this);
        viewPager.setAdapter(adapter);
//        indicator.setViewPager(viewPager);

        // SharedPreferences
        SharedPreferences preferences = getSharedPreferences("prefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("firstStart",false);
        editor.commit();
    }

    /**
     *    set up of  sign in and sign up Buttons
     */
    private void initComponent() {
        btnSignIN = findViewById(R.id.btnSignIn);
        btnCreateAccount = findViewById(R.id.btnSignUp);

        // assigning Events to Buttons
        btnSignIN.setOnClickListener(this);
        btnCreateAccount.setOnClickListener(this);


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
