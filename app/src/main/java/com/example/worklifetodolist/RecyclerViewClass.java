package com.example.worklifetodolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;

//Used RecyclerViewClass as file name although it's technically an activity bc it's an android class
public class RecyclerViewClass extends AppCompatActivity
{
    //Declaring swipeRefreshLayout
    SwipeRefreshLayout swipeRefreshLayout;
    //RecyclerView changed, it was accessing the class instead of the widget, so renamed class
    //to get it to access the widget instead and specified RecyclerView as a class for clarity.
    RecyclerView recyclerView;
    //Declaring adapter
   // RecycleViewAdapter adapter;
    //Declaring dao
    DAOCharacter dao;
    //Defining isLoading as false;
    boolean isLoading=false;
    //Declaring a null key
    String key =null;

    //Database 4
    FirebaseRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        //swipeRefreshLayout being set to widget with id of swip (used in activity_recycler_view.xml)
        swipeRefreshLayout = findViewById(R.id.swip);
        //recyclerView being set to widget with id of rv (used in activity_recycler_view.xml)
        recyclerView = findViewById(R.id.rv);
        //Size is fixed (set as constant)
        recyclerView.setHasFixedSize(true);
        //Set the manager to the RecyclerView
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        //adapter is a RecyclerViewAdapter
        // commented this out because I do it later adapter = new RecycleViewAdapter(this);
        //The adapter for recyclerView is set as adapter
        //recyclerView.setAdapter(adapter);
        dao = new DAOCharacter();

        //Database 4
        FirebaseRecyclerOptions<Character> option =
                new FirebaseRecyclerOptions.Builder<Character>()
                        .setQuery(dao.get(), new SnapshotParser<Character>() {
                            @NonNull
                            @Override
                            public Character parseSnapshot(@NonNull DataSnapshot snapshot) {
                                Character emp = snapshot.getValue(Character.class);
                                emp.setKey(snapshot.getKey());
                                return emp;
                            }
                        }).build();
        adapter = new FirebaseRecyclerAdapter(option) {

            @Override
            protected void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i, Object o) {
                //Casting holder as CharacterViewHolder and calling it viewholder. (holder is passed in parameter above)
                CharacterViewHolder viewholder = (CharacterViewHolder) viewHolder;
                //emp is a Character object and is grabbed from the list (position is passed in parameter above)
                Character emp = (Character) o;
                //Setting the text for editTextACName and editTextACNumber
                viewholder.editTextACName.setText(emp.getName());
                viewholder.editTextACNumber.setText(emp.getNumber());
                //Sets an OnClickListener associated with the txt_option a.k.a. the UI 'edit' and 'remove'
                viewholder.txt_option.setOnClickListener(v ->
                {
                    //Makes a new popup menu
                    PopupMenu popupMenu = new PopupMenu(RecyclerViewClass.this, viewholder.txt_option);
                    //Inflate the menu
                    popupMenu.inflate(R.menu.option_menu);

                    //sets an OnClickListener for the popupMenu
                    popupMenu.setOnMenuItemClickListener(item ->
                    {
                        //Switch that handles when the edit or remove options are selected.
                        switch (item.getItemId()) {
                            case R.id.menu_edit:
                                //to stop duplication clears the list.
                                //no longer works in here list.clear();

                                Intent intent = new Intent(RecyclerViewClass.this, ProfileActivity.class);
                                //Is EDIT correct to put here?
                                intent.putExtra("EDIT", emp);
                                startActivity(intent);
                                break;
                            case R.id.menu_remove:
                                //to stop duplication clears the list.
                                // no longer works in here list.clear();

                                DAOCharacter dao = new DAOCharacter();
                                //Uses getKey to grab the key for the emp Character object.
                                dao.remove(emp.getKey()).addOnSuccessListener(suc ->
                                {

                                    Toast.makeText(RecyclerViewClass.this, "Successfully removed record", Toast.LENGTH_SHORT).show();
                                    //Removes the item from the recycler view to reflect the change in the database.
                                    // no longer necessary notifyItemRemoved(position);
                                }).addOnFailureListener(er ->
                                {
                                    Toast.makeText(RecyclerViewClass.this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                                break;
                        }
                        return false;
                    });
                    //Shows the popup menu
                    popupMenu.show();
                });
            }

            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                //A view that inflates from the context, inflates the layout of the item.
                View view = LayoutInflater.from(RecyclerViewClass.this).inflate(R.layout.item, parent, false);
                //Return the view using the CharacterViewHolder class.
                return new CharacterViewHolder(view);
            }

            @Override
            public void onDataChanged() {
                //Toast.makeText(RecyclerViewClass.this, "Data changed", Toast.LENGTH_SHORT).show();
            }
        };
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        adapter.stopListening();
    }
}