package com.nexm.ghatanjionline.fragments;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

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
import com.nexm.ghatanjionline.GOApplication;
import com.nexm.ghatanjionline.ProductActivity;
import com.nexm.ghatanjionline.R;
import com.nexm.ghatanjionline.adapters.ViewPagerAdapter;
import com.nexm.ghatanjionline.models.ProductListing;
import com.nexm.ghatanjionline.models.Provider;
import com.nexm.ghatanjionline.models.TransportItem;
import com.nexm.ghatanjionline.util.SetViewPager;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDetailFragment extends Fragment implements
        CommonDetailsFragment.OnFragmentInteractionListener{
    private OnFragmentInteractionListener mListener;
    private String category , subCategory,itemID,providerID,deliveryID;
    private int ratings,no_reviews;
    private TransportItem detailsdata;
    private Provider provider;
    private CommonDetailsFragment commonDetailsFragment;

    public ProductDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);
        FragmentManager fragmentManager = getChildFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.common_details_container,commonDetailsFragment).commit();
        getData(view);
        return view;
    }

    private void setTextViews(View view) {

        final TextView addressView = (TextView)view.findViewById(R.id.product_detail_address);
        final TextView driverMobileView = (TextView)view.findViewById(R.id.product_detail_driver_mobile);
        final TextView driverNameView = (TextView)view.findViewById(R.id.product_detail_driver_name);
        final TextView modelView = (TextView)view.findViewById(R.id.product_detail_model);
        final TextView ownerMobileView = (TextView)view.findViewById(R.id.product_detail_owner_mobile);
        final TextView ownerNameView = (TextView)view.findViewById(R.id.product_detail_owner_name);
        final TextView rateView = (TextView)view.findViewById(R.id.product_detail_rate);
        final TextView ratingsView = (TextView)view.findViewById(R.id.product_detail_ratings);
        final TextView noOfUsers = (TextView)view.findViewById(R.id.product_detail_no_of_users);
        final TextView regNoView = (TextView)view.findViewById(R.id.product_detail_reg_no);

       // final TextView emergencyDetailsView = (TextView)view.findViewById(R.id.product_details_emergency_details);
        final ImageView providerPhoto = (ImageView)view.findViewById(R.id.product_detail_owner_image);
        final ImageView driverPhoto = (ImageView)view.findViewById(R.id.product_detail_driver_image);

        if(detailsdata != null && provider!= null) {
            addressView.setText(provider.providerADRESS);
            driverMobileView.setText(detailsdata.transportItemDRIVER_NO);
            driverNameView.setText(detailsdata.transportItemDRIVER_NAME);
            modelView.setText(detailsdata.transportItemMODEL);
            ownerMobileView.setText(provider.providerMO_NO);
            ownerNameView.setText(provider.providerNAME);
            rateView.setText(detailsdata.transportItemPRICE);

            if(no_reviews != 0){
                final float ratingss = GOApplication.round((float)(ratings/ no_reviews));
                ratingsView.setText(String.valueOf(ratingss));
                noOfUsers.setText("(");
                noOfUsers.append(String.valueOf(no_reviews));
                noOfUsers.append(")");
            }else{
                ratingsView.setText("0.0");
                noOfUsers.setText("");
            }

            regNoView.setText(detailsdata.transportItemREG_NO);
            Glide.with(getActivity())
                    .load(provider.providerPHOTOURL)
                    .placeholder(R.drawable.ic_account_circle_black_24dp)
                    //.crossFade()
                    .into(providerPhoto);
            Glide.with(getActivity())
                    .load(provider.providerPHOTOURL)
                    .placeholder(R.drawable.ic_account_circle_black_24dp)
                    //.crossFade()
                    .into(driverPhoto);

           // final String emergencyAvailable = detailsdata.getEmergency() ? "Available" : "Not Available";
           // emergencyView.setText(emergencyAvailable);

           // emergencyDetailsView.setText(detailsdata.getEmergencyDetails());
        }




    }

    @Override
    public void onAttach(Context context){

        super.onAttach(context);

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
       // mFirebaseAdapter.cleanup();

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
        void onFragmentInteraction1(Uri uri);
    }


    private void setViewPager(View view){

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_pager_procuct_detail);
        SetViewPager pager = new SetViewPager(view, viewPager);
        String[] urls = {detailsdata.transportItemPHOTO1,detailsdata.transportItemPHOTO2,
                            detailsdata.transportItemPHOTO3};
        final ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity(),urls);
        pager.populateViewPager(viewPagerAdapter);

    }
    private void getData(final View view){
        DatabaseReference item_reference = GOApplication.database.getReference();
        item_reference.child(category).child(subCategory).child(itemID)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        detailsdata = dataSnapshot.getValue(TransportItem.class);
                        setViewPager(view);
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




}
