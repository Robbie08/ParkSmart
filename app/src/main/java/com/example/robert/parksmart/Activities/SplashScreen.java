package com.example.robert.parksmart.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.robert.parksmart.MainActivity;
import com.example.robert.parksmart.R;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Roberto on 7/18/2017.
 */

public class SplashScreen extends AppCompatActivity {
        private FirebaseAuth firebaseAuth;
        private static int SPLASH_TIME_OUT = 2500;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.splash_screen);

            firebaseAuth = FirebaseAuth.getInstance(); //create an instance of the FirebaseAuth class




//            //create an action bar object that will utilize the support methods
//            ActionBar actionBar = getSupportActionBar();
//            //Hides our action bar via java and not .XML
//            assert actionBar != null;
//            actionBar.hide();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                      /*Check if the user exists/signed in*/
                    if(firebaseAuth.getCurrentUser() != null){
                        //if they do exist
                        finish();//close the activity
                        startActivity(new Intent(SplashScreen.this,MainActivity.class)); //send user to app
                    }else{
                        finish();
                        startActivity(new Intent(SplashScreen.this, User_LogIn.class));

                    }
                }

            }, SPLASH_TIME_OUT);
        }
    }
