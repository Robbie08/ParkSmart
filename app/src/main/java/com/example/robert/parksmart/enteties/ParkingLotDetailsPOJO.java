package com.example.robert.parksmart.enteties;

import java.io.Serializable;

/**
 * Created by bruhshua on 7/26/17.
 */

public class ParkingLotDetailsPOJO implements Serializable {

    private String Name;
    private int Capacity;
    private double Latitude;
    private double Longitude;


    public ParkingLotDetailsPOJO(){

    }

    public ParkingLotDetailsPOJO(String name, int capacity) {
        Name = name;
        Capacity = capacity;
    }

    public ParkingLotDetailsPOJO(String name, int capacity, double latitude, double longitude) {
        Name = name;
        Capacity = capacity;
        Latitude = latitude;
        Longitude = longitude;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getCapacity() {
        return Capacity;
    }

    public void setCapacity(int capacity) {
        Capacity = capacity;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }
}
