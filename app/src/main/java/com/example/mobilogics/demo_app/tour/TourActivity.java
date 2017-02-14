package com.example.mobilogics.demo_app.tour;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.mobilogics.demo_app.R;

public class TourActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour);
        setTitle(getString(R.string.function_4));

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);

        CategoryAdapter simpleFragmentPagerAdapter = new CategoryAdapter(getSupportFragmentManager() , TourActivity.this);

        viewPager.setAdapter(simpleFragmentPagerAdapter);

        TabLayout tablayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tablayout.setupWithViewPager(viewPager);
    }
}
