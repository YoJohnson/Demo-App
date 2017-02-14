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


public class LianchihtanFragment extends Fragment {

    public LianchihtanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.tour_category, container, false);

        TextView introduction = (TextView) rootView.findViewById(R.id.information);
        introduction.setText(R.string.f4_Lianchihtan);

        ArrayList<Information> informations = new ArrayList<Information>();
        informations.add(new Information(getString(R.string.f4_title2_1) , getString(R.string.f4_title2_1_1) , R.drawable.dragon_tiger_tower));
        informations.add(new Information(getString(R.string.f4_title2_2) , getString(R.string.f4_title2_2_1)  , R.drawable.buddha_statues));
        informations.add(new Information(getString(R.string.f4_title2_3) , getString(R.string.f4_title2_3_1)  , R.drawable.cable_water_skiing));
        informations.add(new Information(getString(R.string.f4_title2_4) , getString(R.string.f4_title2_4_1)  , R.drawable.confucius_temple));

        InformationAdapter adapter = new InformationAdapter( getActivity() , informations);

        ListView listView = (ListView) rootView.findViewById(R.id.introduction_one);
        listView.setAdapter(adapter);

        return rootView;
    }

}
