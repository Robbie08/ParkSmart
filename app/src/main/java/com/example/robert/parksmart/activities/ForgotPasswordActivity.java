package com.example.robert.parksmart.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.robert.parksmart.R;
import com.example.robert.parksmart.services.AccountServices;
import com.squareup.otto.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Roberto on 7/15/2017.
 */

public class ForgotPasswordActivity extends BaseActivity {

    @BindView(R.id.activity_forgotpassword_email_editText)
    EditText userEmail;

    /*ResetPassowrd Button*/
    @BindView(R.id.activity_forgotpassword_resetpassword_button)
    Button resetPasswordButton;

    /*Back To Login activity TextView clickable*/
    @BindView(R.id.activity_forgotpassword_back_textview_clickable)
    TextView backToLogin;

    private ProgressDialog mProgressDialog;
    private CoordinatorLayout coordinatorLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_reset_password_activity);
        ButterKnife.bind(this);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Loading...");
        mProgressDialog.setMessage("Attempting To Find Users Information");
        mProgressDialog.setCancelable(false); //user cant dismiss this

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout_forgot_password);

    }


    /*OnClick event for the forgot password button*/
    @OnClick(R.id.activity_forgotpassword_resetpassword_button)
    public void setResetPasswordButton(){
        String email = userEmail.getText().toString().trim();
        String packagedEmail = email.toLowerCase();
        bus.post(new AccountServices.ResetUserPasswordRequest(packagedEmail,mProgressDialog,coordinatorLayout,this));
    }


    /*OnClick event for the back button */
    @OnClick(R.id.activity_forgotpassword_back_textview_clickable)
    public void setBackToLogin(TextView textView){
        //onclick send back to log in
        startActivity(new Intent(ForgotPasswordActivity.this,LoginActivity.class)); //send back to log in
        finish();
    }

    /*Will handle if the user logged in correctly or not based on the @Subscribe from ResetUserPasswordResponse*/
    @Subscribe
    public void ResetUserPassword(AccountServices.ResetUserPasswordResponse response){
        if(!response.didSucceede()){
            userEmail.setError(response.getPropertyError("email"));
            Vibrator v  = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(50);
        }
    }


}
