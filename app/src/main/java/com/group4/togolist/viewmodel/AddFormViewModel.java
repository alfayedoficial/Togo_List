package com.group4.togolist.viewmodel;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.group4.togolist.R;
import com.group4.togolist.repository.DatabaseHandler;
import com.group4.togolist.model.Trip;
import com.group4.togolist.util.TripAlarm;
import com.group4.togolist.view.activities.HomeActivity;
import com.group4.togolist.view.activities.ProfileActivity;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.Calendar;
import java.util.concurrent.ExecutionException;

/**
 * class to handle add form view
 */

public class AddFormViewModel extends ViewModel{

    private Activity activity;
    private DatabaseHandler databaseHandler;
    public static final String TRIP_ONE = "(go)";
    public static final String TRIP_TWO = "(return)";

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
    public void createNewTrip(String tripName, double startLocationLongitude, double startLocationLatitude, double endLocationLongitude, double endLocationLatitude, Calendar startDate,Calendar roundDate, int repetition, boolean isRoundTrip, String notes){

        if(isRoundTrip){
            if(!roundDate.before(startDate)) {
                Trip newTrip = new Trip(tripName + TRIP_ONE, startLocationLongitude, startLocationLatitude, endLocationLongitude, endLocationLatitude, Trip.UPCOMING, repetition, isRoundTrip, notes);
                newTrip.setStartTime(startDate);
                Trip newTripRound = new Trip(tripName + TRIP_TWO, endLocationLongitude, endLocationLatitude, startLocationLongitude, startLocationLatitude, Trip.UPCOMING, repetition, isRoundTrip, notes);
                newTripRound.setStartTime(roundDate);
                Log.i("Tag",roundDate.getTime().toString());

                setAlarm(newTrip);
                setAlarm(newTripRound);

            }else {
                Toast.makeText(activity, activity.getString(R.string.wrong_round_time), Toast.LENGTH_SHORT).show();
                return;
            }
        }else {
                Trip newTrip = new Trip(tripName, startLocationLongitude, startLocationLatitude, endLocationLongitude, endLocationLatitude, Trip.UPCOMING, repetition, isRoundTrip, notes);
                newTrip.setStartTime(startDate);
                setAlarm(newTrip);

        }




//        setAlarm(startDate,alarmManager,activity);


    }

    public void setAlarm(Trip newTrip){
        databaseHandler.addTrip(newTrip);
        try {
            newTrip = databaseHandler.getTripByName(newTrip.getTripName());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

            Calendar startDate = newTrip.getStartTime();
            Log.i("trip date",newTrip.getStartTime().getTime().toString());
            startDate.set(Calendar.MONTH,startDate.get(Calendar.MONTH)-1);
            startDate.set(Calendar.SECOND,0);
            if(startDate.after(Calendar.getInstance())) {
                AlarmManager alarmManager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(activity, TripAlarm.class);
                intent.putExtra(TripAlarm.TRIP_NAME, newTrip.getTripName());
                PendingIntent pendingIntent = PendingIntent.getBroadcast(activity, newTrip.getId(), intent, 0);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, startDate.getTimeInMillis(), pendingIntent);
                activity.startActivity(new Intent(activity, HomeActivity.class));
            }
            else {
                FancyToast.makeText(activity,activity.getString(R.string.wrong_start_time),FancyToast.LENGTH_SHORT,FancyToast.ERROR,true).show();
                databaseHandler.deleteTrip(newTrip);
            }

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
