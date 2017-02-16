package com.example.mobilogics.demo_app.quake;

import java.util.UnknownFormatConversionException;

/**
 * Created by mobilogics on 2017/2/16.
 */

public class Quake {

    private double mMagnitude;

    private String mLocation;

    private long mTimeInMillIsSeconds;

    private String mUrl;

    public Quake(double magnitude, String location , long timeInMillIsSeconds , String url){

        mMagnitude = magnitude;

        mLocation = location;

        mTimeInMillIsSeconds = timeInMillIsSeconds;

        mUrl = url;

    }

    public double getMagnitude(){return mMagnitude;}

    public String getLocation(){return mLocation;}

    public long getTimeInMillIsSeconds (){return mTimeInMillIsSeconds;}

    public String getUrl(){return mUrl;}
}
