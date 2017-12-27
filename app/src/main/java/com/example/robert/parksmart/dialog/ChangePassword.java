package com.example.robert.parksmart.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.example.robert.parksmart.R;
import com.example.robert.parksmart.infrastructure.Utils;
import com.example.robert.parksmart.services.AccountServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.otto.Subscribe;

import butterknife.ButterKnife;

/**
 * Created by ortiz on 12/26/2017.
 */

public class ChangePassword extends BaseDialog implements View.OnClickListener{
    public static ChangePassword newInstance() {

        Bundle args = new Bundle();

        ChangePassword fragment = new ChangePassword();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View rootView = layoutInflater.inflate(R.layout.dialog_change_password,null);
        ButterKnife.bind(this,rootView);



        userEmail = getActivity().getSharedPreferences(Utils.MY_PREFERENCE, Context.MODE_PRIVATE).getString(Utils.EMAIL,"");

        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setView(rootView)
                .setPositiveButton("Yes", null)
                .setNegativeButton("Cancel", null)
                .show();

        Button nButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE); //negative button to cancel
        Button pButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE); //positive button to Add location


        //Set the color of our buttons
        nButton.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        pButton.setTextColor(getResources().getColor(R.color.colorAccent));

        pButton.setOnClickListener(this);

        return alertDialog;
    }

    @Override
    public void onClick(View v) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();
        String formatedEmail = Utils.encodeEmail(email);
        assert email != null;

        bus.post(new AccountServices.UpdatePasswordRequest(email));
        dismiss();

    }

    @Subscribe
    public void updateUserPassword(AccountServices.UpdatePasswordResponse response){
        dismiss();
    }
}
