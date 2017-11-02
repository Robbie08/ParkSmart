package com.example.robert.parksmart.live;

import android.widget.Toast;


import com.example.robert.parksmart.enteties.HistoryList;
import com.example.robert.parksmart.infrastructure.ParkSmartApplication;
import com.example.robert.parksmart.infrastructure.Utils;
import com.example.robert.parksmart.services.HistoryListService;
import com.firebase.client.Firebase;
import com.google.firebase.database.ServerValue;
import com.squareup.otto.Subscribe;

import java.util.HashMap;

public class LiveHistoryListServices extends BaseLiveService {
    public LiveHistoryListServices(ParkSmartApplication application){
        super(application);
    }


    @Subscribe
    public void addItem(final HistoryListService.HistoryListRequest request){
        final HistoryListService.HistoryListResponse response = new HistoryListService.HistoryListResponse();

        if(request.listName.isEmpty()){
            response.setPropertyErrors("listName","Please give a name to the location");
        }

        if(response.didSucceede()){
            //if the task returned no errors
            Firebase reference = new Firebase(Utils.FIRE_BASE_HISTORY_LIST_REFERENCE+request.ownerEmail).push();
            HashMap<String,Object> timeStampCreated = new HashMap<>();
            timeStampCreated.put("timestamp",ServerValue.TIMESTAMP);
            HistoryList historyList = new HistoryList(reference.getKey(),request.listName,Utils.decodeEmail(request.ownerEmail),
                    request.ownerName,timeStampCreated);
            reference.child("id").setValue(historyList.getId());
            reference.child("listName").setValue(historyList.getListName());
            reference.child("ownerEmail").setValue(historyList.getOwnerEmail());
            reference.child("ownerName").setValue(historyList.getOwnerName());
            reference.child("dateCreated").setValue(historyList.getDateCreated());
            reference.child("dateLastChanged").setValue(historyList.getDateLastChanged());


            Toast.makeText(application.getApplicationContext()," Location has been added",
                    Toast.LENGTH_LONG).show();
        }

        bus.post(response);
    }

    @Subscribe
    public void DeleteHistoryItem(HistoryListService.DeleteHistoryListRequest request){
        Firebase ref = new Firebase(Utils.FIRE_BASE_HISTORY_LIST_REFERENCE +request.ownerEmail + "/" +request.historyItemId);
        ref.removeValue();
    }
}

