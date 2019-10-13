package com.group4.togolist.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.group4.togolist.view.HomeActivity;
import com.group4.togolist.repository.FirebaseHandler;
import com.group4.togolist.view.FirstActivity;

/**
 * Class do : Handle Sign up Activity
 * Created by Group 4 ITI (Eng/Bassen - Eng Fatma - Eng Ali)
 */

public class RegisterViewModel extends ViewModel {

    private Activity registerActivity;
    private FirebaseHandler firebaseHandler;

    /**
     * RegisterViewModel Constructor Method, it takes Activity as its input parameter
     */
    public RegisterViewModel(Activity registerActivity){
        this.registerActivity = registerActivity;
        firebaseHandler = new FirebaseHandler(registerActivity,this);
    }

    /**
     * this method called when Sign up Button in the Register activity,
     * it checks firebase to sign up new account.
     */
    public void signUp(String username, String email, String password, String repeatedPassword){
        if(username != null && !username.isEmpty() &&
        email != null && !email.isEmpty() &&
        password != null && !password.isEmpty() &&
        repeatedPassword != null && !repeatedPassword.isEmpty()){
            if(password.equals(repeatedPassword)){
                firebaseHandler.signUp(username,password);

            }
            else {
                Toast.makeText(registerActivity,"the password and repeated password is not the same",Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(registerActivity, "Please Enter required Fields Correctly", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * if sign up is successful go to Home Screen
     */

    public void signUpToHomeScreen(int registerResult){
        if(registerResult == FirebaseHandler.NEW_ACCOUNT_CREATED){
            Intent registerIntent = new Intent(registerActivity, HomeActivity.class);
            registerActivity.startActivity (registerIntent);
        }
        else{
            Toast.makeText(registerActivity, "Please Try Again with different Email and Password", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * this method called when Cancel Button in the Register activity, it takes you back to SplashActivity
     */
    public void cancel(){
        registerActivity.startActivity(new Intent(registerActivity, FirstActivity.class));
    }

}
