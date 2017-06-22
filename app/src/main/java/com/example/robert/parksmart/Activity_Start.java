package com.example.robert.parksmart;



import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

public class Activity_Start extends AppCompatActivity implements Fragment_Map.onDataChanged {

    Toolbar toolBar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Fragment_Map fragment_map; // Initialize an instance of our Fragment Class
    ActionBarDrawerToggle actionBarDrawerToggle;
    Fragment_Park fragment_park; //create an instance of our Fragment_Park Class
    String recievedValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        toolBar = (Toolbar) findViewById(R.id.toolBar); //initialize toolbar
        setSupportActionBar(toolBar); //add toolbar to application


        /*Start The App in the Map Fragment*/
        fragment_map = new Fragment_Map(); //create an instance of Activity
        getSupportFragmentManager() //make a reference to the getSupportFragmentManager
                .beginTransaction() //start the process
                .add(R.id.fragment_container, fragment_map).commit(); //pass in the fragment into the container

        /*NavigationDrawer Code*/
        navigationView = (NavigationView) findViewById(R.id.navigation_view); //create an instance of NavigationView
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout); //initialize layout
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolBar,R.string.drawer_open,
                R.string.drawer_close); //Create DrawerLayout object
        drawerLayout.setDrawerListener(actionBarDrawerToggle); //set up listener for actionBarDrawerToggle



        /*Navigation DrawerListener to handle any click on the items inside NavDrawer*/
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                switch (item.getItemId()){
                    case R.id.home_id:

                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, fragment_map).commit();// pass in our fragment

                        break;
                    case R.id.park_id:
                        fragment_park = new Fragment_Park(); //create an instance of our Fragment_Park Class
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, fragment_park).commit(); //pass in our fragment
                    default:
                        break;

                }

                drawerLayout.closeDrawers(); //close our navigation drawer after a while
                return true;
            }
        });




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
        Log.d("RESULT-search",locationName);
    }
}
