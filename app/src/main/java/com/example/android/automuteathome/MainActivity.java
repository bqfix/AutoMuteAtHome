package com.example.android.automuteathome;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.android.automuteathome.database.PlaceDatabase;
import com.example.android.automuteathome.database.PlaceEntry;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, PlaceAdapter.PlaceClickHandler {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_FINE_LOCATION_CODE = 777;
    private static final int PLACE_PICKER_REQUEST = 7;

    private PlaceDatabase mPlaceDatabase;
    private FloatingActionButton mAddFAB;
    private RecyclerView mRecyclerView;
    private PlaceAdapter mAdapter;
    private List<PlaceEntry> mPlaces;
    private GoogleApiClient mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Assign views
        mAddFAB = (FloatingActionButton) findViewById(R.id.add_fab);
        mRecyclerView = (RecyclerView) findViewById(R.id.places_rv);

        //RecyclerView setup
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new PlaceAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        //Add onClickListeners
        mAddFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchPlacePickerForResult();
            }
        });

        //Get instance of PlaceDatabase for later use
        mPlaceDatabase = PlaceDatabase.getInstance(this);

        //Create a client to access Google's places and location services APIs
        mClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, this)
                .build();

        //SetupViewModel to check places in database and refresh RecyclerView if they have changed
        setupViewModel();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(LOG_TAG, "API Connection Successful!");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(LOG_TAG, "API Connection Suspended.");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(LOG_TAG, "API Connection Failed.");
    }

    //Method called when the Add FAB is clicked, opens place picker to select add a new location to the database
    public void launchPlacePickerForResult() {
        //Request location permission if not granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_FINE_LOCATION_CODE);
            return;
        }

        //Proceed with place picker
        try {
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            Intent i = builder.build(this);
            startActivityForResult(i, PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            Log.e(LOG_TAG, String.format("GooglePlayServices Not Available [%s]", e.getMessage()));
        } catch (GooglePlayServicesNotAvailableException e) {
            Log.e(LOG_TAG, String.format("GooglePlayServices Not Available [%s]", e.getMessage()));
        } catch (Exception e) {
            Log.e(LOG_TAG, String.format("PlacePicker Exception: %s", e.getMessage()));
        }
    }

    //Override to handle the returned place from the launchPlacePickerForResult method
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case PLACE_PICKER_REQUEST: {
                //Check for validity of place selected
                if (resultCode == RESULT_OK) {
                    Place place = PlacePicker.getPlace(this, data);
                    if (place == null) {
                        Toast.makeText(this, getString(R.string.no_place_selected), Toast.LENGTH_LONG).show();
                        return;
                    }
                    //If okay, add to database
                    String id = place.getId();
                    boolean muteOnEnter = true;
                    boolean unmuteOnExit = true;
                    final PlaceEntry placeEntry = new PlaceEntry(id, muteOnEnter, unmuteOnExit);

                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            mPlaceDatabase.placeDao().insertNewPlace(placeEntry);
                        }
                    });

                }
            }
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }

    }

    //Override to handle the results of permission request
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_FINE_LOCATION_CODE: {
                //Permission granted, retry launchPlacePickerForResult
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    launchPlacePickerForResult();
                } else {
                    break;
                }
            }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    //Override of interface to handle item clicks
    @Override
    public void onItemClick(PlaceEntry placeEntry) {
        Toast.makeText(this, "Temporary", Toast.LENGTH_LONG).show();
    }

    /* Access List of PlaceEntries built from Database, and check each id individually
     *   to add name and address to the List (this is necessary because Google prohibits
     *   saving anything but the id in the database). */
    public void setupViewModel() {
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getPlaces().observe(this, new Observer<List<PlaceEntry>>() {
            @Override
            public void onChanged(@Nullable List<PlaceEntry> places) {
                //Set mPlaces to places
                mPlaces = places;
                //Process each place in database, and create a list of strings from the ids to make one big call to the API
                List<String> placeIds = new ArrayList<String>();
                for (int position = 0; position < places.size(); position++) {
                    placeIds.add(places.get(position).getId());
                }
                //Make API call
                PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi.getPlaceById(mClient, placeIds.toArray(new String[placeIds.size()]));
                //Callback to process returned PlaceBuffer
                placeResult.setResultCallback(new ResultCallback<PlaceBuffer>() {
                    @Override
                    public void onResult(@NonNull PlaceBuffer places) {
                        //Iterate through list of places and add name and address to each PlaceEntry in mPlaces
                        for (int position = 0; position < mPlaces.size(); position++) {
                            Place currentPlace = places.get(position);
                            mPlaces.get(position).setPlaceName(currentPlace.getName().toString());
                            mPlaces.get(position).setPlaceAddress(currentPlace.getName().toString());
                        }
                        //Finally, set mPlaces to RecyclerView
                        mAdapter.setPlaces(mPlaces);
                    }
                });
            }
        });
    }
}
