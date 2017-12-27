package com.example.robert.parksmart.Fragments;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.robert.parksmart.R;
import com.example.robert.parksmart.activities.MainActivity;
import com.example.robert.parksmart.dialog.ChangePassword;
import com.example.robert.parksmart.dialog.ChangeUserName;
import com.example.robert.parksmart.dialog.DeleteHistoryItemDialogFragment;
import com.example.robert.parksmart.infrastructure.Utils;
import com.example.robert.parksmart.services.AccountServices;
import com.firebase.client.DataSnapshot;
import com.firebase.client.authentication.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ortiz on 11/22/2017.
 */


public class Fragment_Settings extends BaseFragment {

    private View rootView;
    private String decodedEmail;
    private ValueEventListener mUserNameQuery;


    @BindView(R.id.fragment_settings_user_email)
    TextView text_view_user_email;

    @BindView(R.id.fragment_settings_user_name)
    TextView text_view_user_name;

    @BindView(R.id.fragment_settings_edit_email)
    ImageView editEmail;

    @BindView(R.id.fragment_settings_edit_password)
    ImageView editPassword;

    @BindView(R.id.fragment_settings_edit_user_name)
    ImageView editUserName;

    public static Fragment_Settings newInstance() {

        Bundle args = new Bundle();
        Fragment_Settings fragment = new Fragment_Settings();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_settings,container,false);
        ButterKnife.bind(this,rootView);
        updateUI();



        /*


        Make sure to save instance state so the UI can already be updated instead of having to wait to load


         */


        ((MainActivity) getActivity()).setActionBarTitle("Settings");
        return rootView;
    }


    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        String email = user.getEmail();
        String formatedEmail = Utils.encodeEmail(email);
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(formatedEmail).child("name");

        mUserNameQuery = databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {

                String updatedName = dataSnapshot.getValue().toString();
                text_view_user_name.setText(updatedName);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(application,"Event Failed" ,Toast.LENGTH_LONG).show();
            }
        });

    }

    public void updateUI(){
        //Grab data from reference and declare values
        userEmail = getActivity().getSharedPreferences(Utils.MY_PREFERENCE, Context.MODE_PRIVATE).getString(Utils.EMAIL,"");
        userName = getActivity().getSharedPreferences(Utils.MY_PREFERENCE,Context.MODE_PRIVATE).getString(Utils.USERNAME,"");
        decodedEmail  = Utils.decodeEmail(userEmail); //format email
        try{
            //set our editText to whatever is retrieved from Firebase
            text_view_user_email.setText(decodedEmail); //set user email to the text view in settings
            text_view_user_name.setText(userName); //set user name to the text view in settings
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    @OnClick(R.id.fragment_settings_edit_user_name)
    public void setEditUserName(){

        DialogFragment dialogFragment = ChangeUserName.newInstance();
        dialogFragment.show(getActivity().getFragmentManager(),ChangeUserName.class.getSimpleName());

    }

    //set up onclick event listener to edit password
    @OnClick(R.id.fragment_settings_edit_password)
    public void setEditPassword(){

        DialogFragment dialogFragment = ChangePassword.newInstance();
        dialogFragment.show(getActivity().getFragmentManager(),ChangePassword.class.getSimpleName());
    }

    //set up onClick listener to edit Email
    @OnClick(R.id.fragment_settings_edit_email)
    public void setEditEmail(){
        Toast.makeText(getContext(),"Edit Email Worked",Toast.LENGTH_LONG).show();
    }



}
