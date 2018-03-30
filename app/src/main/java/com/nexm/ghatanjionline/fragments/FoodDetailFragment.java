package com.nexm.ghatanjionline.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.nexm.ghatanjionline.Constants.ConstantRef;
import com.nexm.ghatanjionline.GOApplication;
import com.nexm.ghatanjionline.ProductActivity;
import com.nexm.ghatanjionline.R;
import com.nexm.ghatanjionline.models.Delivery;
import com.nexm.ghatanjionline.models.FoodItem;
import com.nexm.ghatanjionline.models.Provider;


public class FoodDetailFragment extends Fragment  implements
        CommonDetailsFragment.OnFragmentInteractionListener{

    private OnFragmentInteractionListener mListener;
    private String category , subCategory,itemID,providerID,deliveryID;
    private CommonDetailsFragment commonDetailsFragment;
    private Provider provider;
    private Delivery delivery;
    private FoodItem foodItem;
    private int plates = 1;
    private int no_ratings,no_reviews;

    public FoodDetailFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food_detail, container, false);
        FragmentManager fragmentManager = getChildFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.common_details_container,commonDetailsFragment).commit();
        getData(view);
        return view;
    }
    private void getData(final View view){

        DatabaseReference item_reference = GOApplication.database.getReference();
        item_reference.child(category).child(subCategory).child(itemID)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            foodItem = dataSnapshot.getValue(FoodItem.class);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        DatabaseReference provider_reference = GOApplication.database.getReference();
        provider_reference.child("Providers").child(providerID)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            provider = dataSnapshot.getValue(Provider.class);

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        DatabaseReference delivery_reference = GOApplication.database.getReference();
        delivery_reference.child(ConstantRef.DELIVERIES).child(deliveryID)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            delivery = dataSnapshot.getValue(Delivery.class);
                            setTextViews(view);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
    private void setTextViews(View view){
       // ImageView ownerImage = (ImageView)view.findViewById(R.id.food_detail_owner_imageview);
        ImageView itemImage = (ImageView)view.findViewById(R.id.food_detail_imageview);
        //TextView ownerName = (TextView)view.findViewById(R.id.food_detail_owner_name_textview);
        TextView price = (TextView)view.findViewById(R.id.food_detail_price_textview);
        TextView itemName = (TextView)view.findViewById(R.id.food_detail_itemname_textview);
        TextView ratings = (TextView)view.findViewById(R.id.food_detail_ratings_textview);
        final TextView quantity = (TextView)view.findViewById(R.id.food_detail_quantity_textview);
        TextView plus = (TextView)view.findViewById(R.id.food_detail_plus_textview);
        TextView minus = (TextView)view.findViewById(R.id.food_detail_minus_textview);
        TextView description = (TextView)view.findViewById(R.id.food_detail_description_textview);
        TextView details = (TextView)view.findViewById(R.id.food_detail_details_textview);

        if(foodItem != null && provider != null && delivery != null){

            Glide
                    .with(getActivity())
                    .load(foodItem.foodtItemPHOTO1)
                    .placeholder(R.drawable.placeholder)
                    .crossFade()
                    .into(itemImage);
           // ownerName.setText(provider.providerNAME);
            price.setText(foodItem.foodItemPRICE);
            itemName.setText(foodItem.foodItemNAME);
            ratings.setText(String.valueOf(no_ratings));
            plates = delivery.deliveryMIN_ORDER;
            quantity.setText(String.valueOf(plates)+" प्लेट");
            plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    plates++;
                    quantity.setText(String.valueOf(plates)+" प्लेट");
                }
            });
            minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    plates--;
                    if(plates >= delivery.deliveryMIN_ORDER){
                        quantity.setText(String.valueOf(plates)+" प्लेट");
                    }else{
                        Toast.makeText(getActivity(),"No of plates must be greater than "+delivery.deliveryMIN_ORDER,Toast.LENGTH_SHORT).show();
                        plates = delivery.deliveryMIN_ORDER;
                    }

                }
            });
            description.setText(foodItem.foodItemDESCRIPTION);


        }

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFoodDetailsClick(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        category = getArguments().getString("CATEGORY");
        subCategory = getArguments().getString("SUB_CATEGORY");
        itemID = getArguments().getString("ITEM_ID");
        providerID = getArguments().getString("PROVIDER_ID");
        deliveryID = getArguments().getString("DELIVERY_ID");
        no_ratings =  getArguments().getInt("RATINGS");
        no_reviews =  getArguments().getInt("NO_OF_USERS");
        int currentPosition = getArguments().getInt("POSITION");

        commonDetailsFragment = CommonDetailsFragment
                .newInstance(category,subCategory,itemID, currentPosition);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    @Override
    public void onPause(){
        super.onPause();
        ProductActivity.appBarLayout.setExpanded(true,true);
    }

    @Override
    public void onCommonDetailsClick(Uri uri) {

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFoodDetailsClick(Uri uri);
    }
}
