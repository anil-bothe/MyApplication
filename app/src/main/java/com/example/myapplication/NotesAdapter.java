package com.example.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.model.Notes;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends ArrayAdapter<Notes> {
    public NotesAdapter(@NonNull Context context, int resource, List<Notes> arrayList) {
        super(context, resource, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View currentItemView = convertView;

        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.custom_list_view, parent, false);
        }

        Notes currentNumberPosition = getItem(position);

        TextView textView1 = currentItemView.findViewById(R.id.textView1);
        TextView textView2 = currentItemView.findViewById(R.id.textView2);
        TextView statusView = currentItemView.findViewById(R.id.status);

        textView1.setText(currentNumberPosition.getName());
        textView2.setText(String.valueOf(currentNumberPosition.getQuantity()));
        statusView.setText(currentNumberPosition.getStatus());

        return currentItemView;
    }
}
