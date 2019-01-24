package com.example.android.automuteathome.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface PlaceDao {

    @Query("SELECT * FROM places ORDER BY id")
    LiveData<List<PlaceEntry>> loadAllSavedPlaces();

    @Query("SELECT * FROM places WHERE id = :id")
    LiveData<PlaceEntry> loadSavedPlaceById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNewPlace(PlaceEntry placeEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateSavedPlace(PlaceEntry placeEntry);

    @Delete
    void deletePlace(PlaceEntry placeEntry);

}
