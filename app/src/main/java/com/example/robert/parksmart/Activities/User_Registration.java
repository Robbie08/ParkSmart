package com.example.robert.parksmart.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.robert.parksmart.Activities.User_LogIn;
import com.example.robert.parksmart.MainActivity;
import com.example.robert.parksmart.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;


/**
 * Created by Roberto on 7/13/2017.
 */

public class User_Registration extends AppCompatActivity {
    /*Declare Variables*/
    private EditText email, passWord, confirmPassword;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_registration_activity);
        progressDialog = new ProgressDialog(this); //set up progressDialog

        firebaseAuth = FirebaseAuth.getInstance(); //set up FirebaseAuth Object

        /*Initialize Variables*/
        email = (EditText) findViewById(R.id.etEmailLogIn); //connect email et
        passWord = (EditText) findViewById(R.id.etPasswordLogIn); //connect password et
        confirmPassword =(EditText) findViewById(R.id.etPasswordConfirm); //connect et

        /*Check if the user exists and if they are signed in*/
        if(firebaseAuth.getCurrentUser() != null){
            finish(); //close the current activity
            startActivity(new Intent(this,MainActivity.class)); //open MainActivity
        }

    }

    private void registerUser(){
        String stringEmail = email.getText().toString().trim(); //parse et to string
        String stringPassword = passWord.getText().toString().trim(); //parse et to string
        String stringConfirmPassword = confirmPassword.getText().toString();// parse et to string

        /*Check if the Strings are empty or not*/
        if(TextUtils.isEmpty(stringEmail)){
            //email is empty
            Toast.makeText(this,"Please Enter Email",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(stringPassword)){
            //empty password
            Toast.makeText(this,"Please Enter Password",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(stringConfirmPassword)){
            //empty confirm password field
            Toast.makeText(this,"Please Enter Your Password One More Time",Toast.LENGTH_LONG).show();
            return;
        }


        if(Objects.equals(stringConfirmPassword, stringPassword)) {

        /*Set up progress dialog to let users know they are being registered*/
            progressDialog.setMessage("Registering User..."); //set message
            progressDialog.show();

        /*Register the user to server*/
            firebaseAuth.createUserWithEmailAndPassword(stringEmail, stringPassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                        /*Check if the task was successful*/
                            if (task.isSuccessful()) {
                                //user is successfully registered
                                //send to App
                                //diaplay toast to let them know they are successfully registered
                                progressDialog.dismiss();//get rid of progress dialog
                                Toast.makeText(getApplicationContext(), "Registration was successful",
                                        Toast.LENGTH_LONG).show();
                                finish(); //clase the current Activty
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            } else {
                                //Was Not successful
                                Toast.makeText(getApplicationContext(), "Registration was unsuccessful"
                                        , Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }else{
            //empty confirm password field
            Toast.makeText(this,"Passwords do not match, please try again",Toast.LENGTH_LONG).show();
            return;
        }

    }

    public void sendLogIn(View view){
        //OnClick for the sign in button is clicked
        finish(); //close the current Activty
        startActivity(new Intent(this,User_LogIn.class));
    }
    public void register(View view){
        //onClick event that will register the user once the register button is clicked
        registerUser();
    }
}
