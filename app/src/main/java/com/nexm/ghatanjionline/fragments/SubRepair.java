package com.nexm.ghatanjionline.fragments;

import android.content.Context;
import android.net.Uri;
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
import com.nexm.ghatanjionline.R;
import com.nexm.ghatanjionline.adapters.SubRepairDataHolder;
import com.nexm.ghatanjionline.models.SubRepairData;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SubRepair.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SubRepair#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SubRepair extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "CATEGORY";
    private static final String ARG_PARAM2 = "LIST_TABLE j";

    // TODO: Rename and change types of parameters
    private String category;
    private String subCategory;

    private RecyclerView recyclerView;
    private FirebaseRecyclerAdapter adapter;
    private DatabaseReference reference;

    private OnFragmentInteractionListener mListener;

    public SubRepair() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SubRepair.
     */
    // TODO: Rename and change types and number of parameters
    public static SubRepair newInstance(String param1, String param2) {
        SubRepair fragment = new SubRepair();
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
            category = getArguments().getString(ARG_PARAM1);
            subCategory = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_sub_repair, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.subRepair_recyclerView);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
        reference = GOApplication.database.getReference().child("List")
                .child("Repair");
        adapter = new FirebaseRecyclerAdapter<SubRepairData,SubRepairDataHolder>(
                SubRepairData.class,R.layout.sub_repair_item_layout,
                SubRepairDataHolder.class, reference
        ) {
            @Override
            protected void populateViewHolder(SubRepairDataHolder viewHolder, final SubRepairData model, final int position) {

                viewHolder.bindData(model,getActivity());
                viewHolder.setOnItemClickListener(new SubRepairDataHolder.OnItemClickListener() {
                    @Override
                    public void onItemClick(View itemView,int currentposition) {

                        if (mListener != null) {
                            SubRepairData data = (SubRepairData) adapter.getItem(currentposition);
                           // mListener.onFoodDetailsClick(data.subRepairName,subCategory,currentposition);
                        }
                    }
                });
            }
        };
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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
        void onFragmentInteraction(Uri uri);
    }
}
