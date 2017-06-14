package com.example.robert.parksmart;


import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Roberto on 6/13/2017.
 */

public class Activity_Park extends Fragment {

    private Button parkMyCar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view  =  inflater.inflate(R.layout.activity_park, container ,false);

        parkMyCar = (Button) view.findViewById(R.id.bParkMyCar);


        parkMyCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //once the parkMyCar Button is clicked

                //save the location of the user into the "Find My Car" fragment
            }
        });



        return view;
    }
}
