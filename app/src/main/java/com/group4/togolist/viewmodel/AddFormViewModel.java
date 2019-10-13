package com.group4.togolist.viewmodel;

import android.app.Activity;

import androidx.lifecycle.ViewModel;

import com.group4.togolist.model.Trip;

public class AddFormViewModel extends ViewModel{
    /**
     * class to handle add form view
     */

    private Activity activity;

    public AddFormViewModel(Activity activity){
        this.activity = activity;
    }

    public void createNewTrip(String tripName, double startLocationLongitude, double startLocationLatitude, double endLocationLongitude, double endLocationLatitude, int startDateYear, int startDateMonth, int startDateDay, int startDateHours, int startDateMinutes, String status, int repetition, boolean isRoundTrip, String notes){
        Trip newTrip = new Trip(tripName, startLocationLongitude, startLocationLatitude, endLocationLongitude, endLocationLatitude,  startDateYear, startDateMonth, startDateDay, startDateHours, startDateMinutes, status,repetition, isRoundTrip, notes);


    }


}