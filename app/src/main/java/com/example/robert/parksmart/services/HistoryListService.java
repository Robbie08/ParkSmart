package com.example.robert.parksmart.services;

import android.support.design.widget.CoordinatorLayout;
import android.view.View;

import com.example.robert.parksmart.infrastructure.ServiceResponse;

/**
 * Created by ortiz on 10/10/2017.
 */

public class HistoryListService {
    public HistoryListService() {
    }

    public static class HistoryListRequest{

        public String listName;
        public String ownerName;
        public String ownerEmail;


        public HistoryListRequest(String listName, String ownerName, String ownerEmail) {
            this.listName = listName;
            this.ownerName = ownerName;
            this.ownerEmail = ownerEmail;
        }
    }

    public static class HistoryListResponse extends ServiceResponse{
    }

    public static class DeleteHistoryListRequest{
        public String ownerEmail;
        public String historyItemId;

        public DeleteHistoryListRequest(String ownerEmail, String historyItemId) {
            this.ownerEmail = ownerEmail;
            this.historyItemId = historyItemId;
        }
    }
}
