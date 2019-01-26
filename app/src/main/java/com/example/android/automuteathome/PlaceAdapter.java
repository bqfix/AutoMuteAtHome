package com.example.android.automuteathome;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.automuteathome.database.PlaceEntry;

import java.util.List;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder> {

    private List<PlaceEntry> mPlaces;

    private final PlaceClickHandler mPlaceClickHandler;

    //Constructor
    public PlaceAdapter(PlaceClickHandler placeClickHandler) {
        mPlaceClickHandler = placeClickHandler;
    }

    public class PlaceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //Member variables
        private TextView mNameTextView;
        private TextView mAddressTextView;

        //Constructor
        public PlaceViewHolder(@NonNull View itemView) {
            super(itemView);

            mNameTextView = (TextView) itemView.findViewById(R.id.name_text_view);
            mAddressTextView = (TextView) itemView.findViewById(R.id.address_text_view);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            PlaceEntry placeEntry = mPlaces.get(adapterPosition);
            mPlaceClickHandler.onItemClick(placeEntry);
        }
    }

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.place_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);

        return new PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder placeViewHolder, int position) {
        PlaceEntry place = mPlaces.get(position);

        //Set name and address
        placeViewHolder.mNameTextView.setText(place.getPlaceName());
        placeViewHolder.mAddressTextView.setText(place.getPlaceAddress());
    }

    @Override
    public int getItemCount() {
        if (mPlaces != null) {
            return mPlaces.size();
        } else {
            return 0;
        }
    }

    //Helper method for updating data set
    public void setPlaces(List<PlaceEntry> places) {
        mPlaces = places;
        notifyDataSetChanged();
    }

    //Interface to allow customization of click events
    public interface PlaceClickHandler {
        void onItemClick(PlaceEntry placeEntry);
    }
}
