package com.nexm.ghatanjionline.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.nexm.ghatanjionline.Main2Activity;
import com.nexm.ghatanjionline.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WelcomeFragment extends Fragment {


    public WelcomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);
       // final TextView textView = (TextView)view.findViewById(R.id.welcome_textview_name);
        final ProgressBar progressBar = (ProgressBar)view.findViewById(R.id.welcome_progress_bar);
     //   if(getArguments().getInt("CODE") == 0){
     //       textView.setText("Guest");
      //  }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.INVISIBLE);
                Intent i = new Intent(getActivity(),Main2Activity.class);
                startActivity(i);
                getActivity().finish();


            }
        },3000);

        /*final TextView textView1 = (TextView)view.findViewById(R.id.welcome_enter_button);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),Main2Activity.class);
                startActivity(i);
                getActivity().finish();
            }
        });*/
        return view;
    }

}
