package com.example.worklifetodolist;
//A user class
public class User {

    //Declaring public String variables
    public String fullName, age, email;

    //A User constructor.
    public User(){

    }

    //A User constructor that takes in parameters (fullName, age, email).
    public User(String fullName, String age, String email){
        //Initializing string variables.
        this.fullName = fullName;
        this.age = age;
        this.email = email;
    }
}
