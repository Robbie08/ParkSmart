package com.example.robert.parksmart.services;

/**
 * Created by ortiz on 10/31/2017.
 */

public class MapServices {

    private MapServices(){}

    public static class UserLocationRequest {
        String location; //Location Lat&Lng
        String ownerName; //owner Name
        String ownerEmail; // owner Email

        public UserLocationRequest(String location, String ownerName, String ownerEmail) {
            this.location = location;
            this.ownerName = ownerName;
            this.ownerEmail = ownerEmail;
        }
    }

    public static class UserLocationResponse{

    }

}
