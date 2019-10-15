package com.group4.togolist.repository;

import android.app.Activity;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.group4.togolist.db.TripDao;
import com.group4.togolist.db.TripDatabase;
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



    public Trip getTripByName(String tripName) throws ExecutionException, InterruptedException {
        return new GetTripByName(tripName).execute().get();
    }

    public void updateTrip(Trip trip){
        new UpdateTrip(trip).execute();
    }

    public List<Trip> getTripsByStatus(String status) throws ExecutionException, InterruptedException {
        return new GetTripByStatus(status).execute().get();
    }

    public void deleteTrip(Trip trip){

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

    class UpdateTrip extends AsyncTask<Void,Void,Void>{

        Trip trip;
        public UpdateTrip(Trip trip){
            super();
            this.trip = trip;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            daoInstance.updateTrip(trip);
            return null;
        }
    }

    class GetTripByStatus extends AsyncTask<Void,Void,List<Trip>>{
        String status;
        public GetTripByStatus(String status){
            this.status = status;
        }

        @Override
        protected List<Trip> doInBackground(Void... voids) {
            return daoInstance.getTripsByStatus(status);
        }
    }

    class DeleteTrip extends AsyncTask<Void,Void,Void>{
        Trip trip;
        public DeleteTrip(Trip trip){
            this.trip = trip;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            daoInstance.delete(trip);
            return null;
        }
    }



}
