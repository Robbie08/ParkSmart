package com.example.robert.parksmart.Fragments;


import android.Manifest;
import android.app.AlertDialog;
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
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.robert.parksmart.R;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;

/**
 * Created by Roberto on 6/13/2017.
 */

public class Fragment_Park extends Fragment {

    private Button parkMyCar , bAddLocation;
    LocationManager locationManager;
    Criteria criteria;
    String parsedLocationName = "";
    View mView;
    EditText etLocationName;
    double latitude ,longitude ;
   // private Firebase mRef;
    private ProgressDialog progressDialog;
    public String provider;
    private AlertDialog mBuilder;


    public static Fragment_Park newInstance(){

        Fragment_Park fragment_park = new Fragment_Park();
        Bundle args = new Bundle();
        // args.putSerializable();
        fragment_park.setArguments(args);
        return fragment_park;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_park, container, false); // set our view

        /*Set Variables*/
        parkMyCar = (Button) view.findViewById(R.id.bParkMyCar); // button to save the users current location
//        Firebase.setAndroidContext(getContext()); // add context to the firebae object
//        mRef = new Firebase("https://parksmart-f23cb.firebaseio.com/"); // reference to our firebase databse
        /*Progress Dialog Set Up*/
        progressDialog = new ProgressDialog(getContext()); // Create an instance of our ProgressDialog class
        progressDialog.setMessage("Saving Location..."); // set the message for our progress dialog

        mBuilder = new AlertDialog.Builder(getContext()).create();
        mView = inflater.inflate(R.layout.dialog_location,null);

        /*Location Manager Set Up*/
        criteria = new Criteria(); //create an instance of the criteria class
        criteria.setAltitudeRequired(true);
        criteria.setBearingRequired(true);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE); //add location manager
        provider = locationManager.getBestProvider(criteria, true); //set our provider




        etLocationName  = (EditText) mView.findViewById(R.id.etLocationName);
        bAddLocation = (Button) mView.findViewById(R.id.bSaveLocation); //button for dialog box


        /*On Click Listener to save Location*/
        parkMyCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //once the parkMyCar Button is clicked

                try {
                    mBuilder.setView(mView);
                    mBuilder.show();
                    bAddLocation.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                parsedLocationName = etLocationName.getText().toString().trim();
                                if (TextUtils.isEmpty(parsedLocationName)) {
                                    Toast.makeText(getContext(), "Please give your location a name", Toast.LENGTH_LONG).show();
                                    return;
                                }
                            }catch (NullPointerException e){
                                e.printStackTrace();
                            }
                         /*Check if the user has given permission */
                            if ((ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                                    ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {

                                //if we have permission
                                try {
                                    Location location = locationManager.getLastKnownLocation(provider);//create a location and take in the provider
                                    latitude = location.getLatitude(); //get an set our latitude
                                    longitude = location.getLongitude(); //get an set our longitude
                                }catch (NullPointerException e){
                                    Toast.makeText(getContext(),"NullPointerException Caught... You're Welcome",Toast.LENGTH_LONG).show();
                                }
                            }

                            progressDialog.show(); // show our progress dialog
                            new BackGroundTask().execute(); //execute our background tasks

                            try {
                        /*Send information to DB*/
//                                Firebase mRefChild = mRef.child("Current Latitude"); //create our child reference
//                                mRefChild.setValue(latitude); //send our location to our Databse
//
//                                Firebase mRefChild2 = mRef.child("Current Longitude"); //create a child
//                                mRefChild2.setValue(longitude); //send location to DB


                            }catch (NullPointerException e){
                                Toast.makeText(getContext(),"Failed to save location",Toast.LENGTH_LONG).show();
                                progressDialog.dismiss(); //dismiss Dialog if failed
                                mBuilder.dismiss();
                                etLocationName.setText("");


                            }

                /*Log Data*/
                            Log.d("Latitude", Double.toString(latitude));
                            Log.d("Longitude", Double.toString(longitude));

                        }
                    });

                }catch (NullPointerException e){
                    Toast.makeText(getContext(),"Please try again",Toast.LENGTH_LONG).show();
                    return;
                }






        }});


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
        mBuilder.dismiss();
        etLocationName.setText("");
        Toast.makeText(getContext(),"Your Location has now been saved!",Toast.LENGTH_LONG).show();
    }
}
}
