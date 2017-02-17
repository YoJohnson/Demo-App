package com.example.mobilogics.demo_app.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobilogics.demo_app.R;

/**
 * Created by mobilogics on 2017/2/5.
 */

public class OrderActivity extends AppCompatActivity {

    private Spinner mCoffeeSpinner;

    private Spinner mQuantity;

    private ImageView mCoffeeImage;

    private TextView mPriceShow;

    private TextView mOrderShow;

    private CheckBox needSugar;

    private int mCoffeeCategory;

    private int mCoffeeChoosePrice;

    private int iQuantity = 0;

    private int mCoffeePrice;

    private int totalPrice = 0;

    private boolean hasSugar;

    private EditText nameEditText;

    private EditText phoneEditText;

    private String order = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        setTitle(getString(R.string.function_1));

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        mCoffeeSpinner = (Spinner) findViewById(R.id.coffee_type_spinner);
        mQuantity = (Spinner) findViewById(R.id.coffee_quantity);
        mCoffeeImage = (ImageView) findViewById(R.id.coffee_image);
        mPriceShow = (TextView) findViewById(R.id.price_show);
        nameEditText = (EditText) findViewById(R.id.f1_type_name);
        phoneEditText = (EditText) findViewById(R.id.f1_phone_number);
        needSugar = (CheckBox) findViewById(R.id.sugarCheckBox);
        mOrderShow = (TextView) findViewById(R.id.order_list);


        setCoffeeSpinner();
        setQuantitySpinner();

        Button add = (Button) findViewById(R.id.add_button);
        add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                hasSugar = needSugar.isChecked();

                if (mCoffeeCategory == 0) {
                    Toast.makeText(OrderActivity.this, getString(R.string.f1_choose_coffee), Toast.LENGTH_SHORT).show();
                } else if (iQuantity == 0) {
                    Toast.makeText(OrderActivity.this, getString(R.string.f1_choose_quantity), Toast.LENGTH_SHORT).show();
                } else {
                    switch (mCoffeeCategory) {
                        case 1:
                            if (hasSugar) {
                                order = order + "\n" + getString(R.string.f1_coffee_1_y) + "     " + Integer.toString(iQuantity) + "     " + Integer.toString(mCoffeePrice);
                            } else {
                                order = order + "\n" + getString(R.string.f1_coffee_1_n) + "     " + Integer.toString(iQuantity) + "     " + Integer.toString(mCoffeePrice);
                            }

                            showOrder();
                            totalPrice += mCoffeePrice;
                            break;
                        case 2:
                            if (hasSugar) {
                                order = order + "\n" + getString(R.string.f1_coffee_2_y) + "     " + Integer.toString(iQuantity) + "     " + Integer.toString(mCoffeePrice);
                            } else {
                                order = order + "\n" + getString(R.string.f1_coffee_2_n) + "     " + Integer.toString(iQuantity) + "     " + Integer.toString(mCoffeePrice);
                            }

                            showOrder();
                            totalPrice += mCoffeePrice;
                            break;
                        case 3:
                            if (hasSugar) {
                                order = order + "\n" + getString(R.string.f1_coffee_3_y) + "     " + Integer.toString(iQuantity) + "     " + Integer.toString(mCoffeePrice);
                            } else {
                                order = order + "\n" + getString(R.string.f1_coffee_3_n) + "     " + Integer.toString(iQuantity) + "     " + Integer.toString(mCoffeePrice);
                            }

                            showOrder();
                            totalPrice += mCoffeePrice;
                            break;
                    }
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String name = nameEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone)) {
            Toast.makeText(this, getString(R.string.f1_warning), Toast.LENGTH_SHORT).show();
        } else {

            Intent intent = new Intent(OrderActivity.this, CheckOrderActivity.class);
            intent.putExtra("name", name);
            intent.putExtra("phone", phone);
            intent.putExtra("order", order);
            intent.putExtra("price" , totalPrice);
            startActivity(intent);


        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Setup the coffee types spinner
     */

    private void setCoffeeSpinner() {

        ArrayAdapter coffeeSpinnerAdapter = ArrayAdapter.createFromResource(
                this, R.array.coffee_type, android.R.layout.simple_spinner_item);

        coffeeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        mCoffeeSpinner.setAdapter(coffeeSpinnerAdapter);

        //set the item select listener to change the image
        mCoffeeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {

                    case 0:
                        mCoffeeChoosePrice = 0;
                        mCoffeeCategory = 0;
                        priceShow();

                        Toast.makeText(OrderActivity.this, getString(R.string.f1_choose_coffee), Toast.LENGTH_SHORT).show();

                        return;

                    case 1:
                        mCoffeeImage.setImageResource(R.drawable.espresso);
                        mCoffeeChoosePrice = 10;
                        mCoffeeCategory = 1;

                        priceShow();

                        return;

                    case 2:
                        mCoffeeImage.setImageResource(R.drawable.cappuccino);
                        mCoffeeChoosePrice = 15;
                        mCoffeeCategory = 2;

                        priceShow();

                        return;

                    case 3:
                        mCoffeeImage.setImageResource(R.drawable.mocha);
                        mCoffeeChoosePrice = 20;
                        mCoffeeCategory = 3;

                        priceShow();

                        return;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(OrderActivity.this, getString(R.string.f1_choose_coffee), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void setQuantitySpinner() {

        ArrayAdapter quantitySpinnerAdapter = ArrayAdapter.createFromResource(
                this, R.array.quantity, android.R.layout.simple_spinner_item);

        quantitySpinnerAdapter.setDropDownViewResource(R.layout.quantity_spinner_style);

        mQuantity.setAdapter(quantitySpinnerAdapter);

        mQuantity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String quantity = (String) parent.getItemAtPosition(position);

                iQuantity = Integer.parseInt(quantity);

                mCoffeePrice = iQuantity * mCoffeeChoosePrice;

                priceShow();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    /**
     * this is do the price show method.
     */
    private void priceShow() {
        mCoffeePrice = iQuantity * mCoffeeChoosePrice;

        String price = Integer.toString(mCoffeePrice);

        mPriceShow.setText(price);
    }

    /**
     * Setup the menu
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.order_menu, menu);
        return true;
    }

    private void showOrder() {
        mOrderShow.setText(order);
    }

}
