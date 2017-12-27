package com.example.robert.parksmart.views.HistoryListViews;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.robert.parksmart.R;
import com.example.robert.parksmart.enteties.HistoryList;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryListViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.list_history_list_itemName) TextView itemName; //holds our itemName

    @BindView(R.id.list_history_list_dateCreated) TextView dateCreated; //Holds the date created

    @BindView(R.id.list_history_list_layout) public View layout; //is the layout

    public HistoryListViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView); //bind our butterknife
    }

    public void populate(HistoryList historyList){
        itemName.setText(historyList.getListName()); //set our text to be list name

        if(historyList.getDateCreated().get("timestamp") != null) {
            //only if the timestamp is null
            Log.d("History_List", " list is not null");
            dateCreated.setText(convertTime((long) historyList.getDateCreated().get("timestamp")));
        }
        else {
            Log.d("History_List", " list is null");
        }
    }

    private String convertTime (Long unixTime){
        Date dateObject = new Date(unixTime);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yy kk:mm"); //set our format for out Unixtime
        return simpleDateFormat.format(dateObject); // change from UnixTime to human time
    }
}

