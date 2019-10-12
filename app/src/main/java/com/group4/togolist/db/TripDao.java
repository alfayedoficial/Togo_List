package com.group4.togolist.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.group4.togolist.model.Trip;
import java.util.List;

@Dao
public interface TripDao {

    // method for entering new row int Trip Table
    @Insert
    void addTrip(Trip newTrip);

    // get List for all Trips in SQLite Trip Table
    @Query("select * from Trip")
    LiveData<List<Trip>> getTrips();

    // get List of Upcoming Trips
    @Query("select * from Trip where status = 'upcoming'")
    LiveData<List<Trip>> getUpcomingTrips();
    // get List of Ended Trips
    @Query("select * from Trip where status = 'Ended'")
    LiveData<List<Trip>> getEndedTrips();
    // get List of Delayed Trips
    @Query("select * from Trip where status = 'Delayed'")
    LiveData<List<Trip>> getDelayedTrips();
    // get List of Canceled Trips
    @Query("select * from Trip where status = 'Canceled'")
    LiveData<List<Trip>> getCanceledTrips();

    //update Trip
    @Update
    void updateTrip(Trip trip);

    //Delete Trip
    @Delete
    void delete(Trip trip);
}

