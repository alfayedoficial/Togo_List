package com.group4.togolist.viewmodel;

import android.app.Activity;

import androidx.lifecycle.ViewModel;

import com.group4.togolist.repository.FirebaseHandler;

public class ForgetPassViewModel extends ViewModel {

    /**
     * this class handle Forget Password View
     */

    private Activity activity;
    private FirebaseHandler firebaseHandler;


    /**
     * class Constructor
     */
    public ForgetPassViewModel(Activity activity){
        this.activity = activity;
        firebaseHandler = new FirebaseHandler(activity);
    }

    /**
     * class send Email using firebase handler to reset password
     */
    public void sendEmail(String email){
        firebaseHandler.resetPassword(email);
    }
}
