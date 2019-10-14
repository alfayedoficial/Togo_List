package com.group4.togolist.viewmodel;

import android.app.Activity;
import android.widget.Toast;

import com.group4.togolist.model.User;
import com.group4.togolist.repository.FirebaseHandler;
import com.group4.togolist.view.ProfileActivity;

public class ProfileViewModel {

    private User user;
    private ProfileActivity activity;
    private FirebaseHandler firebaseHandler;

    public ProfileViewModel(ProfileActivity activity){
        this.activity = activity;
        user = User.getUserInstance();
        firebaseHandler = new FirebaseHandler(activity,this);
    }


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

}
