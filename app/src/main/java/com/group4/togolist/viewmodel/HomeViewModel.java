package com.group4.togolist.viewmodel;

import android.app.Activity;
import android.content.Intent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.group4.togolist.db.DatabaseHandler;
import com.group4.togolist.model.Trip;
import com.group4.togolist.view.AddFormActivity;
import com.group4.togolist.view.DetailsTripActivity;
import com.group4.togolist.view.HomeActivity;
import com.group4.togolist.view.ProfileActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class HomeViewModel extends ViewModel {
    private Activity activity;
    private DatabaseHandler databaseHandler;

    public final static String TRIP_NAME = "trip_name";


    public HomeViewModel(Activity activity){
        this.activity = activity;
        databaseHandler = new DatabaseHandler(activity);
    }

    public void deleteTrip(String tripName){
        try {
            Trip trip = databaseHandler.getTripByName(tripName);
            databaseHandler.deleteTrip(trip);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<Trip> getUpcomingTrip() throws ExecutionException, InterruptedException {
        return databaseHandler.getTripsByStatus(Trip.UPCOMING);
    }

    public List<Trip> getEndedTrip() throws ExecutionException, InterruptedException {
        return databaseHandler.getTripsByStatus(Trip.ENDED);
    }

    public void upcomingTripItemClicked(int position){
        try {
            ArrayList<Trip> trips = databaseHandler.getTripsByStatus(Trip.UPCOMING);
            Intent detailsIntent = new Intent(activity, DetailsTripActivity.class);
            detailsIntent.putExtra(TRIP_NAME,trips.get(position).getTripName());
            activity.startActivity(detailsIntent);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void endedTripItemClicked (int position){
        try {
            ArrayList<Trip> trips = databaseHandler.getTripsByStatus(Trip.ENDED);
            Intent detailsIntent = new Intent(activity, DetailsTripActivity.class);
            detailsIntent.putExtra(TRIP_NAME,trips.get(position).getTripName());
            activity.startActivity(detailsIntent);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /**
     * SnakeBarHandler
     */

    public void goToProfile(){
        activity.startActivity(new Intent(activity, ProfileActivity.class));
    }

    public void goToAddForm(){
        activity.startActivity(new Intent(activity, AddFormActivity.class));
    }
}
