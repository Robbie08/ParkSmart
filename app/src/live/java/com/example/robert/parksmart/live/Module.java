package com.example.robert.parksmart.live;

import com.example.robert.parksmart.infrastructure.ParkSmartApplication;

/**
 * Created by ortiz on 9/27/2017.
 */

public class Module {

    public static void Register(ParkSmartApplication application){
        new LiveAccountServices(application);
        new LiveHistoryListServices(application);
    }
}
