package com.nexm.ghatanjionline;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.nexm.ghatanjionline.fragments.SignUpFragment;
import com.nexm.ghatanjionline.models.AdData;
import com.nexm.ghatanjionline.models.Category;
import com.nexm.ghatanjionline.models.subCategoryData;

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
