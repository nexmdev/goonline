package com.nexm.ghatanjionline;

import android.content.Intent;
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
import com.nexm.ghatanjionline.fragments.SignUpFragment;
import com.nexm.ghatanjionline.fragments.SubCategoryFragment;
import com.nexm.ghatanjionline.fragments.SubRepair;
import com.nexm.ghatanjionline.fragments.WebAdFragment;
import com.nexm.ghatanjionline.models.ProductListing;

public class ProductActivity extends AppCompatActivity
        implements  ProductCategoryFragment.OnFragmentInteractionListener
                    ,SubCategoryFragment.OnSubCategorySelected ,
                    ServicesDetail.OnFragmentInteractionListener,
                    SubRepair.OnFragmentInteractionListener,
                    AllCategoriesFragment.OnFragmentInteractionListener,
                    CommonDetailsFragment.OnFragmentInteractionListener,
                    OldShopDetail.OnFragmentInteractionListener,
                    FoodDetailFragment.OnFragmentInteractionListener,
                    WebAdFragment.OnFragmentInteractionListener,
        SignUpFragment.OnFragmentInteractionListener{

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
            String caller = getIntent().getStringExtra("Caller");
            String cartID = getIntent().getStringExtra("CartID");
            cartID = cartID == null ? "x": cartID;
            String tag = getIntent().getStringExtra("Tag");

            switch (tag){
                case "Sub":
                    goToSubCategory(selectedsubCategory, selectedCategory);
                    break;
                case "Product":
                    GOApplication.databaseReference = GOApplication.database.getReference();
                    String finalCartID = cartID;
                    GOApplication.databaseReference.child("ProductListings")
                            .child(itemID)
                            .addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.exists()){
                                        ProductListing productListing = dataSnapshot.getValue(ProductListing.class);

                                        goToItemDetail(productListing,-1,dataSnapshot.getKey(), finalCartID);
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
    public void onCategorySelected(String department,String category) {

        SubCategoryFragment fragment = SubCategoryFragment
                .newInstance(department,category);

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
    public void onFragmentInteraction1(ProductListing data, int currentposition, String productID){
        goToItemDetail(data, currentposition,productID, "x");

    }

    private void goToItemDetail(ProductListing data, int currentposition, String productID, String cartID) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("Product_Listing",data);
        bundle.putString("PRODUCT_ID",productID);
        bundle.putInt("POSITION",currentposition);
        bundle.putString("CART_ID",cartID);
        Fragment fragment = null;
        switch (data.getDepartment()){

            case "ट्रांसपोर्ट":
                getSupportActionBar().setTitle(data.getDepartment());

                fragment = new ProductDetailFragment();
                fragment.setArguments(bundle);
                break;
            case "सर्विसेस":case ConstantRef.PROPERTY:
                getSupportActionBar().setTitle(data.getDeptCat());
                fragment = new ServicesDetail();
                fragment.setArguments(bundle);
                break;
            case ConstantRef.OLD_SHOP:
                getSupportActionBar().setTitle(data.getDepartment());
                fragment = new OldShopDetail();
                fragment.setArguments(bundle);
                break;
            case ConstantRef.FOOD:
                String[] title = data.getDeptCat().split(",");

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
            String backStateName = fragment.getClass().getName();
            boolean fragmentPopped = getSupportFragmentManager().popBackStackImmediate(backStateName,0);
            if(!fragmentPopped){
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(android.R.anim.slide_in_left,
                                android.R.anim.slide_out_right,
                                R.anim.slide_in_right,
                                R.anim.slide_out_left)
                        .replace(R.id.product_activity_fragment_holder_layout,fragment)
                        .addToBackStack(backStateName)
                        .commit();
            }
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
    public void onCommonDetailsClick(ProductListing productListing, int position, String productID) {
        goToItemDetail(productListing, position,productID, "x");
    }

    @Override
    public void onServicesDetailClick(Uri uri) {

    }

    @Override
    public void onOldShopDetailClick(Uri uri) {

    }

    @Override
    public void onFoodDetailsClick(String task) {
        if(task.matches("SignIn")){
            SignUpFragment signUpFragment = new SignUpFragment();
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(android.R.anim.slide_in_left,
                            android.R.anim.slide_out_right,
                            R.anim.slide_in_right,
                            R.anim.slide_out_left)
                    .replace(R.id.product_activity_fragment_holder_layout,signUpFragment)
                    .addToBackStack("FoodDetail")
                    .commit();
        }else{
            //Toast.makeText(ProductActivity.this,task,Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ProductActivity.this,Main3Activity.class);
            intent.putExtra("CALLER","Cart");
            intent.putExtra("userID",task);
            startActivity(intent);
        }

    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onFragmentInteraction(int code) {

    }
}
