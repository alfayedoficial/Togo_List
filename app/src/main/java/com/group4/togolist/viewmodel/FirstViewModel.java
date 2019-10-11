package com.group4.togolist.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.view.animation.AccelerateInterpolator;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

public class FirstViewModel extends ViewModel {

    Activity firstActivity;

    public FirstViewModel(Activity firstActivity){
        this.firstActivity = firstActivity;
    }

    public void signIn(){
        Intent signInIntent = new Intent(firstActivity,LoginActivity.class);
        firstActivity.startActivity(signInIntent);
    }

    public void createAccount(){
        Intent signUpIntent = new Intent (firstActivity,RegisterActivity.class);
        firstActivity.startActivity(signUpIntent);
    }

}
