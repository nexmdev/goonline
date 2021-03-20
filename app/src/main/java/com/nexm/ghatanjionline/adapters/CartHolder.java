package com.nexm.ghatanjionline.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nexm.ghatanjionline.R;
import com.nexm.ghatanjionline.models.CartItem;

/**
 * Created by user on 13-02-2018.
 */

public class CartHolder extends RecyclerView.ViewHolder {

    private final TextView productName,sellerName,price,quantity,delivery, total,amount;
    private final ImageView productImage,add,minus,delete;
    private static OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position, String task);
    }
    public void setOnItemClickListner(OnItemClickListener mlistener){
        CartHolder.listener = mlistener;
    }



    public CartHolder(final View itemView) {
        super(itemView);
        productImage = itemView.findViewById(R.id.cart_image);
        add = itemView.findViewById(R.id.cart_quantity_add);
        minus = itemView.findViewById(R.id.cart_quantity_substract);
        delete = itemView.findViewById(R.id.cart_item_delete);
        price = itemView.findViewById(R.id.cart_price);
        quantity = itemView.findViewById(R.id.cart_quantity);
        delivery = itemView.findViewById(R.id.cart_delivery);
        total = itemView.findViewById(R.id.cart_item_total);
        amount = itemView.findViewById(R.id.cart_amount);
        productName = itemView.findViewById(R.id.cart_productName);
        sellerName = itemView.findViewById(R.id.cart_sellerName);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(itemView,position,"DELETE" );
                    }
                }
            }
        });
        productImage .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Triggers click upwards to the adapter on click
                if (listener != null) {
                    int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(itemView,position,"SHOW_PRODUCT" );
                    }
                }
            }
        });
    }
    public void bindData(CartItem cart, Context mcontext){

        productName.setText(cart.getProductName());
        sellerName.setText(cart.getSellertName());
        String[] priceArray = cart.getPrice().split("-") ;
        price.setText("₹."+priceArray[3]+" / "+priceArray[0]);
        quantity.setText(String.valueOf(cart.getQuantity()));
        int deliveryCharges = cart.getDeliveryCharges()*cart.getQuantity();
        String deliveryString = cart.getQuantity()>= cart.getFreeDelivery() ? "Delivery    FREE" :"Delivery    ₹."+String.valueOf(deliveryCharges);
        delivery.setText(deliveryString);
        int offerPrice = cart.getQuantity()>= cart.getFreeDelivery() ? -deliveryCharges : 0;
        total.setText(String.valueOf(offerPrice));
        int netAmount = (Integer.parseInt(priceArray[3]) * cart.getQuantity() )+ deliveryCharges;
        netAmount += offerPrice;
        total.setText(String.valueOf(netAmount));
        amount.setText(String.valueOf(Integer.parseInt(priceArray[3]) * cart.getQuantity() ));

            Glide
                    .with(mcontext)
                    .load(cart.getProductThumb())
                    .placeholder(R.drawable.placeholder)
                    //.crossFade()
                    .into(productImage);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(itemView,position,"ADD" );
                    }
                }
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(itemView,position,"MINUS" );
                    }
                }
            }
        });

    }
}
