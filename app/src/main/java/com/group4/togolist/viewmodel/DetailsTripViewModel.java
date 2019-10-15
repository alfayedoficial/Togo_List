package com.group4.togolist.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.google.android.gms.location.DetectedActivity;
import com.group4.togolist.db.DatabaseHandler;
import com.group4.togolist.model.Trip;
import com.group4.togolist.view.DetailsTripActivity;

import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class DetailsTripViewModel {
    DetailsTripActivity activity;
    DatabaseHandler databaseHandler;
    Trip currentTrip;
    public final static String TRIP_NAME = "trip_name";

    public DetailsTripViewModel(DetailsTripActivity activity){
        this.activity = activity;
        String tripName = activity.getIntent().getExtras().getString(TRIP_NAME);
        databaseHandler = new DatabaseHandler(activity);
        try {
            currentTrip = databaseHandler.getTripByName(tripName);
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
    }

    public void deleteTrip(){
        databaseHandler.deleteTrip(currentTrip);
    }

    public void editTrip(String tripName, double startLocationLongitude, double startLocationLatitude, double endLocationLongitude, double endLocationLatitude, Calendar startDate, int repetition, boolean isRoundTrip, String notes){
        currentTrip.setTripName(tripName);
        currentTrip.setStartLocationLongitude(startLocationLongitude);
        currentTrip.setStartLocationLatitude(startLocationLatitude);
        currentTrip.setEndLocationLongitude(endLocationLongitude);
        currentTrip.setEndLocationLatitude(endLocationLatitude);
        currentTrip.setStartTime(startDate);
        currentTrip.setRepetition(repetition);
        currentTrip.setRoundTrip(isRoundTrip);
        currentTrip.setNotes(notes);
        databaseHandler.updateTrip(currentTrip);
    }
}
