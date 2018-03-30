package com.nexm.ghatanjionline.util;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.nexm.ghatanjionline.GOApplication;
import com.nexm.ghatanjionline.models.TransportItem;

/**
 * Created by user on 09-02-2018.
 */

public class ItemDetails {
    TransportItem transportItem;


    public TransportItem getTransportDetails(String category,String subCategory,
                                              String itemID){

        GOApplication.databaseReference = GOApplication.database.getReference()
                                            .child(category)
                                            .child(subCategory)
                                            .child(itemID);
        GOApplication.databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    transportItem = dataSnapshot.getValue(TransportItem.class);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return transportItem;
    }
}
