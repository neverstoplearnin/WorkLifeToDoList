package com.example.worklifetodolist;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;

public class DAOCharacter
{
    private DatabaseReference databaseReference;
    public DAOCharacter()
    {
        FirebaseDatabase db =FirebaseDatabase.getInstance();
        databaseReference = db.getReference(Character.class.getSimpleName());
    }

    public Task<Void> add(Character character)
    {
        return databaseReference.push().setValue(character);
    }

    public Task<Void> update(String key, HashMap<String,Object> hashMap)
    {
        return databaseReference.child(key).updateChildren(hashMap);
    }

    public Task<Void> remove(String key)
    {
        return databaseReference.child(key).removeValue();
    }

    //Created a public query that orders by key and limits to 8.
    public Query get(String key)
    {
        if(key == null)
        {
            return databaseReference.orderByKey().limitToFirst(8);
        }
        //Start after the key is known.
        return databaseReference.orderByKey().startAfter(key).limitToFirst(8);
    }

    //Database 4
    public Query get()
    {
        return databaseReference;
    }
}
