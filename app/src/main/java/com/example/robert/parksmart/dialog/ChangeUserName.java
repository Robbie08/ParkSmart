package com.example.robert.parksmart.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.robert.parksmart.R;
import com.example.robert.parksmart.services.AccountServices;
import com.squareup.otto.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ortiz on 11/28/2017.
 */

public class ChangeUserName extends BaseDialog implements View.OnClickListener {


    @BindView(R.id.dialog_change_user_name_editText)
    EditText newName; //Edit text that will contain our new name

    @BindView(R.id.dialog_change_user_name_retry_edit_text)
    EditText newNameRetry; //EditText that will contain re-entry of our new name


    public static ChangeUserName newInstance() {

        Bundle args = new Bundle();
        ChangeUserName fragment = new ChangeUserName();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View rootView = layoutInflater.inflate(R.layout.dialog_change_user_name,null);
        ButterKnife.bind(this,rootView);


        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setView(rootView)
                .setPositiveButton("Change", null)
                .setNegativeButton("Never Mind", null)
                .show();

        Button nButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE); //negative button to cancel
        Button pButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE); //positive button to change the name


        //Set the color of our buttons
        nButton.setTextColor(getResources().getColor(R.color.colorPrimaryDark));//set color of our button to red
        pButton.setTextColor(getResources().getColor(R.color.colorAccent)); //set our color of our button to green

        pButton.setOnClickListener(this);//ready to listen for the click


        return alertDialog;
    }


    @Override
    public void onClick(View v) {

        //update the database with new name
        bus.post(new AccountServices.UpdateUserNameRequest(newName.getText().toString().trim(), newNameRetry.getText().toString().trim()));
    }

    @Subscribe
    public void updateUserName(AccountServices.UpdateUserNameResponse response){
        if(!response.didSucceede()){

            //set our errors if response was not successful
            newName.setError(response.getPropertyError("newName"));
            newNameRetry.setError(response.getPropertyError("newNameRetry"));
            newNameRetry.setError(response.getPropertyError("names"));
            newName.setError(response.getPropertyError("names"));
            newName.setError(response.getPropertyError("fields"));
            newNameRetry.setError(response.getPropertyError("fields"));
            Vibrator v  = (Vibrator) application.getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(50);
        }else {
            //dismiss our dialog
            dismiss();
        }

    }
}
