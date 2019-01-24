package com.example.android.automuteathome.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "places")
public class PlaceEntry {

    @PrimaryKey(autoGenerate = false)
    private int id;
    private boolean muteOnEnter;
    private boolean unmuteOnExit;

    public PlaceEntry(int id, boolean muteOnEnter, boolean unmuteOnExit) {
        this.id = id;
        this.muteOnEnter = muteOnEnter;
        this.unmuteOnExit = unmuteOnExit;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMuteOnEnter(boolean muteOnEnter) {
        this.muteOnEnter = muteOnEnter;
    }

    public void setUnmuteOnExit(boolean unmuteOnExit) {
        this.unmuteOnExit = unmuteOnExit;
    }

    public int getId() {
        return id;
    }

    public boolean isMuteOnEnter() {
        return muteOnEnter;
    }

    public boolean isUnmuteOnExit() {
        return unmuteOnExit;
    }
}
