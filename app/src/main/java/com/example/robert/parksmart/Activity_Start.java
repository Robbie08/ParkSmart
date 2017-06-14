package com.example.robert.parksmart;



import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class Activity_Start extends AppCompatActivity {

    Toolbar toolBar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        toolBar = (Toolbar) findViewById(R.id.toolBar); //initialize toolbar
        setSupportActionBar(toolBar); //add toolbar to application

        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout); //initialize layout
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolBar,R.string.drawer_open,
                R.string.drawer_close); //Create DrawerLayout object

        drawerLayout.setDrawerListener(actionBarDrawerToggle); //set up listener for actionBarDrawerToggle

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.home_id:
                        Activity_Map activity_map = new Activity_Map();
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, activity_map).commit();
                        break;
                    case R.id.park_id:
                        Activity_Park activity_park = new Activity_Park(); //create an instance of our Activity_Park Class
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container,activity_park).commit(); //pass in our fragment
                    default:
                        break;

                }
                drawerLayout.closeDrawers();
                return true;
            }
        });



    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();

    }
}
