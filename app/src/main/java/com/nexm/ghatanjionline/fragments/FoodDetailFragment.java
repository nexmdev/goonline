package com.nexm.ghatanjionline.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.nexm.ghatanjionline.Constants.ConstantRef;
import com.nexm.ghatanjionline.GOApplication;
import com.nexm.ghatanjionline.ProductActivity;
import com.nexm.ghatanjionline.R;
import com.nexm.ghatanjionline.models.CartItem;
import com.nexm.ghatanjionline.models.Delivery;
import com.nexm.ghatanjionline.models.ProductDelivery;
import com.nexm.ghatanjionline.models.ProductDetails;
import com.nexm.ghatanjionline.models.ProductListing;
import com.nexm.ghatanjionline.models.Provider;
import com.nexm.ghatanjionline.models.Seller;

import java.util.HashMap;
import java.util.Map;


public class FoodDetailFragment extends Fragment  implements
        CommonDetailsFragment.OnFragmentInteractionListener{

    private OnFragmentInteractionListener mListener;
    private String category , subCategory,itemID,providerID,deliveryID;
    private CommonDetailsFragment commonDetailsFragment;
    private Provider provider;
    private Seller seller;
    private Delivery delivery;
    private ProductDelivery productDelivery;
    private ProductDetails productDetails;
    private int plates = 1,orderAmount=0;
    private int no_ratings,no_reviews;
    private ProductListing productListing;
    private TextView orderButton;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private CartItem cartItem;
    private String cartID;

    public FoodDetailFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food_detail, container, false);
        getActivity().setTitle(productListing.getDeptCat());
        orderButton = view.findViewById(R.id.product_detail_button_booknow);
        progressBar = view.findViewById(R.id.food_detail_order_progressbar);
        FragmentManager fragmentManager = getChildFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.common_details_container,commonDetailsFragment).commit();
        getData(view);
        cartItem = new CartItem();
        auth = FirebaseAuth.getInstance();
        if(!cartID.matches("x")){
            orderButton.setText("Go To Cart");
            orderButton.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,null,null);
        }
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                if(!orderButton.getText().toString().matches("Go To Cart")){
                    user = auth.getCurrentUser();
                    if(user == null){
                        if(mListener!= null){
                            mListener.onFoodDetailsClick("SignIn");
                        }
                    }else{
                        createCartData();
                        saveToUserCart();
                    }
                }else{
                    mListener.onFoodDetailsClick(user.getUid());
                }

            }
        });
        return view;
    }

    private void saveToUserCart() {
        Map dataMap = new HashMap();
        String cartItemKey = GOApplication.databaseReference.child("Cart")
                .child(user.getUid()).push().getKey();

        dataMap.put("Cart/"+user.getUid()+"/cartItems/"+cartItemKey, cartItem);
        dataMap.put("CUSTOMERS/"+user.getUid()+"/cartitemsNo", ServerValue.increment(1));

        int delivery = cartItem.getQuantity() >= cartItem.getFreeDelivery() ? 0 : cartItem.getQuantity() * cartItem.getDeliveryCharges();
        dataMap.put("Cart/"+user.getUid()+"/cartTotal", ServerValue.increment(orderAmount+delivery));

       GOApplication.databaseReference.updateChildren(dataMap)
               .addOnCompleteListener(new OnCompleteListener() {
                   @Override
                   public void onComplete(@NonNull Task task) {
                       if(task.isSuccessful()){
                           progressBar.setVisibility(View.GONE);
                           Toast.makeText(getActivity(),"यशस्वी\nऔर्डर शाौपिंग कार्ट मध्ये जोडली गेली आहे.",Toast.LENGTH_LONG).show();
                           orderButton.setText("Go To Cart");
                           orderButton.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,null,null);
                       }else{
                           progressBar.setVisibility(View.GONE);
                           Toast.makeText(getActivity(), "अयशस्वी\nपुन्हा प्रयत्न करा.", Toast.LENGTH_LONG).show();
                       }
                   }
               });

    }




    private void createCartData() {
        cartItem.setProductName(productListing.getProductName());
        cartItem.setProductThumb(productListing.getProductThumb());
        cartItem.setSellertName(productListing.getSellerName());
        cartItem.setSellerId(productListing.getSellerID());
        cartItem.setProductId(itemID);
        cartItem.setPrice(productListing.getPrice());
        cartItem.setDeliveryCharges(productDelivery.getCharges());
        cartItem.setFreeDelivery(productDelivery.getFree_delivery());
        cartItem.setQuantity(plates);
        cartItem.setMinOrder(productDelivery.getMinOrder());

    }

    private void getData(final View view){

        DatabaseReference item_reference = GOApplication.database.getReference();
       item_reference.child("ProductDetails").child(itemID)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            productDetails = dataSnapshot.getValue(ProductDetails.class);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        DatabaseReference provider_reference = GOApplication.database.getReference();
        provider_reference.child("Sellers").child(providerID)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            seller = dataSnapshot.getValue(Seller.class);

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        DatabaseReference delivery_reference = GOApplication.database.getReference();
        delivery_reference.child(ConstantRef.DELIVERIES).child(deliveryID)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            productDelivery = dataSnapshot.getValue(ProductDelivery.class);
                            setTextViews(view);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
    private void setTextViews(View view){
       // ImageView ownerImage = (ImageView)view.findViewById(R.id.food_detail_owner_imageview);
        ImageView itemImage = (ImageView)view.findViewById(R.id.food_detail_imageview);
        TextView ownerName = (TextView)view.findViewById(R.id.foodDetail_seller_name);
        TextView price = (TextView)view.findViewById(R.id.food_detail_price_textview);
        TextView itemName = (TextView)view.findViewById(R.id.food_detail_itemname_textview);
        TextView ratings = (TextView)view.findViewById(R.id.food_detail_ratings_textview);
        final TextView quantity = (TextView)view.findViewById(R.id.food_detail_quantity_textview);
        ImageView plus = view.findViewById(R.id.food_detail_plus_textview);
        ImageView minus = view.findViewById(R.id.food_detail_minus_textview);
        TextView description = (TextView)view.findViewById(R.id.food_detail_description_textview);
        TextView serving = view.findViewById(R.id.foodDetail_serving_details_textView);
        TextView time = view.findViewById(R.id.foodDetail_time);
        TextView minOrder = view.findViewById(R.id.foodDetail_minOrder);
        TextView returnAvailablity = view.findViewById(R.id.foodDetail_returnAvailable);
        TextView deliveryCharges = view.findViewById(R.id.foodDetail_deliveryCharges);
        TextView freeDeliveryCondition = view.findViewById(R.id.foodDetail_freeDelivery);
        TextView amount = view.findViewById(R.id.foodDetail_amount);
//        TextView details = (TextView)view.findViewById(R.id.food_detail_details_textview);

        if(productDetails != null && seller != null && productDelivery != null){

            Glide
                    .with(getActivity())
                    .load(productDetails.getUrl1())
                    .placeholder(R.drawable.placeholder)

                    .into(itemImage);
            ownerName.setText(seller.getDisplayName()+" "+seller.getAddress());
            String[] priceArray = productDetails.getPrices().split("-");
            price.setText("₹."+priceArray[3]+" / "+priceArray[0]+"*");
            itemName.setText(productListing.getProductName());
            if(no_reviews != 0){
                float ratings1 = GOApplication.round((float) no_ratings/no_reviews);
                ratings.setText(String.valueOf(ratings1));

            }
            plates = productDelivery.getMinOrder();
            quantity.setText(String.valueOf(plates));
            serving.setText("*"+productDetails.getTwo());
            String[] timeArray1 = productDelivery.getTime().split("-");
            String[] timeArray2 = timeArray1[0].split(":");
            String ampm = Integer.parseInt(timeArray2[0])<12? "am":"pm";
            String[] timeArray3 = timeArray1[1].split(":");
            int tt=Integer.parseInt(timeArray3[0]);
            int t2 = tt-12>0 ? tt-12 : 12;
            time.setText(Integer.parseInt(timeArray2[0])+" "+ampm+"\nते\n"+t2+" pm");
            minOrder.setText("मिनिमम\nऑर्डर\n"+productDelivery.getMinOrder()+" "+priceArray[0]);
            String returnString = productDelivery.isReturnAvailable()?"Return\navailable":"Return\nnot\navailable";
            returnAvailablity.setText(returnString);
            deliveryCharges.setText("डिलेवरी\n₹ "+String.valueOf(productDelivery.getCharges())+" per\n"+priceArray[0]+"*");
            freeDeliveryCondition.setText("* FREE delivery on order of "+productDelivery.getFree_delivery()+" or more plates.");
            amount.setText("₹."+productDelivery.getMinOrder()*Integer.parseInt(priceArray[3]));
            orderAmount = plates*Integer.parseInt(priceArray[3]);
            minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    plates--;
                    if(plates >= productDelivery.getMinOrder()){
                        quantity.setText(String.valueOf(plates));
                        orderAmount = plates*Integer.parseInt(priceArray[3]);
                        amount.setText("₹."+plates*Integer.parseInt(priceArray[3]));
                    }else{
                        Toast.makeText(getActivity(),"No of plates must be greater than "+productDelivery.getMinOrder(),Toast.LENGTH_SHORT).show();
                        plates = productDelivery.getMinOrder();
                        orderAmount = plates*Integer.parseInt(priceArray[3]);
                    }

                }
            });
            plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    plates++;
                    quantity.setText(String.valueOf(plates));
                    orderAmount = plates*Integer.parseInt(priceArray[3]);
                    amount.setText("₹."+plates*Integer.parseInt(priceArray[3]));
                }
            });
            description.setText(productDetails.getDiscription());

        }


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String uri) {
        if (mListener != null) {
            mListener.onFoodDetailsClick(uri);
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
        productListing = getArguments().getParcelable("Product_Listing");
        category = productListing.getDepartment();
        subCategory = productListing.getDeptCat();
        itemID = getArguments().getString("PRODUCT_ID");
        cartID = getArguments().getString("CART_ID");
        providerID = productListing.getSellerID();
        deliveryID = itemID;
        no_ratings =  productListing.getRatings();
        no_reviews =  productListing.getNoOfRaters();
        int currentPosition = getArguments().getInt("POSITION");

        commonDetailsFragment = CommonDetailsFragment
                .newInstance(category,subCategory,itemID, currentPosition);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    @Override
    public void onPause(){
        super.onPause();
        ProductActivity.appBarLayout.setExpanded(true,true);
    }

    @Override
    public void onCommonDetailsClick(ProductListing productListing, int position, String productID) {

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFoodDetailsClick(String task);
    }
}
