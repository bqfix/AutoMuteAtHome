package com.example.android.automuteathome.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "places")
public class PlaceEntry {

    @PrimaryKey(autoGenerate = false) @NonNull
    private String id;
    private boolean muteOnEnter;
    private boolean unmuteOnExit;
    @Ignore
    private String mPlaceName = "";
    @Ignore
    private String mPlaceAddress = "";


    public PlaceEntry(String id, boolean muteOnEnter, boolean unmuteOnExit) {
        this.id = id;
        this.muteOnEnter = muteOnEnter;
        this.unmuteOnExit = unmuteOnExit;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMuteOnEnter(boolean muteOnEnter) {
        this.muteOnEnter = muteOnEnter;
    }

    public void setUnmuteOnExit(boolean unmuteOnExit) {
        this.unmuteOnExit = unmuteOnExit;
    }

    public void setPlaceName(String placeName) {
        this.mPlaceName = placeName;
    }

    public void setPlaceAddress(String placeAddress) {
        this.mPlaceAddress = placeAddress;
    }

    public String getId() {
        return id;
    }

    public boolean isMuteOnEnter() {
        return muteOnEnter;
    }

    public boolean isUnmuteOnExit() {
        return unmuteOnExit;
    }

    public String getPlaceName() {
        return mPlaceName;
    }

    public String getPlaceAddress() {
        return mPlaceAddress;
    }
}
