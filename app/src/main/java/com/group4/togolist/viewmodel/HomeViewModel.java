package com.group4.togolist.viewmodel;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.group4.togolist.repository.DatabaseHandler;
import com.group4.togolist.model.Trip;
import com.group4.togolist.repository.FirebaseHandler;
import com.group4.togolist.util.TripAlarm;
import com.group4.togolist.view.activities.AddFormActivity;
import com.group4.togolist.view.activities.AppActivity;
import com.group4.togolist.view.activities.DetailsTripActivity;
import com.group4.togolist.view.activities.FirstActivity;
import com.group4.togolist.view.activities.PastTripDetailsActivity;
import com.group4.togolist.view.activities.ProfileActivity;
import com.group4.togolist.view.activities.SplashActivity;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Class Handle Home Activity
 */

public class HomeViewModel extends ViewModel {
    private Activity activity;
    private DatabaseHandler databaseHandler;
    private FirebaseHandler firebaseHandler;
    private LiveData<List<Trip>> upcomingTrips, pastTrips;

    public final static String TRIP_NAME = "trip_name";


    /**
     * class Constructor
     */
    public HomeViewModel(Activity activity){
        this.activity = activity;
        databaseHandler = new DatabaseHandler(activity);
        firebaseHandler = new FirebaseHandler(activity);
        updateFirebase();
        upcomingTrips = databaseHandler.getUpcomingTrips();
        pastTrips = databaseHandler.getPastTrips();

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
    public LiveData<List<Trip>> getUpcomingTrip(){
        return upcomingTrips;
    }

    /**
     * get an ArrayList of EndedTrips
     */
    public LiveData<List<Trip>> getEndedTrip() {
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

    public void updateFirebase(){
        SharedPreferences loadDataSetting = activity.getSharedPreferences(SplashActivity.PREFF_NAME,0);
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String  uID = firebaseUser.getUid();
        if(loadDataSetting.getBoolean(SplashActivity.TAG_LOAD_DATA,false)){
            if(uID!= null) {
                databaseHandler.loadFromFireBase(uID);
            }else {
                Log.i("user", "user ID is Null");
            }
        }
        SharedPreferences.Editor editor = loadDataSetting.edit();
        editor.putBoolean(SplashActivity.TAG_LOAD_DATA,false);
        editor.commit();
    }

    public void goToApp() {
        activity.startActivity(new Intent(activity , AppActivity.class));
    }
}
