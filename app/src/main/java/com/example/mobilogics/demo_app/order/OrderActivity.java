package com.example.mobilogics.demo_app.order;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.mobilogics.demo_app.R;

/**
 * Created by mobilogics on 2017/2/5.
 */

public class OrderActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        setTitle(getString(R.string.function_1));
    }
}
