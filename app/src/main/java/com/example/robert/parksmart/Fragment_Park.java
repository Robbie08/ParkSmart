package com.example.robert.parksmart;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.renderscript.Double2;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.AsyncLayoutInflater;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;

/**
 * Created by Roberto on 6/13/2017.
 */

public class Fragment_Park extends Fragment {

    private Button parkMyCar;
    LocationManager locationManager;
    Criteria criteria;
    double latitude ,longitude;
    private Firebase mRef;
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_park, container, false); // set our view

        /*Set Variables*/
        parkMyCar = (Button) view.findViewById(R.id.bParkMyCar); // button to save the users current location
        Firebase.setAndroidContext(getContext()); // add context to the firebae object
        mRef = new Firebase("https://parksmart-f23cb.firebaseio.com/"); // reference to our firebase databse

        /*Progress Dialog Set Up*/
        progressDialog = new ProgressDialog(getContext()); // Create an instance of our ProgressDialog class
        progressDialog.setMessage("Saving Location..."); // set the message for our progress dialog


        /*Location Manager Set Up*/
        criteria = new Criteria(); //create an instance of the criteria class
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE); //add location manager
        String provider = locationManager.getBestProvider(criteria, true); //set our provider

        /*Check if the user has given permission */
        if ((ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {

            //if we have permission
            Location location = locationManager.getLastKnownLocation(provider);//create a location and take in the provider
            latitude = location.getLatitude(); //get an set our latitude
            longitude = location.getLongitude(); //get an set our longitude
        }

        /*On Click Listener to save Location*/
        parkMyCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //once the parkMyCar Button is clicked

                progressDialog.show(); // show our progress dialog
                new BackGroundTask().execute(); //execute our background tasks

                try {
                    /*Send information to DB*/
                    Firebase mRefChild = mRef.child("Current Latitude"); //create our child reference
                    mRefChild.setValue(latitude); //send our location to our Databse

                    Firebase mRefChild2 = mRef.child("Current Longitude"); //create a child
                    mRefChild2.setValue(longitude); //send location to DB


                }catch (NullPointerException e){
                    Toast.makeText(getContext(),"Failed to save location",Toast.LENGTH_LONG).show();
                    progressDialog.dismiss(); //dismiss Dialog if failed
                }

                /*Log Data*/
                Log.d("Latitude", Double.toString(latitude));
                Log.d("Longitude", Double.toString(longitude));

            }
        });



        return view; //return our view
    }

    /*Class to execute background tasks*/
private class BackGroundTask extends AsyncTask<Void,Void,Void>{
    @Override
    protected Void doInBackground(Void... voids) {
        try{
            Thread.sleep(3000); // allow for the transaction to happen
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        progressDialog.dismiss();
        Toast.makeText(getContext(),"Your Location has now been saved!",Toast.LENGTH_LONG).show();
    }
}
}
