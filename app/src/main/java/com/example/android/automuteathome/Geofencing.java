package com.example.android.automuteathome;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;

import java.util.ArrayList;
import java.util.List;

public class Geofencing {
    private Context mContext;
    private GeofencingClient mClient;
    private PendingIntent mGeofencePendingIntent;
    private List<Geofence> mGeofences;

    //Constants for building geofences
    private static final float GEOFENCE_RADIUS = 50; // 50 meters
    private static final long GEOFENCE_TIMEOUT = 24 * 60 * 60 * 1000; // 24 hours

    //Constructor
    public Geofencing(Context context) {
        this.mContext = context;
        mClient = LocationServices.getGeofencingClient(context);
        mGeofencePendingIntent = null;
        mGeofences = new ArrayList<>();
    }

    /**
     * Method to create a list of Geofences and set them to mGeofences to later be registered
     *
     * @param places to create geofences around
     */
    public void updateGeofenceList(PlaceBuffer places) {
        mGeofences = new ArrayList<>();
        if (places == null || places.getCount() == 0) return;
        for (Place place : places) {
            //Get necessary data from each place
            String placeId = place.getId();
            double placeLat = place.getLatLng().latitude;
            double placeLng = place.getLatLng().longitude;

            //Build the new Geofence and add to the list
            Geofence geofence = new Geofence.Builder()
                    .setRequestId(placeId)
                    .setCircularRegion(placeLat, placeLng, GEOFENCE_RADIUS)
                    .setExpirationDuration(GEOFENCE_TIMEOUT)
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
                    .build();

            mGeofences.add(geofence);
        }
    }

    /**
     * Method to create a GeofencingRequest object
     *
     * @return the GeofencingRequest object
     */
    private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(mGeofences);
        return builder.build();
    }

    /**
     * Method to create a PendingIntent object
     *
     * @return the PendingIntent
     */
    private PendingIntent getGeofencePendingIntent() {
        if (mGeofencePendingIntent != null) {
            return mGeofencePendingIntent;
        }
        Intent intent = new Intent(mContext, GeofenceBroadcastReceiver.class);
        mGeofencePendingIntent = PendingIntent.getBroadcast(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return mGeofencePendingIntent;
    }

    /** Method to register each Geofence
     *
     */
    public void registerAllGeofences() {
        if (mGeofences == null || mGeofences.isEmpty()) return;
        try {
            mClient.addGeofences(getGeofencingRequest(), getGeofencePendingIntent());
        } catch (SecurityException e) {
            Toast.makeText(mContext, mContext.getString(R.string.security_error) + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
