package com.group4.togolist.viewmodel;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import androidx.lifecycle.ViewModel;

import com.group4.togolist.db.DatabaseHandler;
import com.group4.togolist.db.TripDao;
import com.group4.togolist.db.TripDatabase;
import com.group4.togolist.model.Trip;
import com.group4.togolist.repository.TripAlarm;
import com.group4.togolist.view.AddFormActivity;
import com.group4.togolist.view.HomeActivity;
import com.group4.togolist.view.ProfileActivity;

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
        AlarmManager alarmManager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(activity, TripAlarm.class);
        intent.putExtra(TripAlarm.TRIP_NAME,newTrip.getTripName());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(activity, newTrip.getId(), intent, 0);

        if (startDate.before(Calendar.getInstance())) {
            startDate.add(Calendar.DATE, 1);
        }

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, startDate.getTimeInMillis(), pendingIntent);
    }


    /**
     * SnakeBarHandler
     */
    public void goToHome(){
        activity.startActivity(new Intent(activity, HomeActivity.class));
    }

    public void goToProfile(){
        activity.startActivity(new Intent(activity, ProfileActivity.class));
    }

}
