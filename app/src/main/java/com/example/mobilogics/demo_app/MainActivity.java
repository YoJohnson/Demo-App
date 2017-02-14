package com.example.mobilogics.demo_app;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.Activity;

import com.example.mobilogics.demo_app.inventory.InventoryActivity;
import com.example.mobilogics.demo_app.order.OrderActivity;
import com.example.mobilogics.demo_app.quake.QuakeActivity;
import com.example.mobilogics.demo_app.tour.TourActivity;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more Information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more Information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();


        //set the activity change
        Button fb1 = (Button) findViewById(R.id.fb_1);
        fb1.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, OrderActivity.class);
                startActivity(intent);
            }
        });


        //set the activity change
        Button fb2 = (Button) findViewById(R.id.fb_2);
        fb2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, InventoryActivity.class);
                startActivity(intent);
            }
        });

        //set the activity change
        Button fb3 = (Button) findViewById(R.id.fb_3);
        fb3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, QuakeActivity.class);
                startActivity(intent);
            }
        });

        //set the activity change
        Button fb4 = (Button) findViewById(R.id.fb_4);
        fb4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, TourActivity.class);
                startActivity(intent);
            }
        });

    }
}

