package com.example.mobilogics.demo_app.inventory;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.mobilogics.demo_app.R;

/**
 * Created by mobilogics on 2017/2/7.
 */

public class InventoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        setTitle(getString(R.string.function_2));
    }
}