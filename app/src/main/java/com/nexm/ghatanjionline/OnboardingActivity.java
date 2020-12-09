package com.nexm.ghatanjionline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.ImageView;

import com.nexm.ghatanjionline.adapters.LauncherPagerAdapter;

public class OnboardingActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private LauncherPagerAdapter adapter;
    private ImageView imageView1,imageView2,imageView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        getSupportActionBar().hide();

        imageView1 = findViewById(R.id.indicator_one);
        imageView2 = findViewById(R.id.indicator_two);
        imageView3 = findViewById(R.id.indicator_three);

        viewPager = findViewById(R.id.onboard_viewpager);
        adapter = new LauncherPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
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
