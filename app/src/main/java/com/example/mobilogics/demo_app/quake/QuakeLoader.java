package com.example.mobilogics.demo_app.quake;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by mobilogics on 2017/2/16.
 */

public class QuakeLoader extends AsyncTaskLoader<List<Quake>> {

    private static final String LOG_TAG = QuakeLoader.class.getName();

    private String murl;

    public QuakeLoader(Context context, String url) {
        super(context);
        murl = url;
    }

    @Override
    public void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Quake> loadInBackground() {
        if (murl== null) {
            return null;
        }

        List<Quake> quakes = QueryUtils.fetchQuakeData(murl);
        return quakes;
    }
}
