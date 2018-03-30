package com.nexm.ghatanjionline.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.nexm.ghatanjionline.GOApplication;
import com.nexm.ghatanjionline.ProductActivity;
import com.nexm.ghatanjionline.R;
import com.nexm.ghatanjionline.adapters.DataHolder;
import com.nexm.ghatanjionline.models.ListItem;
import com.nexm.ghatanjionline.models.subCategoryData;

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
        GOApplication.databaseReference= GOApplication.database.getReference()
                .child("ListItems").child(category).child(subCategory);
       /* GOApplication.databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data2 : dataSnapshot.getChildren()){

                    subCategoryData data1 = data2.getValue(subCategoryData.class);
                    data.add(data1);
                    listAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

        mFirebaseAdapter = new FirebaseRecyclerAdapter<ListItem,DataHolder>(
                ListItem.class,R.layout.other_options_recycler_view_item_layout,
                DataHolder.class,GOApplication.databaseReference
        ) {
            @Override
            protected void populateViewHolder(DataHolder viewHolder, final ListItem model, final int position) {

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
        mFirebaseAdapter.cleanup();
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
