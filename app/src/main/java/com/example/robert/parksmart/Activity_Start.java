package com.example.robert.parksmart;



import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class Activity_Start extends AppCompatActivity {

    Toolbar toolBar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        toolBar = (Toolbar) findViewById(R.id.toolBar); //initialize toolbar
        setSupportActionBar(toolBar); //add toolbar to application


        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout); //initialize layout
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolBar,R.string.drawer_open,
                R.string.drawer_close); //Create DrawerLayout object

        drawerLayout.setDrawerListener(actionBarDrawerToggle); //set up listener for actionBarDrawerToggle


    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }
}
