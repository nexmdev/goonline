package com.nexm.ghatanjionline.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.nexm.ghatanjionline.GOApplication;
import com.nexm.ghatanjionline.ProductActivity;
import com.nexm.ghatanjionline.R;
import com.nexm.ghatanjionline.adapters.CategoryHolder;
import com.nexm.ghatanjionline.adapters.CustomRecyclerAdapter;
import com.nexm.ghatanjionline.models.Category;
import com.nexm.ghatanjionline.models.CategoryItemData;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProductCategoryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ProductCategoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private OnFragmentInteractionListener mListener;
    private ImageView imageView;
    private String name;
    private FirebaseRecyclerAdapter adapter;


    public ProductCategoryFragment() {
        // Required empty public constructor
    }
    public static ProductCategoryFragment newInstance(String category){

        ProductCategoryFragment fragment = new ProductCategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString("CATEGORY",category);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_category, container, false);

        imageView = (ImageView)view.findViewById(R.id.productCategoryTopImage);
      /*  switch (name){

            case "Cars":
                imageView.setImageResource(R.drawable.indica_ad);
                break;
            case "Electricians":
                imageView.setImageResource(R.drawable.tap_ad);
        }*/
        recyclerView = (RecyclerView)view.findViewById(R.id.category_recyclerView);
        gridLayoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
       // recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String SelectedName,String CategoryName) {
        if (mListener != null) {
            mListener.onCategorySelected(SelectedName,CategoryName);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnSubCategorySelected");
        }
        final String category = getArguments().getString("CATEGORY");
        if(category==null){
            GOApplication.databaseReference  = GOApplication.database.getReference()
                    .child("SubCategory").child("ट्रांसपोर्ट");
        }else{
            GOApplication.databaseReference  = GOApplication.database.getReference()
                    .child("SubCategory").child(category);
        }

        adapter = new FirebaseRecyclerAdapter<Category,CategoryHolder>(
                Category.class,R.layout.category_item_layout,CategoryHolder.class,
                GOApplication.databaseReference

        ) {
            @Override
            protected void populateViewHolder(CategoryHolder viewHolder, Category model, int position) {
                viewHolder.bindData(model,getActivity());
                viewHolder.setOnItemClickListner(new CategoryHolder.OnItemClickListener() {
                    @Override
                    public void onItemClick(View itemView, int position) {
                        Category subCategory = getItem(position);
                        mListener.onCategorySelected(category,subCategory.categoryID);
                    }
                });
            }
        };

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        adapter.cleanup();
    }
    @Override
    public void onPause(){
        super.onPause();
        ProductActivity.appBarLayout.setExpanded(true,true);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onCategorySelected(String category,String subcategory);
    }
}
