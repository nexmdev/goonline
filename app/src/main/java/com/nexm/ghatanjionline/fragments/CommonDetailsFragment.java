package com.nexm.ghatanjionline.fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nexm.ghatanjionline.Constants.ConstantRef;
import com.nexm.ghatanjionline.GOApplication;
import com.nexm.ghatanjionline.R;
import com.nexm.ghatanjionline.adapters.BaseHolder;
import com.nexm.ghatanjionline.adapters.DataHolder;
import com.nexm.ghatanjionline.models.CommentData;
import com.nexm.ghatanjionline.models.ListItem;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CommonDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CommonDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CommonDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "itemIDD";
    private static final String ARG_PARAM2 = "param2";
    private LinearLayout comment_container;

    // TODO: Rename and change types of parameters
    private String itemID;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private FirebaseRecyclerAdapter mFirebaseAdapter;
    private String category,subCategory;
    private int currentPosition;
    private RecyclerView recyclerView;

    public CommonDetailsFragment() {
        // Required empty public constructor
    }


    public static CommonDetailsFragment newInstance(String category, String subCategory,String itemID,int currentPosition) {
        CommonDetailsFragment fragment = new CommonDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("POSITION",currentPosition);
        bundle.putString("CATEGORY",category);
        bundle.putString("SUB_CATEGORY",subCategory);
        bundle.putString("ITEM_ID",itemID);

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //itemID = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_common_details, container, false);
        comment_container = (LinearLayout)view.findViewById(R.id.product_detail_reviews_container_layout);
        recyclerView = (RecyclerView)view.findViewById(R.id.product_detail_recycler_view_other_options);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mFirebaseAdapter);
        setComments();
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onCommonDetailsClick(uri);
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
        currentPosition = getArguments().getInt("POSITION");

        final Query query = GOApplication.database.getReference()
                .child("ListItems")
                .child(category).child(subCategory);

        FirebaseRecyclerOptions<ListItem> options =  new FirebaseRecyclerOptions.Builder<ListItem>()
                .setQuery(query, ListItem.class)
                .build();

        GOApplication.databaseReference= GOApplication.database.getReference()
                .child("ListItems") .child(category).child(subCategory);
        mFirebaseAdapter = new FirebaseRecyclerAdapter<ListItem,BaseHolder>(
                options
        ) {
            @Override
            protected void onBindViewHolder(BaseHolder viewHolder,int position, ListItem model) {
                if(position==currentPosition){
                    viewHolder.bindData1();
                }else {
                    ((DataHolder)viewHolder).bindData(model,getActivity(),true);
                }
            }
            @Override
            public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType){
                switch (viewType){
                    case ConstantRef.TYPE_ONE:
                        View userType1 = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.zeo_width_layout, parent, false);
                        return new BaseHolder(userType1);
                    case ConstantRef.TYPE_TWO:
                        View userType2 = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.other_options_recycler_view_item_layout, parent, false);
                        return new DataHolder(userType2);
                }
                View userType1 = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.zeo_width_layout, parent, false);
                return new BaseHolder(userType1);
                //final BaseHolder baseHolder = super.onCreateViewHolder(parent, viewType);
               // return baseHolder;
            }
            @Override
            public int getItemViewType(int position){
                if(position==currentPosition){
                    return ConstantRef.TYPE_ONE;
                }else {
                    return ConstantRef.TYPE_TWO;
                }

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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onCommonDetailsClick(Uri uri);
    }
    private void setComments(){

        DatabaseReference commentRef = GOApplication.database.getReference();
        commentRef.child("Comments").child(itemID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot != null && dataSnapshot.exists()){
                    for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        final View Cview = getLayoutInflater(null).inflate(R.layout.comment_layout, null);
                        CommentData comment = dataSnapshot1.getValue(CommentData.class);
                        if (comment != null) {

                            final TextView commentView = (TextView) Cview.findViewById(R.id.comment_layout_comment_View);
                            final TextView userView = (TextView) Cview.findViewById(R.id.comment_layout_userView);
                            final RatingBar barView = (RatingBar) Cview.findViewById(R.id.comment_layout_ratingbarView);
                            final TextView ratingView = (TextView)Cview.findViewById(R.id.comment_layout_rating_textView);
                            commentView.setText(comment.Comment);
                            userView.setText("by ");
                            userView.append(comment.user);
                            barView.setIsIndicator(true);
                            barView.setRating(Float.parseFloat(comment.rating));
                            ratingView.setText(comment.rating);

                            comment_container.addView(Cview, comment_container.getChildCount());

                        } else {

                            final TextView textView = new TextView(getActivity());
                            textView.setText("No Reviews yet");
                            comment_container.addView(textView);
                        }
                    }
                }else{
                    final TextView textView = new TextView(getActivity());
                    textView.setPadding(16,16,16,16);
                    textView.setBackgroundColor(Color.WHITE);
                    textView.setGravity(Gravity.CENTER);
                    textView.setCompoundDrawables(null, ContextCompat.getDrawable(getActivity(),R.drawable.ic_error_outline_black_24dp),null,null);
                    textView.setText("No Reviews yet");
                    comment_container.addView(textView);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
