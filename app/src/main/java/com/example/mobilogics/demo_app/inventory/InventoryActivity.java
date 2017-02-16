package com.example.mobilogics.demo_app.inventory;


import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.mobilogics.demo_app.R;
import com.example.mobilogics.demo_app.inventory.data.Contract;
import com.example.mobilogics.demo_app.inventory.data.Contract.ProductEntry;
import com.example.mobilogics.demo_app.inventory.data.DbHelper;

/**
 * Created by mobilogics on 2017/2/7.
 */

public class InventoryActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int URL_LOADER = 0;

    DbHelper mDbHelper;

    ProductAdapter mProductAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        setTitle(getString(R.string.function_2));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InventoryActivity.this , EditorActivity.class);
                startActivity(intent);
            }
        });

        mDbHelper = new DbHelper(this);

        ListView productListView = (ListView) findViewById(R.id.list);

        View emptyView = findViewById(R.id.empty_view);

        productListView.setEmptyView(emptyView);

        mProductAdapter = new ProductAdapter( this , null);

        productListView.setAdapter(mProductAdapter);

        productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {

                Intent informationIntent = new Intent(InventoryActivity.this , InformationActivity.class);

                Uri currentProductUri = ContentUris.withAppendedId(ProductEntry.CONTENT_URI , id);

                informationIntent.setData(currentProductUri);

                startActivity(informationIntent);
            }
        });

        getLoaderManager().initLoader(URL_LOADER , null , this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.inventory_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_insert_dummy_data:
                insertProduct();
                return true;
            case R.id.action_delete_all_entries:
                deleteAllProduct();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle args) {

        String[] projection = {
                ProductEntry._ID,
                ProductEntry.COLUMN_NAME,
                ProductEntry.COLUMN_PRICE,
                ProductEntry.COLUMN_QUANTITY };

        return new CursorLoader(this,
                ProductEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mProductAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mProductAdapter.swapCursor(null);
    }

    private void insertProduct(){

        ContentValues values = new ContentValues();
        values.put(ProductEntry.COLUMN_NAME , "Short Ribs");
        values.put(ProductEntry.COLUMN_TYPE , "Steak");
        values.put(ProductEntry.COLUMN_QUANTITY , "10");
        values.put(ProductEntry.COLUMN_PRICE , "10");
        values.put(ProductEntry.COLUMN_INFORMATION , "test");

        getContentResolver().insert(ProductEntry.CONTENT_URI , values);

    }

    private void deleteAllProduct(){
        int rowsDeleted = getContentResolver().delete(ProductEntry.CONTENT_URI , null,null);
        Log.v("MainActivity" , rowsDeleted + " rows deleted from product database");
    }
}