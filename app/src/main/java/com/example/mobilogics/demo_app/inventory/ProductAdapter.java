package com.example.mobilogics.demo_app.inventory;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.mobilogics.demo_app.R;
import com.example.mobilogics.demo_app.inventory.data.Contract.ProductEntry;


/**
 * Created by mobilogics on 2017/2/15.
 */

public class ProductAdapter extends CursorAdapter{

    private String mProductQuantity;

    private TextView quantityTextView;

    public ProductAdapter(Context context , Cursor cursor){super(context , cursor , 0);}

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.inventory_list_item ,parent , false);
    }

    @Override
    public void bindView(View view,final Context context, Cursor cursor) {

        final int itemId = cursor.getInt(cursor.getColumnIndexOrThrow(ProductEntry._ID));
        final int itemQuantity = cursor.getInt(cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_QUANTITY));

        TextView nameTextView = (TextView) view.findViewById(R.id.show_name);
        TextView priceTextView = (TextView) view.findViewById(R.id.show_price);
        quantityTextView = (TextView) view.findViewById(R.id.show_quantity);
        Button sellButtonView = (Button) view.findViewById(R.id.sell_button);

        int nameColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_NAME);
        int priceColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRICE);

        String productName = cursor.getString(nameColumnIndex);

        int price = cursor.getInt(priceColumnIndex);
        String productPrice = Integer.toString(price);

        mProductQuantity = Integer.toString(itemQuantity);

        nameTextView.setText(productName);
        priceTextView.setText(productPrice);
        quantityTextView.setText(mProductQuantity);

        sellButtonView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                ContentValues values = new ContentValues();

                if(itemQuantity > 0 ){
                    int mIntQuantity = itemQuantity -1;
                    values.put(ProductEntry.COLUMN_QUANTITY , mIntQuantity);
                    Uri uri = ContentUris.withAppendedId(ProductEntry.CONTENT_URI , itemId);
                    context.getContentResolver().update(uri , values , null , null);
                }

                context.getContentResolver().notifyChange(ProductEntry.CONTENT_URI , null);

            }
        });
    }
}
