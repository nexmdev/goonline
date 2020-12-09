package com.nexm.ghatanjionline;

import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.nexm.ghatanjionline.fragments.WelcomeFragment;

import java.util.Objects;

public class MainActivity extends AppCompatActivity
        {

    private FragmentManager fragmentManager;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();

        //Check for 1st run

        final SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("GO_ONLINE_RUNNING_STATUS", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        Boolean run = sharedPreferences.getBoolean("FIRST", true);

        //for 1st opening of app
        if(run){
            editor.putBoolean("FIRST", false);
            editor.apply();

            showPrivacyPolicy();
        }else{
            // app is opened for 2nd time and thereafter
            WelcomeFragment welcomeFragment = new WelcomeFragment();
            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.welcome_root_frame_layout,welcomeFragment)
                    .commit();
        }

    }

    private void showIntroTabs() {
        Intent intent = new Intent(MainActivity.this,OnboardingActivity.class);
        startActivity(intent);
    }

    private void showPrivacyPolicy() {
                final Dialog dialog;

                dialog = new Dialog(this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.privacy_layout);

                final TextView exit = (dialog).findViewById(R.id.privacy_exit);
                final TextView agree = (dialog).findViewById(R.id.privacy_agree);
                final TextView showPolicy = (dialog).findViewById(R.id.privacy_see);

                showPolicy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        editor.putBoolean("FIRST", true);
                        editor.apply();
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://lucidity-6e2e0.firebaseapp.com/privacy.html"));
                        String title = "Open page Using";
                        Intent chooser = Intent.createChooser(intent, title);
                        startActivity(chooser);
                    }
                });

                exit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        editor.putBoolean("FIRST", true);
                        editor.apply();
                        finish();
                    }
                });
                agree.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       dialog.dismiss();
                       showIntroTabs();

                    }
                });
                dialog.setCanceledOnTouchOutside(false);
                dialog.setCancelable(false);

                Objects.requireNonNull(dialog.getWindow()).setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.WRAP_CONTENT);
                dialog.show();

            }




}
