package com.example.mobilogics.demo_app.inventory;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.content.CursorLoader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mobilogics.demo_app.R;
import com.example.mobilogics.demo_app.inventory.data.Contract.ProductEntry;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;

/**
 * Created by mobilogics on 2017/2/15.
 */

public class EditorActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int PRODUCT_LOADER = 1;

    private DisplayMetrics mPhone;

    private Uri mProductUri;

    private EditText nameEditText;

    private EditText typeEditText;

    private EditText quantityEditText;

    private EditText priceEditText;

    private EditText informationEditText;

    private ImageView mImageView;

    private boolean mProductHasChanged = false;

    private boolean isGalleryPicture = false;

    private static final int PICK_IMAGE_REQUEST = 0;
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    private Bitmap mBitmap;

    private Uri mUri;

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            mProductHasChanged = true;
            return false;
        }
    };
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory_editor);

        final Intent intent = getIntent();
        mProductUri = intent.getData();

        if (mProductUri == null) {
            setTitle(getString(R.string.f2_editor_add_title));
        } else {
            setTitle(getString(R.string.f2_editor_editor_title));

            getLoaderManager().initLoader(PRODUCT_LOADER, null, this);

            Log.v("Editor", "Uri" + mProductUri);
        }

        mPhone = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(mPhone);

        nameEditText = (EditText) findViewById(R.id.edit_product_name);
        typeEditText = (EditText) findViewById(R.id.edit_product_type);
        quantityEditText = (EditText) findViewById(R.id.edit_product_quantity);
        priceEditText = (EditText) findViewById(R.id.edit_product_price);
        informationEditText = (EditText) findViewById(R.id.edit_product_introduction);
        mImageView = (ImageView) findViewById(R.id.editor_image);

        nameEditText.setOnTouchListener(mTouchListener);
        typeEditText.setOnTouchListener(mTouchListener);
        quantityEditText.setOnTouchListener(mTouchListener);
        priceEditText.setOnTouchListener(mTouchListener);
        informationEditText.setOnTouchListener(mTouchListener);

        Button mPthoto = (Button) findViewById(R.id.get_image_album);

        mPthoto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent;

                if (Build.VERSION.SDK_INT < 19) {
                    intent = new Intent(Intent.ACTION_GET_CONTENT);
                } else {
                    intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                }

                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void saveProduct() {

        String nameString = nameEditText.getText().toString().trim();
        String typeString = typeEditText.getText().toString().trim();
        String quantityString = quantityEditText.getText().toString().trim();
        String priceString = priceEditText.getText().toString().trim();
        String informationString = informationEditText.getText().toString().trim();

        if (mProductUri == null &&
                TextUtils.isEmpty(nameString) || TextUtils.isEmpty(typeString) ||
                TextUtils.isEmpty(quantityString) || TextUtils.isEmpty(priceString) ||
                TextUtils.isEmpty(informationString) || mImageView.getDrawable() == null) {

            Toast.makeText(this, "Have less information", Toast.LENGTH_SHORT).show();
            return;
        }

        ContentValues values = new ContentValues();
        values.put(ProductEntry.COLUMN_NAME, nameString);
        values.put(ProductEntry.COLUMN_TYPE, typeString);
        values.put(ProductEntry.COLUMN_INFORMATION, informationString);

        int quantity = 0;
        if (!TextUtils.isEmpty(quantityString)) {
            quantity = Integer.parseInt(quantityString);
        }
        int price = 0;
        if (!TextUtils.isEmpty(priceString)) {
            price = Integer.parseInt(priceString);
        }

        values.put(ProductEntry.COLUMN_QUANTITY, quantity);
        values.put(ProductEntry.COLUMN_PRICE, price);

        if (mBitmap != null) {
            byte[] bytes = bitmapToBytes(mBitmap);
            values.put(ProductEntry.COLUMN_IMAGE, bytes);
        }

        if (mProductUri == null) {
            Uri newUri = getContentResolver().insert(ProductEntry.CONTENT_URI, values);

            if (newUri == null) {
                // If the new content URI is null, then there was an error with insertion.
                Toast.makeText(this, getString(R.string.f2_editor_insert_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the insertion was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.f2_editor_insert_successful),
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            int rowsAffected = getContentResolver().update(mProductUri, values, null, null);

            if (rowsAffected == 0) {
                // If no rows were affected, then there was an error with the update.
                Toast.makeText(this, getString(R.string.f2_editor_update_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the update was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.f2_editor_update_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/inventory_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.inventory_editor, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        if (mProductUri == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_save:

                saveProduct();

                finish();
                return true;

            case R.id.action_delete:

                showDeleteConfirmationDialog();

                return true;

            case android.R.id.home:

                if (!mProductHasChanged) {
                    NavUtils.navigateUpFromSameTask(EditorActivity.this);
                    return true;
                }

                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                NavUtils.navigateUpFromSameTask(EditorActivity.this);
                            }
                        };


                showUnsavedChangesDialog(discardButtonClickListener);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        if (!mProductHasChanged) {
            super.onBackPressed();
            return;
        }

        DialogInterface.OnClickListener discardButtonClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                };

        showUnsavedChangesDialog(discardButtonClickListener);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        String[] projection = {
                ProductEntry._ID,
                ProductEntry.COLUMN_NAME,
                ProductEntry.COLUMN_TYPE,
                ProductEntry.COLUMN_QUANTITY,
                ProductEntry.COLUMN_PRICE,
                ProductEntry.COLUMN_INFORMATION,
                ProductEntry.COLUMN_IMAGE};

        return new CursorLoader(this, mProductUri, projection, null, null , null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        if (cursor.moveToFirst()) {

            int nameColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_NAME);
            int typeColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_TYPE);
            int quantityColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_QUANTITY);
            int priceColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRICE);
            int informationColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_INFORMATION);
            int imageColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_IMAGE);

            String name = cursor.getString(nameColumnIndex);
            String type = cursor.getString(typeColumnIndex);

            int intQuantity = cursor.getInt(quantityColumnIndex);
            String quantity = Integer.toString(intQuantity);

            int intPrice = cursor.getInt(priceColumnIndex);
            String price = Integer.toString(intPrice);

            String information = cursor.getString(informationColumnIndex);

            byte[] bytes = cursor.getBlob(imageColumnIndex);

            if (bytes != null) {
                Bitmap image = bytesToBitmap(bytes);
                mImageView.setImageBitmap(image);
            }

            nameEditText.setText(name);
            typeEditText.setText(type);
            quantityEditText.setText(quantity);
            priceEditText.setText(price);
            informationEditText.setText(information);
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        nameEditText.setText("");
        typeEditText.setText("");
        quantityEditText.setText("");
        priceEditText.setText("");
        informationEditText.setText("");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.  Pull that uri using "resultData.getData()"

            if (resultData != null) {
                mUri = resultData.getData();


                mBitmap = getBitmapFromUri(mUri);
                mImageView.setImageBitmap(mBitmap);

                isGalleryPicture = true;
            }
        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {

            mBitmap = getBitmapFromUri(mUri);
            mImageView.setImageBitmap(mBitmap);

            isGalleryPicture = false;
        }
    }

    private byte[] bitmapToBytes(Bitmap bitmap) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    private Bitmap bytesToBitmap(byte[] bytes) {
        if (bytes.length != 0) {
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        } else {
            return null;
        }
    }

    private Bitmap getBitmapFromUri(Uri uri) {
        ParcelFileDescriptor parcelFileDescriptor = null;
        try {
            parcelFileDescriptor =
                    getContentResolver().openFileDescriptor(uri, "r");
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
            parcelFileDescriptor.close();
            return image;
        } catch (Exception e) {
            return null;
        } finally {
            try {
                if (parcelFileDescriptor != null) {
                    parcelFileDescriptor.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showUnsavedChangesDialog(
            DialogInterface.OnClickListener discardButtonClickListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.f2_unsaved_changes_dialog_msg);
        builder.setPositiveButton(R.string.f2_discard, discardButtonClickListener);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
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
        if (mProductUri != null) {
            int rowsDeleted = getContentResolver().delete(mProductUri, null, null);

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

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Editor Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
