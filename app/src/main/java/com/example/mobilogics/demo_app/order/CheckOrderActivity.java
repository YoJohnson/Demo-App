package com.example.mobilogics.demo_app.order;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mobilogics.demo_app.R;

/**
 * Created by mobilogics on 2017/2/17.
 */

public class CheckOrderActivity extends AppCompatActivity {

    private String name;

    private String phone;

    private String price;

    private String order;

    private String submitMessage;

    private int iPrice;

    private TextView nameView;

    private TextView phoneView;

    private TextView priceView;

    private TextView orderView;

    private Button submitView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_check);
        setTitle(getString(R.string.f1_order_list_title));

        Intent mIntent = getIntent();

        name = mIntent.getStringExtra("name");
        phone = mIntent.getStringExtra("phone");
        iPrice = mIntent.getIntExtra("price" , 0);
        price = Integer.toString(iPrice);
        order = mIntent.getStringExtra("order");

        nameView = (TextView) findViewById(R.id.check_order_name);
        phoneView = (TextView) findViewById(R.id.check_order_phone);
        priceView = (TextView) findViewById(R.id.check_order_price);
        orderView = (TextView) findViewById(R.id.check_order_list);
        submitView = (Button) findViewById(R.id.submit_order);

        nameView.setText(name);
        phoneView.setText(phone);
        priceView.setText(price);
        orderView.setText(order);


        submitMessage = "Name : " + name
                +"\n" + "Phone : " + phone
                +"\n" + "Price : " + price
                +"\n" + "Order List : " + order;


        submitView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:johnson129515@gmail.com"));
                intent.putExtra(Intent.EXTRA_SUBJECT , "Good Coffee Order!");
                intent.putExtra(Intent.EXTRA_TEXT , submitMessage);
                if (intent.resolveActivity(getPackageManager()) != null){
                    startActivity(intent);
                }

            }
        });
    }
}
