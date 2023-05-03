package com.example.worklifetodolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    //Making private textview,edittext, and button
    private TextView register, forgotPassword;
    private EditText editTextEmail, editTextPassword;
    private Button signIn;

    // Making private progressBar and FirebaseAuth (Firebase Authentication)
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Sets register to the textview with the 'register' id.
        register = (TextView) findViewById(R.id.register);
        //Sets an onClickListener for register
        register.setOnClickListener(this);

        //Sets signIn to the Button with 'button' id.
        signIn = (Button) findViewById(R.id.button);
        //Sets onClickListener for signIn
        signIn.setOnClickListener(this);

        //Sets editTextEmail to the EditText with an id of 'email'.
        editTextEmail = (EditText) findViewById(R.id.email);
        //Sets  editTextPassword to the EditText with an id of 'password'.
        editTextPassword = (EditText) findViewById(R.id.password);
        //Sets progressBar to the ProgressBar with an id of progressBar
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //Sets mAuth to an instance of FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        //Sets forgotPassword to the TextView with an id of forgotPassword.
        forgotPassword = (TextView) findViewById(R.id.forgotPassword);
        //Sets an OnClickListener to forgotPassword
        forgotPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //A switch that is used to log in the user or register them depending on the case
        switch (v.getId()){
            //Navigates the user to register if register is clicked.
            case R.id.register:
                startActivity(new Intent(this, RegisterUser.class));
                break;
            //Does a user login when the button is pressed.
            //used 'button instead of signIn'
            case R.id.button:
                userLogin();
                break;

            //Navigates to the ForgotPassword class when 'forgotPassword' is clicked.
            case R.id.forgotPassword:
                startActivity(new Intent(this, ForgotPassword.class));
                break;
        }
    }

    //Validates that fields are entered correctly when attempting login.
    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        //If the email field is empty an error is set and the email field is focused on.
        if(email.isEmpty()){
            editTextEmail.setError("Email is required!");
            editTextEmail.requestFocus();
            return;
        }

        //Checks if the email entered does not have the structure of an email, if not it is focused on.
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Provide a valid email please!");
            editTextEmail.requestFocus();
            return;
        }

        //Checks if anything is entered for the password field and sets and error
        //& focus on the password field if not.
        if(password.isEmpty()){
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
            return;
        }

        //If the password entered is less than 6 characters then an error is set and the
        //password field is focused on.
        if(password.length() < 6){
            editTextPassword.setError("Minimum password length is 6 characters!");
            editTextPassword.requestFocus();
            return;
        }
        //Shows the progress bar circling by making it 'VISIBLE'.
        progressBar.setVisibility(View.VISIBLE);

        //Signs in using given email and password.
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //redirect to user profile
                    startActivity(new Intent(MainActivity.this, ProfileActivity.class));

                }else{
                    //Prompt a toast that says 'failed' message.
                    Toast.makeText(MainActivity.this, "Failed to login! Please check your credentials", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
