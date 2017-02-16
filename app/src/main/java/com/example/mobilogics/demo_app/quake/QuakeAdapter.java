package com.example.mobilogics.demo_app.quake;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mobilogics.demo_app.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by mobilogics on 2017/2/16.
 */

public class QuakeAdapter extends ArrayAdapter<Quake> {

    String primaryLocation;
    String locationOffset;

    private static final String LOCATION_SEPARATOR = " of";

    public QuakeAdapter(Activity context , ArrayList<Quake> quakes){
        super(context , 0 , quakes);
    }

    @Override
    public View getView(int position, View convertView , ViewGroup parent){
        View listItemView = convertView;
        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.quake_list_item , parent, false);
        }

        Quake currentQuake = getItem(position);

        String originalLocation = currentQuake.getLocation();

        if(originalLocation.contains(LOCATION_SEPARATOR)){
            String[] parts = originalLocation.split(LOCATION_SEPARATOR);
            locationOffset = parts[0] + LOCATION_SEPARATOR;
            primaryLocation = parts[1];
        } else {
            locationOffset = getContext().getString(R.string.f3_near_the);
            primaryLocation = originalLocation;
        }

        TextView magView = (TextView) listItemView.findViewById(R.id.magnitude);

        String formattedMagnitude = formatMagnitude(currentQuake.getMagnitude());

        magView.setText(formattedMagnitude);

        TextView primaryLocationView = (TextView) listItemView.findViewById(R.id.primary_location);
        primaryLocationView.setText(primaryLocation);

        TextView locationView = (TextView)listItemView.findViewById(R.id.location_offset);
        locationView.setText(locationOffset);

        Date dateObject = new Date(currentQuake.getTimeInMillIsSeconds());

        TextView dateView = (TextView)listItemView.findViewById(R.id.date);
        String formattedDate = formatDate(dateObject);
        dateView.setText(formattedDate);

        TextView timeView = (TextView)listItemView.findViewById(R.id.time);
        String formattedTime = formatTime(dateObject);
        timeView.setText(formattedTime);

        GradientDrawable magnitudeCirle = (GradientDrawable) magView.getBackground();

        int magnitudeColor = getMagnitudeColor(currentQuake.getMagnitude());

        magnitudeCirle.setColor(magnitudeColor);

        return listItemView;
    }

    private String formatMagnitude(double magnitude){
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }

    private String formatDate (Date dateObject){
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    private String formatTime (Date dateObject){
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return  timeFormat.format(dateObject);
    }

    private int getMagnitudeColor(double magnitude){
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);

        switch (magnitudeFloor){
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
        }
        return ContextCompat.getColor(getContext(),magnitudeColorResourceId);
    }
}
