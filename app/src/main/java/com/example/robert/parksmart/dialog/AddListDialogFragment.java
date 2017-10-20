package com.example.robert.parksmart.dialog;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.robert.parksmart.R;
import com.example.robert.parksmart.services.HistoryListService;

import butterknife.BindView;
import butterknife.ButterKnife;

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
                .setPositiveButton("Create", null)
                .setNegativeButton("Cancel", null)
                .show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(this);
        return alertDialog;

    }

    @Override
    public void onClick(View view) {
        bus.post(new HistoryListService.HistoryListRequest(newLocationName.getText().toString(),userName,userEmail));

    }

}
