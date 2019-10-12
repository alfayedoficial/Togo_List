package com.group4.togolist.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.group4.togolist.view.HomeActivity;
import com.group4.togolist.view.SplashActivity;

public class RegisterViewModel extends ViewModel {

    Activity RegisterActivity;


    /**
     * RegisterViewModel Constructor Method, it takes Activity as its input parameter
     */
    public RegisterViewModel(Activity RegiterActivity){
        this.RegisterActivity= RegisterActivity;
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

            }
            else {
                Toast.makeText(RegisterActivity,"the password and repeated password is not the same",Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(RegisterActivity, "Please Enter required Fields Correctly", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * this method called when Cancel Button in the Register activity, it takes you back to SplashActivity
     */
    public void cancel(){
        RegisterActivity.startActivity(new Intent(RegisterActivity, HomeActivity.class));
    }

}
