package com.example.mobilogics.demo_app.tour;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.mobilogics.demo_app.R;



public class CategoryAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 4;
    private Context context;

    public CategoryAdapter(FragmentManager fm , Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new BananaFragment();
        } else if (position == 1) {
            return new LianchihtanFragment();
        } else if (position == 2) {
            return new RueifongNightMarKetFragment();
        } else {
            return new Pier2Fragment();
        }
    }

    @Override
    public int getCount(){return PAGE_COUNT;}

    @Override
    public CharSequence getPageTitle(int position){
        if (position == 0){
            return context.getString(R.string.f4_tabTitle1);
        } else if (position == 1 ){
            return  context.getString(R.string.f4_tabTitle2);
        } else if (position == 2){
            return  context.getString(R.string.f4_tabTitle3);
        } else{
            return context.getString(R.string.f4_tabTitle4);
        }
    }
}
