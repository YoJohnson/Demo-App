package com.example.mobilogics.demo_app.tour;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mobilogics.demo_app.R;

public class TourActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour);
        setTitle(getString(R.string.function_4));
    }
}
