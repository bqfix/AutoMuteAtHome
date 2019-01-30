package com.example.android.automuteathome;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;

import com.example.android.automuteathome.database.PlaceDatabase;
import com.example.android.automuteathome.database.PlaceEntry;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

public class GeofenceBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        //Get the geofencing event that triggered the intent, and its transition type
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        final int transitionType = geofencingEvent.getGeofenceTransition();

        //Get the id of the first geofence triggering the event
        final String id = geofencingEvent.getTriggeringGeofences().get(0).getRequestId();

        //Get the PlaceEntry from database that has a matching ID
        final PlaceDatabase database = PlaceDatabase.getInstance(context);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                //Check user's preferences for entering/exiting
                PlaceEntry placeEntry = database.placeDao().loadSavedPlaceById(id);

                boolean muteOnEntry = placeEntry.isMuteOnEnter();
                boolean muteOnExit = placeEntry.isMuteOnExit();

                //Set the phone's ringer volume based on the Geofence Transition and user's preferences
                if (transitionType == Geofence.GEOFENCE_TRANSITION_ENTER) {
                    if (muteOnEntry) {
                        setRingerMode(context, AudioManager.RINGER_MODE_VIBRATE);
                    } else {
                        setRingerMode(context, AudioManager.RINGER_MODE_NORMAL);
                    }
                } else if (transitionType == Geofence.GEOFENCE_TRANSITION_EXIT) {
                    if (muteOnExit) {
                        setRingerMode(context, AudioManager.RINGER_MODE_VIBRATE);
                    } else {
                        setRingerMode(context, AudioManager.RINGER_MODE_NORMAL);
                    }
                }

            }
        });
    }

    private void setRingerMode(Context context, int mode) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT < 24 || (Build.VERSION.SDK_INT >= 24 && notificationManager.isNotificationPolicyAccessGranted())) {
            AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            audioManager.setRingerMode(mode);
        }
    }
}
