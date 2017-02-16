package com.example.mobilogics.demo_app.inventory;

import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Loader;
import android.content.CursorLoader;
import android.app.LoaderManager;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobilogics.demo_app.R;
import com.example.mobilogics.demo_app.inventory.data.Contract.ProductEntry;

/**
 * Created by mobilogics on 2017/2/16.
 */

public class InformationActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int EXISTING_PRODUCT_LOADER = 0;

    private String mName;

    private String mQuantity;

    private int mIntQuantity;

    private TextView nameShowView;

    private TextView typeShowView;

    private TextView quantityShowView;

    private TextView priceShowView;

    private TextView informationShowView;

    private ImageView imageShowView;

    private Uri mCurrentProductUri;

    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory_information);

        Intent intent = getIntent();
        mCurrentProductUri = intent.getData();

        nameShowView = (TextView) findViewById(R.id.name_text_view);
        typeShowView = (TextView) findViewById(R.id.type_text_view);
        quantityShowView = (TextView) findViewById(R.id.quantity_text_view);
        priceShowView = (TextView) findViewById(R.id.price_text_view);
        informationShowView = (TextView) findViewById(R.id.introduction_text_view);
        imageShowView = (ImageView) findViewById(R.id.image_show_view);

        getLoaderManager().initLoader(EXISTING_PRODUCT_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle){

        String[] projection = {
                ProductEntry._ID,
                ProductEntry.COLUMN_NAME,
                ProductEntry.COLUMN_TYPE,
                ProductEntry.COLUMN_QUANTITY,
                ProductEntry.COLUMN_PRICE,
                ProductEntry.COLUMN_INFORMATION,
                ProductEntry.COLUMN_IMAGE};

        return new CursorLoader(this,
                mCurrentProductUri,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        if (cursor.moveToFirst()) {

            int idColumnIndex = cursor.getColumnIndex(ProductEntry._ID);
            final int itemId = cursor.getInt(idColumnIndex);
            id = itemId;

            int nameColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_NAME);
            int typeColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_TYPE);
            int quantityColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_QUANTITY);
            int priceColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRICE);
            int informationColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_INFORMATION);
            int imageColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_IMAGE);

            mName = cursor.getString(nameColumnIndex);
            String type = cursor.getString(typeColumnIndex);

            mIntQuantity = cursor.getInt(quantityColumnIndex);
            mQuantity = Integer.toString(mIntQuantity);

            int intPrice = cursor.getInt(priceColumnIndex);
            String price = Integer.toString(intPrice);

            String information = cursor.getString(informationColumnIndex);

            byte[] bytes = cursor.getBlob(imageColumnIndex);

            if (bytes != null) {
                Bitmap bitmap = bytesToBitmap(bytes);
                imageShowView.setImageBitmap(bitmap);
            }

            nameShowView.setText(mName);
            typeShowView.setText(type);
            quantityShowView.setText(mQuantity);
            priceShowView.setText(price);
            informationShowView.setText(information);

        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        nameShowView.setText("");
        typeShowView.setText("");
        quantityShowView.setText("");
        priceShowView.setText("");
        informationShowView.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.inventory_information, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;

            case R.id.action_editor:

                Intent intent = new Intent(InformationActivity.this, EditorActivity.class);

                intent.setData(mCurrentProductUri);

                startActivity(intent);

                return true;

            case R.id.action_delete:
                showDeleteConfirmationDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void sentProductRequest(View view) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:johnson129515@gmail.com"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Order more : " + mName);
        intent.putExtra(Intent.EXTRA_TEXT, "Existing stock :" + mQuantity);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void minusQuantity(View view) {

        if (mIntQuantity != 0) {

            mIntQuantity = mIntQuantity - 1;

            ContentValues values = new ContentValues();
            values.put(ProductEntry.COLUMN_QUANTITY, mIntQuantity);

            Uri uri = ContentUris.withAppendedId(ProductEntry.CONTENT_URI, id);
            getContentResolver().update(uri, values, null, null);

            mQuantity = Integer.toString(mIntQuantity);
            quantityShowView.setText(mQuantity);

        }
    }

    public void plusQuantity(View view) {

        mIntQuantity = mIntQuantity + 1;

        ContentValues values = new ContentValues();
        values.put(ProductEntry.COLUMN_QUANTITY, mIntQuantity);

        Uri uri = ContentUris.withAppendedId(ProductEntry.CONTENT_URI, id);
        getContentResolver().update(uri, values, null, null);

        mQuantity = Integer.toString(mIntQuantity);
        quantityShowView.setText(mQuantity);

    }

    private void showDeleteConfirmationDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.f2_delete_dialog_msg);
        builder.setPositiveButton(R.string.f2_delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                deleteProduct();
            }
        });
        builder.setNegativeButton(R.string.f2_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deleteProduct() {
        if (mCurrentProductUri != null) {
            int rowsDeleted = getContentResolver().delete(mCurrentProductUri, null, null);

            if (rowsDeleted == 0) {
                Toast.makeText(this, getString(R.string.f2_editor_delete_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.f2_editor_delete_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }

        finish();
    }

    private Bitmap bytesToBitmap(byte[] bytes) {
        if (bytes.length != 0) {
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        } else {
            return null;
        }
    }
}
