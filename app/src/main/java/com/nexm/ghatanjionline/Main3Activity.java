package com.nexm.ghatanjionline;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.nexm.ghatanjionline.fragments.CartFragment;
import com.nexm.ghatanjionline.fragments.OrderFragment;
import com.nexm.ghatanjionline.fragments.SignUpFragment;

public class Main3Activity extends AppCompatActivity implements
        SignUpFragment.OnFragmentInteractionListener,
CartFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        //getSupportActionBar().hide();
        if(getIntent().getStringExtra("CALLER").matches("Cart")){
            CartFragment fragment = CartFragment.newInstance(getIntent().getStringExtra("userID"),"");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_holder,fragment)
                    .commit();
        }else if((getIntent().getStringExtra("CALLER").matches("Order"))){
            OrderFragment orderFragment = OrderFragment.newInstance(getIntent().getStringExtra("userID"),"");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_holder,orderFragment)
                    .commit();

        }else{
            SignUpFragment fragment = new SignUpFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_holder,fragment)
                    .commit();
        }


    }

    @Override
    public void onFragmentInteraction(int code) {

    }

    @Override
    public void goToOrder() {
        OrderFragment orderFragment = OrderFragment.newInstance(getIntent().getStringExtra("userID"),"");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_holder,orderFragment)

                .commit();
    }
}
