package com.nexm.ghatanjionline.onboardingFragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.nexm.ghatanjionline.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OnboardFragmentTwo extends Fragment {


    public OnboardFragmentTwo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_onboard_fragment_two, container, false);
    }

}
