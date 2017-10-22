package com.example.robert.parksmart.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextClock;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.example.robert.parksmart.R;
import com.example.robert.parksmart.infrastructure.Utils;
import com.example.robert.parksmart.services.AccountServices;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.otto.Subscribe;


/**
 * Created by Roberto on 7/13/2017.
 */

 public class LoginActivity extends BaseActivity{

    @BindView(R.id.activity_login_send_registration_activity)
    TextView sendRegistrationActivity;

    @BindView(R.id.activity_login_test_forgot_password)
    TextView testForgotPassword;

    @BindView(R.id.activity_login_signin_button)
    Button login;

    @BindView(R.id.activity_login_password_editText)
    EditText etPassword;

    @BindView(R.id.activity_login_email_editText)
    EditText etEmail;


    private ProgressDialog mProgressDialog;

    private SharedPreferences sharedPreferences;

    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login_activity);
        ButterKnife.bind(this); //bind with ButterKnife

        /*create progress dialog*/
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Loading...");
        mProgressDialog.setMessage("Attempting to Log User In");
        mProgressDialog.setCancelable(false); //user cant dismiss this
        sharedPreferences = getSharedPreferences(Utils.MY_PREFERENCE, Context.MODE_PRIVATE);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);


    }

    /*Method will send user to the Registration Activity*/
    @OnClick(R.id.activity_login_send_registration_activity)
    public void setSendRegistrationActivity(){
        startActivity(new Intent(this,RegisterActivity.class));
        finish();
    }

    @OnClick(R.id.activity_login_test_forgot_password)
    public void setTestForgotPassword(){
        startActivity(new Intent(this,ForgotPasswordActivity.class));
        finish();
    }

    /* Button that will log the user in via Firebase authentication and also */
    @OnClick(R.id.activity_login_signin_button)
    public void setSignInButton(){

        String email = etEmail.getText().toString().trim();
        String packagedEmail = email.toLowerCase();
        /*Call our constructor from the AccountService.LogUserInRequest(email,password,progressDialog)*/
        bus.post(new AccountServices.LogUserInRequest(packagedEmail,etPassword.getText().toString().trim(),
                mProgressDialog,sharedPreferences, coordinatorLayout,getApplicationContext()));
    }


    /*Will keep track of errors*/
    @Subscribe
    public void LogUserIn(AccountServices.LogUserInResponse response){
        if(!response.didSucceede()){
            /* If there are any errors, prompt the user with the errors*/
            etEmail.setError(response.getPropertyError("email"));
            etPassword.setError(response.getPropertyError("password"));
        }
    }


}

