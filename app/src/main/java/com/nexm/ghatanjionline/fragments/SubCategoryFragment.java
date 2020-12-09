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
import com.nexm.ghatanjionline.ProductActivity;
import com.nexm.ghatanjionline.R;
import com.nexm.ghatanjionline.adapters.DataHolder;
import com.nexm.ghatanjionline.models.ListItem;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnSubCategorySelected} interface
 * to handle interaction events.
 */
public class SubCategoryFragment extends Fragment {

    private OnSubCategorySelected mListener;
    private RecyclerView recyclerView;
    //private RecyclerListAdapter listAdapter;
    private FirebaseRecyclerAdapter mFirebaseAdapter;
   // private ArrayList<subCategoryData> data = new ArrayList<>();
    private String category,subCategory;

    public SubCategoryFragment() {
        // Required empty public constructor
    }
    public static SubCategoryFragment newInstance(String category,String subCategory){

        SubCategoryFragment fragment = new SubCategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString("SUB_CATEGORY",subCategory);
        bundle.putString("CATEGORY",category);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sub_category, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.subCategory_recyclerView);
       // LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
       // recyclerView.setLayoutManager(linearLayoutManager);
       // recyclerView.setAdapter(listAdapter);
        getActivity().setTitle(subCategory);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(mFirebaseAdapter);

        return view;
    }
    @Override
    public void onPause(){
        super.onPause();
        ProductActivity.appBarLayout.setExpanded(true,true);
    }

    // TODO: Rename method, update argument and hook method into UI event


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        category = getArguments().getString("CATEGORY");
        subCategory = getArguments().getString("SUB_CATEGORY");


        final Query query = GOApplication.database.getReference()
                .child("ListItems").child(category).child(subCategory);

        FirebaseRecyclerOptions<ListItem> options =  new FirebaseRecyclerOptions.Builder<ListItem>()
                .setQuery(query, ListItem.class)
                .build();
        mFirebaseAdapter = new FirebaseRecyclerAdapter<ListItem,DataHolder>(
             options
        ) {
            @NonNull
            @Override
            public DataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.other_options_recycler_view_item_layout, parent, false);
                return new DataHolder(view);
            }

            @Override
            protected void onBindViewHolder(DataHolder viewHolder,final int position, final ListItem model) {

                viewHolder.bindData(model,getActivity(),true);
                viewHolder.setOnItemClickListener(new DataHolder.OnItemClickListener() {
                    @Override
                    public void onItemClick(View itemView,int currentposition) {

                        if (mListener != null) {
                            ListItem data = (ListItem) mFirebaseAdapter.getItem(currentposition);
                           // mListener.onFragmentInteraction1(data.category,data.subCategory,
                           //         data.itemID,data.providerID,data.deliveryID);
                            mListener.onFragmentInteraction1(data,currentposition);
                        }
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
        if (context instanceof OnSubCategorySelected) {
            mListener = (OnSubCategorySelected) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnSubCategorySelected");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;

    }

    @Override
    public void onStart() {
        super.onStart();
        mFirebaseAdapter.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        mFirebaseAdapter.stopListening();
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
    public interface OnSubCategorySelected {
        void onFragmentInteraction1(ListItem data, int currentposition);
        // TODO: Update argument type and name
        //void onFragmentInteraction1(String category , String subCategory,
        //                            String itemID,String providerID,String deliveryID);
        
    }
}
