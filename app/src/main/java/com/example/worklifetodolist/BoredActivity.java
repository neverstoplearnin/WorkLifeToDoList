package com.example.worklifetodolist;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.RequiresPermission;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class BoredActivity extends AppCompatActivity {

    //Declaring ListView, ArrayList, string, and JSONObject.
    ListView display;
    //    Button result;
    ArrayList<String> data;
    String json_result;
    JSONObject object;
    int next;

    //Creates a mutable sequence of characters out of data that is read.
    private String readData(Reader reader) throws IOException {
        StringBuilder builder = new StringBuilder();

        while( (next = reader.read()) != -1){
            builder.append((char) next);

        }
        return builder.toString();
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bored);

        //Checking the software version of the device is higher than 9.
        if (Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        display = findViewById(R.id.display);

        //Initialising data as a ArrayList.
        data = new ArrayList<String>();
        String url = "https://www.boredapi.com/api/activity";
        try {
            InputStream stream = new URL(url).openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            json_result = readData(reader);
            JSONObject json_data = new JSONObject(json_result);
            if(json_data.getString("link") == ""){
                data.add("activity: " + json_data.getString("activity"));
                data.add("accessibility: " + json_data.getString("accessibility"));
                data.add("type: " + json_data.getString("type"));
                data.add("participants: " + json_data.getString("participants"));
                data.add("price: " + json_data.getString("price"));
                data.add("link: " + json_data.getString("link"));
                data.add("key: " + json_data.getString("key"));
            } else {
                data.add("activity: " + json_data.getString("activity"));
                data.add("accessibility: " + json_data.getString("accessibility"));
                data.add("type: " + json_data.getString("type"));
                data.add("participants: " + json_data.getString("participants"));
                data.add("price: " + json_data.getString("price"));
                data.add("key: " + json_data.getString("key"));
            }
            Log.i("data", data.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        data.add(json_result);
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,  data);
        display.setAdapter(adapter);
//        display.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
//                String value = (String) adapter.getItemAtPosition(position);
//                Log.i("itemclick", value);
//                SharedPreferences.Editor shared_preferences = getSharedPreferences("this", Activity.MODE_PRIVATE).edit();
//                shared_preferences.putString("value", value);
//                shared_preferences.apply();
//                Intent intent = new Intent(BoredActivity.this, ViewDataActivity.class);
//                startActivity(intent);
//
//            }
//        });
    }

    //Closes the activity when the back button is pressed.
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }
}