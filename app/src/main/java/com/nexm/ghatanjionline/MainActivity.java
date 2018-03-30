package com.nexm.ghatanjionline;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.nexm.ghatanjionline.fragments.SignUpFragment;
import com.nexm.ghatanjionline.fragments.WelcomeFragment;

public class MainActivity extends AppCompatActivity
        {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();
      /*  if(savedInstanceState == null) {
            getSupportActionBar().setTitle("");
            fragmentManager = getSupportFragmentManager();
            SignUpFragment signUpFragment = new SignUpFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.welcome_root_frame_layout, signUpFragment)
                    .commit();
        }*/
        WelcomeFragment welcomeFragment = new WelcomeFragment();
      //  Bundle bundle = new Bundle();
       // bundle.putInt("CODE",code);
        //welcomeFragment.setArguments(bundle);
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.welcome_root_frame_layout,welcomeFragment)
                .commit();

    }




}
