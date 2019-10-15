package com.group4.togolist.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.group4.togolist.model.Trip;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface TripDao {
    /**
     * Class do :
     * Created by Group 4 ITI (Eng/Bassen - Eng Fatma - Eng Ali)
     */

    // method for entering new row int Trip Table
    @Insert
    void addTrip(Trip newTrip);

    // get List for all Trips in SQLite Trip Table
    @Query("select * from Trip")
    ArrayList<Trip> getTrips();

    // get List of Trips by trip Status
    @Query("select * from Trip where status = :tripStatus")
    ArrayList<Trip> getTripsByStatus(String tripStatus);

    @Query("select * from Trip where tripName = :tripByName")
    Trip getTripByName(String tripByName);

    //update Trip
    @Update
    void updateTrip(Trip trip);

    //Delete Trip
    @Delete
    void delete(Trip trip);
}

