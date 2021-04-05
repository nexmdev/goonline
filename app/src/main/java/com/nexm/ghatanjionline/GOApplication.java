package com.nexm.ghatanjionline;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by user on 15-04-2017.
 */

public class GOApplication extends Application{

    public static FirebaseDatabase database;
    public static DatabaseReference databaseReference;
    public static String customerID,customerName;
   // public static FirebaseAuth auth;

    @Override
    public void onCreate(){



        database = FirebaseDatabase.getInstance();
        database.setPersistenceEnabled(true);
        databaseReference = database.getReference();
        databaseReference.keepSynced(true);
        customerID="Guest";
        customerName = "Guest";

       /* auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser()==null){
            auth.signInAnonymously();
        }*/

        super.onCreate();

    }
    public static float round(float d){

        int pow = 10;
        for(int i = 1; i<1;i++){
            pow *= 10;}
        float temp = d * pow;
        return (float)(int)((temp - (int)temp) > 0.5f ? temp+1 : temp) / pow;

    }
}
