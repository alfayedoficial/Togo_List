package com.group4.togolist.viewmodel;

import android.app.Activity;

import com.group4.togolist.repository.FirebaseHandler;

public class ForgetPassViewModel {

    private Activity activity;
    private FirebaseHandler firebaseHandler;

    public ForgetPassViewModel(Activity activity){
        this.activity = activity;
        firebaseHandler = new FirebaseHandler(activity);
    }

    public void sendEmail(String email){
        firebaseHandler.resetPassword(email);
    }
}
