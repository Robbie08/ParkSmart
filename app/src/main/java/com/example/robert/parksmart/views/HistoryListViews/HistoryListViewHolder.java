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

/**
 * Created by ortiz on 10/26/2017.
 */

public class HistoryListViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.list_history_list_itemName)
    TextView itemName;

    @BindView(R.id.list_history_list_dateCreated)
    TextView dateCreated;

    @BindView(R.id.list_history_list_layout)
    public View layout;

    public HistoryListViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void populate(HistoryList historyList){
        itemName.setText(historyList.getListName());

        if(historyList.getDateCreated().get("timestamp") != null) {
            Log.d("History_List", " list is not null");
            dateCreated.setText(convertTime((long) historyList.getDateCreated().get("timestamp")));
        }
        else {
            Log.d("History_List", " list is null");
        }
    }

    private String convertTime (Long unixTime){
        Date dateObject = new Date(unixTime);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yy kk:mm");
        return simpleDateFormat.format(dateObject);
    }
}

