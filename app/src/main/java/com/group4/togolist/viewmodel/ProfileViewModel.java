package com.group4.togolist.viewmodel;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.group4.togolist.R;
import com.group4.togolist.model.User;
import com.group4.togolist.repository.DatabaseHandler;
import com.group4.togolist.repository.FirebaseHandler;
import com.group4.togolist.view.activities.AddFormActivity;
import com.group4.togolist.view.activities.FirstActivity;
import com.group4.togolist.view.activities.HomeActivity;
import com.group4.togolist.view.activities.ProfileActivity;
import com.shashank.sony.fancytoastlib.FancyToast;

/**
 * Class Created to handle Profile Activity
 */

public class ProfileViewModel extends ViewModel {

    private User user;
    private ProfileActivity activity;
    private FirebaseHandler firebaseHandler;
    private DatabaseHandler databaseHandler;

    /**
     * Class Constructor
     */
    public ProfileViewModel(ProfileActivity activity){
        this.activity = activity;
        user = User.getUserInstance();
       // Log.i("ProfileViewModel",user.getEmail());
        firebaseHandler = new FirebaseHandler(activity, this);

        databaseHandler = new DatabaseHandler(activity);


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
                //  Toast.makeText(activity, R.string.mesprofile, Toast.LENGTH_SHORT).show();
                FancyToast.makeText(activity ,activity.getString(R.string.confirmNewPassoword), FancyToast.DEFAULT ,FancyToast.ERROR ,false).show();

            }
            else {

            }
        }
        else{
          //  Toast.makeText(activity, R.string.mesprofile, Toast.LENGTH_SHORT).show();
            FancyToast.makeText(activity ,activity.getString(R.string.mesprofile), FancyToast.DEFAULT ,FancyToast.ERROR ,false).show();

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

                            FirebaseAuth auth = FirebaseAuth.getInstance();
                            FirebaseUser usr  = auth.getCurrentUser();
                            String uID = usr.getUid();


                        if (uID != null)
                             databaseHandler.syncWithFireBase(uID);
                        else
                            FancyToast.makeText(activity , "User id is Null" , FancyToast.DEFAULT ,FancyToast.ERROR ,false).show();

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
