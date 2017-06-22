package com.example.robert.parksmart;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Fragment_Map extends Fragment implements OnMapReadyCallback, View.OnClickListener,GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    private SupportMapFragment mSupportMapFragment;
    onDataChanged onDataChanged; //create an instance of the Interface
    View view;
    ImageButton bSearchLocation;
    EditText etSearchLocation;
    String parsedLocation;
    static final LatLng UCSDGILMAN = new LatLng(32.877592, -117.233713);
    static final LatLng UCSDPARKINGLOT208 = new LatLng(32.880419, -117.242952);
    static final LatLng UCSDPARKINGLOT207 = new LatLng(32.880417, -117.241365);
    static final LatLng UCSDPARKINGLOTP103 = new LatLng(32.873129, -117.242259);

    float zoomLevel = (float) 12.0; //The zoom of the map
    float parkingZoomLevel = (float) 18.0; //The zoom of the map
    float blue = BitmapDescriptorFactory.HUE_AZURE;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view  =  inflater.inflate(R.layout.fragment_map, container ,false);
        bSearchLocation = (ImageButton) view.findViewById(R.id.bSearchLocation); //initialize searchLocation button
        etSearchLocation = (EditText) view.findViewById(R.id.etSearchLocation); //initialize searchLocation EditText


        bSearchLocation.setOnClickListener(this); //will set up the onClick Listener

        mSupportMapFragment = SupportMapFragment.newInstance();
        FragmentManager fm = getFragmentManager();
        mSupportMapFragment.getMapAsync(this);
        if (!mSupportMapFragment.isAdded())
            fm.beginTransaction().add(R.id.map, mSupportMapFragment).commit();

        else if (mSupportMapFragment.isAdded())
            fm.beginTransaction().hide(mSupportMapFragment).commit();
        else
            fm.beginTransaction().show(mSupportMapFragment).commit();



        return view;
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap; //create an instance of our googleMap class

        // Add a marker in San Diego and move the camera
        LatLng sanDiego = new LatLng(32.7157,-117.1611); //arguments Lat and Long

        mMap.addMarker(new MarkerOptions().position(sanDiego).title("Park Smart Headquarters")); //pass in the LatLng object
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sanDiego,zoomLevel)); //move the camera to where the object is pointing


        Marker ucsdGilman = mMap.addMarker(new MarkerOptions()
                .position(UCSDGILMAN)
                .title("Gilman Parking Structure")
                .snippet("Currently 63% Full").icon(BitmapDescriptorFactory.defaultMarker(blue))
        );

        Marker ucsdParkingLot208 = mMap.addMarker(new MarkerOptions()
                .position(UCSDPARKINGLOT208)
                .title("UCSD Parking Lot 208")
                .snippet("Currently 51% Full").icon(BitmapDescriptorFactory.defaultMarker(blue))
        );
        Marker ucsdParkingLot207 = mMap.addMarker(new MarkerOptions()
                .position(UCSDPARKINGLOT207)
                .title("UCSD Parking Lot 207")
                .snippet("Currently 39% Full").icon(BitmapDescriptorFactory.defaultMarker(blue))
        );
        Marker ucsdParkingLotP303 = mMap.addMarker(new MarkerOptions()
                .position(UCSDPARKINGLOTP103)
                .title("UCSD Parking Lot P103")
                .snippet("Currently 76% Full").icon(BitmapDescriptorFactory.defaultMarker(blue))
        );




        mMap.setOnInfoWindowClickListener(this);

    }

    @Override
    public void onInfoWindowClick(Marker marker) {


        marker.showInfoWindow();
        Toast.makeText(getContext(), "Info window clicked",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            onDataChanged = (onDataChanged) context;
        }catch (Exception ex){
            Toast.makeText(getContext(),"Please Try Again",Toast.LENGTH_LONG).show();
            return;
        }
    }

    @Override
    public void onClick(View view) {
        if(etSearchLocation != null) {
            parsedLocation = etSearchLocation.getText().toString().trim(); //parse the value in the EditText
        }else{
            //if the etSearchLocation is null
            parsedLocation = "FAIL";
        }
        onDataChanged.dataSend(parsedLocation); //pass data through the interface
        Log.d("TEST", "Location: " +parsedLocation);

    }

    public interface onDataChanged {
        void dataSend(String locationName);
    }
}