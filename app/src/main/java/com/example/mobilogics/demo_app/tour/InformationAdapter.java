package com.example.mobilogics.demo_app.tour;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobilogics.demo_app.R;

import java.util.ArrayList;
import java.util.List;


public class InformationAdapter extends ArrayAdapter<Information> {

    public InformationAdapter(Activity context, ArrayList<Information> words) {
        super(context, 0 , words);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.tour_list_item, parent, false);
        }

        Information introduction = getItem(position);

        TextView listName = (TextView) listItemView.findViewById(R.id.list_name);

        listName.setText(introduction.getmName());

        TextView listIntroduction = (TextView) listItemView.findViewById(R.id.list_introduction);

        listIntroduction.setText(introduction.getmIntroduction());

        ImageView imageView = (ImageView) listItemView.findViewById(R.id.image);

        imageView.setImageResource(introduction.getmImageResourceId());

        return listItemView;
    }

}
