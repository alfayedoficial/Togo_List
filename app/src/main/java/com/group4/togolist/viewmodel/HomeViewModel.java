package com.group4.togolist.viewmodel;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.group4.togolist.repository.DatabaseHandler;
import com.group4.togolist.model.Trip;
import com.group4.togolist.repository.FirebaseHandler;
import com.group4.togolist.repository.TripAlarm;
import com.group4.togolist.view.activities.AddFormActivity;
import com.group4.togolist.view.activities.AppActivity;
import com.group4.togolist.view.activities.DetailsTripActivity;
import com.group4.togolist.view.activities.FirstActivity;
import com.group4.togolist.view.activities.PastTripDetailsActivity;
import com.group4.togolist.view.activities.ProfileActivity;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Class Handle Home Activity
 */

public class HomeViewModel extends ViewModel {
    private Activity activity;
    private DatabaseHandler databaseHandler;
    private FirebaseHandler firebaseHandler;
    private MutableLiveData<List<Trip>> upcomingTrips, pastTrips;

    public final static String TRIP_NAME = "trip_name";


    /**
     * class Constructor
     */
    public HomeViewModel(Activity activity){
        this.activity = activity;
        databaseHandler = new DatabaseHandler(activity);
        firebaseHandler = new FirebaseHandler(activity);
        upcomingTrips = new MutableLiveData<>();
        pastTrips = new MutableLiveData<>();
    }

    /**
     * delete selected Trip
     */
    public void deleteTrip(String tripName){
        try {
            Trip trip = databaseHandler.getTripByName(tripName);
            databaseHandler.deleteTrip(trip);
            cancelAlarm(trip);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void cancelAlarm(Trip currentTrip) {
        AlarmManager alarmManager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(activity, TripAlarm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(activity, currentTrip.getId(), intent, 0);

        alarmManager.cancel(pendingIntent);
    }
    /**
     * get an ArrayList of UpComing Trips
     */
    public MutableLiveData<List<Trip>> getUpcomingTrip(){
        databaseHandler.getTripsByStatus(upcomingTrips,Trip.UPCOMING);
        return upcomingTrips;
    }

    /**
     * get an ArrayList of EndedTrips
     */
    public MutableLiveData<List<Trip>> getEndedTrip() {
        databaseHandler.getTripsByStatus(pastTrips,Trip.ENDED);
        return pastTrips;
    }

    /**
     * send activity to Details with trip name
     */
    public void upcomingTripItemClicked(int position){

            Intent detailsIntent = new Intent(activity, DetailsTripActivity.class);
            detailsIntent.putExtra(TRIP_NAME,upcomingTrips.getValue().get(position).getTripName());
            activity.startActivity(detailsIntent);

    }

    /**
     * send activity to Past Trip Details with trip name
     */

    public void endedTripItemClicked (int position){
            Intent pastDetailsIntent = new Intent(activity, PastTripDetailsActivity.class);
            pastDetailsIntent.putExtra(TRIP_NAME,pastTrips.getValue().get(position).getTripName());
            activity.startActivity(pastDetailsIntent);

    }
    /**
     * SnakeBarHandler
     */

    public void goToProfile(){
        activity.startActivity(new Intent(activity, ProfileActivity.class));
    }

    /**
     *
     */
    public void goToAddForm(){
        activity.startActivity(new Intent(activity, AddFormActivity.class));
    }
    /**
     *
     */

    /**
     * User Logout
     */
    public void logOut(){
        firebaseHandler.logOut();
        activity.startActivity(new Intent(activity, FirstActivity.class));
    }

    public void goToApp() {
        activity.startActivity(new Intent(activity , AppActivity.class));
    }
}
