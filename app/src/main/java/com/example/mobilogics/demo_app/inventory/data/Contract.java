package com.example.mobilogics.demo_app.inventory.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by mobilogics on 2017/2/14.
 */

public class Contract {

    private Contract(){

    }

    public static final String CONTENT_AUTHORITY = "com.example.mobilogics.demo_app";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_PRODUCT = "demo_app";

    public static final class ProductEntry implements BaseColumns{

        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCT;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCT;

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI , PATH_PRODUCT);

        public static final String TABLE_NAME = "InventoryApp";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_INFORMATION = "information";
        public static final String COLUMN_IMAGE = "image";
    }
}
