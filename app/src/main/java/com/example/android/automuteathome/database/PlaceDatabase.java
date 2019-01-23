package com.example.android.automuteathome.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Place.class}, version = 1, exportSchema = false)
public abstract class PlaceDatabase extends RoomDatabase {

    /*********************************************
     * A class to instantiate and access the DB  *
     *********************************************/

    private static final String LOG_TAG = PlaceDatabase.class.getSimpleName();

    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "saved_places";
    private static PlaceDatabase sInstance;

    public static PlaceDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        PlaceDatabase.class, PlaceDatabase.DATABASE_NAME)
                        .build();
            }
        }
        return sInstance;
    }

    public abstract PlaceDao placeDao();

}
