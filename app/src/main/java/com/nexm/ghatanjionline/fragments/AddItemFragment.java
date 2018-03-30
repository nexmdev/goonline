package com.nexm.ghatanjionline.fragments;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.nexm.ghatanjionline.Constants.ConstantRef;
import com.nexm.ghatanjionline.GOApplication;
import com.nexm.ghatanjionline.R;
import com.nexm.ghatanjionline.models.Delivery;
import com.nexm.ghatanjionline.models.ExtraItemInfo;
import com.nexm.ghatanjionline.models.Item;
import com.nexm.ghatanjionline.models.ListItem;
import com.nexm.ghatanjionline.models.Provider;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddItemFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String category, subCategory;
    private String pID = "X";
    private int noItems;
    private EditText e1,e2,e3,e4,e5,e6,e7,e8,e9,e10;
    private Spinner spinner2;
    private Item item;
    private Provider provider;
    private Delivery delivery;
    private CheckBox box1,box2;
    private ListItem listItem;
    private Button b , verifyButton;
    private ProgressBar progressBar;
    private  final DatabaseReference ref = GOApplication.database.getReference();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    public AddItemFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddItemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddItemFragment newInstance(String param1, String param2) {
        AddItemFragment fragment = new AddItemFragment();
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
        final View view = inflater.inflate(R.layout.fragment_add_item, container, false);
        getEditTexts(view);

        progressBar = (ProgressBar)view.findViewById(R.id.add_item_progressbar);
        item = new Item();
        delivery = new Delivery();
        provider = new Provider();
        listItem = new ListItem();

        b = (Button)view.findViewById(R.id.add_item_button_submit);
        verifyButton = (Button)view.findViewById(R.id.add_item_mo_no_submit_button);
        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String mono = e5.getText().toString();
                ref.child(ConstantRef.PROVIDERS).orderByChild("providerMO_NO")
                        .equalTo(mono).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot != null){

                            for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                            Provider provider = snapshot.getValue(Provider.class);
                            if(provider != null){

                                e4.setText(provider.providerNAME);
                                e5.setText(provider.providerMO_NO);
                                e6.setText(provider.providerADRESS);
                                e7.setText(provider.providerEMAIL);
                                pID = provider.providerID;
                                noItems = provider.providerNO_OF_ITEMS;
                                progressBar.setVisibility(View.GONE);
                            }}
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmit();
            }
        });

        final Spinner spinner1 = (Spinner)view.findViewById(R.id.add_item_spinner_category);
        spinner2 = (Spinner)view.findViewById(R.id.add_item_spinner_subcategory);

        final String[] categories = { "Food","Transport","Services","Old Shop","Rentals","Cloths","Crafts"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, categories);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setEnabled(true);
        spinner1.setAdapter(arrayAdapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = parent.getItemAtPosition(position).toString();
                setUpSpinner2(position);
                setOptionalHints();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                setUpSpinner2(1);
                setOptionalHints();
            }
        });
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

        box1 = (CheckBox)view.findViewById(R.id.add_item_checkbox_cod);
        box2 = (CheckBox)view.findViewById(R.id.add_item_checkbox_return);

        return view;
    }

    private void getEditTexts(View view) {

        e1 = (EditText)view.findViewById(R.id.add_item_editText_item_name);
        e2 = (EditText)view.findViewById(R.id.add_item_editText_item_price);
        e3 = (EditText)view.findViewById(R.id.add_item_editText_item_description);
        e4 = (EditText)view.findViewById(R.id.add_item_editText_provider_name);
        e5 = (EditText)view.findViewById(R.id.add_item_editText_provider_mobile_no);
        e6 = (EditText)view.findViewById(R.id.add_item_editText_provider_address);
        e7 = (EditText)view.findViewById(R.id.add_item_editText_provider_email);
        e8 = (EditText)view.findViewById(R.id.add_item_editText_return_policy);
        e9 = (EditText)view.findViewById(R.id.add_item_editText_min_order);
        e10 = (EditText)view.findViewById(R.id.add_item_editText_delivery_charges);



    }

    private void setUpSpinner2(int i) {
        final String[] food_subcategories = { "Breakfast","Meal"};
        final String[] transport_subcategories = { "Cars","Cruisers"};
        final String[] services_subcategories = { "Electrician","Painter"};
        final String[] oldshop_subcategories = { "Mobiles","Household"};
        final String[] rentals_subcategories = { "Rooms","Shops"};
        final String[] cloths_subcategories = { "Kids","Women"};
        final String[] crafts_subcategories = { "Paintings","Embroidery"};
        ArrayAdapter<String> arrayAdapter = null;

        switch(i){

            case 0 : arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, food_subcategories);
                    break;
            case 1 : arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, transport_subcategories);
                    break;
            case 2 : arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, services_subcategories);
                    break;
            case 3 : arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, oldshop_subcategories);
                    break;
            case 4 : arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, rentals_subcategories);
                    break;
            case 5 : arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, cloths_subcategories);
                break;
            case 6 : arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, crafts_subcategories);
                break;
        }

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setEnabled(true);
        spinner2.setAdapter(arrayAdapter);
    }

    public void onSubmit() {

        progressBar.setVisibility(View.VISIBLE);

        OnCompleteListener<Void> ol = new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Item Set Successful !");
                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        clearForm();
                    }
                });
                AlertDialog alertDialog = builder.create();
                progressBar.setVisibility(View.GONE);
                alertDialog.show();
            }
        };

        if(category.matches("Food")||category.matches("Old Shop")||category.matches("Cloths")||category.matches("Crafts")){

            delivery.deliveryCOD_AVAILABLE = box1.isChecked();
            delivery.deliveryRETURN_AVAILABLE = box2.isChecked();
            delivery.deliveryRETURN_POLICY = e8.getText().toString();
            delivery.deliveryMIN_ORDER = Integer.parseInt(e9.getText().toString());
            delivery.deliveryDELIVERY_CHARGES = Integer.parseInt(e10.getText().toString());
            delivery.deliveryID = ref.child(ConstantRef.DELIVERIES).push().getKey();
            listItem.deliveryID = delivery.deliveryID;
            ref.child(ConstantRef.DELIVERIES).child(delivery.deliveryID).setValue(delivery).addOnCompleteListener(ol);

        }else{

            listItem.deliveryID = ConstantRef.DELIVERY_NOT_APPLICABLE;
            ExtraItemInfo extraItemInfo = new ExtraItemInfo();
            extraItemInfo.emergencyPOLICY = e8.getText().toString();
            extraItemInfo.itemYEAR = e10.getText().toString();
            if(e9.isEnabled()){extraItemInfo.itemServiceArea = e9.getText().toString();
            }else{extraItemInfo.itemServiceArea = "XXXX";}
            extraItemInfo.emergencyAVAILABLE = box1.isChecked();

            extraItemInfo.extra_item_infoID = ref.child(ConstantRef.EXTRA_INFO).push().getKey();

            ref.child(ConstantRef.EXTRA_INFO).child(extraItemInfo.extra_item_infoID).setValue(extraItemInfo);
        }


        item.itemNAME = e1.getText().toString();
        item.itemPRICE = Float.parseFloat(e2.getText().toString());
        item.itemDESCRIPTION = e3.getText().toString();

        provider.providerNAME = e4.getText().toString();
        provider.providerMO_NO = e5.getText().toString();
        provider.providerADRESS = e6.getText().toString();
        provider.providerEMAIL = e7.getText().toString();


        item.itemID = ref.child(ConstantRef.ITEMS).push().getKey();
        if(pID.matches("X")){
            provider.providerID = ref.child(ConstantRef.PROVIDERS).push().getKey();
            ref.child(ConstantRef.PROVIDERS).child(provider.providerID).setValue(provider);
        }else{
            ref.child(ConstantRef.PROVIDERS).child(pID).child("providerNO_OF_ITEMS")
                    .setValue(noItems++);
        }


        listItem.itemNAME = e1.getText().toString();
    //    listItem.itemPRICE = Float.parseFloat(e2.getText().toString());
        listItem.providerNAME = e4.getText().toString();
        listItem.itemID = item.itemID;
        listItem.providerID = provider.providerID;


        ref.child(ConstantRef.ITEMS).child(item.itemID).setValue(item);

        ref.child(ConstantRef.LIST).child(category).child(subCategory).push().setValue(listItem).addOnCompleteListener(ol);

    }
    private void setOptionalHints(){
        if(category.matches("Transport")){
            box1.setText("Emergency Available");
            box2.setEnabled(false);
            e8.setEnabled(true);
            e9.setEnabled(true);
            e8.setHint("Emergency Polycy");
            e9.setHint("Registration number");
            e10.setHint("Model Year");
        }else if(category.matches("Services")){
            box1.setText("Urgent Available");
            box2.setEnabled(false);
            e8.setEnabled(true);
            e8.setHint("Urgency Polycy");
            e9.setHint("Servicable Area");
            e10.setHint("Year of start of Business");
        }else if(category.matches("Rentals")){
            box1.setText("Ready Available");
            box2.setEnabled(false);
            e8.setHint("Availability for whome");
            e9.setEnabled(false);
            e10.setHint("Year of construction");
        }else {

            clearForm();
        }

    }

    private void clearForm() {

        e1.setText("");
        e2.setText("");
        e3.setText("");
        e4.setText("");
        e5.setText("");
        e6.setText("");
        e7.setText("");
        e8.setText("");
        e9.setEnabled(true);
        e9.setText("");
        e10.setText("");
        box1.setChecked(false);
        box2.setChecked(false);
        box2.setEnabled(true);
    }
}
