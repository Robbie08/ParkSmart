package com.example.robert.parksmart.live;

import com.example.robert.parksmart.infrastructure.ParkSmartApplication;
import com.example.robert.parksmart.infrastructure.Utils;
import com.example.robert.parksmart.services.HistoryListService;
import com.fasterxml.jackson.databind.cfg.HandlerInstantiator;
import com.firebase.client.Firebase;
import com.google.firebase.database.ServerValue;
import com.squareup.otto.Subscribe;

import java.util.HashMap;

/**
 * Created by ortiz on 10/12/2017.
 */

public class LiveHistoryListServices extends BaseLiveService {
    public LiveHistoryListServices(ParkSmartApplication application){
        super(application);
    }


    @Subscribe
    public void addItem(final HistoryListService.HistoryListRequest request){
        final HistoryListService.HistoryListResponse response = new HistoryListService.HistoryListResponse();

        if(request.ownerEmail.isEmpty()){
            //if null is passed to the owner email
            response.setPropertyErrors("EMAIL","No email found");
        }

        if(request.ownerName.isEmpty()){
            //if null is passed to the owner Name
            response.setPropertyErrors("USERNAME","No user name was found");
        }

        if(response.didSucceede()){
            //if the task returned no errors

            Firebase reference = new Firebase(Utils.FIRE_BASE_USER_REFERENCE );

            /*Add timeStamp to HashMap*/
            HashMap<String, Object> timeJoined = new HashMap<>();
            timeJoined.put("dateLocationAdded", ServerValue.TIMESTAMP);

            /*Create and populate our path*/
            reference.child("child").setValue(request.ownerEmail);
            reference.child("listName").setValue(request.historyListName);
            //reference.child("location").setValue(request.location);
            reference.child("name").setValue(request.ownerEmail);
            reference.child("timeJoined").setValue(timeJoined);



            /*

            Connect Floating action bar with the AddListDialogFragment

            */



        }
    }
}
