package com.nexm.ghatanjionline.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.nexm.ghatanjionline.onboardingFragments.OnboardFragmentOne;
import com.nexm.ghatanjionline.onboardingFragments.OnboardFragmentThree;
import com.nexm.ghatanjionline.onboardingFragments.OnboardFragmentTwo;

public class LauncherPagerAdapter extends FragmentStatePagerAdapter {
    public LauncherPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0 : return new OnboardFragmentOne();
            case 1 : return new OnboardFragmentTwo();
            case 2 : return new OnboardFragmentThree();
          //  case 3 : return new Launcher_fragment_three();

        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}

