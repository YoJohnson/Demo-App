package com.example.mobilogics.demo_app.quake;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mobilogics.demo_app.R;

import java.util.ArrayList;
import java.util.List;

public class QuakeActivity extends AppCompatActivity implements LoaderCallbacks<List<Quake>>{

    private TextView mEmptyView;

    private QuakeAdapter mAdapter;

    private static final int EARTHQUAKE_LOADER_ID = 1;

    private static final String USGS_REQUEST_URL = "http://earthquake.usgs.gov/fdsnws/event/1/query";

    public static final String LOG_TAG = QuakeActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quake);
        setTitle(getString(R.string.function_3));

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        mAdapter = new QuakeAdapter(this , new ArrayList<Quake>());

        ListView quakeListView = (ListView) findViewById(R.id.list);
        quakeListView.setAdapter(mAdapter);

        mEmptyView = (TextView) findViewById(R.id.empty_list);

        quakeListView.setEmptyView(mEmptyView);

        quakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Quake currentQuake = mAdapter.getItem(position);

                Uri quakeUri = Uri.parse(currentQuake.getUrl());

                Intent websiteIntent = new Intent(Intent.ACTION_VIEW , quakeUri);

                startActivity(websiteIntent);

            }
        });

        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();

            loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
        } else {
            View mProgressBar = findViewById(R.id.progress_bar);

            mProgressBar.setVisibility(View.GONE);

            mEmptyView.setText(R.string.f3_no_internet);
        }

    }

    @Override
    public Loader<List<Quake>> onCreateLoader(int i, Bundle bundle) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        String minMagnitude = sharedPrefs.getString(
                getString(R.string.f3_settings_min_magnitude_key),
                getString(R.string.f3_settings_min_magnitude_default));

        String orderBy = sharedPrefs.getString(
                getString(R.string.f3_settings_order_by_key),
                getString(R.string.f3_settings_order_by_default)
        );

        Uri baseUri = Uri.parse(USGS_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("format", "geojson");
        uriBuilder.appendQueryParameter("limit", "10");
        uriBuilder.appendQueryParameter("minmag", minMagnitude);
        uriBuilder.appendQueryParameter("orderby", orderBy);

        return new QuakeLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Quake>> loader, List<Quake> quakes) {
        View mProgressBar = findViewById(R.id.progress_bar);

        mProgressBar.setVisibility(View.GONE);

        mEmptyView.setText(R.string.f3_no_earthquakes);

        mAdapter.clear();

        if (quakes != null && !quakes.isEmpty()) {
            mAdapter.addAll(quakes);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Quake>> loader) {mAdapter.clear();}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.quake_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
