package com.example.mobilogics.demo_app.tour;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mobilogics.demo_app.R;

import java.util.ArrayList;


public class Pier2Fragment extends Fragment{

    public Pier2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.tour_category, container, false);

        TextView introduction = (TextView) rootView.findViewById(R.id.information);
        introduction.setText(R.string.f4_ThePier2ArtCenter);

        ArrayList<Information> informations = new ArrayList<Information>();
        informations.add(new Information( getString(R.string.f4_title4_1) , getString(R.string.f4_title4_1_1) , R.drawable.robot));
        informations.add(new Information( getString(R.string.f4_title4_2) , getString(R.string.f4_title4_2_1)  , R.drawable.house));
        informations.add(new Information( getString(R.string.f4_title4_3) , getString(R.string.f4_title4_3_1) , R.drawable.warehouse));
        informations.add(new Information( getString(R.string.f4_title4_4) , getString(R.string.f4_title4_4_1)  , R.drawable.love));
        informations.add(new Information( getString(R.string.f4_title4_5) , getString(R.string.f4_title4_5_1)  , R.drawable.two_people));

        InformationAdapter adapter = new InformationAdapter( getActivity() , informations);

        ListView listView = (ListView) rootView.findViewById(R.id.introduction_one);
        listView.setAdapter(adapter);

        return rootView;
    }

}
