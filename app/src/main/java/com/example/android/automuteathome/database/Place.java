package com.example.android.automuteathome.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "places")
public class Place {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private boolean muteOnEnter;
    private boolean muteOnExit;

    @Ignore
    public Place(int id, boolean muteOnEnter, boolean muteOnExit) {
        this.id = id;
        this.muteOnEnter = muteOnEnter;
        this.muteOnExit = muteOnExit;
    }

    public Place(boolean muteOnEnter, boolean muteOnExit) {
        this.muteOnEnter = muteOnEnter;
        this.muteOnExit = muteOnExit;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMuteOnEnter(boolean muteOnEnter) {
        this.muteOnEnter = muteOnEnter;
    }

    public void setMuteOnExit(boolean muteOnExit) {
        this.muteOnExit = muteOnExit;
    }

    public int getId() {
        return id;
    }

    public boolean isMuteOnEnter() {
        return muteOnEnter;
    }

    public boolean isMuteOnExit() {
        return muteOnExit;
    }
}
