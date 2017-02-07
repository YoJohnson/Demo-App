package com.example.mobilogics.demo_app.quake;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mobilogics.demo_app.R;

public class QuakeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quake);
        setTitle(getString(R.string.function_3));

    }
}
