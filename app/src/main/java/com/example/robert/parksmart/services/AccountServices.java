package com.example.robert.parksmart.services;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
<<<<<<< HEAD
import android.support.design.widget.CoordinatorLayout;
=======
>>>>>>> origin/restruct_model_controller

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
<<<<<<< HEAD
        public CoordinatorLayout coordinatorLayout;

        public RegisterUserRequest(String userEmail,String userName,ProgressDialog progressDialog, CoordinatorLayout coordinatorLayout ){
            this.userEmail = userEmail;
            this.userName = userName;
            this.progressDialog = progressDialog;
            this.coordinatorLayout = coordinatorLayout;
=======

        public RegisterUserRequest(String userEmail,String userName,ProgressDialog progressDialog ){
            this.userEmail = userEmail;
            this.userName = userName;
            this.progressDialog = progressDialog;
>>>>>>> origin/restruct_model_controller
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
<<<<<<< HEAD
        public CoordinatorLayout coordinatorLayout;


        public LogUserInRequest(String userEmail, String userPassword, ProgressDialog progressDialog, SharedPreferences sharedPreferences, CoordinatorLayout coordinatorLayout) {
=======
        public Context context;


        public LogUserInRequest(String userEmail, String userPassword, ProgressDialog progressDialog, SharedPreferences sharedPreferences) {
>>>>>>> origin/restruct_model_controller
            this.userEmail = userEmail;
            this.userPassword = userPassword;
            this.progressDialog = progressDialog;
            this.sharedPreferences = sharedPreferences;
<<<<<<< HEAD
            this.coordinatorLayout = coordinatorLayout;
=======
>>>>>>> origin/restruct_model_controller
        }
    }
    public static class LogUserInResponse extends ServiceResponse{
        //empty constructor for handling the Login by extending our ServiceResponse class
    }


    public static class ResetUserPasswordRequest{
        public String userEmail;
        public ProgressDialog progressDialog;
<<<<<<< HEAD
        public CoordinatorLayout coordinatorLayout;

        public ResetUserPasswordRequest(String userEmail, ProgressDialog progressDialog, CoordinatorLayout coordinatorLayout) {
            this.userEmail = userEmail;
            this.progressDialog = progressDialog;
            this.coordinatorLayout = coordinatorLayout;
=======

        public ResetUserPasswordRequest(String userEmail, ProgressDialog progressDialog) {
            this.userEmail = userEmail;
            this.progressDialog = progressDialog;
>>>>>>> origin/restruct_model_controller
        }
    }
    public static class ResetUserPasswordResponse extends ServiceResponse{
        //empty constructor for handling the Login by extending our ServiceResponse class
    }

}
