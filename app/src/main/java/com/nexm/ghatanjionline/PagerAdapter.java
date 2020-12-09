package com.nexm.ghatanjionline;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.nexm.ghatanjionline.fragments.AllCategoriesFragment;
import com.nexm.ghatanjionline.fragments.NewHomeFragment;

/**
 * Created by user on 22-03-2016.
 */
class PagerAdapter extends FragmentStatePagerAdapter {


    private final String[] titless_array = {"होम","सर्व कॅटेगरीज"};

    public PagerAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;
        switch (position){

            case 0 :
                NewHomeFragment frag = new NewHomeFragment();
                fragment = frag;
                break;
            case 1:
                AllCategoriesFragment frag1 = new AllCategoriesFragment();
                fragment = frag1;
                break;

        }


        return fragment;
    }

    @Override
    public int getCount() {
       return titless_array.length;
    }


    @Override
    public CharSequence getPageTitle(int position) {

        return titless_array[position];
    }
}