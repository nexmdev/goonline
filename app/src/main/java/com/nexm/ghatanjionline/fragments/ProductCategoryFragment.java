package com.nexm.ghatanjionline.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.nexm.ghatanjionline.GOApplication;
import com.nexm.ghatanjionline.ProductActivity;
import com.nexm.ghatanjionline.R;
import com.nexm.ghatanjionline.adapters.CategoryHolder;
import com.nexm.ghatanjionline.models.Category;

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
        final Query query;
        if(category==null){
            query  = GOApplication.database.getReference()
                    .child("SubCategory").child("ट्रांसपोर्ट");
        }else{
            query  = GOApplication.database.getReference()
                    .child("SubCategory").child(category);
        }


        FirebaseRecyclerOptions<Category> options =  new FirebaseRecyclerOptions.Builder<Category>()
                .setQuery(query, Category.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<Category,CategoryHolder>(
               options

        ) {
            @NonNull
            @Override
            public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.category_item_layout, parent, false);
                return  new CategoryHolder(view);
            }

            @Override
            protected void onBindViewHolder(CategoryHolder viewHolder, int position, Category model) {
                viewHolder.bindData(model,getActivity());
                viewHolder.setOnItemClickListner(new CategoryHolder.OnItemClickListener() {
                    @Override
                    public void onItemClick(View itemView, int position) {
                        Category subCategory = getItem(position);
                        mListener.onCategorySelected(category,subCategory.categoryID);
                    }
                });
            }
            @Override
            public void onDataChanged() {
                // Called each time there is a new data snapshot. You may want to use this method
                // to hide a loading spinner or check for the "no documents" state and update your UI.
                // ...
            }

            @Override
            public void onError(DatabaseError e) {
                // Called when there is an error getting data. You may want to update
                // your UI to display an error message to the user.
                // ...
            }
        };

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;

    }
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
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
