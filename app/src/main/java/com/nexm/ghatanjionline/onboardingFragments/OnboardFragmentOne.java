package com.nexm.ghatanjionline.onboardingFragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nexm.ghatanjionline.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OnboardFragmentOne extends Fragment {


    public OnboardFragmentOne() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_onboard_fragment_one, container, false);
    }

}
