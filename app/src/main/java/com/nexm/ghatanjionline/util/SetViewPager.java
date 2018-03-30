package com.nexm.ghatanjionline.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.nexm.ghatanjionline.R;
import com.nexm.ghatanjionline.adapters.HeaderHolder;
import com.nexm.ghatanjionline.adapters.ViewPagerAdapter;
import com.nexm.ghatanjionline.fragments.NewHomeFragment;

/**
 * Created by user on 23-01-2018.
 */

public class SetViewPager {
    final ImageView imageView1,imageView2,imageView3;
    private ViewPager viewPager;
    final Context context;



    public SetViewPager(View view,ViewPager v) {
        imageView1 = (ImageView)view.findViewById(R.id.indicator_one);
        imageView2 = (ImageView)view.findViewById(R.id.indicator_two);
        imageView3 = (ImageView)view.findViewById(R.id.indicator_three);
        //viewPager = (ViewPager)view.findViewById(R.id.view_pager_procuct_detail);
        context = view.getContext();
        viewPager = v;
    }
    public void populateViewPager(final ViewPagerAdapter viewPagerAdapter){

        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setPageMargin(5);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

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

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
