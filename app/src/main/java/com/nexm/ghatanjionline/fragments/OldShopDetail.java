package com.nexm.ghatanjionline.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.nexm.ghatanjionline.Constants.ConstantRef;
import com.nexm.ghatanjionline.GOApplication;
import com.nexm.ghatanjionline.ProductActivity;
import com.nexm.ghatanjionline.R;
import com.nexm.ghatanjionline.adapters.ViewPagerAdapter;
import com.nexm.ghatanjionline.models.Delivery;
import com.nexm.ghatanjionline.models.OldItem;
import com.nexm.ghatanjionline.models.ProductListing;
import com.nexm.ghatanjionline.models.Provider;
import com.nexm.ghatanjionline.util.SetViewPager;


public class OldShopDetail extends Fragment implements
        CommonDetailsFragment.OnFragmentInteractionListener{


    private OnFragmentInteractionListener mListener;
    private String category , subCategory,itemID,providerID,deliveryID;
    private CommonDetailsFragment commonDetailsFragment;
    private OldItem oldItem;
    private Provider provider;
    private Delivery delivery;
    public OldShopDetail() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_old_shop_detail, container, false);
        FragmentManager fragmentManager = getChildFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.common_details_container,commonDetailsFragment).commit();
        getData(view);
        return view;
    }

    private void getData(final View view) {

        DatabaseReference item_reference = GOApplication.database.getReference();
        item_reference.child(category).child(subCategory).child(itemID)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            oldItem = dataSnapshot.getValue(OldItem.class);
                            setViewPager(view);
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

    private void setTextViews(View view) {

        TextView price = (TextView)view.findViewById(R.id.oldshop_price_textview);
        TextView description = (TextView)view.findViewById(R.id.oldshop_description_textview);
        TextView place = (TextView)view.findViewById(R.id.oldshop_place_textview);
        TextView bill = (TextView)view.findViewById(R.id.oldshop_bill_textview);
        TextView returnText = (TextView)view.findViewById(R.id.oldshop_return_textview);
        TextView deliveryText = (TextView)view.findViewById(R.id.oldshop_delivery_textview);
        TextView cod = (TextView)view.findViewById(R.id.oldshop_cod_textview);

        if(oldItem != null && delivery != null && provider != null){
            price.setText(oldItem.oldItemPRICE);
          /*  String deliveryCharge = delivery.deliveryDELIVERY_CHARGES == 0 ? "(Free Delivery)" :
                                                                            ("Delivery Rs. "+String.valueOf(delivery.deliveryDELIVERY_CHARGES));
           // price.append(deliveryCharge);
            description.setText(oldItem.oldItemDESCRIPTION);
            place.setText(R.string.address);
            place.append("  :   ");
            place.append(provider.providerADRESS);

            String billAvailable = oldItem.oldItemBILL_AVAILABLE ? "बिल    :    उपलब्ध" : "बिल    :    उपलब्ध नाही";
            bill.setText(billAvailable);

            String returnAvailable = delivery.deliveryRETURN_AVAILABLE ? delivery.deliveryRETURN_POLICY : "रिटर्न उपलब्ध नाही.";
            returnText.setText(returnAvailable);
            deliveryText.setText("डिलेव्हरी");
            deliveryText.append("   :   ");
            deliveryText.append(delivery.deliveryTIME);
            deliveryText.append("- ");
            deliveryText.append(deliveryCharge);
            String codAvailable = delivery.deliveryCOD_AVAILABLE ? "COD उपलब्ध":"COD उपलब्ध नाही.";
            cod.setText(codAvailable);*/
        }
    }

    private void setViewPager(View view) {

        ViewPager2 viewPager = (ViewPager2) view.findViewById(R.id.view_pager_procuct_detail);
        SetViewPager pager = new SetViewPager(view, viewPager);
        String[] urls = {oldItem.oldItemPHOTO1,oldItem.oldItemPHOTO2,
                oldItem.oldItemPHOTO3};
        final ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity(),urls);
        pager.populateViewPager(viewPagerAdapter);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onOldShopDetailClick(uri);
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
       // ratings =  getArguments().getInt("RATINGS");
       // no_reviews =  getArguments().getInt("NO_OF_USERS");
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
    public void onCommonDetailsClick(ProductListing productListing, int position, String productID) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onOldShopDetailClick(Uri uri);
    }
}
