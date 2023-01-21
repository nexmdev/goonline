package com.nexm.ghatanjionline.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.nexm.ghatanjionline.GOApplication;
import com.nexm.ghatanjionline.Main2Activity;
import com.nexm.ghatanjionline.Main3Activity;
import com.nexm.ghatanjionline.R;
import com.nexm.ghatanjionline.models.Customer;

import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class WelcomeFragment extends Fragment {

    private static final String TAG ="Welcome" ;
    private FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
    private HashMap<String, Object> firebaseDefaultMap;
    public static final String VERSION_CODE_KEY = "latest_app_version";
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    public WelcomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);

        progressBar = view.findViewById(R.id.welcome_progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#ffffff"),
                PorterDuff.Mode.MULTIPLY);
        auth = FirebaseAuth.getInstance();
        checkForUpdate();

        return view;
    }
    private void checkForUpdate() {
        firebaseDefaultMap = new HashMap<>();
        firebaseDefaultMap.put(VERSION_CODE_KEY, getCurrentVersionCode());
        //mFirebaseRemoteConfig.setDefaults(firebaseDefaultMap);
        mFirebaseRemoteConfig.setDefaultsAsync(firebaseDefaultMap);

        mFirebaseRemoteConfig.setConfigSettingsAsync(
                new FirebaseRemoteConfigSettings.Builder()
                        .build());
        mFirebaseRemoteConfig.fetch().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    mFirebaseRemoteConfig.fetchAndActivate();
                    //Log.d(TAG, "Fetched value: " + mFirebaseRemoteConfig.getString(VERSION_CODE_KEY));
                    //calling function to check if new version is available or not
                    checkForUpdateAvailability();
                } else {
                    checkUser();
                }
            }
        });

        Log.d(TAG, "Default value: " + mFirebaseRemoteConfig.getString(VERSION_CODE_KEY));
    }
    private void checkForUpdateAvailability() {
        int latestAppVersion = (int) mFirebaseRemoteConfig.getDouble(VERSION_CODE_KEY);
        if (latestAppVersion > getCurrentVersionCode()) {
            progressBar.setVisibility(View.GONE);
            new AlertDialog.Builder(getActivity()).setTitle("Please Update the App")
                    .setMessage("A new version of this app is available. Please update it").setPositiveButton(
                    "OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("market://details?id=com.nexm.lucidity")));
                            dialog.dismiss();
                        }
                    }).setCancelable(false).show();
        } else {
            checkUser();
        }
    }

    private void checkUser() {
        FirebaseUser user = auth.getCurrentUser();
        if(user!=null){
            getUser(user.getUid());
        }else{
            progressBar.setVisibility(View.GONE);
            Intent intent = new Intent(getActivity(), Main3Activity.class);
            intent.putExtra("CALLER","Welcome");
            getActivity().startActivity(intent);
        }
    }

    private void getUser(String uid) {
        GOApplication.databaseReference.child("CUSTOMERS")
                .child(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        progressBar.setVisibility(View.GONE);
                        if(snapshot.exists()){
                            Customer customer = snapshot.getValue(Customer.class);
                            Toast.makeText(getActivity(),"Success fetching User Profile !",Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getActivity(), Main2Activity.class);
                            i.putExtra("CUSTOMER_UID",uid);
                            GOApplication.customerID = uid;
                            GOApplication.customerName=customer.getName()+" "+customer.getSurname();
                            startActivity(i);
                            getActivity().finish();
                        }else{
                            Toast.makeText(getActivity(),"No User Profile found !",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(), Main3Activity.class);
                            getActivity().startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(),"Error fetching User Profile !",Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private int getCurrentVersionCode() {
        try {
            return getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }


}
