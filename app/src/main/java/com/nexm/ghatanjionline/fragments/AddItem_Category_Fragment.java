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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.nexm.ghatanjionline.GOApplication;
import com.nexm.ghatanjionline.R;
import com.nexm.ghatanjionline.adapters.add_ur_item_category_recycler_holder;
import com.nexm.ghatanjionline.models.Category;


public class AddItem_Category_Fragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private RecyclerView recyclerView_category,recyclerView_subCategory;
    private GridLayoutManager gridLayoutManager;
    private FirebaseRecyclerAdapter adapter;
    private String category,subCategory;

    public AddItem_Category_Fragment() {
        // Required empty public constructor
    }


    public static AddItem_Category_Fragment newInstance() {
        AddItem_Category_Fragment fragment = new AddItem_Category_Fragment();

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
        View view = inflater.inflate(R.layout.add_ur_item_category_fragment, container, false);
        recyclerView_category = (RecyclerView)view.findViewById(R.id.add_ur_item_category_recyclerView);
        recyclerView_subCategory = (RecyclerView)view.findViewById(R.id.add_ur_item_subcategory_recyclerView);
        gridLayoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView_category.setLayoutManager(gridLayoutManager);
        recyclerView_category.hasFixedSize();
        recyclerView_category.setNestedScrollingEnabled(false);
        recyclerView_category.setAdapter(adapter);
//        recyclerView_subCategory.setLayoutManager(gridLayoutManager);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String uri,String uri2) {
        if (mListener != null) {
            mListener.onCategorySelection(uri,uri2 );
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
        final Query query = GOApplication.database.getReference()
                .child("Category");

        FirebaseRecyclerOptions<Category> options =  new FirebaseRecyclerOptions.Builder<Category>()
                .setQuery(query, Category.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<Category,add_ur_item_category_recycler_holder>(
                options
        ){
            @NonNull
            @Override
            public add_ur_item_category_recycler_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.add_ur_item_category_recyclerview_item_layout, parent, false);

                return new add_ur_item_category_recycler_holder(view);
            }

            @Override
            protected void onBindViewHolder(add_ur_item_category_recycler_holder holder, int position, Category model){
                holder.bindData(model,getActivity());
                holder.setOnItemClickListner(new add_ur_item_category_recycler_holder.OnItemClickListener() {
                    @Override
                    public void onItemClick(View itemView, int position) {
                        Category subCategory = getItem(position);
                        category = subCategory.getNAME();
                       // mListener.onCategorySelection(subCategory.categoryID,category );

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


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onCategorySelection(String category, String subcategory);
    }
}
