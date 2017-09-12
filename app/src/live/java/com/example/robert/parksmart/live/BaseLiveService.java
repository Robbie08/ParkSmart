package com.example.robert.parksmart.live;

import com.example.robert.parksmart.infrastructure.ParkSmartApplication;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.otto.Bus;

/**
 * Created by ortiz on 9/27/2017.
 */

public class BaseLiveService {

    protected Bus bus;
    protected ParkSmartApplication application;
    protected FirebaseAuth auth;

    public BaseLiveService(ParkSmartApplication application) {
        this.application = application;
        bus = application.getBus();
        bus.register(this);
        auth = FirebaseAuth.getInstance(); //instantiate the firebase auth
    }
}
