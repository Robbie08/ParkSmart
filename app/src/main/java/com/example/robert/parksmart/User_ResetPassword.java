package com.example.robert.parksmart;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Roberto on 7/15/2017.
 */

public class User_ResetPassword extends AppCompatActivity {
    private EditText inputEmail;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_reset_password_activity);

        inputEmail = (EditText) findViewById(R.id.etResetEmail);
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();

    }

    public void resetUser(){
        String email = inputEmail.getText().toString().trim();

        /*check if the user has put something in the email field*/
        if(TextUtils.isEmpty(email)){
            Toast.makeText(getApplicationContext(),"Please input your email",Toast.LENGTH_LONG).show();
            return;
        }

        /*Verify if the user has an account and if they do or not handle*/

        progressDialog.setMessage("Attempting to send reset instructions to email...");
        progressDialog.show();

        firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(User_ResetPassword.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(getApplicationContext(),User_LogIn.class));
                        }else{
                            Toast.makeText(User_ResetPassword.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        progressDialog.dismiss();
                    }
                });
    }
    public void resetPassword(View view){
        //onclick send instructions to user
        resetUser();
    }

    public void backToLogin(View view){
        //onclick send back to log in
        finish();
        startActivity(new Intent(User_ResetPassword.this,User_LogIn.class)); //send back to log in
    }
}
