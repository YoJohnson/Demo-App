package com.example.mobilogics.demo_app.order;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.mobilogics.demo_app.R;

/**
 * Created by mobilogics on 2017/2/5.
 */

public class OrderActivity extends AppCompatActivity{

    private Spinner mCoffeeSpinner;

    private Spinner mQuantity;

    private ImageView mCoffeeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        setTitle(getString(R.string.function_1));

        mCoffeeSpinner = (Spinner)findViewById(R.id.coffee_type_spinner);
        mQuantity = (Spinner)findViewById(R.id.coffee_quantity);
        mCoffeeImage = (ImageView) findViewById(R.id.coffee_image);

        setCoffeeSpinner();
        setQuantitySpinner();
    }

    /**
     * Setup the coffee types spinner
     */

    private void setCoffeeSpinner(){

        ArrayAdapter coffeeSpinnerAdapter = ArrayAdapter.createFromResource(
                this,R.array.coffee_type , android.R.layout.simple_spinner_item);

        coffeeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        mCoffeeSpinner.setAdapter(coffeeSpinnerAdapter);

        //set the item select listener to change the image
        mCoffeeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position){

                    case 0:
                        Toast.makeText(OrderActivity.this, getString(R.string.choose_coffee),Toast.LENGTH_SHORT).show();

                        return;

                    case 1:
                        mCoffeeImage.setImageResource(R.drawable.espresso);

                        return;

                    case 2:
                        mCoffeeImage.setImageResource(R.drawable.cappuccino);

                        return;

                    case 3:
                        mCoffeeImage.setImageResource(R.drawable.mocha);

                        return;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(OrderActivity.this, getString(R.string.choose_coffee),Toast.LENGTH_SHORT).show();
            }
        });{

        };



    }

    private void setQuantitySpinner(){

        ArrayAdapter quantitySpinnerAdapter = ArrayAdapter.createFromResource(
                this,R.array.quantity , android.R.layout.simple_spinner_item);

        quantitySpinnerAdapter.setDropDownViewResource(R.layout.quantity_spinner_style);

        mQuantity.setAdapter(quantitySpinnerAdapter);
    }

    /**
     * Setup the menu
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.order_menu,menu);
        return true;
    }

}
