package com.example.android.automuteathome.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

@Entity(tableName = "places")
public class PlaceEntry implements Parcelable {

    @PrimaryKey(autoGenerate = false) @NonNull
    private String id;
    private boolean muteOnEnter;
    private boolean muteOnExit;
    @Ignore
    private String mPlaceName = "";
    @Ignore
    private String mPlaceAddress = "";
    private String mUserInputName = "";
    private String mUserInputAddress = "";


    public PlaceEntry(String id, boolean muteOnEnter, boolean muteOnExit) {
        this.id = id;
        this.muteOnEnter = muteOnEnter;
        this.muteOnExit = muteOnExit;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMuteOnEnter(boolean muteOnEnter) {
        this.muteOnEnter = muteOnEnter;
    }

    public void setMuteOnExit(boolean muteOnExit) {
        this.muteOnExit = muteOnExit;
    }

    public void setPlaceName(String placeName) {
        mPlaceName = placeName;
    }

    public void setPlaceAddress(String placeAddress) {
        mPlaceAddress = placeAddress;
    }

    public void setUserInputName(String userInputName) {
        mUserInputName = userInputName;
    }

    public void setUserInputAddress(String userInputAddress) {mUserInputAddress = userInputAddress;}

    public String getId() {
        return id;
    }

    public boolean isMuteOnEnter() {
        return muteOnEnter;
    }

    public boolean isMuteOnExit() {
        return muteOnExit;
    }

    public String getPlaceName() {
        return mPlaceName;
    }

    public String getPlaceAddress() {
        return mPlaceAddress;
    }

    public String getUserInputName() {
        return mUserInputName;
    }

    public String getUserInputAddress() {
        return mUserInputAddress;
    }

    //Parcelable logic
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeByte(this.muteOnEnter ? (byte) 1 : (byte) 0);
        dest.writeByte(this.muteOnExit ? (byte) 1 : (byte) 0);
        dest.writeString(this.mPlaceName);
        dest.writeString(this.mPlaceAddress);
        dest.writeString(this.mUserInputName);
        dest.writeString(this.mUserInputAddress);
    }

    protected PlaceEntry(Parcel in) {
        this.id = in.readString();
        this.muteOnEnter = in.readByte() != 0;
        this.muteOnExit = in.readByte() != 0;
        this.mPlaceName = in.readString();
        this.mPlaceAddress = in.readString();
        this.mUserInputName = in.readString();
        this.mUserInputAddress = in.readString();
    }

    public static final Parcelable.Creator<PlaceEntry> CREATOR = new Parcelable.Creator<PlaceEntry>() {
        @Override
        public PlaceEntry createFromParcel(Parcel source) {
            return new PlaceEntry(source);
        }

        @Override
        public PlaceEntry[] newArray(int size) {
            return new PlaceEntry[size];
        }
    };
}
