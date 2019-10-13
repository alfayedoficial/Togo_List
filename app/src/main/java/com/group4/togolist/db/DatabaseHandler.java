package com.group4.togolist.db;

import android.app.Activity;
import android.os.AsyncTask;

import com.group4.togolist.model.Trip;

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

    /**
     *  this method get a Trip object as an input and add it to database
     */
    public void addTrip(Trip trip){
        new AddTrip().execute(trip);
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

    //Initialize database
    public void initComponent(Activity activity){
        if(daoInstance == null){
            TripDatabase databaseInstance = TripDatabase.getDataBaseInstance(activity);
            daoInstance = databaseInstance.getDaoInstance();
        }
    }
}
