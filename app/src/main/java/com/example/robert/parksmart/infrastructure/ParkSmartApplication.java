package com.example.robert.parksmart.infrastructure;

import android.app.Application;

import com.example.robert.parksmart.live.Module;
import com.firebase.client.Firebase;
import com.squareup.otto.Bus;

/**
 * Created by ortiz on 9/25/2017.
 *
 * singleton that will allow easy communication between the Application
 */

public class ParkSmartApplication extends Application {

    private Bus bus;

    public ParkSmartApplication() {
        bus = new Bus();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
        Module.Register(this); //ensure bus gets lauched
    }
    public Bus getBus(){
        return bus;
    }
}
