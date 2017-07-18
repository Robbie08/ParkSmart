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

import com.example.robert.parksmart.MainActivity;
import com.example.robert.parksmart.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Roberto on 7/13/2017.
 */

 public class User_LogIn extends AppCompatActivity{
    private EditText emailLogIn, passwordLogIn;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login_activity);

        progressDialog = new ProgressDialog(this);//set up progress dialog

        firebaseAuth = FirebaseAuth.getInstance(); //set up firebaseAuth

        /*initialize XML variables*/
        emailLogIn = (EditText) findViewById(R.id.etEmailLogIn);
        passwordLogIn = (EditText) findViewById(R.id.etPasswordLogIn);

        /*Check if the user exists/signed in*/
        if(firebaseAuth.getCurrentUser() != null){
            //if they do exist
            finish();//close the activity
            startActivity(new Intent(User_LogIn.this,MainActivity.class)); //send user to app
        }



    }

    private void logInUser(){
        /*parse user input*/
        String stringEmailLogIn = emailLogIn.getText().toString().trim();
        String stringPasswordLogIn = passwordLogIn.getText().toString().trim();

        /*check if the user input is null*/
        if(TextUtils.isEmpty(stringEmailLogIn)){
            //if the email is null
            Toast.makeText(getApplicationContext(),"Please Enter a Email",
                    Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(stringPasswordLogIn)){
            //if the password is null
            Toast.makeText(getApplicationContext(),"Please Enter a Password",
                    Toast.LENGTH_LONG).show();
            return;
        }

        /*Prompt the user with Progress Dialog*/
        progressDialog.setMessage("Signing in..."); //set message
        progressDialog.show(); //display

        firebaseAuth.signInWithEmailAndPassword(stringEmailLogIn,stringPasswordLogIn)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            finish();//close the activity
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else{
                            Toast.makeText(getApplicationContext(),
                                    "The Credentials Entered Are Not Correct, Please Try Again",
                                    Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }
    public void logIn(View view){
        logInUser();
    }
    public void sendRegister(View view){
        finish();//end current Activity
        startActivity(new Intent(this,User_Registration.class));
    }
    public void forgotPassword(View view){
        finish();//end current activity
        startActivity(new Intent(this,User_ResetPassword.class)); //send to the reset password activity
    }

}
