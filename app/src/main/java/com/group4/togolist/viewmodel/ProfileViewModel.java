package com.group4.togolist.viewmodel;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.group4.togolist.R;
import com.group4.togolist.model.User;
import com.group4.togolist.repository.FirebaseHandler;
import com.group4.togolist.view.AddFormActivity;
import com.group4.togolist.view.FirstActivity;
import com.group4.togolist.view.HomeActivity;
import com.group4.togolist.view.ProfileActivity;

/**
 * Class Created to handle Profile Activity
 */

public class ProfileViewModel extends ViewModel {

    private User user;
    private ProfileActivity activity;
    private FirebaseHandler firebaseHandler;


    /**
     * Class Constructor
     */
    public ProfileViewModel(ProfileActivity activity){
        this.activity = activity;
        user = User.getUserInstance();
        Log.i("ProfileViewModel",user.getEmail());
        firebaseHandler = new FirebaseHandler(activity, this);

        if(user != null) {
            this.activity.setUserName(user.getName());
            this.activity.setEmail(user.getEmail());
        }
    }


    /**
     * update User Password
     */
    public void updateUser(String password, String confirmPassword){
        if(password != null && !password.isEmpty()){
            if(password.equals(confirmPassword)){
                firebaseHandler.changeUserPassword(password);
            }
            else {

            }
        }
        else{
            Toast.makeText(activity, "Please Enter New Password", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * User Logout
     */
    public void logOut(){
        new AlertDialog.Builder(activity)
                .setTitle(R.string.titlelog)
                .setMessage(R.string.messagelog)
                .setNegativeButton(R.string.no, null)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dilog, int arg1) {
                        firebaseHandler.logOut();
                        activity.startActivity(new Intent(activity, FirstActivity.class));
                    }
                }).create().show();

    }


    /**
     * SnakeBarHandler
     */
    public void goToHome(){
        activity.startActivity(new Intent(activity, HomeActivity.class));
    }



    public void goToAddForm(){
        activity.startActivity(new Intent(activity, AddFormActivity.class));
    }

}
