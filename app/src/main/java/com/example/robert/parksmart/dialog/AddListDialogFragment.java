package com.example.robert.parksmart.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.robert.parksmart.R;
import com.example.robert.parksmart.services.HistoryListService;
import com.squareup.otto.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddListDialogFragment extends BaseDialog implements View.OnClickListener {

    @BindView(R.id.dialog_add_list_editText) EditText newLocationName; //editText accepts locationName
    private CoordinatorLayout coordinatorLayout;
    private View mapView;

    public AddListDialogFragment(){}
    public static AddListDialogFragment newInstance(){
        return new AddListDialogFragment();
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View rootView = layoutInflater.inflate(R.layout.dialog_add_list,null);
        //mapView = layoutInflater.inflate(R.layout.fragment_map,null);
        //View mView = layoutInflater.inflate(R.layout.fragment_map,null);

        ButterKnife.bind(this,rootView);

        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setView(rootView)
                .setPositiveButton("Add Location", null)
                .setNegativeButton("Never Mind", null)
                .show();


        Button nButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE); //negative button to cancel
        Button pButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE); //positive button to Add location


        //Set the color of our buttons
        nButton.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        pButton.setTextColor(getResources().getColor(R.color.colorAccent));

        pButton.setOnClickListener(this); //add a on click listener
        return alertDialog;

    }



    @Override
    public void onClick(View view) {

        //post our current information to the event bus
        //pass our locationName, userName, userEmail to
        //the historyListServices to be used by LiveHistoryListServices
        bus.post(new HistoryListService.HistoryListRequest(newLocationName.getText().toString(),userName,userEmail));


    }


    @Subscribe
    public void AddItem(HistoryListService.HistoryListResponse response){

        if(!response.didSucceede()){
            //if the user failed to enter a location name
            newLocationName.setError(response.getPropertyError("listName"));
            Vibrator v  = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(50);
        } else {
            dismiss();
        }
    }

}
