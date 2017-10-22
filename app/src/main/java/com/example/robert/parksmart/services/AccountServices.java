package com.example.robert.parksmart.services;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.CoordinatorLayout;

import com.example.robert.parksmart.infrastructure.ServiceResponse;

/**
 * Created by ortiz on 9/25/2017.
 *
 * request and response class for our autobus
 */

public class AccountServices {
    private AccountServices() {
    }

    public static class RegisterUserRequest{
        public String userEmail;
        public String userName;
        public ProgressDialog progressDialog;
        public CoordinatorLayout coordinatorLayout;
        public Context context;

        public RegisterUserRequest(String userEmail,String userName,ProgressDialog progressDialog, CoordinatorLayout coordinatorLayout,Context context ){
            this.userEmail = userEmail;
            this.userName = userName;
            this.progressDialog = progressDialog;
            this.coordinatorLayout = coordinatorLayout;
            this.context = context;
        }


    }
    public static class RegistersUserResponse extends ServiceResponse {
        //empty constructor for handling the Login by extending our ServiceResponse class
    }

    public static class LogUserInRequest{
        public String userEmail;
        public String userPassword;
        public ProgressDialog progressDialog;
        public SharedPreferences sharedPreferences;
        public CoordinatorLayout coordinatorLayout;
        public Context context;


        public LogUserInRequest(String userEmail, String userPassword, ProgressDialog progressDialog, SharedPreferences sharedPreferences, CoordinatorLayout coordinatorLayout,
        Context context) {
            this.userEmail = userEmail;
            this.userPassword = userPassword;
            this.progressDialog = progressDialog;
            this.sharedPreferences = sharedPreferences;
            this.coordinatorLayout = coordinatorLayout;
            this.context = context;
        }
    }
    public static class LogUserInResponse extends ServiceResponse{
        //empty constructor for handling the Login by extending our ServiceResponse class
    }


    public static class ResetUserPasswordRequest{
        public String userEmail;
        public ProgressDialog progressDialog;
        public CoordinatorLayout coordinatorLayout;
        public Context context;

        public ResetUserPasswordRequest(String userEmail, ProgressDialog progressDialog, CoordinatorLayout coordinatorLayout, Context context) {
            this.userEmail = userEmail;
            this.progressDialog = progressDialog;
            this.coordinatorLayout = coordinatorLayout;
            this.context = context;
        }
    }
    public static class ResetUserPasswordResponse extends ServiceResponse{
        //empty constructor for handling the Login by extending our ServiceResponse class
    }

}
