package com.example.a70871p;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemAdapter extends ArrayAdapter<Item> {

    public ItemAdapter(Context context, ArrayList<Item> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Item item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_lost_found, parent, false);
        }

        TextView textType = convertView.findViewById(R.id.textType);
        TextView textDescription = convertView.findViewById(R.id.textDescription);
        TextView textDateTime = convertView.findViewById(R.id.textDateTime);

        textType.setText(item.type);
        textDescription.setText(item.description + " - " + item.category);
        textDateTime.setText(item.dateTime);

        return convertView;
    }
}
