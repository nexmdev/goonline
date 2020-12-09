package com.nexm.ghatanjionline;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.nexm.ghatanjionline.fragments.SignUpFragment;

public class Main3Activity extends AppCompatActivity implements
        SignUpFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        SignUpFragment fragment = new SignUpFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_holder,fragment)
                .commit();

    }

    @Override
    public void onFragmentInteraction(int code) {

    }
}
