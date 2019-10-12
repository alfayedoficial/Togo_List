package com.group4.togolist.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.group4.togolist.view.MainActivity;

public class LoginViewModel extends ViewModel {

    public static final int ACCESS_DENIED = 2001;
    public static final int ACCESS_GRANTED = 2002;

    private Activity loginActivity;


    /**
     * Login View Model Constructor Method it takes the activity as input parameter
     */
    public LoginViewModel(Activity loginActivity){
        this.loginActivity = loginActivity;
    }

    /**
     * this method called when Sign in Button in the Log In activity, it check firebase for your email address
     */
    public void signIn(String username, String password){
        if(username != null && !username.isEmpty() && password != null && !password.isEmpty()){

        }
        else {
            Toast.makeText(loginActivity, "Please Enter your Username and Password", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * this method called when Cancel Button in the Log In activity, it takes you back to MainActivity
     */
    public void cancel(){
        Intent intent = new Intent(loginActivity, MainActivity.class);
        loginActivity.startActivity(intent);
    }

}
