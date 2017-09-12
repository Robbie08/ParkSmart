package com.example.robert.parksmart.dialog;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

import com.example.robert.parksmart.infrastructure.ParkSmartApplication;
import com.example.robert.parksmart.infrastructure.Utils;
import com.squareup.otto.Bus;

/**
 * Created by ortiz on 9/25/2017.
 *
 * Base dialog that will be set as our model Dialog
 */

public class BaseDialog extends DialogFragment {

    protected ParkSmartApplication application;
    protected Bus bus;
    protected String userEmail,userName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = (ParkSmartApplication) getActivity().getApplication();
        bus = application.getBus();
        bus.register(this);

        /* Allows us to get the userEmail and userName to all of our Dialog Fragments*/
        userEmail = getActivity().getSharedPreferences(Utils.MY_PREFERENCE, Context.MODE_PRIVATE).getString(Utils.EMAIL,"");
        userName = getActivity().getSharedPreferences(Utils.MY_PREFERENCE,Context.MODE_PRIVATE).getString(Utils.USERNAME,"");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
    }
}
