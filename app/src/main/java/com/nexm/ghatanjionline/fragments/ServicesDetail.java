package com.nexm.ghatanjionline.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.nexm.ghatanjionline.Constants.ConstantRef;
import com.nexm.ghatanjionline.GOApplication;
import com.nexm.ghatanjionline.ProductActivity;
import com.nexm.ghatanjionline.R;
import com.nexm.ghatanjionline.models.ProductListing;
import com.nexm.ghatanjionline.models.PropertyItem;
import com.nexm.ghatanjionline.models.Provider;
import com.nexm.ghatanjionline.models.ServicesItem;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ServicesDetail.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ServicesDetail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ServicesDetail extends Fragment implements
        CommonDetailsFragment.OnFragmentInteractionListener{

    private String category , subCategory,itemID,providerID,deliveryID;
    private int ratings,no_reviews;
    private Provider provider;
    private CommonDetailsFragment commonDetailsFragment;
    private ServicesItem servicesItem;
    private PropertyItem propertyItem;

    private OnFragmentInteractionListener mListener;

    public ServicesDetail() {
        // Required empty public constructor
    }


    public static ServicesDetail newInstance(String category, String subCategory,
                                             String itemID,int currentPosition) {
        ServicesDetail fragment = new ServicesDetail();
        Bundle bundle = new Bundle();
        bundle.putInt("POSITION",currentPosition);
        bundle.putString("CATEGORY",category);
        bundle.putString("SUB_CATEGORY",subCategory);
        bundle.putString("ITEM_ID",itemID);

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.services_detail_layout, container, false);
        FragmentManager fragmentManager = getChildFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.services_detail_common_details_container,commonDetailsFragment).commit();
        getData(view);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onServicesDetailClick(uri);
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
        ratings =  getArguments().getInt("RATINGS");
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
    public void onCommonDetailsClick(ProductListing productListing, int position, String productID) {

    }
    @Override
    public void onPause(){
        super.onPause();
        ProductActivity.appBarLayout.setExpanded(true,true);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onServicesDetailClick(Uri uri);
    }
    private void getData(final View view){
        DatabaseReference item_reference = GOApplication.database.getReference();
        item_reference.child(category).child(subCategory).child(itemID)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        switch(category){
                            case ConstantRef.SERVICES :
                                servicesItem = dataSnapshot.getValue(ServicesItem.class);
                                break;
                            case ConstantRef.PROPERTY:
                                propertyItem = dataSnapshot.getValue(PropertyItem.class);
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
                        provider = dataSnapshot.getValue(Provider.class);
                        setTextViews(view);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    private void setTextViews(View view) {

        TextView service_name = (TextView)view.findViewById(R.id.services_detail_type_textview);
        TextView service_provider = (TextView)view.findViewById(R.id.services_detail_name_textview);
        TextView description = (TextView)view.findViewById(R.id.services_detail_description_textview);
        TextView address = (TextView)view.findViewById(R.id.services_detail_address_textview);
        TextView mono = (TextView)view.findViewById(R.id.services_detail_mono_textview);
        TextView shop = (TextView)view.findViewById(R.id.services_detail_shop_textview);
        TextView education = (TextView)view.findViewById(R.id.services_detail_education_textview);
        TextView experience = (TextView)view.findViewById(R.id.services_detail_experience_textview);
        ImageView imageView = (ImageView)view.findViewById(R.id.services_detail_imageview);
        TextView shop_lable = (TextView)view.findViewById(R.id.services_detail_shop_lable_textview);
        TextView education_lable = (TextView)view.findViewById(R.id.services_detail_education_lable_textview);
        TextView experience_lable = (TextView)view.findViewById(R.id.services_detail_experience_lable_textview);

        switch (category){
            case ConstantRef.SERVICES:
                if(servicesItem != null && provider != null){
                    getActivity().setTitle(servicesItem.servicesItemNAME);
                    service_name.setText(servicesItem.servicesItemNAME);
                    service_provider.setText(provider.providerNAME);
                    description.setText(servicesItem.servicesItemDESCRIPTION);
                    address.setText(provider.providerADRESS);
                    mono.setText(provider.providerMO_NO);
                    shop.setText(servicesItem.servicesItemPRICE);
                    education.setText(servicesItem.servicesItemQUALIFICATION);
                    experience.setText(servicesItem.servicesItemEXPERIENCE);

                    Glide.with(getActivity())
                            .load(provider.providerPHOTOURL)
                            .placeholder(R.drawable.ic_account_circle_black_24dp)
                            //.crossFade()
                            .into(imageView);

                }

                break;
            case ConstantRef.PROPERTY:
                if(propertyItem != null && provider != null){
                    getActivity().setTitle(propertyItem.rentalsItemNAME);
                    shop_lable.setText(R.string.owner);
                    education_lable.setText(R.string.address);
                    experience_lable.setText(R.string.age);
                    service_name.setText(propertyItem.rentalsItemPRICE);
                    service_provider.setText(propertyItem.rentalsItemNAME);
                    description.setText(propertyItem.rentalsItemDESCRIPTION);
                    address.setText(propertyItem.rentalsItemADDRESS);
                    mono.setText(provider.providerMO_NO);
                    shop.setText(provider.providerNAME);
                    education.setText(provider.providerADRESS);
                    experience.setText(propertyItem.rentalsItemAGE);
                    imageView.setAdjustViewBounds(true);
                    Glide.with(getActivity())
                            .load(propertyItem.rentalsItemPHOTO1)
                            //.crossFade()
                            .into(imageView);
                }
                break;

        }

    }
}
