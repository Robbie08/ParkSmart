package com.example.robert.parksmart.activities;



import android.Manifest;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.robert.parksmart.R;
import com.example.robert.parksmart.Fragments.Fragment_Map;
import com.example.robert.parksmart.Fragments.Fragment_RecentLocations;
import com.example.robert.parksmart.Fragments.Fragment_SchoolsList;
import com.example.robert.parksmart.infrastructure.Utils;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class MainActivity extends BaseActivity implements Fragment_Map.onDataChanged{

    private Toolbar toolBar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Fragment_Map fragment_map; // Initialize an instance of our Fragment Class
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Fragment_RecentLocations fmRecentLocations;
    private String recievedValue, passedLocation;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private Location mLocation;
    private Bundle bundle;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private Fragment_SchoolsList fragSchoolList;
    private String mText;
    private String schoolNameString;

    //TESTING GIT BASH COMMIT
    //TEST IS WRITTEN



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolBar = (Toolbar) findViewById(R.id.toolBar); //initialize toolbar
        setSupportActionBar(toolBar); //add toolbar to application



        progressDialog = new ProgressDialog(this); // will create a new ProgressDialog object
        firebaseAuth = FirebaseAuth.getInstance(); // connect our FirebaseAuth by getting the currentInstance
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE); //

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                Log.i("Location", location.toString()); //log current location

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
            startActivity(new Intent(this, LoginActivity.class)); //send user to the SignIn Activity

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
        drawerLayout.addDrawerListener(actionBarDrawerToggle); //set up listener for actionBarDrawerToggle



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
                    case R.id.recent_id:
                        fmRecentLocations = Fragment_RecentLocations.newInstance();
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, fmRecentLocations).commit();

                        break;
                    case R.id.signout_id:
                        SharedPreferences sharedPreferences2 = getSharedPreferences(Utils.MY_PREFERENCE, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences2.edit();
                        editor.putString(Utils.EMAIL,null).apply();
                        editor.putString(Utils.USERNAME,null).apply();
                        try {
                            auth.signOut();
                            Toast.makeText(getApplicationContext(), "Successfully signed out!", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(MainActivity.this, LoginActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                            finish();
                        }catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Did not log off correctly!", Toast.LENGTH_LONG).show();

                        }
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
        final MenuItem menuItem = menu.findItem(R.id.action_search);


        MenuItemCompat.setOnActionExpandListener(menuItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {

                        Log.d("SchoolSet","school: " +newText); // test value passed
                        try{
                            fragSchoolList = Fragment_SchoolsList.newInstance();
                            fragSchoolList.searchQuery(newText);
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fragment_container, fragSchoolList).commit();
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                        return false;
                    }
                });
                SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
                searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {

                try {
                    fragment_map = new Fragment_Map(); //Input desired data.
                    getSupportFragmentManager() //make a reference to the getSupportFragmentManager
                            .beginTransaction() //start the process
                            .add(R.id.fragment_container, fragment_map).commit(); //pass in the fragment into the container


                } catch (Exception e){
                    e.printStackTrace();
                    return false;
                }
                return true;
            }
        });


        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_search:
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }


    public interface SearchSchoolList{
        public void searchQuery(String val);
    }
}
