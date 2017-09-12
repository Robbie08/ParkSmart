package com.example.robert.parksmart.services;

import com.example.robert.parksmart.infrastructure.ServiceResponse;

/**
 * Created by ortiz on 10/10/2017.
 */

public class HistoryListService {
    public HistoryListService() {
    }

    public static class HistoryListRequest{

        public String historyListName;
        public String ownerName;
        public String ownerEmail;

        public HistoryListRequest(String historyListName, String ownerName, String ownerEmail) {
            this.historyListName = historyListName;
            this.ownerName = ownerName;
            this.ownerEmail = ownerEmail;
        }
    }

    public static class HistoryListResponse extends ServiceResponse{
    }
}
