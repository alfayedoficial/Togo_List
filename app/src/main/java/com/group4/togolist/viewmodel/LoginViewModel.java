package com.group4.togolist.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.group4.togolist.repository.DatabaseHandler;
import com.group4.togolist.model.User;
import com.group4.togolist.view.ForgetPasswordActivity;
import com.group4.togolist.view.HomeActivity;
import com.group4.togolist.repository.FirebaseHandler;
import com.group4.togolist.view.FirstActivity;

/**
 * Class do : Handle Sign In Activity
 * Created by Group 4 ITI (Eng/Bassen - Eng Fatma - Eng Ali)
 */


public class LoginViewModel extends ViewModel {



    private Activity loginActivity;
    private FirebaseHandler firebaseHandler;
    private DatabaseHandler databaseHandler;
    User user;
    /**
     * Login View Model Constructor Method it takes the activity as input parameter
     */
    public LoginViewModel(Activity loginActivity){
        this.loginActivity = loginActivity;
        firebaseHandler = new FirebaseHandler(loginActivity,this);
        databaseHandler = new DatabaseHandler(loginActivity);
    }

    /**
     * this method called when Sign in Button in the Log In activity, it check firebase for your email address
     */
    public void signIn(String username, String password){
        if(username != null && !username.isEmpty() && password != null && !password.isEmpty()){
            firebaseHandler.signIn(username,password);
            user = User.getUserInstance(username,username,password);
        }
        else {
            Toast.makeText(loginActivity, "Please Enter your Username and Password", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     *  Go To Home Screen
     */
    public void loginToHomeScreen(int loginResult){
        if(loginResult == FirebaseHandler.ACCESS_GRANTED){
            Intent loginIntent = new Intent(loginActivity, HomeActivity.class);
            loginActivity.startActivity(loginIntent);
        }else{
            Toast.makeText(loginActivity, "Your Email or Password is incorrect", Toast.LENGTH_SHORT).show();
            user = null;
        }
    }

    /**
     * this method called when Cancel Button in the Log In activity, it takes you back to SplashActivity
     */
    public void cancel(){
        Intent intent = new Intent(loginActivity, FirstActivity.class);
        loginActivity.startActivity(intent);
    }

    public void signInWithGoogle(){

    }

    public void forgetPassword(){
        Intent intent = new Intent(loginActivity, ForgetPasswordActivity.class);
        loginActivity.startActivity(intent);
    }

}
