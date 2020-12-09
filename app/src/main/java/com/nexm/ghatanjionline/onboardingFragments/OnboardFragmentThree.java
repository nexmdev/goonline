package com.nexm.ghatanjionline.onboardingFragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.nexm.ghatanjionline.Main2Activity;
import com.nexm.ghatanjionline.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OnboardFragmentThree extends Fragment {


    public OnboardFragmentThree() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_onboard_fragment_three, container, false);
        final TextView start = view.findViewById(R.id.onboard_start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Main2Activity.class);
                startActivity(i);
                getActivity().finish();
            }
        });

        return view;
    }

}
