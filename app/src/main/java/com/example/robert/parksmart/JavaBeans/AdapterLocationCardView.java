package com.example.robert.parksmart.JavaBeans;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.robert.parksmart.R;

import java.util.ArrayList;

/**
 * Created by Roberto on 7/20/2017.
 */

public class AdapterLocationCardView extends RecyclerView.Adapter<AdapterLocationCardView.LocationViewHolder> {
    ArrayList<LocationCardView> locationInfoArrayList = new ArrayList<>();

    public AdapterLocationCardView(ArrayList<LocationCardView> locationInfoArrayList){
        this.locationInfoArrayList = locationInfoArrayList;
    }
    @Override
    public LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.locations_cardview,parent,false);
        LocationViewHolder locationViewHolder = new LocationViewHolder(view); // create an instan ce of our LocationViewHolderClass

        return locationViewHolder;
    }


    @Override
    public void onBindViewHolder(LocationViewHolder holder, int position) {

        /*Assign Resources*/
        LocationCardView LOC = locationInfoArrayList.get(position);
        holder.locationName.setText(LOC.getName());
        holder.locationDate.setText(LOC.getDate());
        holder.locationTime.setText(LOC.getTime());

    }

    @Override
    public int getItemCount() {
        return locationInfoArrayList.size();
    }

    public static class LocationViewHolder extends RecyclerView.ViewHolder{

         TextView locationName,locationTime,locationDate;
         Button bGoToMap, bDelete;

        public LocationViewHolder(View view){
            super(view);

            locationName = (TextView) view.findViewById(R.id.tvCardViewLocationName);
            locationDate = (TextView) view.findViewById(R.id.tvCardVewDate);
            locationTime = (TextView) view.findViewById(R.id.tvCardVewTime);
            bGoToMap = (Button) view.findViewById(R.id.bCardViewGoToMap);
            bDelete = (Button) view.findViewById(R.id.bCardViewDelete);
        }

    }
}


