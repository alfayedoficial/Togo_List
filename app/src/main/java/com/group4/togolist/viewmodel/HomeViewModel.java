package com.group4.togolist.viewmodel;

import android.app.Activity;
import android.content.Intent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.group4.togolist.db.DatabaseHandler;
import com.group4.togolist.model.Trip;
import com.group4.togolist.view.AddFormActivity;
import com.group4.togolist.view.HomeActivity;
import com.group4.togolist.view.ProfileActivity;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class HomeViewModel extends ViewModel {
    private Activity activity;
    private DatabaseHandler databaseHandler;
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

    public LiveData<List<Trip>> getUpcomingTrip() throws ExecutionException, InterruptedException {
        return databaseHandler.getTripsByStatus(Trip.UPCOMING);
    }

    public LiveData<List<Trip>> getEndedTrip() throws ExecutionException, InterruptedException {
        return databaseHandler.getTripsByStatus(Trip.ENDED);
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
