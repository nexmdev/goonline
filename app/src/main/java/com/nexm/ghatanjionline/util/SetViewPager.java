package com.nexm.ghatanjionline.util;

import android.content.Context;

import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.view.View;
import android.widget.ImageView;

import com.nexm.ghatanjionline.R;
import com.nexm.ghatanjionline.adapters.ViewPagerAdapter;

/**
 * Created by user on 23-01-2018.
 */

public class SetViewPager {
    final ImageView imageView1,imageView2,imageView3;
    private final ViewPager2 viewPager;
    final Context context;



    public SetViewPager(View view,ViewPager2 v) {
        imageView1 = (ImageView)view.findViewById(R.id.indicator_one);
        imageView2 = (ImageView)view.findViewById(R.id.indicator_two);
        imageView3 = (ImageView)view.findViewById(R.id.indicator_three);
        //viewPager = (ViewPager)view.findViewById(R.id.view_pager_procuct_detail);
        context = view.getContext();
        viewPager = v;
    }
    public void populateViewPager(final ViewPagerAdapter viewPagerAdapter){

        viewPager.setAdapter(viewPagerAdapter);
       // viewPager.setOffscreenPageLimit(1);
        //viewPager.setPageTransformer(new MarginPageTransformer(1500));
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position){

                    case 0 :
                        imageView1.setImageResource(R.drawable.selected_dot);
                        imageView2.setImageResource(R.drawable.default_dot);
                        imageView3.setImageResource(R.drawable.default_dot);
                        break;
                    case 1 :
                        imageView1.setImageResource(R.drawable.default_dot);
                        imageView2.setImageResource(R.drawable.selected_dot);
                        imageView3.setImageResource(R.drawable.default_dot);
                        break;
                    case 2 :
                        imageView1.setImageResource(R.drawable.default_dot);
                        imageView2.setImageResource(R.drawable.default_dot);
                        imageView3.setImageResource(R.drawable.selected_dot);
                }
            }
        });
    }
}
