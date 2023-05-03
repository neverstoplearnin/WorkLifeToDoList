package com.example.worklifetodolist;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

//Creating a public class that implements Serializable
public class Character implements Serializable {


    //Not saving this key field into the firebase database so use exclude.
    @Exclude
    private String key;
    //Declaring name and number
    private String name;
    private String number;

    public Character(){}
    public Character(String name, String number)
    {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
    //new
    public String getKey() {
        return key;
    }
    //new
    public void setKey(String key) {
        this.key = key;
    }
}
