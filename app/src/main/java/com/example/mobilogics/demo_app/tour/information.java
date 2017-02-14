package com.example.mobilogics.demo_app.tour;

/**
 * Created by mobilogics on 2017/2/14.
 */

public class Information {

    //this is create a Information constructor

    private int mImageResourceId;

    private String mName;

    private String mIntroduction;

    public Information(String name , String introduction , int imageResourceId){

        mName = name;
        mIntroduction = introduction;
        mImageResourceId = imageResourceId;

    }

    public String getmName(){return mName;}

    public String getmIntroduction(){return mIntroduction;}

    public int getmImageResourceId(){return mImageResourceId;}

}
