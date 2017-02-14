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


public class BananaFragment extends Fragment {

    public BananaFragment(){
        //Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.tour_category, container, false);

        TextView introduction = (TextView) rootView.findViewById(R.id.information);
        introduction.setText(R.string.f4_bananaKingdom);

        ArrayList<Information> Informations = new ArrayList<Information>();
        Informations.add(new Information(getString(R.string.f4_title1_1) , getString(R.string.f4_title1_1_1) , R.drawable.stinky_tofu));
        Informations.add(new Information(getString(R.string.f4_title1_2) , getString(R.string.f4_title1_2_1) , R.drawable.banana_beer));
        Informations.add(new Information(getString(R.string.f4_title1_3) , getString(R.string.f4_title1_3_1) , R.drawable.banana_cake));
        Informations.add(new Information(getString(R.string.f4_title1_4) , getString(R.string.f4_title1_4_1) , R.drawable.banana_egg_rolls));
        Informations.add(new Information(getString(R.string.f4_title1_5) , getString(R.string.f4_title1_5_1) , R.drawable.banana_vinegar));
        Informations.add(new Information(getString(R.string.f4_title1_6) , getString(R.string.f4_title1_6_1) , R.drawable.ice_cream_bar));

        InformationAdapter adapter = new InformationAdapter( getActivity() , Informations);

        ListView listView = (ListView) rootView.findViewById(R.id.introduction_one);
        listView.setAdapter(adapter);

        return rootView;
    }
}
