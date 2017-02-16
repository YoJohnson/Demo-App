package com.example.mobilogics.demo_app.inventory.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.mobilogics.demo_app.inventory.data.Contract.ProductEntry;

/**
 * Created by mobilogics on 2017/2/14.
 */

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "product.db";
    private static final int DATABASE_VERSION = 1;

    public static final String LOG_TAG = DbHelper.class.getSimpleName();

    public DbHelper(Context context) {super(context , DATABASE_NAME ,null , DATABASE_VERSION);}

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_DATABASE_PRODUCT_TABLE = "CREATE TABLE " + ProductEntry.TABLE_NAME +"("
                +ProductEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                +ProductEntry.COLUMN_NAME + " TEXT NOT NULL,"
                +ProductEntry.COLUMN_TYPE + " TEXT,"
                +ProductEntry.COLUMN_QUANTITY + " INTEGER DEFAULT 0,"
                +ProductEntry.COLUMN_PRICE + " INTEGER DEFAULT 0,"
                +ProductEntry.COLUMN_INFORMATION + " TEXT,"
                +ProductEntry.COLUMN_IMAGE + " BLOB);";

        db.execSQL(SQL_DATABASE_PRODUCT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
