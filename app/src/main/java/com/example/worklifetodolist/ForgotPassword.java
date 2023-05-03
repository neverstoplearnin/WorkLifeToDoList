package com.example.worklifetodolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    //Declaring private variables for the EditTextEmail, buttonResetPassword, progressBar.
    private EditText  editTextEmail;
    private Button buttonResetPassword;
    private ProgressBar progressBar;

    //Declaring a FirebaseAuth (Firebase Authentication) variable.
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        //Initializing the variables: editTextEmail, buttonResetPassword, and progressBar.
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        buttonResetPassword = (Button) findViewById(R.id.buttonResetPassword);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //Initializing the auth variable to and instance of FirebaseAuth.
        auth = FirebaseAuth.getInstance();

        buttonResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });
    }

    private void resetPassword(){
        String email = editTextEmail.getText().toString().trim();

        //If the email has nothing entered set an error and focus on the field.
        if(email.isEmpty()){
            editTextEmail.setError("An email is required!");
            editTextEmail.requestFocus();
            return;
        }

        //If the email entered is not the structure of an email set an error and focus on the field.
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Provide a valid email please!");
            editTextEmail.requestFocus();
            return;
        }

        //Make the progressBar show.
        progressBar.setVisibility(View.VISIBLE);
        //Using the firebase authenticator object, send a password reset email with an OnComplete listener (to know when that is done).
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //If the task is successful, tell the user to check their email for the password reset email and make progressBar disappear.
                if (task.isSuccessful()) {
                    Toast.makeText(ForgotPassword.this, "Check your email to reset your password!", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                } else {
                //If the task fails or otherwise, tell the user that something went wrong and make progressBar disappear.
                    Toast.makeText(ForgotPassword.this, "Try again! Something went wrong!", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}