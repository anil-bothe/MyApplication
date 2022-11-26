package com.example.myapplication;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.db.ConfigDB;
import com.example.myapplication.model.Notes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wdullaer.swipeactionadapter.SwipeActionAdapter;
import com.wdullaer.swipeactionadapter.SwipeDirection;

import java.util.ArrayList;


public class MainActivity extends ListActivity {
    FloatingActionButton mAddFab;
    ListView todoListView;
    ConfigDB note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todoListView = findViewById(android.R.id.list);
        mAddFab = findViewById(R.id.add_fab);

        note = new ConfigDB(this);
        ArrayList<Notes> notesArray = note.getAllNotes();

        ArrayAdapter arrayAdapter = new NotesAdapter(this, R.layout.custom_list_view, notesArray);
        todoListView.setAdapter(arrayAdapter);

        SwipeActionAdapter mAdapter = new SwipeActionAdapter(arrayAdapter);
        mAdapter.setListView(getListView());
        setListAdapter(mAdapter);

        mAdapter.addBackground(SwipeDirection.DIRECTION_NORMAL_LEFT,R.layout.row_bg_left);

        // Listen to swipes
        mAdapter.setSwipeActionListener(new SwipeActionAdapter.SwipeActionListener(){
            @Override
            public boolean hasActions(int position, SwipeDirection direction){
                if(direction.isLeft()) return true;
                return false;
            }

            @Override
            public boolean shouldDismiss(int position, SwipeDirection direction){
                return direction == SwipeDirection.DIRECTION_NORMAL_LEFT;
            }

            @Override
            public void onSwipe(int[] positionList, SwipeDirection[] directionList){
                for(int i=0;i<positionList.length;i++) {
                    SwipeDirection direction = directionList[i];
                    int position = positionList[i];
                    String dir = "";

                    switch (direction) {
                        case DIRECTION_NORMAL_LEFT:

                            DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
                                switch (which){
                                    case DialogInterface.BUTTON_POSITIVE:
                                        Toast.makeText(
                                                MainActivity.this,
                                                dir + " Thanks! " + mAdapter.getItem(position),
                                                Toast.LENGTH_SHORT
                                        ).show();
                                        break;

                                    case DialogInterface.BUTTON_NEGATIVE:
                                        //No button clicked
                                        break;
                                }
                            };

                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                                    .setNegativeButton("No", dialogClickListener).show();
                            break;
                    }
                    mAdapter.notifyDataSetChanged();
                }
            }
        });

        // add new
        mAddFab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, view.class);
            startActivity(intent);
        });
    }
}