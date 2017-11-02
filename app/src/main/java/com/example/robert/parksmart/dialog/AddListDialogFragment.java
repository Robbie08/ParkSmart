package com.example.robert.parksmart.dialog;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.example.robert.parksmart.R;
import com.example.robert.parksmart.enteties.HistoryList;
import com.example.robert.parksmart.services.HistoryListService;
import com.squareup.otto.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ortiz on 10/10/2017.
 */

public class AddListDialogFragment extends BaseDialog implements View.OnClickListener {

    @BindView(R.id.dialog_add_list_editText)
    EditText newLocationName;

    public AddListDialogFragment(){}
    public static AddListDialogFragment newInstance(){
        return new AddListDialogFragment();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View rootView = layoutInflater.inflate(R.layout.dialog_add_list,null);
        ButterKnife.bind(this,rootView);

        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setView(rootView)
                .setPositiveButton("Add Location", null)
                .setNegativeButton("Never Mind", null)
                .show();


        Button nButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        Button pButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);


        nButton.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        pButton.setTextColor(getResources().getColor(R.color.colorAccent));

        pButton.setOnClickListener(this);
        return alertDialog;

    }



    @Override
    public void onClick(View view) {

        bus.post(new HistoryListService.HistoryListRequest(newLocationName.getText().toString(),userName,userEmail));

    }

    @Subscribe
    public void AddItem(HistoryListService.HistoryListResponse response){
        if(!response.didSucceede()){
            newLocationName.setError(response.getPropertyError("listName"));
            Vibrator v  = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(50);
        } else {
            dismiss();
        }
    }

}
