package com.example.worklifetodolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;

//ProfileActivity is a class
public class ProfileActivity extends AppCompatActivity {
    //Declaring a button type private variable.
    private Button buttonLogOut;
    private Button buttonShowMe;

    private Button buttonBored;
    private Button buttonBored2;

    private EditText editTextACNumber;
    private EditText editTextACName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Declaring a variable that represents the 'buttonLogout' button.
        buttonLogOut = (Button)findViewById(R.id.buttonLogOut);

        buttonBored = (Button)findViewById(R.id.buttonBored);
        buttonBored2 = (Button) findViewById(R.id.buttonBored2);

        //This buttonShowMe has the capacity to update delete create
        Button buttonShowMe = findViewById(R.id.buttonShowMe);
        final EditText editTextACName = findViewById(R.id.editTextACName);
        final EditText editTextACNumber = findViewById(R.id.editTextACNumber);

        Button btn_open = findViewById(R.id.buttonOpen);
        //Sets an OnClickListener on the 'btn_open'
        btn_open.setOnClickListener(v->
        {
            //intent called intent
            Intent intent = new Intent(ProfileActivity.this, RecyclerViewClass.class);
            //starts the intent
            startActivity(intent);
        });
        //Declaring DaoCharacter called dao (database character object)
        DAOCharacter dao =new DAOCharacter();
        //Grabs from RecycleViewAdapter Case
        Character emp_edit = (Character)getIntent().getSerializableExtra("EDIT");

        if(emp_edit !=null)
        {
            buttonShowMe.setText("UPDATE");
            editTextACName.setText(emp_edit.getName());
            editTextACNumber.setText(emp_edit.getNumber());
            //Hides the open button.
            btn_open.setVisibility(View.GONE);
        }
        else
        {
            buttonShowMe.setText("ADD");
            //Makes the open button visible.
            btn_open.setVisibility(View.VISIBLE);
        }

        buttonShowMe.setOnClickListener(v->
        {
            Character character = new Character(editTextACName.getText().toString(),editTextACNumber.getText().toString());
            //If the character object is null.
            if(emp_edit==null)
            {
                dao.add(character).addOnSuccessListener(suc ->
                {

                    Toast toast = Toast.makeText(this, "Successfully inserted record", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL,  0, -1200);
                    toast.show();
                }).addOnFailureListener(er ->
                {
                    Toast toastError = Toast.makeText(this, "" + er.getMessage(), Toast.LENGTH_SHORT);
                    toastError.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL,  0, -1200);
                    toastError.show();
                });
            }
            else
            {
                HashMap<String,Object> hashMap =new HashMap<>();
                hashMap.put("name",editTextACName.getText().toString());
                hashMap.put("number",editTextACNumber.getText().toString());
                //grabs the firebase key for the Character object and adds an OnSuccessListener.
                dao.update(emp_edit.getKey(), hashMap).addOnSuccessListener(suc->
                {

                            Toast.makeText(this,"Successfully updated record",Toast.LENGTH_SHORT).show();
                            //Closes the activity after we have finished updating.
                            finish();
                        }).addOnFailureListener(er->
                        {
                            //Shows an error message upon failure.
                            Toast.makeText(this,""+er.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            }

            //removing the data at the specific spot

        });

        //Sets an onClickListener to the logout button.
        buttonLogOut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Getting an instance of Firebase Authentication and signing out
                FirebaseAuth.getInstance().signOut();
                //Navigates back to the main activity from the profile activity.
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
            }
        });

        //This was moved above
//        Button btn_open = findViewById(R.id.buttonOpen);
//        btn_open.setOnClickListener(v->
//                {
//                    Intent intent = new Intent(ProfileActivity.this, RecyclerViewClass.class);
//                    startActivity(intent);
//                });

        buttonBored.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ProfileActivity.this, BookActivity.class));
            }
        });

        buttonBored2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ProfileActivity.this, BoredActivity.class));
            }
        });
    }
}