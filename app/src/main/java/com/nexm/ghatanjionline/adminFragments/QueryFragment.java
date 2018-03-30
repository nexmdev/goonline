package com.nexm.ghatanjionline.adminFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.nexm.ghatanjionline.Constants.ConstantRef;
import com.nexm.ghatanjionline.GOApplication;
import com.nexm.ghatanjionline.R;
import com.nexm.ghatanjionline.models.Provider;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QueryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QueryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ProgressBar progressBar;


    public QueryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QueryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QueryFragment newInstance(String param1, String param2) {
        QueryFragment fragment = new QueryFragment();
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
        final View view = inflater.inflate(R.layout.fragment_query, container, false);
        progressBar = (ProgressBar)view.findViewById(R.id.queryProgressBar);
        final Button searchButton = (Button)view.findViewById(R.id.querySearchProvidersButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);
                final EditText mobileNoEditText = (EditText)view.findViewById(R.id.queryMobileNoEditText);
                if(mobileNoEditText.getText().length()==10){

                    DatabaseReference reference = GOApplication.database.getReference();
                    reference.child(ConstantRef.PROVIDERS).orderByChild("providerMO_NO")
                            .equalTo(mobileNoEditText.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot != null){


                                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                    Provider provider = snapshot.getValue(Provider.class);

                                    showProvider(provider,view);

                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
        return view;
    }

    private void showProvider(Provider provider,View v) {

        progressBar.setVisibility(View.GONE);

        if(provider != null){

            final LinearLayout layout = (LinearLayout)v.findViewById(R.id.queryProviderLayout);
            layout.setVisibility(View.VISIBLE);
            final TextView tID = (TextView)v.findViewById(R.id.queryProviderID);
            final TextView tNAME = (TextView)v.findViewById(R.id.queryProviderNameTextView);
            final TextView tADDRESS = (TextView)v.findViewById(R.id.queryProviderAddress);
            final TextView tEMAIL = (TextView)v.findViewById(R.id.queryProviderEmail);
            final TextView tNO_OF_ITEMS = (TextView)v.findViewById(R.id.queryProviderNoOfItems);
            final TextView tNO_OF_ORDERS = (TextView)v.findViewById(R.id.queryProviderNoOfOrders);
            final TextView tNO_OF_INQUIRES = (TextView)v.findViewById(R.id.queryProviderNoOfInquires);

            tID.setText(provider.providerID);
            tNAME.setText(provider.providerNAME);
            tADDRESS.setText(provider.providerADRESS);
            tEMAIL.setText(provider.providerEMAIL);
            tNO_OF_INQUIRES.setText(String.valueOf(provider.providerNO_OF_INQUIRES));
            tNO_OF_ITEMS.setText(String.valueOf(provider.providerNO_OF_ITEMS));
            tNO_OF_ORDERS.setText(String.valueOf(provider.providerNO_OF_ORDERS));

            final Button button = (Button)v.findViewById(R.id.querySearchItemsButton);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });


        }else{

            Toast.makeText(getActivity(),"Provider Not Found",Toast.LENGTH_SHORT).show();

        }


    }

}
