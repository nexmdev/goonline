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
import com.nexm.ghatanjionline.Constants.ConstantRef;
import com.nexm.ghatanjionline.GOApplication;
import com.nexm.ghatanjionline.R;
import com.nexm.ghatanjionline.adapters.BaseHolder;
import com.nexm.ghatanjionline.adapters.HeaderHolder;
import com.nexm.ghatanjionline.adapters.HomeRecycleHolder;
import com.nexm.ghatanjionline.adapters.HomeRecycleHolderTypeContainer;
import com.nexm.ghatanjionline.adapters.Home_Category_Grid_Holder;
import com.nexm.ghatanjionline.models.AdData;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewHomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewHomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private FirebaseRecyclerAdapter mFirebaseAdapter;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public NewHomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewHomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewHomeFragment newInstance(String param1, String param2) {
        NewHomeFragment fragment = new NewHomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_new_home, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.home_recycle_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        if (mFirebaseAdapter != null) {
             recyclerView.setAdapter(mFirebaseAdapter);
        }


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String category,String url) {
        if (mListener != null) {
            mListener.onImageAdClick(category,url );
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        GOApplication.databaseReference = GOApplication.database.getReference()
                .child("Ads").child("Home");

        mFirebaseAdapter = new FirebaseRecyclerAdapter<AdData,BaseHolder>(
                AdData.class,R.layout.home_recycle_view_item,
                BaseHolder.class,GOApplication.databaseReference
        ) {

            @Override
            protected void populateViewHolder(BaseHolder viewHolder,
                                              final AdData model, final int position) {

                switch (model.type){

                    case "IMAGE":
                        ((HomeRecycleHolder)viewHolder).bindData(model,getActivity());
                        ((HomeRecycleHolder)viewHolder).setOnItemClickListener(new HomeRecycleHolder.OnItemClickListener() {
                            @Override
                            public void onItemClick(View itemView,int currentposition) {

                                if (mListener != null) {
                                    AdData data = (AdData) mFirebaseAdapter.getItem(currentposition);
                                    mListener.onImageAdClick(data.selectedTag_url_2,data.webUrl_title_3 );
                                }
                            }
                        });
                        break;
                    case "CONTAINER":
                        ((HomeRecycleHolderTypeContainer)viewHolder).bindData(model,getActivity());
                       ((HomeRecycleHolderTypeContainer)viewHolder).setOnItemClickListener(new HomeRecycleHolderTypeContainer.OnItemClickListener() {
                            @Override
                            public void onItemClick(int itemNo,int currentposition) {

                                if (mListener != null) {
                                    AdData data = (AdData) mFirebaseAdapter.getItem(currentposition);

                                    String listID = "";
                                    String subCategory = "";
                                    switch (itemNo){
                                        case 1:
                                            listID = data.listID_1;
                                            subCategory = data. end_date_title_1;
                                            break;
                                        case 2:
                                            listID = data.listID_2;
                                            subCategory = data.adImageUrl_title_2;
                                            break;
                                        case 3:
                                            listID = data.listID_3;
                                            subCategory = data.webUrl_title_3;
                                            break;
                                        case 4:
                                            listID = data.listID_4;
                                            subCategory = data.client_name_title_4;
                                            break;
                                        case 0 :
                                            listID = "SeeAll";
                                            break;

                                    }
                                    mListener.onContainerItemClick(data.category,subCategory,listID);
                                }
                            }
                        });
                        break;
                    case "GRID":
                        ((Home_Category_Grid_Holder)viewHolder).bindData(model,getActivity());
                        ((Home_Category_Grid_Holder)viewHolder).setOnItemClickListener(new Home_Category_Grid_Holder.OnItemClickListener() {
                            @Override
                            public void onItemClick(int itemNo, int position) {
                                    String category = "";
                                    if (mListener != null) {
                                        switch (itemNo){

                                            case 1:
                                                category = getItem(position).end_date_title_1;
                                                break;
                                            case 2:
                                                category = getItem(position).adImageUrl_title_2;
                                                break;
                                            case 3:
                                                category = getItem(position).webUrl_title_3;
                                                break;
                                            case 4:
                                                category = getItem(position).client_name_title_4;
                                                break;
                                            case 5:
                                                category = getItem(position).subCategory;
                                                break;
                                            case 6:
                                                category = "All";
                                                break;
                                        }

                                        mListener.onCategorySelected(category);
                                    }

                            }
                        });
                        break;
                    case "PAGER":
                        ((HeaderHolder)viewHolder).bindData(model,getActivity());

                        ((HeaderHolder)viewHolder).setOnItemClickListener(new HeaderHolder.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                if (mListener != null) {
                                    AdData data = (AdData) mFirebaseAdapter.getItem(0);

                                    mListener.onHeaderClick(data,position );
                                }
                            }
                        });
                        break;

                }
    }

            @Override
            public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType){

                switch (viewType){

                    case ConstantRef.TYPE_ONE:

                        View userType1 = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.home_recycle_view_item, parent, false);
                        return new HomeRecycleHolder(userType1);
                    case ConstantRef.TYPE_TWO:
                        View userType2 = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.home_r_item_layout_two, parent, false);
                        return new HomeRecycleHolderTypeContainer(userType2);
                    case ConstantRef.TYPE_THREE:
                        View userType3 = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.home_r_category_grid, parent, false);
                        return new Home_Category_Grid_Holder(userType3);
                    case ConstantRef.TYPE_FOUR:
                        View userType4 = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.home_top_slide_item, parent, false);
                        return new HeaderHolder(userType4);


                }
                return super.onCreateViewHolder(parent, viewType);
            }
            @Override
            public int getItemViewType(int position){

                AdData data = getItem(position);
                switch (data.type){

                    case "IMAGE":
                        return ConstantRef.TYPE_ONE;
                    case "CONTAINER":
                        return ConstantRef.TYPE_TWO;
                    case "GRID":
                        return ConstantRef.TYPE_THREE;
                    case "PAGER":
                        return ConstantRef.TYPE_FOUR;

                }
                return super.getItemViewType(position);
            }
        };
        if(context instanceof OnFragmentInteractionListener)

    {
        mListener = (OnFragmentInteractionListener) context;
    } else

    {
        throw new RuntimeException(context.toString()
                + " must implement OnFragmentInteractionListener");
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
        void onImageAdClick(String category, String webUrl);
        void onContainerItemClick(String category,String subCategory,String listID);
        void onCategorySelected(String categoryName);
        void onHeaderClick(AdData data, int position);
    }

}
