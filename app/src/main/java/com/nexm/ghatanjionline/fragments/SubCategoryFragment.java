package com.nexm.ghatanjionline.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.nexm.ghatanjionline.GOApplication;
import com.nexm.ghatanjionline.ProductActivity;
import com.nexm.ghatanjionline.R;
import com.nexm.ghatanjionline.adapters.DataHolder;
import com.nexm.ghatanjionline.adapters.SubCategoryHorizontalHolder;
import com.nexm.ghatanjionline.models.Category;
import com.nexm.ghatanjionline.models.ProductListing;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnSubCategorySelected} interface
 * to handle interaction events.
 */
public class SubCategoryFragment extends Fragment {

    private OnSubCategorySelected mListener;
    private RecyclerView recyclerView,horizontalRecyclerView;
    //private RecyclerListAdapter listAdapter;
    private FirebaseRecyclerAdapter mFirebaseAdapter,horizontalAdapter;
   // private ArrayList<subCategoryData> data = new ArrayList<>();
    private String category, department;
    private TextView topAll;

    public SubCategoryFragment() {
        // Required empty public constructor
    }
    public static SubCategoryFragment newInstance(String department,String category){

        SubCategoryFragment fragment = new SubCategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString("CATEGORY",category);
        bundle.putString("DEPARTMENT",department);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sub_category, container, false);
        topAll = view.findViewById(R.id.subCategory_top_all_textView);
        recyclerView = (RecyclerView)view.findViewById(R.id.subCategory_recyclerView);
        horizontalRecyclerView = (RecyclerView)view.findViewById(R.id.sub_category_horizontal_recyclerView);
       // LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
       // recyclerView.setLayoutManager(linearLayoutManager);
       // recyclerView.setAdapter(listAdapter);
        getActivity().setTitle(department);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false);
        horizontalRecyclerView.setLayoutManager(linearLayoutManager);
        horizontalRecyclerView.setAdapter(horizontalAdapter);
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
        department = getArguments().getString("DEPARTMENT");
        setHorizontalRecyclerView();

        final Query query = GOApplication.database.getReference()
                .child("ProductListings").orderByChild("deptCat").equalTo(department+","+category);

        FirebaseRecyclerOptions<ProductListing> options =  new FirebaseRecyclerOptions.Builder<ProductListing>()
                .setQuery(query, ProductListing.class)
                .build();
        mFirebaseAdapter = new FirebaseRecyclerAdapter<ProductListing,DataHolder>(
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
            protected void onBindViewHolder(DataHolder viewHolder,final int position, final ProductListing model) {

                viewHolder.bindData(model,getActivity(),true);
                viewHolder.setOnItemClickListener(new DataHolder.OnItemClickListener() {
                    @Override
                    public void onItemClick(View itemView,int currentposition) {

                        if (mListener != null) {
                            ProductListing data = (ProductListing) mFirebaseAdapter.getItem(currentposition);

                           // mListener.onFragmentInteraction1(data.category,data.subCategory,
                           //         data.itemID,data.providerID,data.deliveryID);
                            String key = mFirebaseAdapter.getRef(currentposition).getKey();
                            mListener.onFragmentInteraction1(data,currentposition,mFirebaseAdapter.getRef(currentposition).getKey() );
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

    private void setHorizontalRecyclerView() {
        final Query query = GOApplication.database.getReference()
                .child(department).child("SubCategory").child(category);

        FirebaseRecyclerOptions<Category> options =  new FirebaseRecyclerOptions.Builder<Category>()
                .setQuery(query, Category.class)
                .build();
        horizontalAdapter = new FirebaseRecyclerAdapter<Category, SubCategoryHorizontalHolder>(
                options
        ) {
            @NonNull
            @Override
            public SubCategoryHorizontalHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.sub_category_horizontal_recycler_item, parent, false);
                return new SubCategoryHorizontalHolder(view);
            }

            @Override
            protected void onBindViewHolder(SubCategoryHorizontalHolder viewHolder,final int position, final Category model) {

                viewHolder.bindData(model,getActivity());
                viewHolder.setOnItemClickListener(new SubCategoryHorizontalHolder.OnItemClickListener() {
                    @Override
                    public void onItemClick(View itemView,int currentposition) {

                        if (mListener != null) {
                            Category data = (Category) horizontalAdapter.getItem(currentposition);
                            topAll.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
                            refreshRecyclerView(data.getNAME());

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
    }

    private void refreshRecyclerView(String name) {
        final Query query = GOApplication.database.getReference()
                .child("ProductListings").orderByChild("deptCatSubCat").equalTo(department+","+category+","+name);

        FirebaseRecyclerOptions<ProductListing> options =  new FirebaseRecyclerOptions.Builder<ProductListing>()
                .setQuery(query, ProductListing.class)
                .build();
        mFirebaseAdapter.updateOptions(options);

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
        horizontalAdapter.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        mFirebaseAdapter.stopListening();
        horizontalAdapter.stopListening();
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
        void onFragmentInteraction1(ProductListing data, int currentposition, String productID);
        // TODO: Update argument type and name
        //void onFragmentInteraction1(String category , String subCategory,
        //                            String itemID,String providerID,String deliveryID);
        
    }
}
