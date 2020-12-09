package com.nexm.ghatanjionline;


import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.nexm.ghatanjionline.Constants.ConstantRef;
import com.nexm.ghatanjionline.adminFragments.AddFoodFragment;
import com.nexm.ghatanjionline.models.Category;
import com.nexm.ghatanjionline.models.Delivery;
import com.nexm.ghatanjionline.models.ListItem;
import com.nexm.ghatanjionline.models.Provider;
import com.nexm.ghatanjionline.models.ProviderItemList;

import java.util.ArrayList;

public class AddNewItemActivity extends AppCompatActivity {

    private DatabaseReference reference;
    public static ListItem listItem;
    public static ProgressBar progressBar;
    private LinearLayout providerLayout,deliveryLayout;
    private EditText pNAME,pMONO,pADDRESS,pEMAIL;
    private String pID , category , subCategory;
    private ArrayList<String> categories,subCategories;
    private int noItems;
    private Spinner spinner1,spinner2;
    private FrameLayout itemLayout;
    private TextView pTextView,dTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_item);


        listItem = new ListItem();
        progressBar = (ProgressBar)findViewById(R.id.progressBar_addNewItem);
        providerLayout = (LinearLayout)findViewById(R.id.provider_layout);
        deliveryLayout = (LinearLayout)findViewById(R.id.deliveryLayout);
        itemLayout = (FrameLayout)findViewById(R.id.add_newItem_framelayout_for_item);
        // provider editTexts
        pNAME = (EditText)findViewById(R.id.add_newItem_editText_provider_name);
        pADDRESS = (EditText)findViewById(R.id.add_newItem_editText_provider_address);
        pMONO = (EditText)findViewById(R.id.add_newItem_editText_provider_mobile_no);
        pEMAIL = (EditText)findViewById(R.id.add_newItem_editText_provider_email);

        pTextView = (TextView)findViewById(R.id.add_newItem_provider_textView);
        dTextView = (TextView)findViewById(R.id.add_newItem_delivery_textView);

        categories = new ArrayList<>();
        subCategories = new ArrayList<>();

        //spinners
        spinner1 = (Spinner)findViewById(R.id.add_newItem_spinner_category);
        spinner2 = (Spinner)findViewById(R.id.add_newItem_spinner_subcategory);

        reference = GOApplication.database.getReference().child(ConstantRef.CATEGORY);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                    Category categoryModel = snapshot.getValue(Category.class);
                    categories.add(categoryModel.categoryID);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, categories);
                arrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown);

                spinner1.setEnabled(true);
                spinner1.setAdapter(arrayAdapter);
                spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        category = parent.getItemAtPosition(position).toString();
                        showDeliveryLayout();
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



    }

    private void showDeliveryLayout() {

        if(category.matches("Transport")||category.matches("Services")||category.matches("Rentals")){
            deliveryLayout.setVisibility(View.GONE);
            dTextView.setVisibility(View.GONE);
        }else {
            deliveryLayout.setVisibility(View.VISIBLE);
            dTextView.setVisibility(View.VISIBLE);
        }
    }

    private void showItemLayout() {

                AddFoodFragment foodfragment = AddFoodFragment.newInstance(category,subCategory);
                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.add_newItem_framelayout_for_item,foodfragment,"FoodFragment")
                        .disallowAddToBackStack().commit();

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
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(),R.layout.spinner_item, subCategories);
                arrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
                spinner2.setEnabled(true);
                spinner2.setAdapter(arrayAdapter);
                spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        subCategory = parent.getItemAtPosition(position).toString();
                        showItemLayout();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        subCategory = parent.getItemAtPosition(0).toString();
                        showItemLayout();
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }

    public void verifyMobile(View view) {

        final EditText verifyMoNo = (EditText)findViewById(R.id.addNewItem_editText_mobileNo_verification);
        String mono = verifyMoNo.getText().toString();
        mono = mono.length()>0 ? mono : "123";

        if(mono.matches("123")){

            Provider provider = null;
            providerEditTexts(provider);

        }else{
            reference = GOApplication.database.getReference();
            reference.child(ConstantRef.PROVIDERS).orderByChild("providerMO_NO")
                    .equalTo(mono).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot != null){

                        progressBar.setVisibility(View.VISIBLE);
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                            Provider provider = snapshot.getValue(Provider.class);
                            providerEditTexts(provider);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

    }
    private void providerEditTexts(Provider provider){

        progressBar.setVisibility(View.GONE);
        providerLayout.setVisibility(View.VISIBLE);
        pTextView.setVisibility(View.VISIBLE);
        if(provider != null){

            pNAME.setText(provider.providerNAME);
            pMONO.setText(provider.providerMO_NO);
            pADDRESS.setText(provider.providerADRESS);
            pEMAIL.setText(provider.providerEMAIL);
            pID = provider.providerID;
            noItems = provider.providerNO_OF_ITEMS;
            progressBar.setVisibility(View.GONE);
            listItem.providerID = pID;
            listItem.providerNAME = provider.providerNAME;
        }

    }

    public void submitNewProvider(View view) {

        if(pID == null){
            progressBar.setVisibility(View.VISIBLE);
            Provider newProvider = new Provider();
            newProvider.providerNAME = pNAME.getText().toString();
            newProvider.providerMO_NO = pMONO.getText().toString();
            newProvider.providerADRESS = pADDRESS.getText().toString();
            newProvider.providerEMAIL = pEMAIL.getText().toString();

            reference = GOApplication.database.getReference();
            newProvider.providerID = reference.child(ConstantRef.PROVIDERS).push().getKey();

            listItem.providerID = newProvider.providerID;
            listItem.providerNAME = newProvider.providerNAME;

            reference.child(ConstantRef.PROVIDERS).child(newProvider.providerID)
                    .setValue(newProvider)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){

                                Toast.makeText(getApplicationContext(),"Provider set successful !",Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
        }

    }

    public void submitDeliveryData(View view) {

        progressBar.setVisibility(View.VISIBLE);
        final EditText dRETURN_POLICY = (EditText)findViewById(R.id.add_newItem_editText_return_policy);
        final EditText dMIN_ORDER = (EditText)findViewById(R.id.add_newItem_editText_min_order);
        final EditText dDELIVERY_CHARGES = (EditText)findViewById(R.id.add_newItem_editText_delivery_charges);
        final EditText dDELIVERY_TIME = (EditText)findViewById(R.id.add_newItem_editText_delivery_time);
        final CheckBox dRETURN_AVAILABLE = (CheckBox) findViewById(R.id.add_newItem_checkbox_return);
        final CheckBox dCOD_AVAILABLE = (CheckBox) findViewById(R.id.add_newItem_checkbox_cod);

        final Delivery delivery = new Delivery();
        delivery.deliveryRETURN_POLICY = dRETURN_POLICY.getText().toString();
        delivery.deliveryMIN_ORDER = Integer.parseInt(dMIN_ORDER.getText().toString());
        delivery.deliveryDELIVERY_CHARGES = Integer.parseInt(dDELIVERY_CHARGES.getText().toString());
        delivery.deliveryRETURN_AVAILABLE = dRETURN_AVAILABLE.isChecked();
        delivery.deliveryCOD_AVAILABLE = dCOD_AVAILABLE.isChecked();
        delivery.deliveryTIME = dDELIVERY_TIME.getText().toString();

        reference = GOApplication.database.getReference().child(ConstantRef.DELIVERIES);
        delivery.deliveryID = reference.push().getKey();
        reference.child(delivery.deliveryID).setValue(delivery)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        progressBar.setVisibility(View.GONE);
                        if(task.isSuccessful()){

                            Toast.makeText(getApplicationContext(),"Delivery Set Successful",Toast.LENGTH_SHORT).show();
                            dRETURN_POLICY.setText("");
                            dDELIVERY_CHARGES.setText("");
                            dDELIVERY_TIME.setText("");
                            dMIN_ORDER.setText("");
                            dRETURN_AVAILABLE.setChecked(false);
                            dCOD_AVAILABLE.setChecked(false);
                            AddNewItemActivity.listItem.deliveryID = delivery.deliveryID;
                        }else {
                            Toast.makeText(getApplicationContext(),"XXX Delivery Set Unsuccessful",Toast.LENGTH_SHORT).show();

                        }
                    }
                });



    }

    public void processOK(View view) {

        progressBar.setVisibility(View.VISIBLE);
        reference = GOApplication.database.getReference();
        reference.child(ConstantRef.LIST).child(category).child(subCategory).push().setValue(listItem)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressBar.setVisibility(View.GONE);
                        if(task.isSuccessful()){

                            updateProviderItems();

                            Toast.makeText(getApplicationContext(),"Data Set Successful",Toast.LENGTH_SHORT).show();
                            recreateForm();

                        }else {
                            Toast.makeText(getApplicationContext(),"XXX Data Set Unsuccessful",Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    private void recreateForm() {

        this.recreate();
    }

    private void updateProviderItems() {

        if(pID != null){
            noItems++;
            reference = GOApplication.database.getReference();
            reference.child(ConstantRef.PROVIDERS).child(pID).child("providerNO_OF_ITEMS")
                    .setValue(noItems);

            ProviderItemList providerItemList = new ProviderItemList();
            providerItemList.itemID = listItem.itemID;
            providerItemList.categoryID = category;
            providerItemList.subCategoryID = subCategory;

            reference.child(ConstantRef.PROVIDER_ITEM_LIST).child(pID).push()
                    .setValue(providerItemList);

        }

    }


}
