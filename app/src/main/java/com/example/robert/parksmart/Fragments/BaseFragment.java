package com.example.robert.parksmart.Fragments;

import android.content.Context;
import android.location.Criteria;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.robert.parksmart.infrastructure.ParkSmartApplication;
import com.example.robert.parksmart.infrastructure.Utils;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.GoogleMap;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.otto.Bus;

import butterknife.ButterKnife;


public class BaseFragment extends Fragment{
    protected ParkSmartApplication application;
    protected Bus bus;
    protected String  userName;
    protected String userEmail;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getId(), container,false);
        ButterKnife.bind(this,view);
        application = (ParkSmartApplication) getActivity().getApplication();
        bus = application.getBus();
        bus.register(this);

        /* Allows us to get the userEmail and userName to all of our Fragments*/
        userEmail = getActivity().getSharedPreferences(Utils.MY_PREFERENCE, Context.MODE_PRIVATE).getString(Utils.EMAIL,"");
        userName = getActivity().getSharedPreferences(Utils.MY_PREFERENCE,Context.MODE_PRIVATE).getString(Utils.USERNAME,"");


        return view;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}


