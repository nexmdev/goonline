package com.nexm.ghatanjionline.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.nexm.ghatanjionline.GOApplication;
import com.nexm.ghatanjionline.ProductActivity;
import com.nexm.ghatanjionline.R;
import com.nexm.ghatanjionline.adapters.AllCategoriesHolder;
import com.nexm.ghatanjionline.models.Category;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AllCategoriesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AllCategoriesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllCategoriesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private FirebaseRecyclerAdapter adapter;
    private DatabaseReference reference;

    private OnFragmentInteractionListener mListener;

    public AllCategoriesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AllCategoriesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AllCategoriesFragment newInstance(String param1, String param2) {
        AllCategoriesFragment fragment = new AllCategoriesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_categories, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.all_categories_recycler_view);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String categoryName) {
        if (mListener != null) {
            mListener.onAllCategorySelected(categoryName);
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
        reference = GOApplication.database.getReference().child("Category");
        adapter = new FirebaseRecyclerAdapter<Category,AllCategoriesHolder>(
                Category.class,R.layout.all_categories_recycler_view_item_layout,
                AllCategoriesHolder.class,reference
        ) {
            @Override
            protected void populateViewHolder(AllCategoriesHolder viewHolder, Category model, int position) {

                viewHolder.bindData(model,getActivity());
                viewHolder.setOnItemClickListener(new AllCategoriesHolder.OnItemClickListener() {
                    @Override
                    public void onItemClick(View itemView, int position) {
                        if(mListener != null){
                            Category data = (Category)adapter.getItem(position);
                            mListener.onAllCategorySelected(data.categoryID);
                        }
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
        void onAllCategorySelected(String categoryName);
    }
}
