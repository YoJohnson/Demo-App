package com.example.mobilogics.demo_app.order;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.mobilogics.demo_app.R;

/**
 * Created by mobilogics on 2017/2/5.
 */

public class OrderActivity extends AppCompatActivity{

    private Spinner mCoffeeSpinner;

    private Spinner mQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        setTitle(getString(R.string.function_1));

        mCoffeeSpinner = (Spinner)findViewById(R.id.coffee_type_spinner);
        mQuantity = (Spinner)findViewById(R.id.coffee_quantity);

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
