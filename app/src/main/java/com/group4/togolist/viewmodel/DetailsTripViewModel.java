package com.group4.togolist.viewmodel;

import android.content.Intent;
import android.net.Uri;

import androidx.lifecycle.ViewModel;

import com.group4.togolist.repository.DatabaseHandler;
import com.group4.togolist.model.Trip;
import com.group4.togolist.view.AddFormActivity;
import com.group4.togolist.view.DetailsTripActivity;
import com.group4.togolist.view.HomeActivity;
import com.group4.togolist.view.ProfileActivity;

import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

/**
 * Class created to handle Details Activity
 */

public class DetailsTripViewModel extends ViewModel {
    DetailsTripActivity activity;
    DatabaseHandler databaseHandler;
    Trip currentTrip;


    /**
     * Class Constructor
     */
    public DetailsTripViewModel(DetailsTripActivity activity){
        this.activity = activity;
        String tripName = activity.getIntent().getExtras().getString(HomeViewModel.TRIP_NAME);
        databaseHandler = new DatabaseHandler(activity);
        try {
            currentTrip = databaseHandler.getTripByName(tripName);
            this.activity.setTripDetails(currentTrip);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // need to set Data in details form using currentTrip Data
    }

    public void startTrip(){
        String uri = String.format(Locale.ENGLISH, "geo:%f,%f", currentTrip.getEndLocationLatitude(), currentTrip.getEndLocationLongitude());
        Intent tripIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        tripIntent.setPackage("com.google.android.apps.maps");
        activity.startActivity(tripIntent);
        currentTrip.setStatus(Trip.ENDED);
    }

    public void deleteTrip(){
        databaseHandler.deleteTrip(currentTrip);
        activity.startActivity(new Intent(activity, HomeActivity.class));
    }

    public void editTrip(String tripName, Calendar startDate, String notes){
        currentTrip.setTripName(tripName);
//        currentTrip.setStartLocationLongitude(startLocationLongitude);
//        currentTrip.setStartLocationLatitude(startLocationLatitude);
//        currentTrip.setEndLocationLongitude(endLocationLongitude);
//        currentTrip.setEndLocationLatitude(endLocationLatitude);
        currentTrip.setStartTime(startDate);
//        currentTrip.setRepetition(repetition);
//        currentTrip.setRoundTrip(isRoundTrip);
        currentTrip.setNotes(notes);
        databaseHandler.updateTrip(currentTrip);
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

    public void goToAddForm(){
        activity.startActivity(new Intent(activity, AddFormActivity.class));
    }
}
