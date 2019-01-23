package com.example.android.automuteathome.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Place {

@PrimaryKey(autoGenerate = true)
private int id;
private boolean muteOnEnter;
private boolean muteOnExit;

    public Place(boolean muteOnEnter, boolean muteOnExit) {
        this.muteOnEnter = muteOnEnter;
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
