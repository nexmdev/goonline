package com.nexm.ghatanjionline.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.nexm.ghatanjionline.GOApplication;
import com.nexm.ghatanjionline.Main2Activity;
import com.nexm.ghatanjionline.ProductActivity;
import com.nexm.ghatanjionline.R;
import com.nexm.ghatanjionline.adapters.AddressHolder;
import com.nexm.ghatanjionline.adapters.CartHolder;
import com.nexm.ghatanjionline.models.Address;
import com.nexm.ghatanjionline.models.CartItem;
import com.nexm.ghatanjionline.models.Order;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private OnFragmentInteractionListener mListener;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView recyclerView,deliveryAddressesRecyclerView;
    private FirebaseRecyclerAdapter mFirebaseAdapter,addressesAdapter;
    private int checkoutStep = 1;

    // TODO: Rename and change types of parameters
    private String userID,deliveryAddressID;
    private String mParam2;
    private ProgressBar progressBar;
    private TextView topTextView,cart_total,checkout,add_newAddress,saveAddressButton,selected_address_name,
            selected_address_address1,selected_address_address2,selected_address_address3,selected_address_mobile,backButton,
            confirm_noOfItems,confirm_amount,confirm_address,confirm_codHint;
    private ConstraintLayout checkoutLayout,selectedAddressLayout,confirmLayout;
    private LinearLayout newAddressLayout;
    private Address selectedAddress;

    public CartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
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
            userID = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        showDeliveryAddresses();
        final Query query = GOApplication.database.getReference()
                .child("Cart").child(userID).child("cartItems");

        FirebaseRecyclerOptions<CartItem> options =  new FirebaseRecyclerOptions.Builder<CartItem>()
                .setQuery(query, CartItem.class)
                .build();
        mFirebaseAdapter = new FirebaseRecyclerAdapter<CartItem, CartHolder>(
                options
        ) {
            @NonNull
            @Override
            public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.cart_item_layout, parent, false);
                return new CartHolder(view);
            }

            @Override
            protected void onBindViewHolder(CartHolder viewHolder,final int position, final CartItem model) {

                viewHolder.bindData(model,getActivity());
                viewHolder.setOnItemClickListner(new CartHolder.OnItemClickListener() {
                    @Override
                    public void onItemClick(View itemView, int currentposition, String task) {
                        CartItem data = (CartItem) mFirebaseAdapter.getItem(currentposition);
                        if(task.matches("DELETE")){

                            int price = getPrice(data);
                            updateCart(null, currentposition,-1,-price);


                        }else if(task.matches("SHOW_PRODUCT")){

                            Intent intent = new Intent(getActivity(), ProductActivity.class);
                            intent.putExtra("Tag","Product");
                            intent.putExtra("ItemID",data.getProductId());
                            intent.putExtra("Caller","Cart");
                            intent.putExtra("CartID",mFirebaseAdapter.getRef(currentposition).getKey());
                            getActivity().startActivity(intent);

                        }else if(task.matches("ADD")){
                            progressBar.setVisibility(View.VISIBLE);
                            int oldAmount = getPrice(data);
                            mFirebaseAdapter.getRef(currentposition).child("quantity").setValue(data.getQuantity()+1)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                         data.setQuantity(data.getQuantity()+1);
                                         int newAmount = getPrice(data);
                                            updateCart(data, currentposition,0,-oldAmount+newAmount);

                                        }
                                    });

                        }else if(task.matches("MINUS")){
                            progressBar.setVisibility(View.VISIBLE);

                            if(data.getQuantity()== data.getMinOrder()){
                                //Toast.makeText(getActivity(),"Minimum order : "+data.getMinOrder(),Toast.LENGTH_SHORT).show();
                                showToast("Minimum order : "+data.getMinOrder());
                                progressBar.setVisibility(View.GONE);
                                return;
                            }
                            int oldAmount = getPrice(data);
                            mFirebaseAdapter.getRef(currentposition).child("quantity").setValue(data.getQuantity()-1)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            data.setQuantity(data.getQuantity()-1);
                                            int newAmount = getPrice(data);
                                            updateCart(data, currentposition,0,-oldAmount+newAmount);
                                        }
                                    });
                        }


                    }
                });
            }
            @Override
            public void onDataChanged() {
                // Called each time there is a new data snapshot. You may want to use this method
                // to hide a loading spinner or check for the "no documents" state and update your UI.
                // ...
               topTextView.setText("My Cart ( "+mFirebaseAdapter.getItemCount()+" )");
            }

            @Override
            public void onError(DatabaseError e) {
                // Called when there is an error getting data. You may want to update
                // your UI to display an error message to the user.
                // ...
            }
        };
    }

    private int getPrice(CartItem data) {
        String[] priceArray = data.getPrice().split("-");
        int price = Integer.parseInt(priceArray[3]) * data.getQuantity();
        int delivery = data.getQuantity() >= data.getFreeDelivery() ? 0 : data.getQuantity() * data.getDeliveryCharges();
        price += delivery;
        return price;
    }


    private void updateCart(CartItem data, int currentposition,int noOfItems,int amount) {
        Map dataMap = new HashMap();
       if(noOfItems!=0) dataMap.put("CUSTOMERS/"+userID+"/cartitemsNo", ServerValue.increment(noOfItems));

        dataMap.put("Cart/"+userID+"/cartTotal", ServerValue.increment(amount));
       if(data==null) dataMap.put("Cart/"+userID+"/cartItems/"+mFirebaseAdapter.getRef(currentposition).getKey(),data);
        GOApplication.databaseReference.updateChildren(dataMap).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        progressBar = view.findViewById(R.id.cart_progressbar);
        topTextView = view.findViewById(R.id.cart_top_textView);
        cart_total = view.findViewById(R.id.cart_total);
        checkout = view.findViewById(R.id.cart_checkout);
        checkoutLayout = view.findViewById(R.id.checkout_layout);
        add_newAddress = view.findViewById(R.id.checkout_newaddress);
        newAddressLayout = view.findViewById(R.id.newaddress_layout);
        saveAddressButton = view.findViewById(R.id.save_address_button);
        selectedAddressLayout = view.findViewById(R.id.selectedAddressLayout);
        selected_address_name = view.findViewById(R.id.selected_address_name);
        selected_address_address1 = view.findViewById(R.id.selected_address_address1);
        selected_address_address2 = view.findViewById(R.id.selected_address_address2);
        selected_address_address3 = view.findViewById(R.id.selected_address_address3);
        selected_address_mobile = view.findViewById(R.id.selected_address_mobile);
        backButton = view.findViewById(R.id.cart_backButton);
        confirm_noOfItems = view.findViewById(R.id.confirmOrder_no_of_items);
        confirm_amount = view.findViewById(R.id.confirmOrder_total_amount);
        confirm_address = view.findViewById(R.id.confirmOrder_address);
        confirm_codHint = view.findViewById(R.id.confirmOrder_cod_hint);
        confirmLayout = view.findViewById(R.id.confirmLayout);

        GOApplication.databaseReference.child("Cart").child(userID).child("cartTotal")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            cart_total.setText("₹."+String.valueOf(snapshot.getValue(Integer.class)));
                            checkout.setVisibility(View.VISIBLE);
                        }else{
                            cart_total.setText("₹.0");
                            checkout.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        deliveryAddressesRecyclerView = view.findViewById(R.id.address_recyclerView);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false);
        deliveryAddressesRecyclerView.setLayoutManager(linearLayoutManager2);
        deliveryAddressesRecyclerView.setAdapter(addressesAdapter);
        recyclerView = view.findViewById(R.id.cart_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mFirebaseAdapter);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkout.getText().toString().matches("Checkout")){
                    recyclerView.setVisibility(View.GONE);
                    checkoutLayout.setVisibility(View.VISIBLE);
                    deliveryAddressesRecyclerView.setVisibility(View.VISIBLE);
                    checkout.setText("Next");
                    backButton.setVisibility(View.VISIBLE);
                }else if (checkout.getText().toString().matches("Next")){
                    if(selectedAddress == null){
                        //.makeText(getActivity(),"Select delivery address",Toast.LENGTH_SHORT).show();
                        showToast("Select delivery address");
                        return;
                    }
                    checkoutStep++;
                    checkoutLayout.setVisibility(View.GONE);
                    deliveryAddressesRecyclerView.setVisibility(View.GONE);
                    checkout.setText("Confirm");
                    checkout.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,null,null);
                    backButton.setVisibility(View.VISIBLE);
                    confirmLayout.setVisibility(View.VISIBLE);
                    confirm_noOfItems.setText("Total "+ mFirebaseAdapter.getItemCount()+" items.");
                    confirm_amount.setText("Total amount : "+cart_total.getText().toString());
                    confirm_address.setText(selectedAddress.getName()+"\n"+selectedAddress.getAddress1()+"\n"+selectedAddress.getAddress2()+"\n...");
                    confirm_codHint.setText("* Pay "+cart_total.getText().toString()+" to delivery agent.");

                }else if(checkout.getText().toString().matches("Confirm")){
                    progressBar.setVisibility(View.VISIBLE);
                    saveOrders();
                }

            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkoutStep ==1){
                    recyclerView.setVisibility(View.VISIBLE);
                    checkoutLayout.setVisibility(View.GONE);
                    deliveryAddressesRecyclerView.setVisibility(View.GONE);
                    checkout.setText("Checkout");
                    backButton.setVisibility(View.GONE);
                }else if (checkoutStep == 2){
                    checkoutStep--;
                    checkout.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,getResources().getDrawable(R.drawable.ic_arrow_forward_black_24dp),null);
                    confirmLayout.setVisibility(View.GONE);
                    checkoutLayout.setVisibility(View.VISIBLE);
                    deliveryAddressesRecyclerView.setVisibility(View.VISIBLE);
                    selectedAddressLayout.setVisibility(View.VISIBLE);
                    checkout.setText("Next");
                    backButton.setVisibility(View.VISIBLE);
                }

            }
        });
        add_newAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newAddressLayout.setVisibility(View.VISIBLE);
                deliveryAddressesRecyclerView.setVisibility(View.GONE);
                selectedAddressLayout.setVisibility(View.GONE);
            }
        });
        saveAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                saveAddress(view);
            }
        });

        return view;
    }
    private void showToast(String message){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast_layout,null);
        TextView textView = layout.findViewById(R.id.toast_text);
        textView.setText(message);

        Toast toast = new Toast(getActivity());
        toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    private void saveOrders() {
        for(int i=0 ; i<mFirebaseAdapter.getItemCount();i++) {
            CartItem cartItem = (CartItem) mFirebaseAdapter.getItem(i);
            Order order = new Order();
            order.setCustomerID(userID);
            order.setDate(System.currentTimeMillis());
            order.setPrice(cartItem.getPrice());
            order.setDeliveryCharges(cartItem.getQuantity() >= cartItem.getFreeDelivery()
                    ? 0 : cartItem.getQuantity() * cartItem.getDeliveryCharges());
            order.setProductId(cartItem.getProductId());
            order.setSellerId(cartItem.getSellerId());
            order.setProductName(cartItem.getProductName());
            order.setProductThumb(cartItem.getProductThumb());
            order.setDeliveryAgent("NA");
            order.setQuantity(cartItem.getQuantity());
            order.setDeliveryAddressID(deliveryAddressID);
            order.setSellertName(cartItem.getSellertName());
            order.setCustomerNameAddress(GOApplication.customerName+"\n"+selectedAddress.getAddress1()+"\n"+
                    selectedAddress.getAddress2()+"\n"+selectedAddress.getAddress3());

            Map updates = new HashMap();
            String orderPushKey = GOApplication.databaseReference.child("ORDERS").push().getKey();
            updates.put("ORDERS/" + orderPushKey, order);
            updates.put("Cart/" + userID, null);
            updates.put("CUSTOMERS/" + userID + "/cartitemsNo", 0);

            GOApplication.databaseReference.updateChildren(updates)
                    .addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()) {
                                progressBar.setVisibility(View.GONE);
                                showDialog();


                            } else {
                               showToast("Order can NOT be placed ! Try again");
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
        }
    }

    private void showDialog() {
        final Dialog dialog;

        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.custom_dialog);

        final TextView toOrders = (dialog).findViewById(R.id.order_placement_dialog_see_order);
        final TextView continueShopping = (dialog).findViewById(R.id.order_placement_dialog_continue);

        toOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (mListener != null) {
                    mListener.goToOrder();
                }
            }
        });
        continueShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Main2Activity.class);
                intent.putExtra("CUSTOMER_UID",userID);
                dialog.dismiss();
                getActivity().startActivity(intent);
                getActivity().finish();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        Objects.requireNonNull(dialog.getWindow()).setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    private void saveAddress(View view) {
        final EditText firstName = view.findViewById(R.id.sign_up_edittext_user_name);
        final EditText middleNameE = view.findViewById(R.id.sign_up_user_middle_name);
        final EditText surNameE = view.findViewById(R.id.sign_up_user_surname);
        final EditText address1E = view.findViewById(R.id.sign_up_user_address1);
        final EditText address2E = view.findViewById(R.id.sign_up_user_address2);
        final EditText address3E = view.findViewById(R.id.sign_up_user_address3);
        final EditText mobile1E = view.findViewById(R.id.sign_up_user_phone);
        final EditText mobile2E = view.findViewById(R.id.sign_up_user_email);

        String name = firstName.getText().toString();
        String middleName = middleNameE.getText().toString();
        String surname = surNameE.getText().toString();
        String address1 = address1E.getText().toString();
        String address2 = address2E.getText().toString();
        String address3 = address3E.getText().toString();
        String mobile1 = mobile1E.getText().toString();
        String mobile2 = mobile2E.getText().toString();

        if(name.isEmpty()){
            firstName.setError("Enter first name");return;
        }
        if(middleName.isEmpty()){
            middleNameE.setError("Enter middle name");return;
        }
        if(surname.isEmpty()){
            surNameE.setError("Enter surname ");return;
        }
        if(address1.isEmpty()){
            address1E.setError("Enter ward/nagar");return;
        }
        if(address2.isEmpty()){
            address2E.setError("Enter landmark");return;
        }
        if(address3.isEmpty()){
            address2E.setError("Enter city/village");return;
        }
        if(mobile1.isEmpty()){
            mobile1E.setError("Enter contact number");return;
        }
        if(mobile2.isEmpty()){
            mobile2="NA";
        }
        Address address = new Address();
        address.setName(name+" "+middleName+" "+surname);
        address.setAddress1(address1);
        address.setAddress2(address2);
        address.setAddress3(address3);
        address.setMobile1(mobile1);
        address.setMobile2(mobile2);

        GOApplication.databaseReference
                .child("DELIVERY_ADDRESSES")
                .child(userID)
                .push()
                .setValue(address)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressBar.setVisibility(View.GONE);
                        if(task.isSuccessful()){
                            newAddressLayout.setVisibility(View.GONE);
                            deliveryAddressesRecyclerView.setVisibility(View.VISIBLE);

                        }else{
                            Toast.makeText(getActivity(),"Something went wrong, try again !",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void showDeliveryAddresses() {



        final Query query = GOApplication.database.getReference()
                .child("DELIVERY_ADDRESSES").child(userID);

        FirebaseRecyclerOptions<Address> options =  new FirebaseRecyclerOptions.Builder<Address>()
                .setQuery(query, Address.class)
                .build();
        addressesAdapter = new FirebaseRecyclerAdapter<Address, AddressHolder>(
                options
        ) {
            @NonNull
            @Override
            public AddressHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.delivery_address_layout, parent, false);
                return new AddressHolder(view);
            }

            @Override
            protected void onBindViewHolder(AddressHolder viewHolder,final int position, final Address model) {

                viewHolder.bindData(model,getActivity());
                viewHolder.setOnItemClickListner(new AddressHolder.OnItemClickListener() {
                    @Override
                    public void onItemClick(View itemView, int position) {
                        deliveryAddressID = addressesAdapter.getRef(position).getKey();
                        selectedAddress = (Address) addressesAdapter.getItem(position);
                        selectedAddressLayout.setVisibility(View.VISIBLE);
                        selected_address_name.setText(selectedAddress.getName());
                        selected_address_address1.setText(selectedAddress.getAddress1());
                        selected_address_address2.setText(selectedAddress.getAddress2());
                        selected_address_address3.setText(selectedAddress.getAddress3());
                        selected_address_mobile.setText(selectedAddress.getMobile1()+"\n"+selectedAddress.getMobile2());
                    }
                });
            }
            @Override
            public void onDataChanged() {
                // Called each time there is a new data snapshot. You may want to use this method
                // to hide a loading spinner or check for the "no documents" state and update your UI.
                // ...
                progressBar.setVisibility(View.GONE);


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
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SignUpFragment.OnFragmentInteractionListener) {
            mListener = (CartFragment.OnFragmentInteractionListener) context;
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
    @Override
    public void onStart() {
        super.onStart();
        mFirebaseAdapter.startListening();
        addressesAdapter.startListening();

    }
    @Override
    public void onStop() {
        super.onStop();
        mFirebaseAdapter.stopListening();
        addressesAdapter.stopListening();
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void goToOrder();
    }
}