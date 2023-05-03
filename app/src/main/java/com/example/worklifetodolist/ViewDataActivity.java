package com.example.worklifetodolist;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class ViewDataActivity extends AppCompatActivity {

    TextView text;

    @SuppressLint("MissingInflatedId")
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);
        text = findViewById(R.id.result);
        SharedPreferences sharedPreferences = getSharedPreferences("this", Activity.MODE_PRIVATE);
        String data = sharedPreferences.getString("value", null);
        Log.i("data", data);
        if(data != null){
            text.setText(data);

        }else {
            text.setText("No data is passed");
        }
    }

    // When the back button is pressed go to the BoredActivity class.
    public void onBackPressed() {
        Intent intent = new Intent(ViewDataActivity.this, BoredActivity.class) ;
        startActivity(intent);
    }
}
