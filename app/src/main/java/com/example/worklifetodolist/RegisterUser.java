package com.example.worklifetodolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {

    //Declaration of private variables.
    private TextView banner, registerUser;
    private EditText editTextFullName, editTextEmail, editTextAge, editTextPassword;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        //Variable initialization below

        //Sets mAuth to be an instance of FirebaseAuth. (Firebase Authentication)
        mAuth = FirebaseAuth.getInstance();

        //Sets the banner to the TextView with an id of 'banner'.
        banner = (TextView) findViewById(R.id.banner);
        //Sets an onClickListener for banner.
        banner.setOnClickListener(this);

        //Sets registerUser to the Button with
        registerUser = (Button) findViewById(R.id.registerUser);
        //Sets an onClickListener for registerUser.
        registerUser.setOnClickListener(this);

        //Sets editTextFullName to the EditText with the id of editTextFullName
        editTextFullName = (EditText) findViewById(R.id.editTextFullName);
        editTextAge = (EditText) findViewById(R.id.editTextAge);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }



    @Override
    public void onClick(View v) {
        //Gets the id of the button clicked.
        switch (v.getId()){
            //Redirects to the home page if banner is selected.
            case R.id.banner:
                startActivity(new Intent( this,MainActivity.class));
                break;
            //Calls the registerUser method if 'registerUser' is clicked.
            case R.id.registerUser:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        //String variable for editTextEmail input that gets the text, turns into string and trims excess.
        String email= editTextEmail.getText().toString().trim();
        //String variable for editTextPassword input that gets the text, turns into string and trims excess.
        String password= editTextPassword.getText().toString().trim();
        //String variable for editTextFullName input that gets the text, turns into string and trims excess.
        String fullName= editTextFullName.getText().toString().trim();
        //String variable for editTextAge input that gets the text, turns into string and trims excess.
        String age= editTextAge.getText().toString().trim();

        //If the fullName variable is empty,an error is set and that field is focused on.
        if(fullName.isEmpty()) {
            editTextFullName.setError("Full name is required!");
            editTextFullName.requestFocus();
            return;
        }

        //If the age variable is empty,an error is set and that field is focused on.
        if(age.isEmpty()){
            editTextAge.setError("Age is required!");
            editTextAge.requestFocus();
            return;
        }

        //If the fullName variable is empty,an error is set and that field is focused on.
        if(email.isEmpty()) {
            editTextEmail.setError("An email is required!");
            editTextEmail.requestFocus();
            return;
        }

        //If the entered text does not match the structure of an email,
        //an error is set and the field is highlighted.
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please provide valid email!");
            editTextEmail.requestFocus();
            return;
        }

        //If the password variable is empty,an error is set and that field is focused on.
        if(password.isEmpty()){
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
            return;
        }

        //If the password length is less than 6 characters, an error is set and the password field is focused on.
        if(password.length() < 6){
            editTextPassword.setError("Password needs to be six characters!");
            editTextPassword.requestFocus();
            return;
        }

        //Makes the progressbar show.
        progressBar.setVisibility(View.VISIBLE);
        //Calls firebase authentication object called mAuth and creates a user using strings created for email and password.
        mAuth.createUserWithEmailAndPassword(email, password)
                //Adds an OnCompleteListener.
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //If the task is successful (user registration), create a user object with fullName, age, and email.
                        if (task.isSuccessful()) {
                            //Create user object.
                            User user = new User(fullName, age, email);

                            //Calls firebase database object and gets id of registered user, this connects them together.
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    //OnCompleteListener checks if data has been inserted into the database.
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            //If the task is successful (user registration worked)
                                            if (task.isSuccessful()) {
                                                //Prompt a toast that says 'successful!' message.
                                                Toast.makeText(RegisterUser.this, "User registration successful!", Toast.LENGTH_LONG).show();
                                                //Makes the progress bar disappear off the screen.
                                                progressBar.setVisibility(View.GONE);

                                                //redirect to Login Layout
                                            //If something else happens (ex. unsuccessful registration)
                                            } else {
                                                //Prompt a toast that says 'failed' message.
                                                Toast.makeText(RegisterUser.this, "User registration failed try again!", Toast.LENGTH_LONG).show();
                                                //No longer shows the progress bar.
                                                progressBar.setVisibility(View.GONE);
                                            }
                                        }
                                    });

                         //If something else happens (ex. unsuccessful registration)
                        }else {
                            //Prompt a toast that says 'failed' message.
                            Toast.makeText(RegisterUser.this, "User registration failed! Retry!", Toast.LENGTH_LONG).show();
                            //No longer shows the progress bar.
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}