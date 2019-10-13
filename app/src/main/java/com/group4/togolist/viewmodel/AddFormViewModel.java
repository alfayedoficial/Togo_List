package com.group4.togolist.viewmodel;

import android.app.Activity;
import android.os.AsyncTask;

import androidx.lifecycle.ViewModel;

import com.group4.togolist.db.DatabaseHandler;
import com.group4.togolist.db.TripDao;
import com.group4.togolist.db.TripDatabase;
import com.group4.togolist.model.Trip;

import java.util.Calendar;

/**
 * class to handle add form view
 */

public class AddFormViewModel extends ViewModel{

    private Activity activity;
    DatabaseHandler databaseHandler;

    /**
     *  class Constructor
     */
    public AddFormViewModel(Activity activity){
        this.activity = activity;
        databaseHandler = new DatabaseHandler(activity);

    }

    /**
     * this method is called by Button Add in addFormActivity
     * it takes trip information as an input and it create a Trip and add to database
     */
    public void createNewTrip(String tripName, double startLocationLongitude, double startLocationLatitude, double endLocationLongitude, double endLocationLatitude, Calendar startDate, int repetition, boolean isRoundTrip, String notes){
        Trip newTrip = new Trip(tripName, startLocationLongitude, startLocationLatitude, endLocationLongitude, endLocationLatitude,startDate, Trip.UPCOMING, repetition, isRoundTrip, notes);
        databaseHandler.addTrip(newTrip);
    }
}
