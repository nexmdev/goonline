package com.nexm.ghatanjionline.fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.nexm.ghatanjionline.Constants.ConstantRef;
import com.nexm.ghatanjionline.GOApplication;
import com.nexm.ghatanjionline.R;
import com.nexm.ghatanjionline.models.Category;

import java.util.ArrayList;


public class Add_Ur_item_category_tab extends Fragment {

    private DatabaseReference reference;
    private Spinner spinner1,spinner2;
    private String pID , category , subCategory;
    private ArrayList<String> categories,subCategories;
    private OnFragmentInteractionListener mListener;
    private TextView next;

    public Add_Ur_item_category_tab() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Add_Ur_item_category_tab newInstance() {
        Add_Ur_item_category_tab fragment = new Add_Ur_item_category_tab();

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
        View view = inflater.inflate(R.layout.fragment_add__ur_item_category_tab, container, false);
        categories = new ArrayList<>();
        subCategories = new ArrayList<>();
        spinner1 = (Spinner)view.findViewById(R.id.add_newItem_spinner_category);
        spinner2 = (Spinner)view.findViewById(R.id.add_newItem_spinner_subcategory);
        next = (TextView)view.findViewById(R.id.add_ur_item_category_tab_next_button);
        reference = GOApplication.database.getReference().child(ConstantRef.CATEGORY);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                    Category categoryModel = snapshot.getValue(Category.class);
                    categories.add(categoryModel.categoryID);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner, categories);
                arrayAdapter.setDropDownViewResource(R.layout.dropdown_layout);

                spinner1.setEnabled(true);
                spinner1.setAdapter(arrayAdapter);
                spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        category = parent.getItemAtPosition(position).toString();

                        setUpSpinner2(category);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        setUpSpinner2(category);

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(category!= null && subCategory!= null){

                    mListener.onCategorySelection(category,subCategory);

                }
            }
        });

        return view;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String uri,String sub) {
        if (mListener != null) {
            mListener.onCategorySelection(uri,sub );
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
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onCategorySelection(String category, String subCategory);
    }
    private void setUpSpinner2(String mCategory) {

        subCategories.clear();
        reference = GOApplication.database.getReference().child(ConstantRef.SUBCATEGORY).child(mCategory);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Category subCategory = snapshot.getValue(Category.class);
                    subCategories.add(subCategory.categoryID);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),R.layout.spinner, subCategories);
                arrayAdapter.setDropDownViewResource(R.layout.dropdown_layout);
                spinner2.setEnabled(true);
                spinner2.setAdapter(arrayAdapter);
                spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        subCategory = parent.getItemAtPosition(position).toString();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        subCategory = parent.getItemAtPosition(0).toString();

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }

}
