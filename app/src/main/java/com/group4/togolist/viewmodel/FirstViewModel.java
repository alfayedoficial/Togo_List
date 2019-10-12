package com.group4.togolist.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.view.animation.AccelerateInterpolator;
import android.widget.Toast;

import com.group4.togolist.view.SignIN;
import com.group4.togolist.view.SignUP;

import androidx.lifecycle.ViewModel;

public class FirstViewModel extends ViewModel {

    Activity firstActivity;

    /**
     * FirstViewModel Constructor take activity as input parameter
     */
    public FirstViewModel(Activity firstActivity){
        this.firstActivity = firstActivity;
    }


    /**
     * this method called when Sign in Button in the starting activity, it takes you to Sign In Activity
     */
    public void signIn(){
        Intent signInIntent = new Intent(firstActivity, SignIN.class);
        firstActivity.startActivity(signInIntent);
    }

    /**
     * this method called when create an Account Button in the starting activity, it takes you to Register Activity
     */
    public void createAccount(){
        Intent signUpIntent = new Intent (firstActivity, SignUP.class);
        firstActivity.startActivity(signUpIntent);
    }

}
