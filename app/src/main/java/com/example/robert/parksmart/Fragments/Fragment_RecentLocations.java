package com.example.robert.parksmart.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import com.example.robert.parksmart.JavaBeans.AdapterLocationCardView;

import com.example.robert.parksmart.JavaBeans.LocationCardView;
import com.example.robert.parksmart.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Roberto on 7/7/2017.
 */

public class Fragment_RecentLocations extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    public static ArrayList<LocationCardView> list = new ArrayList<>();
    public static String date,time,locationName;
    private DateFormat formatDay, formatTime;
    Calendar calendar;


    public static Fragment_RecentLocations newInstance(){

        Fragment_RecentLocations fragment_recentLocations = new Fragment_RecentLocations();
        Bundle args = new Bundle();
        fragment_recentLocations.setArguments(args);
        return fragment_recentLocations;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recent_location, container,false);

        calendar = Calendar.getInstance();

        //Initialize our DateFormat Objects
        formatDay = new SimpleDateFormat("MM/dd/yyyy",Locale.US);
        formatTime = new SimpleDateFormat("HH:mm:ss", Locale.US);

        //name = getResources().getStringArray(R.array.locationsName);
        time = formatTime.format(calendar.getTime()); //declare our time
        date = formatDay.format(calendar.getTime()); // declare our data



        adapter = new AdapterLocationCardView(list);
        LocationCardView loc = new LocationCardView(locationName, time, date);

        if(!(TextUtils.isEmpty(locationName) || locationName.length() == 0)) {
            //if the value is not null
            list.add(loc); //add value to the list
            locationName = "";// to reset value to prevent multiple items from being created
        }

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewLocations);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(adapter);

        return view;


    }

    public void updateInfo(String nm){
       locationName = nm;
        Log.d("LOCATION_NAME","name: "+locationName);

    }
}
