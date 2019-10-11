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
    @Insert
    void addTrip(Trip newTrip);

    @Query("select * from Trip")
    LiveData<List<Trip>> getTrips();

    @Query("select * from Trip where status = 'upcoming'")
    LiveData<List<Trip>> getUpcomingTrips();
    @Query("select * from Trip where status = 'Ended'")
    LiveData<List<Trip>> getEndedTrips();
    @Query("select * from Trip where status = 'Delayed'")
    LiveData<List<Trip>> getDelayedTrips();
    @Query("select * from Trip where status = 'Canceled'")
    LiveData<List<Trip>> getCanceledTrips();

    @Update
    void updateTrip(Trip trip);

    @Delete
    void delete(Trip trip);
}

