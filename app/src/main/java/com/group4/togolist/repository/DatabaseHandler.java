package com.group4.togolist.repository;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.group4.togolist.db.TripDao;
import com.group4.togolist.db.TripDatabase;
import com.group4.togolist.model.Trip;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * This class is used to handle access to SQLite database
 */

public class DatabaseHandler {
    private TripDao daoInstance;
    private DatabaseReference databaseReference ;



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

            /**
             * fireBase initiate the DB
             */

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

    public List<Trip> getAllTrips(){
        List<Trip> allTrips = new ArrayList<>();
        class GetAllTrips extends AsyncTask<Void,Void,List<Trip>>{
            @Override
            protected List<Trip> doInBackground(Void... voids) {
                return daoInstance.getTrips();
            }
        }
        try {
            allTrips = new GetAllTrips().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return allTrips ;
    }

    public void getTripsByStatus(final MutableLiveData<List<Trip>> tripList, final String status)  {
        class GetTripByStatus extends AsyncTask<Void,Void,List<Trip>>{

            @Override
            protected List<Trip> doInBackground(Void... voids) {
                return daoInstance.getTripsByStatus(status);
            }

            @Override
            protected void onPostExecute(List<Trip> trips) {
                super.onPostExecute(trips);
                tripList.setValue(trips);
            }
        }
        new GetTripByStatus().execute();
    }

    public void deleteTrip(Trip trip){
         new DeleteTrip(trip).execute();
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
            /**
             *
             */
//            firbaseHelp();
//            databaseReference.child("Users").child(user.getUid()).child(trip.getId() + "").setValue(trip);
//            Log.i("DatabaseHandler" ,trip.getId() + "" );
            return null;
        }
    }

    /**
     *
     */


    class DeleteTrip extends AsyncTask<Void,Void,Void> {
        Trip trip;

        public DeleteTrip(Trip trip) {
            this.trip = trip;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            daoInstance.delete(trip);

            /**
             *
                    firbaseHelp();
                  databaseReference.child("Users").child(user.getUid()).child(trip.getId() + "").removeValue();
             */

            return null;
        }
    }

        /**
         *  sync with firebase
         */

        class SyncWithFireBase extends AsyncTask<Void,Void,Void>{
            List<Trip> tripList;
            String userID ;
            public SyncWithFireBase(List<Trip> tripList , String userID){
                this.tripList = tripList;
                this.userID = userID ;
            }

            @Override
            protected Void doInBackground(Void... voids) {

                /**
                 *  getting all trips from Room ad upload it to FireBase Database
                 */

                fireBaseHelp();

            if ( userID != null) {
                databaseReference.child("Users").child(userID).removeValue();
                for (Trip trip : tripList) {
                    databaseReference.child("Users").child(userID).child(trip.getId() + "").setValue(trip);

                }
            }else
                Log.i("User Id " , " user id is null ");

                return null;
            }
    }

    public void syncWithFireBase(String userId){
            new SyncWithFireBase( getAllTrips(),userId).execute();
    }

    public void loadFromFireBase( String userID) {

          fireBaseHelp();
          if (userID != null) {
              databaseReference.child("Users").child(userID).addValueEventListener(new ValueEventListener() {
                  @Override
                  public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                      if (!(dataSnapshot.getChildrenCount() == 0)) {
                          Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                          for (DataSnapshot dSTrip : children) {
                              Trip trip = dSTrip.getValue(Trip.class);
                              deleteTrip(trip);
                              addTrip(trip);
                          }
                      }
                  }

                  @Override
                  public void onCancelled(@NonNull DatabaseError databaseError) {
                      Log.i("DataBaseHandler", databaseError.toString());
                  }
              });
          }

    }

    private void fireBaseHelp(){

                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference();


        }

}
