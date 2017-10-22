package com.example.robert.parksmart.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
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
 * Created by Roberto on 7/13/2017.
 */

public class RegisterActivity extends BaseActivity {


    /*Button that will call handler for registration*/
    @BindView(R.id.activity_register_register_button)
    Button bRegister;

    /*TextView will send user to the Login Activity*/
    @BindView(R.id.activity_register_send_login_activity)
    TextView tvSendLogin;

    /*EditText that will hold the users Email*/
    @BindView(R.id.activity_registration_email)
    EditText userEmail;

    /*EditText that will hold the usersName*/
    @BindView(R.id.activity_registration_user_name)
    EditText userName;


    ProgressDialog mProgressDialog;
    private CoordinatorLayout coordinatorLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_registration_activity);
        ButterKnife.bind(this); //bind our butterknife

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Loading...");
        mProgressDialog.setMessage("Attempting to Register Account");
        mProgressDialog.setCancelable(false); //user cant dismiss this

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout_registration);
    }

    /*send user back to the registration activity*/
    @OnClick(R.id.activity_register_send_login_activity)
    public void sendLogIn(View view){
        startActivity(new Intent(this,LoginActivity.class));
        overridePendingTransition(android.R.anim.slide_out_right,android.R.anim.slide_in_left );
        finish();


    }

    /*Button that will register the user  */
    @OnClick(R.id.activity_register_register_button)
    public void setbRegister(){
        String email = userEmail.getText().toString().trim();
        String packagedEmail = email.toLowerCase();
        bus.post(new AccountServices.RegisterUserRequest(packagedEmail,userName.getText().toString(),mProgressDialog,coordinatorLayout, this));

    }

    /*Connected with event bus*/
    @Subscribe
    public void RegisterUser(AccountServices.RegistersUserResponse response){
        if(!response.didSucceede()){
            //implying that there are errors since it did not return true
            userEmail.setError(response.getPropertyError("email")); //calls the error response from Account Services
            userName.setError(response.getPropertyError("userName"));//calls the error response from Account Services

        }
    }
}
