package com.example.robert.parksmart.Fragments;

import android.Manifest;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.robert.parksmart.activities.MainActivity;
import com.example.robert.parksmart.dialog.AddListDialogFragment;
import com.example.robert.parksmart.listServices.AdapterSchoolNameCardView;
import com.example.robert.parksmart.enteties.ParkingLotDetailsPOJO;
import com.example.robert.parksmart.R;
import com.example.robert.parksmart.services.GPSTracker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Fragment_Map extends Fragment implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener, AdapterSchoolNameCardView.OnLocationSelected, GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener{

    private GoogleMap mMap;
    private SupportMapFragment mSupportMapFragment;
    private onDataChanged onDataChanged; //create an instance of the Interface
    private View view, mView ; // view object that will be returned in the onCreateView

    private CameraUpdate camUpdate;
    private ProgressDialog progressDialog, progSaveLoc;


    private float USER_LOCATION_ZOOM = (float) 15.5; //The zoom of the map
    private float schoolZoomLevel = (float) 14.5;
    private float green = BitmapDescriptorFactory.HUE_GREEN;

    private Criteria criteria;
    private FirebaseDatabase database;
    private DatabaseReference ref;
    private FloatingActionButton fab_plus;
    private AlertDialog alertDialog;
    private LocationManager locationManager;
    private String provider, parsedLocation;
    private Location mLocation;
    private LatLng USER_LOCATION;
    private Fragment_RecentLocations recentLocations;
    private ArrayList<ParkingLotDetailsPOJO> parkingLotDetails;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    public double longitude;
    public double latitude;
    private String TAG = "API CHECK";

    @BindView(R.id.fab_save_location)
    FloatingActionButton floatingActionButton;




    public static Fragment_Map newInstance(){

        Fragment_Map fragment_map = new Fragment_Map();
        Bundle args = new Bundle();
        fragment_map.setArguments(args);
        return fragment_map;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_map, container, false);
        parkingLotDetails = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        ButterKnife.bind(this, view);

        /*Progress Dialog*/
        progSaveLoc = new ProgressDialog(getContext());
        progSaveLoc.setMessage("Saving Location...");

        alertDialog = new AlertDialog.Builder(getContext()).create();
        mView = inflater.inflate(R.layout.dialog_add_list, container, false); //inflate dialog view

        /*Location Manager Set Up*/
        criteria = new Criteria();
        criteria.setAltitudeRequired(true);
        criteria.setBearingRequired(true);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);



        recentLocations = Fragment_RecentLocations.newInstance();
        mSupportMapFragment = SupportMapFragment.newInstance();
        FragmentManager fm = getFragmentManager();
        mSupportMapFragment.getMapAsync(this);


        //set up ApiClient
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        //set up LocationManager
        locationManager = (LocationManager) getActivity().getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(criteria,true);



        ((MainActivity) getActivity()).setActionBarTitle("ParkSmart");
        if (!mSupportMapFragment.isAdded())
            fm.beginTransaction().add(R.id.map, mSupportMapFragment).commit();

        else if (mSupportMapFragment.isAdded())
            fm.beginTransaction().hide(mSupportMapFragment).commit();
        else
            fm.beginTransaction().show(mSupportMapFragment).commit();

        return view;
    }

    @OnClick(R.id.fab_save_location)
    public void setFloatingActionButton(){
        //display the alert dialog
        DialogFragment  dialogFragment= AddListDialogFragment.newInstance();
        dialogFragment.show(getActivity().getFragmentManager(), AddListDialogFragment.class.getSimpleName());

    }
//
//    @OnClick(R.id.bSearchLocation)
//    public void setbSearchLocation(){
//
//        if (!(TextUtils.isEmpty(etSearchLocation.getText().toString().trim()))) {
//            try {
//                parsedLocation = etSearchLocation.getText().toString().trim(); //parse the value in the EditText
//                //queryParkingData(parsedLocation);
//                etSearchLocation.setText("");
//                goToPlace(parsedLocation, schoolZoomLevel);
//            }catch (Exception e){
//                e.printStackTrace();
//                Toast.makeText(getActivity(),"Please input a school", Toast.LENGTH_LONG).show();
//            }
//        }
//        if(TextUtils.isEmpty(etSearchLocation.getText().toString().trim())){
//            Toast.makeText(getActivity(),"Please input a school",Toast.LENGTH_LONG).show();
//        }
//
//    }

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

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL); //sets the maps view





        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);


//        if ( (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
//                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
//
//            //if we have permission
//            try {
//
//                //Location location = locationManager.getLastKnownLocation(provider);//create a location and take in the provider
//                USER_LOCATION = new LatLng(latitude, longitude); //create our Lat&Long object
//                Log.d("Latitude", Double.toString(mLocation.getLatitude()));
//                Log.d("longitude",Double.toString(mLocation.getLongitude()));
//
//            }
//            catch (NullPointerException e){
//
//                USER_LOCATION = new LatLng(35.6895,139.6917); //se the location to be in Tokyo
//                USER_LOCATION_ZOOM = (float) 14.5;
//                Snackbar snackbar = Snackbar.make(view, "Please enable GPS location on your device ",Snackbar.LENGTH_LONG);
//                snackbar.show();
//            }
//
//        }else {
//            Snackbar snackbar = Snackbar.make(view, "Please enable GPS location on your",Snackbar.LENGTH_LONG);
//            snackbar.show();
//        }

        if(Double.toString(latitude).isEmpty() || Double.toString(longitude).isEmpty() || (mLocation == null)) {

            //if the LatLng object is null
            USER_LOCATION = new LatLng(35.6895, 139.6917); //se the location to be in Tokyo
            USER_LOCATION_ZOOM = (float) 14.5;
        }


        //add the circle to the users current location
        mMap.addCircle(new CircleOptions()
                .center(USER_LOCATION).radius(350)
                .strokeColor(Color.GRAY)
                .fillColor(0x30ff0000)
                .strokeWidth(1).fillColor(0x30ff0000));

        //add a smaller circle to the current location
        mMap.addCircle(new CircleOptions()
                .center(USER_LOCATION).radius(80)
                .strokeColor(Color.BLACK)
                .fillColor(0x30ff0000)
                .strokeWidth(1).fillColor(0x30ff0000));



        camUpdate = CameraUpdateFactory.newLatLngZoom(USER_LOCATION, USER_LOCATION_ZOOM); //move the camera to where the object is pointing
        mMap.animateCamera(camUpdate);
        mMap.setOnInfoWindowClickListener(this);

    }

    protected void startLocationUpdates(){
        // Create the location request
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        // Request location updates
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this);
        Log.d("reque", "--->>>>");
    }


    public void queryParkingData(String locationPassed) {

        parsedLocation = locationPassed;
        Log.d("PassedValue"," value: "+parsedLocation);

        if(TextUtils.isEmpty(parsedLocation) || parsedLocation.length() == 0){
            parsedLocation = "UCSD";
        }

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        ref = database.getReference("Schools");

        if(parsedLocation.equals("UCSD") || parsedLocation.equals("ucsd") || parsedLocation.equals("University of California San Diego")){
            Log.d("mydebugger","inside condition");

            ref = database.getReference("Schools/UCSD");
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for(DataSnapshot messageSnapshot : dataSnapshot.getChildren()){
                        ParkingLotDetailsPOJO pojo =  messageSnapshot.getValue(ParkingLotDetailsPOJO.class);
                        parkingLotDetails.add(pojo);
                        Log.d("Fragment_Map","children: " + messageSnapshot.getValue().toString());
                    }
                    updateUI();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

    }

    private void updateUI() {
        //Todo: Update the Google map with the new pojos in the arraylist.



        for(int i = 0; i < parkingLotDetails.size();i++){
            LatLng position = new LatLng(parkingLotDetails.get(i).getLatitude(),parkingLotDetails.get(i).getLongitude());
            Marker parkingLotMarker = mMap.addMarker(new MarkerOptions()
                    .position(position)
                    .title(parkingLotDetails.get(i).getName())
                    .snippet("Capacity: " + parkingLotDetails.get(i).getCapacity())
                    .icon(BitmapDescriptorFactory.defaultMarker(green)));
        }

        progressDialog.dismiss();
    }

    /**
     * Method will contain the ability to find any mLocation from the Geocoder services form the
     * Android Dev kit. Will convert the mLocation entered into a mLocation by the Geocoder and then
     * stored into a List. We will have to catch some exceptions in case 1) There is no connection
     * to the Geocoder or if the user input a mLocation that does not exist.
     *
     * @param locationName will be the mLocation entered into the EditText
     * @param zoomLev will be the desired zoomLevel of the camera
     */
    private void goToPlace(String locationName, float zoomLev){
        // ToDo: find this place using the GeoCoder and go there
        Geocoder geoCoder = new Geocoder(getContext()); // create an instance of Geocoder class
        if(Geocoder.isPresent()){

            try {
                List<Address> addresses = geoCoder.getFromLocationName(locationName,1);
                if(addresses.size() == 0){
                    //if there is no address found
                    Toast.makeText(getContext(),"No Address Found, Please Try Again",Toast.LENGTH_LONG).show();
                    return;
                }
                Address address = addresses.get(0); //Address at top of list
                LatLng location = new LatLng(address.getLatitude(),address.getLongitude()); //store the lat and long from place
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,zoomLev)); //send our user to the mLocation

            } catch (IOException e) {
                //if can't establish connection to Geo Coder
                Toast.makeText(getContext(),"Network Connection Not Found",Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
            catch (IllegalArgumentException e){
                //if null
                Toast.makeText(getContext(),"Please Input A Place",Toast.LENGTH_LONG).show();
            }
        }

    }

    /**
     * Will be executed once the user clicks on the infoWindow.
     *
     * @param marker the Marker from the loaction
     */
    @Override
    public void onInfoWindowClick(Marker marker) {
        //if the infoWindow is clicked

        marker.showInfoWindow();
        Toast.makeText(getContext(), "Info window clicked",
                Toast.LENGTH_SHORT).show();
    }

    /**
     * Will create an instance of the onDataChanged interface
     *
     * @param context the fragment or activity
     */
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
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();




    }

    @Override
    public void onStop() {
        super.onStop();
        if(mGoogleApiClient.isConnected()){
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void sendData(String location) {
        Log.d("LocationCaught", " value: "+location);
        queryParkingData(location);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //help connect the user

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }


        mLocation = locationManager.getLastKnownLocation(locationManager
                .getBestProvider(criteria, false));


        if(mLocation == null){
            startLocationUpdates();
        }
        if (mLocation != null) {
            latitude = mLocation.getLatitude();
            longitude = mLocation.getLongitude();
            USER_LOCATION = new LatLng(latitude, longitude); //create our Lat&Long object

        } else {
            Toast.makeText(getContext(), "Location not Detected", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG," Connection Suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG, "Connection Failed. Error: " +connectionResult.getErrorCode());
    }

    @Override
    public void onLocationChanged(Location location) {

    }


    /**
     * Interface will establish Communication between
     * this fragment and the Containing Activity
     */
    public interface onDataChanged {
        void dataSend(String locationName);
    }


    /*Class to execute background tasks*/
    private class BackGroundTask extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try{
                Thread.sleep(3000); // allow for the transaction to happen
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

    }

}


