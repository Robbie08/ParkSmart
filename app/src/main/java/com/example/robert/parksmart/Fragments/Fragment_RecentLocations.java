package com.example.robert.parksmart.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.robert.parksmart.JavaBeans.AdapterLocationCardView;
import com.example.robert.parksmart.JavaBeans.LocationCardView;
import com.example.robert.parksmart.R;

import java.util.ArrayList;

/**
 * Created by Roberto on 7/7/2017.
 */

public class Fragment_RecentLocations extends Fragment {

    View view;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<LocationCardView> list = new ArrayList<>();
    String[] name,date,time;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recent_location, container,false);

        name = getResources().getStringArray(R.array.locationsName);
        date = getResources().getStringArray(R.array.locationDate);
        time = getResources().getStringArray(R.array.locationTime);

        int count = 0;

        for(String Name : name){
            LocationCardView loc = new LocationCardView(Name,time[count],date[count]);
            count++;

            list.add(loc);
        }

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewLocations);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new AdapterLocationCardView(list);
        recyclerView.setAdapter(adapter);


        return view;


    }
}
