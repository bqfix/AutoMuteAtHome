package com.example.android.automuteathome;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.android.automuteathome.database.PlaceDatabase;
import com.example.android.automuteathome.database.PlaceEntry;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    // Constant for logging
    private static final String TAG = MainViewModel.class.getSimpleName();

    private LiveData<List<PlaceEntry>> places;

    public MainViewModel(Application application) {
        super(application);
        PlaceDatabase database = PlaceDatabase.getInstance(this.getApplication());
        places = database.placeDao().loadAllSavedPlaces();
    }

    public LiveData<List<PlaceEntry>> getPlaces() {
        return places;
    }
}