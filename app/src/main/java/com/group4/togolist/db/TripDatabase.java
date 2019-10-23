package com.group4.togolist.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.group4.togolist.model.Trip;

/**
 * Class do :
 * Created by Group 4 ITI (Eng/Bassem - Eng Fatma - Eng Ali)
 */

@Database(entities = {Trip.class},version = 1 , exportSchema = false)
public abstract class TripDatabase extends RoomDatabase {
    public static TripDatabase getDataBaseInstance(Context context){
        RoomDatabase.Builder<TripDatabase> builder = Room.databaseBuilder(context,TripDatabase.class,"trips.db");
        return builder.build();
    }

    public abstract TripDao getDaoInstance();

}
