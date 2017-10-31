package com.example.robert.parksmart.enteties;

import com.google.firebase.database.ServerValue;

import java.util.HashMap;

/**
 * Created by ortiz on 10/10/2017.
 */

public class HistoryList {
    private String id;
    private String listName;
    private String ownerEmail;
    private String ownerName;
    private HashMap<String,Object> dateCreated;
    private HashMap<String,Object> dateLastChanged;

    public HistoryList(){
    }

    public HistoryList(String id, String listName, String userEmail, String ownerName, HashMap<String, Object> dateCreated) {
        this.id = id;
        this.listName = listName;
        this.ownerEmail = userEmail;
        this.ownerName = ownerName;
        this.dateCreated = dateCreated;


        HashMap<String,Object> dateLastChangedObject = new HashMap<>();
        dateLastChangedObject.put("timestamp", ServerValue.TIMESTAMP);

        this.dateLastChanged = dateLastChangedObject;
    }

    public String getId() {
        return id;
    }

    public String getListName() {
        return listName;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public HashMap<String, Object> getDateCreated() {
        //Check just in case list is not instantiated
        if(dateLastChanged != null){
            return dateCreated;
        }

        HashMap<String,Object> dateCreatedObject = new HashMap<>();
        dateCreatedObject.put("timestamp",ServerValue.TIMESTAMP);
        return dateCreatedObject;
    }

    public HashMap<String, Object> getDateLastChanged() {
        return dateLastChanged;
    }
}


