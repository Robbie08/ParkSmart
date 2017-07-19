package com.example.robert.parksmart.JavaBeans;

/**
 * Created by Roberto on 7/20/2017.
 */

public class LocationCardView {


    public LocationCardView(String name, String time, String date){
        /*call the setter methods for our instance variables*/
        this.setDate(date);
        this.setName(name);
        this.setTime(time);
    }


    private String name,time,date;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



}
