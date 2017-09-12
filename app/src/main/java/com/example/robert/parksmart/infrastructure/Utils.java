package com.example.robert.parksmart.infrastructure;

import com.example.robert.parksmart.activities.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by ortiz on 9/27/2017.
 */

public class Utils {

    public static final String FIRE_BASE_URL = "https://parksmart-f23cb.firebaseio.com/";
    public static final String FIRE_BASE_USER_REFERENCE = FIRE_BASE_URL +"users/";

    public static final String MY_PREFERENCE = "MY_PREFERENCE";
    public static final String EMAIL = "EMAIL";
    public static final String USERNAME = "USERNAME";

    public static String encodeEmail(String userEmail){

        return userEmail.replace(".",",");
    }

}
