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


public class RueifongNightMarKetFragment extends Fragment{

    public RueifongNightMarKetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.tour_category, container, false);

        TextView introduction = (TextView) rootView.findViewById(R.id.information);
        introduction.setText(R.string.f4_RueifongNightMarket);

        ArrayList<Information> informations = new ArrayList<Information>();
        informations.add(new Information( getString(R.string.f4_title3_1) , getString(R.string.f4_title3_1_1) , R.drawable.pork_knuckle));
        informations.add(new Information( getString(R.string.f4_title3_2) , getString(R.string.f4_title3_2_1) , R.drawable.roasted_duck_burrito));
        informations.add(new Information( getString(R.string.f4_title3_3) , getString(R.string.f4_title3_3_1) , R.drawable.mochi));
        informations.add(new Information( getString(R.string.f4_title3_4) , getString(R.string.f4_title3_4_1) , R.drawable.chicken_skewers));

        InformationAdapter adapter = new InformationAdapter( getActivity() , informations);

        ListView listView = (ListView) rootView.findViewById(R.id.introduction_one);
        listView.setAdapter(adapter);

        return rootView;
    }
}
