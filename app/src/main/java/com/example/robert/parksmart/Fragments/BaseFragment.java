package com.example.robert.parksmart.Fragments;

import android.app.Activity;

import android.app.Notification;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.text.util.LinkifyCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.robert.parksmart.infrastructure.ParkSmartApplication;
import com.example.robert.parksmart.infrastructure.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.otto.Bus;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by ortiz on 10/10/2017.
 */

public class BaseFragment extends Fragment {
    protected ParkSmartApplication application;
    protected Bus bus;
    protected String  userName;
    protected FirebaseAuth.AuthStateListener authStateListener;
    protected FirebaseAuth auth;
    protected SharedPreferences sharedPreferences;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getId(), container,false);
        ButterKnife.bind(this,view);
        application = (ParkSmartApplication) getActivity().getApplication();
        bus = application.getBus();
        bus.register(this);

         /* Allows us to get the userEmail and userName to all of our Dialog Fragments*/
        //userEmail = getActivity().getSharedPreferences(Utils.MY_PREFERENCE, Context.MODE_PRIVATE).getString(Utils.EMAIL,"");
        userName = getActivity().getSharedPreferences(Utils.MY_PREFERENCE,Context.MODE_PRIVATE).getString(Utils.USERNAME,"");
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}


