package com.example.worklifetodolist;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//Creating CharacterViewHolder class that extends the RecyclerView.ViewHolder
public class CharacterViewHolder extends RecyclerView.ViewHolder
{

    //Declaring public textview: editTextACName and editTextACNumber as TextView
    public TextView editTextACName, editTextACNumber, txt_option;
    //Declaring CharacterViewHolder with itemView parameter which is required using @NonNull
    public CharacterViewHolder(@NonNull View itemView)
    {
        //Refers to the parent class constructor in other words
        // CharacterViewHolder is a subclass of RecyclerView.ViewHolder
        super(itemView);
        //Textviews are found using the id
        editTextACName = itemView.findViewById(R.id.editTextACName);
        editTextACNumber = itemView.findViewById(R.id.editTextACNumber);
        txt_option = itemView.findViewById(R.id.txt_option);
    }
}
