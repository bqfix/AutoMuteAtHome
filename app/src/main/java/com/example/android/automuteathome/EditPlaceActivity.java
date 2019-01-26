package com.example.android.automuteathome;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.android.automuteathome.database.PlaceDatabase;
import com.example.android.automuteathome.database.PlaceEntry;

public class EditPlaceActivity extends AppCompatActivity {

    private PlaceEntry mPlaceEntry;

    private EditText mNameEditText;
    private EditText mAddressEditText;
    private Switch mEnterMuteSwitch;
    private Switch mExitMuteSwitch;
    private FloatingActionButton mSaveButton;

    private PlaceDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_place);

        setTitle(getString(R.string.edit_place_activity_title));

        //Get included parcelable, finish if not available
        Intent intent = getIntent();
        if (intent.hasExtra(getString(R.string.edit_intent_parcelable_key))) {
            mPlaceEntry = intent.getParcelableExtra(getString(R.string.edit_intent_parcelable_key));
        } else {
            finish();
            Toast.makeText(this, getString(R.string.parcelable_not_available_error), Toast.LENGTH_LONG).show();
        }

        //Confirm that parcelable is not null
        if (mPlaceEntry == null) {
            finish();
            Toast.makeText(this, getString(R.string.parcelable_not_available_error), Toast.LENGTH_LONG).show();
        }

        //Assign views
        mNameEditText = (EditText) findViewById(R.id.name_et);
        mAddressEditText = (EditText) findViewById(R.id.address_et);
        mEnterMuteSwitch = (Switch) findViewById(R.id.enter_mute_switch);
        mExitMuteSwitch = (Switch) findViewById(R.id.exit_mute_switch);
        mSaveButton = (FloatingActionButton) findViewById(R.id.save_fab);

        //Get instance of database
        mDatabase = PlaceDatabase.getInstance(this);

        //Set starting status of views
        //Text should be the user's choice, to allow editing.  Hint should be Google's suggestion.
        mNameEditText.setText(mPlaceEntry.getUserInputName());
        mNameEditText.setHint(mPlaceEntry.getPlaceName());

        mAddressEditText.setText(mPlaceEntry.getUserInputAddress());
        mAddressEditText.setHint(mPlaceEntry.getPlaceAddress());

        mEnterMuteSwitch.setChecked(mPlaceEntry.isMuteOnEnter());
        mExitMuteSwitch.setChecked(mPlaceEntry.isMuteOnExit());

        //Set save functionality to save button
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Update PlaceEntry's values
                mPlaceEntry.setUserInputName(mNameEditText.getText().toString());
                mPlaceEntry.setUserInputAddress(mAddressEditText.getText().toString());
                mPlaceEntry.setMuteOnEnter(mEnterMuteSwitch.isChecked());
                mPlaceEntry.setMuteOnExit(mExitMuteSwitch.isChecked());

                //Make a database update call with the update PlaceEntry
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        mDatabase.placeDao().updateSavedPlace(mPlaceEntry);
                    }
                });
                Toast.makeText(EditPlaceActivity.this, getString(R.string.updated_entry), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(this, getString(R.string.changes_discarded), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.edit_place_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.action_delete): {
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        mDatabase.placeDao().deletePlace(mPlaceEntry);
                    }
                });
                Toast.makeText(this, getString(R.string.deleted_entry), Toast.LENGTH_SHORT).show();
                finish();
                return true;
            }
            case (android.R.id.home) : {
                onBackPressed();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
