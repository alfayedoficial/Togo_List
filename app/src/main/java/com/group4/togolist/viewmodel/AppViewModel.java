package com.group4.togolist.viewmodel;

import android.content.Intent;


import androidx.lifecycle.ViewModel;

import com.group4.togolist.view.activities.AboutAppActivity;
import com.group4.togolist.view.activities.AddFormActivity;
import com.group4.togolist.view.activities.AppActivity;
import com.group4.togolist.view.activities.HomeActivity;
import com.group4.togolist.view.activities.PrivacyActivity;
import com.group4.togolist.view.activities.ProfileActivity;

/**
 * Class do :
 * Created by Group 4 ITI (Eng/Bassem - Eng Fatma - Eng Ali)
 */
public class AppViewModel extends ViewModel {

    private AppActivity appActivity;

    /**
     * class Constructor
     */
    public AppViewModel(AppActivity appActivity) {
        this.appActivity = appActivity;
    }
    /**
     * go to profile
     */

    public void goToProfile(){
        appActivity.startActivity(new Intent(appActivity, ProfileActivity.class));
    }

    /**
     * go to add form
     */
    public void goToAddForm(){
        appActivity.startActivity(new Intent(appActivity, AddFormActivity.class));
    }
    /**
     * go to home
     */
    public void goToHome(){
        appActivity.startActivity(new Intent(appActivity, HomeActivity.class));
    }
    /**
     * go to Privacy
     */
    public void goToPrivacyLayout(){
        appActivity.startActivity(new Intent(appActivity, PrivacyActivity.class));
    }
    /**
     * go to Info
     */
    public void goToInfoLayout(){
        appActivity.startActivity(new Intent(appActivity, AboutAppActivity.class));
    }





}
