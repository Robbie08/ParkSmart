package com.example.robert.parksmart.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.example.robert.parksmart.R;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Roberto on 7/18/2017.
 */

public class SplashScreenActivity extends BaseActivity {
        private FirebaseAuth firebaseAuth;
        private static int SPLASH_TIME_OUT = 2500;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.splash_screen);


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //handle what happens after our timeout
                    Intent i = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                    finish();
                }

            }, SPLASH_TIME_OUT);
        }
    }
