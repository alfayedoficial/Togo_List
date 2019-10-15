package com.group4.togolist.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.group4.togolist.repository.FirebaseHandler;
import com.group4.togolist.view.FirstActivity;

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
        firebaseHandler = new FirebaseHandler(activity,this);
    }

    /**
     * method send Email using firebase handler to reset password
     */
    public void sendEmail(String email){
        firebaseHandler.resetPassword(email);
    }


    /**
     * check is the Email sent or not
     */
    public void onResult(int result){
        switch(result){
            case FirebaseHandler.RESET_EMAIL_SENT:
                Toast.makeText(activity, "Email sent", Toast.LENGTH_SHORT).show();
                break;
            case FirebaseHandler.RESET_EMAIL_FAILED:
                Toast.makeText(activity, "Email not found", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * Go Back to Login Activity
     */
    public void cancel(){
        Intent intent = new Intent(activity, LoginViewModel.class);
        activity.startActivity(intent);
    }
}
