package com.nexm.ghatanjionline;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.nexm.ghatanjionline.fragments.AllCategoriesFragment;
import com.nexm.ghatanjionline.fragments.NewHomeFragment;

/**
 * Created by user on 22-03-2016.
 */
class PagerAdapter extends FragmentStateAdapter {




 public PagerAdapter(@NonNull FragmentManager fm, @NonNull Lifecycle lifecycle){
     super(fm,lifecycle);
 }








    @NonNull
    @Override
    public Fragment createFragment(int position) {
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
    public int getItemCount() {
         return 2;
    }
}