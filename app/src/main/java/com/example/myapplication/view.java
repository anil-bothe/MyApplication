package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.db.ConfigDB;

public class view extends AppCompatActivity {
    String[] status = { "Unpaid", "Paid"};
    Button saveBtn, cancelBtn;
    EditText editName, editQty;
    Spinner editStatus;
    ConfigDB db;
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        editName = findViewById(R.id.name);
        editQty = findViewById(R.id.quantity);
        editStatus = findViewById(R.id.status);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter statusAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, status);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editStatus.setAdapter(statusAdapter);

        saveBtn = findViewById(R.id.saveBtn);
        cancelBtn = findViewById(R.id.cancelBtn);
        db = new ConfigDB(this);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editName.getText().toString().isEmpty()){
                    showToast("Please enter name!");
                    return;
                }
                if (editQty.getText().toString().isEmpty()){
                    showToast("Please enter quantity!");
                    return;
                }

                db.addNote(editName.getText().toString(), editQty.getText().toString(), editStatus.getSelectedItem().toString());
                editName.setText(""); editQty.setText("");
                showToast("Successfully added!");
            }
        });
    }

    public void showToast(String msg){
        this.toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}