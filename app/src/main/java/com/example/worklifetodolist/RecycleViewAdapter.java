package com.example.worklifetodolist;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class RecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    //Declaring context to be used for the popup menu
    private Context context;
    //Declaring an  array list to store data for Character items
    ArrayList<Character> list = new ArrayList<>();
    //Constructor for the context
    public RecycleViewAdapter(Context ctx)
    {
        this.context = ctx;
    }

    public RecycleViewAdapter(FirebaseRecyclerOptions<Character> option) {
    }

    //Array list acts as a container for data
    public void setItems(ArrayList<Character> emp)
    {
        list.addAll(emp);
    }

    protected abstract void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i, Object o);

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        //A view that inflates from the context, inflates the layout of the item.
        View view = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        //Return the view using the CharacterViewHolder class.
        return new CharacterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
            //Casting holder as CharacterViewHolder and calling it viewholder. (holder is passed in parameter above)
            CharacterViewHolder viewholder = (CharacterViewHolder) holder;
            //emp is a Character object and is grabbed from the list (position is passed in parameter above)
            Character emp = list.get(position);
            //Setting the text for editTextACName and editTextACNumber
            viewholder.editTextACName.setText(emp.getName());
            viewholder.editTextACNumber.setText(emp.getNumber());
            //Sets an OnClickListener associated with the txt_option a.k.a. the UI 'edit' and 'remove'
            viewholder.txt_option.setOnClickListener(v->
            {
                //Makes a new popup menu
                PopupMenu popupMenu =new PopupMenu(context,viewholder.txt_option);
                //Inflate the menu
                popupMenu.inflate(R.menu.option_menu);

                //sets an OnClickListener for the popupMenu
                popupMenu.setOnMenuItemClickListener(item->
                {
                    //Switch that handles when the edit or remove options are selected.
                    switch(item.getItemId())
                    {
                        case R.id.menu_edit:
                            //to stop duplication clears the list.
                            list.clear();

                            Intent intent=new Intent(context,ProfileActivity.class);
                            //Is EDIT correct to put here?
                            intent.putExtra("EDIT", emp);
                            context.startActivity(intent);
                            break;
                        case R.id.menu_remove:
                            //to stop duplication clears the list.
                            list.clear();

                            DAOCharacter dao = new DAOCharacter();
                            //Uses getKey to grab the key for the emp Character object.
                            dao.remove(emp.getKey()).addOnSuccessListener(suc->
                            {

                                Toast.makeText(context,"Successfully removed record",Toast.LENGTH_SHORT).show();
                                //Removes the item from the recycler view to reflect the change in the database.
                                notifyItemRemoved(position);
                            }).addOnFailureListener(er->
                            {
                                Toast.makeText(context,""+er.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                            break;
                    }
                    return false;
                });
                //Shows the popup menu
                popupMenu.show();
            });
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }


    public abstract void onDataChanged();
}
