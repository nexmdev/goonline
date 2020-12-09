package com.nexm.ghatanjionline;

import android.net.Uri;
import android.os.Bundle;
import com.google.android.material.appbar.AppBarLayout;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.nexm.ghatanjionline.Constants.ConstantRef;
import com.nexm.ghatanjionline.fragments.AllCategoriesFragment;
import com.nexm.ghatanjionline.fragments.CommonDetailsFragment;
import com.nexm.ghatanjionline.fragments.FoodDetailFragment;
import com.nexm.ghatanjionline.fragments.OldShopDetail;
import com.nexm.ghatanjionline.fragments.ProductCategoryFragment;
import com.nexm.ghatanjionline.fragments.ProductDetailFragment;
import com.nexm.ghatanjionline.fragments.ServicesDetail;
import com.nexm.ghatanjionline.fragments.SubCategoryFragment;
import com.nexm.ghatanjionline.fragments.SubRepair;
import com.nexm.ghatanjionline.fragments.WebAdFragment;
import com.nexm.ghatanjionline.models.ListItem;

public class ProductActivity extends AppCompatActivity
        implements  ProductCategoryFragment.OnFragmentInteractionListener
                    ,SubCategoryFragment.OnSubCategorySelected ,
                    ServicesDetail.OnFragmentInteractionListener,
                    SubRepair.OnFragmentInteractionListener,
                    AllCategoriesFragment.OnFragmentInteractionListener,
                    CommonDetailsFragment.OnFragmentInteractionListener,
                    OldShopDetail.OnFragmentInteractionListener,
                    FoodDetailFragment.OnFragmentInteractionListener,
                    WebAdFragment.OnFragmentInteractionListener{

    public static AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        appBarLayout = (AppBarLayout)findViewById(R.id.productAppBar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(savedInstanceState == null ) {

            String selectedsubCategory = getIntent().getStringExtra("Selected subCategory");
            String selectedCategory = getIntent().getStringExtra("Category");
            String itemID = getIntent().getStringExtra("ItemID");
            String allCategories = getIntent().getStringExtra("AllCategories");
            String tag = getIntent().getStringExtra("Tag");

            switch (tag){
                case "Sub":
                    goToSubCategory(selectedsubCategory, selectedCategory);
                    break;
                case "Item":
                    GOApplication.databaseReference = GOApplication.database.getReference();
                    GOApplication.databaseReference.child(ConstantRef.LIST)
                            .child(selectedCategory)
                            .child(selectedsubCategory)
                            .child(itemID)
                            .addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.exists()){
                                        ListItem listItem = dataSnapshot.getValue(ListItem.class);
                                        goToItemDetail(listItem,-1);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                    break;
                case "All":

                        getSupportActionBar().setTitle(selectedCategory);
                        ProductCategoryFragment fragment = ProductCategoryFragment.newInstance(selectedCategory);
                        getSupportFragmentManager().beginTransaction()
                                .setCustomAnimations(android.R.anim.slide_in_left,
                                        android.R.anim.slide_out_right,
                                        R.anim.slide_in_right,
                                        R.anim.slide_out_left)
                                .replace(R.id.product_activity_fragment_holder_layout,fragment)
                                .commit();
                        break;
                case "Web":
                    WebAdFragment webAdFragment = WebAdFragment.newInstance(selectedCategory);
                    getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(android.R.anim.slide_in_left,
                                    android.R.anim.slide_out_right,
                                    R.anim.slide_in_right,
                                    R.anim.slide_out_left)
                            .replace(R.id.product_activity_fragment_holder_layout,webAdFragment)
                            .commit();
                    break;

            }



        }
    }

    private void goToSubCategory(String selectedsubCategory, String selectedCategory) {
        SubCategoryFragment fragment = SubCategoryFragment
                                        .newInstance(selectedCategory,selectedsubCategory);

        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left,
                        android.R.anim.slide_out_right,
                        R.anim.slide_in_right,
                        R.anim.slide_out_left)
                .replace(R.id.product_activity_fragment_holder_layout,fragment,"SubCategory")
                .commit();
    }

    @Override
    public void onCategorySelected(String selectedCategory,String selectedsubCategory) {

        SubCategoryFragment fragment = SubCategoryFragment
                .newInstance(selectedCategory,selectedsubCategory);

        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left,
                        android.R.anim.slide_out_right,
                        R.anim.slide_in_right,
                        R.anim.slide_out_left)
                .replace(R.id.product_activity_fragment_holder_layout,fragment,"SubCategory")
                .addToBackStack("Category")
                .commit();

    }

    @Override
    public void onFragmentInteraction1(ListItem data, int currentposition){
        goToItemDetail(data, currentposition);

    }

    private void goToItemDetail(ListItem data, int currentposition) {
        Bundle bundle = new Bundle();
        bundle.putInt("POSITION",currentposition);
        bundle.putInt("RATINGS",data.itemRATINGS);
        bundle.putInt("NO_OF_USERS",data.itemNO_OF_REVIEWS);
        bundle.putString("CATEGORY",data.category);
        bundle.putString("SUB_CATEGORY",data.subCategory);
        bundle.putString("ITEM_ID",data.itemID);
        bundle.putString("PROVIDER_ID",data.providerID);
        bundle.putString("DELIVERY_ID",data.deliveryID);
        Fragment fragment = null;
        switch (data.category){

            case "ट्रांसपोर्ट":
                getSupportActionBar().setTitle(data.itemNAME);
                fragment = new ProductDetailFragment();
                fragment.setArguments(bundle);
                break;
            case "सर्विसेस":case ConstantRef.PROPERTY:
                getSupportActionBar().setTitle(data.subCategory);
                fragment = new ServicesDetail();
                fragment.setArguments(bundle);
                break;
            case ConstantRef.OLD_SHOP:
                getSupportActionBar().setTitle(data.itemNAME);
                fragment = new OldShopDetail();
                fragment.setArguments(bundle);
                break;
            case ConstantRef.FOOD:
                getSupportActionBar().setTitle(data.itemNAME);
                fragment = new FoodDetailFragment();
                fragment.setArguments(bundle);
                break;
        }

        if(currentposition ==-1){
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(android.R.anim.slide_in_left,
                            android.R.anim.slide_out_right,
                            R.anim.slide_in_right,
                            R.anim.slide_out_left)
                    .replace(R.id.product_activity_fragment_holder_layout,fragment)
                    .commit();
        }else{
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(android.R.anim.slide_in_left,
                            android.R.anim.slide_out_right,
                            R.anim.slide_in_right,
                            R.anim.slide_out_left)
                    .replace(R.id.product_activity_fragment_holder_layout,fragment)
                    .addToBackStack("SubCategory")
                    .commit();
        }

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        appBarLayout = null;
    }
    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }



    @Override
    public void onWebFragmentClick(Uri uri) {

    }

    @Override
    public void onAllCategorySelected(String categoryName) {
      //  Toast.makeText(getApplicationContext(),categoryName,Toast.LENGTH_SHORT).show();
        ProductCategoryFragment fragment = ProductCategoryFragment.newInstance(categoryName);
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left,
                        android.R.anim.slide_out_right,
                        R.anim.slide_in_right,
                        R.anim.slide_out_left)
                .replace(R.id.product_activity_fragment_holder_layout,fragment)
                .addToBackStack("AllCategories")
                .commit();
    }

    @Override
    public void onCommonDetailsClick(Uri uri) {

    }

    @Override
    public void onServicesDetailClick(Uri uri) {

    }

    @Override
    public void onOldShopDetailClick(Uri uri) {

    }

    @Override
    public void onFoodDetailsClick(Uri uri) {

    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
