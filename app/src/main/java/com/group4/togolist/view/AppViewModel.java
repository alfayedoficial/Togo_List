package com.group4.togolist.view;

import android.app.Activity;
import android.content.Intent;


import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * Class do :
 * Created by Group 4 ITI (Eng/Bassem - Eng Fatma - Eng Ali)
 */
class AppViewModel extends ViewModel {

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
