package com.example.robert.parksmart;



import android.Manifest;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.robert.parksmart.Activities.User_LogIn;
import com.example.robert.parksmart.Fragments.Fragment_Map;
import com.example.robert.parksmart.Fragments.Fragment_Park;
import com.example.robert.parksmart.Fragments.Fragment_RecentLocations;
import com.example.robert.parksmart.Fragments.Fragment_SchoolsList;
import com.example.robert.parksmart.JavaBeans.AdapterSchoolNameCardView;
import com.example.robert.parksmart.JavaBeans.SchoolName;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Fragment_Map.onDataChanged, SearchView.OnQueryTextListener {

    private Toolbar toolBar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Fragment_Map fragment_map; // Initialize an instance of our Fragment Class
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Fragment_RecentLocations fmRecentLocations;
    private Fragment_Park fragment_park; //create an instance of our Fragment_Park Class
    private String recievedValue, passedLocation;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private Location mLocation;
    private Bundle bundle;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private Fragment_SchoolsList fragSchoolList;

    /*Create our list that we will pass in to the RecyclerView*/
    public String[] schoolNames = {"University of California, San Diego", "University of San Diego",
            "California State University, San Marcos", "San Diego State University"};
    RecyclerView recyclerViewSchools;
    AdapterSchoolNameCardView adapterSchoolNameCardView;
    RecyclerView.LayoutManager layoutManagerSchool;
    ArrayList<SchoolName> schoolList = new ArrayList<>();

    private final String schoolTest = "UCSD";

    //TEST IS WRITTEN


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        toolBar = (Toolbar) findViewById(R.id.toolBar); //initialize toolbar
        setSupportActionBar(toolBar); //add toolbar to application
//        recyclerViewSchools = (RecyclerView) findViewById(R.id.recyclerViewSchools);
//        layoutManagerSchool = new LinearLayoutManager(this);


        progressDialog = new ProgressDialog(this); // will create a new ProgressDialog object
        firebaseAuth = FirebaseAuth.getInstance(); // connect our FirebaseAuth by getting the currentInstance
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE); //

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                Log.i("Location", location.toString()); //log current location
                Log.i("My current Location", mLocation.toString()); // get current location

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        /*Check if the user is singed in */
        if (firebaseAuth.getCurrentUser() == null) {

            //if the user is not signed in, finish the current Activity
            finish();
            startActivity(new Intent(this, User_LogIn.class)); //send user to the SignIn Activity

        }

        /*Check if device is running SDK < 23*/
        if (Build.VERSION.SDK_INT < 23) {
            // Permission is not needed on versions lower than SDK 23
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener); // update the users current mLocation
            mLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER); //get the users mLocation one time

        } else {
              /*Check if the user has given permission*/
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //if we dont have permission we will have to ask for it
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1); //ask the user for permission
            } else {
                //if permission has been granted previously
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                mLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER); //get the users mLocation one time

            }
        }

        /* Start The App in the Map Fragment */
        fragment_map = Fragment_Map.newInstance(); //Input desired data.
        getSupportFragmentManager() //make a reference to the getSupportFragmentManager
                .beginTransaction() //start the process
                .add(R.id.fragment_container, fragment_map).commit(); //pass in the fragment into the container

        /*NavigationDrawer Code*/
        navigationView = (NavigationView) findViewById(R.id.navigation_view); //create an instance of NavigationView
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout); //initialize layout
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolBar, R.string.drawer_open,
                R.string.drawer_close); //Create DrawerLayout object
        drawerLayout.setDrawerListener(actionBarDrawerToggle); //set up listener for actionBarDrawerToggle



        /*Navigation DrawerListener to handle any click on the items inside NavDrawer*/
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                switch (item.getItemId()) {

                    case R.id.home_id:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, fragment_map).commit();// pass in our fragment

                        break;
                    case R.id.park_id:
                        fragment_park = Fragment_Park.newInstance();//Input desired data, set data parameters
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, fragment_park).commit(); //pass in our fragment
                        break;
                    case R.id.recent_id:
                        fmRecentLocations = Fragment_RecentLocations.newInstance();
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, fmRecentLocations).commit();
                        break;
                    case R.id.signout_id:
                        progressDialog.setMessage("Signing Out...");
                        progressDialog.show();
                        logUserOut();
                        break;

                    default:
                        break;

                }

                drawerLayout.closeDrawers(); //close our navigation drawer after a while
                return true;
            }
        });


    }

    /**
     * Method that weill log use out. Will call the singOut() from the FirebaseAuth
     * Lib and then will perfrom a check to see if the user is still logged in our not.
     * Using the getCurrentUser() from the FirebaseAuth Lib we will get the current user,
     * if the method returns null then that means the user is successfully lgged off. In
     * the given event that this method executes successfully the current Activity will
     * close and then creating a new Instance the user will be sent to the LogIn Activity.
     * <p>
     * If the user could not be signed our successgfully then a Toast MSG will appear and
     * let the user know that he was not able to be logged out successfully and dismiss the
     * progress and return.
     */
    private void logUserOut() {

        try {
            firebaseAuth.signOut();
            if (firebaseAuth.getCurrentUser() == null) {
                finish();
                Intent i = new Intent(MainActivity.this, User_LogIn.class);
                startActivity(i);
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Singed out Successfully",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Could not sign out, please try again",
                        Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
                return;
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Could not sign out, please try again",
                    Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
            return;

        }

    }

    /**
     * Method  will ask for permission to use the Phones Current GPS mLocation
     * if they have not done granted permssion before
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     **/

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

             /*Check if the user has given permission*/
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                //if we do have permission we will get the users mLocation
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                mLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER); //get the users mLocation one time

            }

        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }


    @Override
    public void dataSend(String locationName) {
        //override the abstract method
        recievedValue = locationName;
        Log.d("RESULT-search", locationName);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);
        //final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setInputType(InputType.TYPE_CLASS_TEXT);
        searchView.setQueryHint("Enter School...");
        searchView.setOnQueryTextListener(this);
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_search:

                fragSchoolList = Fragment_SchoolsList.newInstance();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, fragSchoolList).commit();

                //call the list
                // Retrieve the SearchView and plug it into SearchManager
//                Fragment_Map map = (Fragment_Map) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
//                map.queryParkingData(schoolTest); // call the queryParkingData() that will search for the school chosen
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {


        return false;
    }
}
