package com.group4.togolist.viewmodel;

import android.content.Intent;

import androidx.lifecycle.ViewModel;

import com.group4.togolist.repository.DatabaseHandler;
import com.group4.togolist.model.Trip;
import com.group4.togolist.view.AddFormActivity;
import com.group4.togolist.view.HomeActivity;
import com.group4.togolist.view.PastTripDetailsActivity;
import com.group4.togolist.view.ProfileActivity;

import java.util.concurrent.ExecutionException;

/**
 * Class Created to handle Past Trips Activity
 */

public class PastTripsDetailsViewModel extends ViewModel {
    private PastTripDetailsActivity activity;
    DatabaseHandler databaseHandler;
    Trip currentTrip;

    /**
     * class Constructor
     */
    public PastTripsDetailsViewModel(PastTripDetailsActivity activity){
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

    /**
     * delete Ended Trip
     */
    public void deleteTrip(){
        databaseHandler.deleteTrip(currentTrip);
        activity.startActivity(new Intent(activity, HomeActivity.class));
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
