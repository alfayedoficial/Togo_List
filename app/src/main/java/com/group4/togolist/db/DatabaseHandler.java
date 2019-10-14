package com.group4.togolist.db;

import android.app.Activity;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.group4.togolist.model.Trip;
import com.group4.togolist.view.FirstActivity;
import com.group4.togolist.view.SignInActivity;
import com.group4.togolist.viewmodel.LoginViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * This class is used to handle access to SQLite database
 */

public class DatabaseHandler {
    private TripDao daoInstance;
    private LiveData<List<Trip>> trips;

    /**
     * class Constructor
     */
    public DatabaseHandler(Activity activity){
        initComponent(activity);
    }

    //Initialize database
    public void initComponent(Activity activity){
        if(daoInstance == null){
            TripDatabase databaseInstance = TripDatabase.getDataBaseInstance(activity);
            daoInstance = databaseInstance.getDaoInstance();
        }
    }

    /**
     *  this method get a Trip object as an input and add it to database
     */
    public void addTrip(Trip trip){
        new AddTrip().execute(trip);
    }

    public LiveData<List<Trip>> getTrips(){
        new GetTrips().execute();
        return trips;
    }

    public Trip getTripByName(String tripName) throws ExecutionException, InterruptedException {
        return new GetTripByName(tripName).execute().get();
    }


    /**
     * Inner class AddTrip used to create a Thread to add Trip to database
     */
    class AddTrip extends AsyncTask<Trip,Void,Void> {

        @Override
        protected Void doInBackground(Trip... trips) {
            daoInstance.addTrip(trips[0]);
            return null;
        }
    }

    class GetTrips extends AsyncTask<Void,Void,LiveData<List<Trip>>>{
        @Override
        protected LiveData<List<Trip>> doInBackground(Void... voids) {
            return daoInstance.getTrips();
        }

        @Override
        protected void onPostExecute(LiveData<List<Trip>> listLiveData) {
            super.onPostExecute(listLiveData);
            trips = listLiveData;
        }
    }

    class GetTripByName extends AsyncTask<Void,Void,Trip>{

        String tripName;

        public GetTripByName(String tripName) {
            super();
            this.tripName = tripName;
        }

        @Override
        protected Trip doInBackground(Void...voids) {

            return daoInstance.getTripByName(tripName);
        }
    }

}
