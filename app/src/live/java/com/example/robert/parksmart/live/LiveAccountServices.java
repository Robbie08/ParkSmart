package com.example.robert.parksmart.live;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.robert.parksmart.R;
import com.example.robert.parksmart.activities.LoginActivity;
import com.example.robert.parksmart.activities.MainActivity;
import com.example.robert.parksmart.enteties.User;
import com.example.robert.parksmart.infrastructure.ParkSmartApplication;
import com.example.robert.parksmart.infrastructure.Utils;
import com.example.robert.parksmart.services.AccountServices;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.squareup.otto.Subscribe;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by ortiz on 9/27/2017.
 *
 * Will handle request made to Firebase
 */


/**
 * Will handle the event where the user is trying to Register and account with ParkSmart. Will first
 * check that the user has input a String value into the editText for email and name. If the user
 * has not, a property error will be set under the email and userName field. If no errors are set
 * it will create a random password for the user and call the createUserWithEmailAndPassword(email,password).
 * Keep in mind that since there is no field for the user to create their own password the handler will
 * use the autogenerated password to create their account. The reason behind this idea is so that we
 * know that everyone on here is 100% verified, otherwise anyone can just type in an email and password
 * and create an account regardless if the email address is their or not. This method will add more
 * security to the user. Once the random password is passed, the handler will create the user under
 * the FirebaseAuth secontion on Firebase. Next, the sendPasswordResetEmail(email) method will be
 * called which is what will send the user an email to change their password so they can now log in.
 * If this is successful the handler will try and create a node called user and store the user information
 * under that node as their email being the proceeding node that will hold their information.
 */
public class LiveAccountServices extends BaseLiveService {
    public LiveAccountServices(ParkSmartApplication application) {
        super(application);
    }

    @Subscribe
    public void RegisterUser(final AccountServices.RegisterUserRequest request) {
        //AccountServices.RegisterUserResponse response = new AccountServices.RegisterUserRequest();
        final AccountServices.RegistersUserResponse response = new AccountServices.RegistersUserResponse();


        if (request.userEmail.isEmpty()) {
            response.setPropertyErrors("email", "Please add your email");
        }
        if (request.userName.isEmpty()) {
            response.setPropertyErrors("userName", "Please add your name");
        }
        if (response.didSucceede()) {
            //Toast.makeText(application.getApplicationContext(),"User will be registered shortly",Toast.LENGTH_LONG).show();
            request.progressDialog.show();

            SecureRandom random = new SecureRandom();
            final String randomPassword = new BigInteger(32, random).toString(); //make a random secure password

            auth.createUserWithEmailAndPassword(request.userEmail, randomPassword)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                request.progressDialog.dismiss();
                                Snackbar snackbar = Snackbar.make(request.coordinatorLayout, task.getException().getMessage(), Snackbar.LENGTH_LONG);
                                snackbar.show();
                                Log.d("EMAIL: ", request.userEmail);

                            } else {
                                auth.sendPasswordResetEmail(request.userEmail)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (!task.isSuccessful()) {
                                                    request.progressDialog.dismiss();
                                                    Toast.makeText(application.getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();

                                                } else {
                                                    Firebase reference = new Firebase(Utils.FIRE_BASE_USER_REFERENCE + Utils.encodeEmail(request.userEmail));

                                                    HashMap<String, Object> timeJoined = new HashMap<>();
                                                    timeJoined.put("dateJoined", ServerValue.TIMESTAMP);

                                                    reference.child("email").setValue(request.userEmail);
                                                    reference.child("name").setValue(request.userName);
                                                    reference.child("hasLoggedInWithPassword").setValue(false);
                                                    reference.child("timeJoined").setValue(timeJoined);


                                                    Toast.makeText(application.getApplicationContext(), "Please Check Your Email", Toast.LENGTH_LONG).show();
                                                    request.progressDialog.dismiss();

                                                    /*Once user has registered in correctly it will send them to the main activity*/
                                                    Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(request.context, android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
                                                    Intent intent = new Intent(application.getApplicationContext(), LoginActivity.class);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    application.startActivity(intent, bundle);
                                                }
                                            }
                                        });
                            }
                        }
                    });
        }

        bus.post(response);
    }


    /**
     * Will handle the event where the user is trying to LogIn. First it will check if the user
     * has input a String value into the editText for email and password, if they have not then
     * a property error will be set for the "email" and "password" field. If there are no property
     * errors then  signInWithEmailAndPassword(email,password) will be called as an onCompleteListener.
     * Considering that it succeeded the user will have successfully logged on.
     *
     * @param request
     */
    @Subscribe
    public void LogInUser(final AccountServices.LogUserInRequest request) {

        final AccountServices.LogUserInResponse response = new AccountServices.LogUserInResponse();

        if (request.userEmail.isEmpty()) {
            response.setPropertyErrors("email", "Please enter your email");
        }

        if (request.userPassword.isEmpty()) {
            response.setPropertyErrors("password", "Please enter your password");
        }

        if (response.didSucceede()) {
            request.progressDialog.show();
            auth.signInWithEmailAndPassword(request.userEmail, request.userPassword)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                request.progressDialog.dismiss();
                                Snackbar snackbar = Snackbar.make(request.coordinatorLayout, task.getException().getMessage(), Snackbar.LENGTH_LONG);
                                snackbar.show();
                                Log.d("EMAIL: ", request.userEmail);
                                Log.d("PASSWORD: ", request.userPassword);
                            } else {
                                final Firebase userLocation = new Firebase(Utils.FIRE_BASE_USER_REFERENCE + Utils.encodeEmail(request.userEmail));
                                userLocation.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        //when user logs in
                                        User user = dataSnapshot.getValue(User.class);
                                        if (user != null) {

//                                            Snackbar snackbar = Snackbar.make(request.context,"Welcome !",Snackbar.LENGTH_LONG);
//                                            snackbar.show();
                                            Toast.makeText(application.getApplicationContext(), "Successfully Logged In!", Toast.LENGTH_LONG).show();
                                            userLocation.child("hasLoggedInWithPassword").setValue(true);
                                            SharedPreferences sharedPreferences = request.sharedPreferences;
                                            sharedPreferences.edit().putString(Utils.EMAIL, Utils.encodeEmail(user.getEmail())).apply();
                                            sharedPreferences.edit().putString(Utils.USERNAME, user.getName()).apply();

                                            request.progressDialog.dismiss();

                                            /*Once user has logged in correctly it will send them to the main activity*/

                                            Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(request.context, R.anim.fade_in, R.anim.fade_out).toBundle();
                                            Intent intent = new Intent(application.getApplicationContext(), MainActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            application.startActivity(intent, bundle);
                                        } else {
                                            request.progressDialog.dismiss();
                                            Snackbar snackbar = Snackbar.make(request.coordinatorLayout, "Failed to connect to server, Please try again later", Snackbar.LENGTH_LONG);
                                            snackbar.show();
                                        }
                                    }


                                    @Override
                                    public void onCancelled(FirebaseError firebaseError) {
                                        request.progressDialog.dismiss();
                                        Snackbar snackbar = Snackbar.make(request.coordinatorLayout, firebaseError.getMessage(), Snackbar.LENGTH_LONG);
                                        snackbar.show();
                                    }
                                });
                            }
                        }
                    });

        }
        bus.post(response);

    }

    /**
     * Will handle the event where the user is trying to reset their password from the
     * ForgotPasswordActivity.java class. It will check to see if the user has input any
     * String in the editText, if not then it will set a property error for the email
     * reference. Will call the sendPasswordResetEmail(@param) method from the FirebaseAuth
     * library.
     *
     * @param request
     */
    @Subscribe
    public void ResetUserPassword(final AccountServices.ResetUserPasswordRequest request) {
        AccountServices.ResetUserPasswordResponse response = new AccountServices.ResetUserPasswordResponse();

        if (request.userEmail.isEmpty()) {
            response.setPropertyErrors("email", "Please enter your email");
        }

        if (response.didSucceede()) {
            request.progressDialog.show();
            auth.sendPasswordResetEmail(request.userEmail)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (!task.isSuccessful()) {
                                //if the email is not recognized by firebase
                                request.progressDialog.dismiss();
                                Snackbar snackbar = Snackbar.make(request.coordinatorLayout, task.getException().getMessage(), Snackbar.LENGTH_LONG);
                                snackbar.show();
                            } else {
                                request.progressDialog.dismiss();
                                Toast.makeText(application.getApplicationContext(), "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(application.getApplicationContext(), LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                application.startActivity(intent);
                            }
                        }
                    });
        }
        bus.post(response);
    }

    @Subscribe
    public void UpdateUserName(final AccountServices.UpdateUserNameRequest request){
        AccountServices.UpdateUserNameResponse response = new AccountServices.UpdateUserNameResponse();


        if(request.newName.isEmpty()){
            response.setPropertyErrors("newName", "Please enter your new name");
        }

        if(request.newNameRetry.isEmpty()){
            response.setPropertyErrors("newNameRetry", "Please enter your password");
        }

        if(!Objects.equals(request.newName, request.newNameRetry)){
            response.setPropertyErrors("names" , "Please make sure the two entries match");
        }

        if((request.newName.isEmpty()) && (request.newNameRetry.isEmpty())){
            response.setPropertyErrors("fields","Please make sure to fill out both fields");
        }

        if(response.didSucceede()){

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


            if(user != null){
                //user is signed in
                String email = user.getEmail();
                String formatedEmail = Utils.encodeEmail(email);
                assert email != null;
                try {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(formatedEmail).child("name");
                    databaseReference.setValue(request.newName);
                    Toast.makeText(application.getApplicationContext()," Name has been changed successfully",
                            Toast.LENGTH_LONG).show();
                }catch (NullPointerException e){
                    e.printStackTrace();
                    Toast.makeText(application,"Error connecting to server",Toast.LENGTH_LONG).show();
                }
            }


        }
        bus.post(response);

    }

    @Subscribe
    public void UpdatePassword(final AccountServices.UpdatePasswordRequest request){

        auth.sendPasswordResetEmail(request.userEmail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()) {
                            //if the email is not recognized by firebase
                            Toast.makeText(application.getApplicationContext(), "Something went wrong! Please try again later", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(application.getApplicationContext(), "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}

