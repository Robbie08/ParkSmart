package com.example.robert.parksmart.Fragments;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.robert.parksmart.dialog.DeleteHistoryItemDialogFragment;
import com.example.robert.parksmart.enteties.HistoryList;

import com.example.robert.parksmart.infrastructure.Utils;
import com.example.robert.parksmart.R;
import com.example.robert.parksmart.views.HistoryListViews.HistoryListViewHolder;
import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseRecyclerAdapter;

import butterknife.ButterKnife;

public class Fragment_RecentLocations extends BaseFragment {

    View view;
    RecyclerView recyclerView;
    FirebaseRecyclerAdapter adapter;
    Context ctx;
    LinearLayoutManager manager;
    String userEmail;

    public  Fragment_RecentLocations(){}

    public static Fragment_RecentLocations newInstance(){

        Fragment_RecentLocations fragment_recentLocations = new Fragment_RecentLocations();
        Bundle args = new Bundle();
        fragment_recentLocations.setArguments(args);
        return fragment_recentLocations;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recent_location, container,false);
        ButterKnife.bind(this,view);
        recyclerView = (RecyclerView) view.findViewById(R.id.history_list_recycler_view);

        return view;


    }


    @Override
    public void onResume() {
        super.onResume();
        ctx = getContext();
        manager = new LinearLayoutManager(ctx);
        userEmail = getActivity().getSharedPreferences(Utils.MY_PREFERENCE, Context.MODE_PRIVATE).getString(Utils.EMAIL,"");



        Firebase historyListRef = new Firebase(Utils.FIRE_BASE_HISTORY_LIST_REFERENCE +userEmail);

        adapter = new FirebaseRecyclerAdapter<HistoryList, HistoryListViewHolder>(HistoryList.class,
                R.layout.list_history_list,
                HistoryListViewHolder.class,
                historyListRef) {

            @Override
            protected void populateViewHolder(HistoryListViewHolder historyListViewHolder, final HistoryList historyList, int i) {
                historyListViewHolder.populate(historyList);
                historyListViewHolder.setIsRecyclable(true);
                historyListViewHolder.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getContext().getApplicationContext(), historyList.getListName() +
                                " was clicked", Toast.LENGTH_LONG).show();
                    }
                });

                historyListViewHolder.layout.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if(userEmail.equals(Utils.encodeEmail(historyList.getOwnerEmail()))){
                            DialogFragment dialogFragment = DeleteHistoryItemDialogFragment.newInstance(historyList.getId(),true);
                            dialogFragment.show(getActivity().getFragmentManager(),DeleteHistoryItemDialogFragment.class.getSimpleName());
                            return true;
                        } else {
                            Toast.makeText(getActivity().getApplicationContext(),"Only the owner can delete this item",Toast.LENGTH_LONG).show();
                            return true;
                        }
                    }
                });


            }
        };


        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

    }


    @Override
    public void onPause() {
        super.onPause();
        adapter.cleanup();
    }
}
