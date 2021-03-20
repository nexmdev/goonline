package com.nexm.ghatanjionline.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nexm.ghatanjionline.R;
import com.nexm.ghatanjionline.models.CartItem;
import com.nexm.ghatanjionline.models.Order;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by user on 13-02-2018.
 */

public class OrderHolder extends RecyclerView.ViewHolder {

    private final TextView productName,sellerName,quantity, deliveryEst, orderdate,amount,status,cancel;
    private final ImageView productImage;
    private static OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position, String task);
    }
    public void setOnItemClickListner(OnItemClickListener mlistener){
        OrderHolder.listener = mlistener;
    }



    public OrderHolder(final View itemView) {
        super(itemView);
        productImage = itemView.findViewById(R.id.orderItem_productThumb);
        status = itemView.findViewById(R.id.orderItem_status);
        quantity = itemView.findViewById(R.id.orderItem_qty_delv);
        deliveryEst = itemView.findViewById(R.id.orderItem_delivery_est);
        orderdate = itemView.findViewById(R.id.orderItem_date);
        amount = itemView.findViewById(R.id.orderItem_amt);
        productName = itemView.findViewById(R.id.orderItem_productName);
        sellerName = itemView.findViewById(R.id.orderItem_sellerName);
        cancel = itemView.findViewById(R.id.orderItem_cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
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

    }
    public void bindData(Order order, Context mcontext){

        productName.setText(order.getProductName());
        sellerName.setText(order.getSellertName());
        String[] priceArray = order.getPrice().split("-") ;
        quantity.setText("Qty: "+ order.getQuantity()+" " +priceArray[0]);
        int oamount = Integer.parseInt(priceArray[3]) * order.getQuantity()+order.getDeliveryCharges();
        amount.setText("Amount: ₹"+oamount);
        Date date = new Date(order.getDate());
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int orderHour=calendar.get(Calendar.HOUR_OF_DAY);
        int orderMin = calendar.get(Calendar.MINUTE);

        String dateString = format.format(date);
        orderdate.setText(dateString);
        status.setText(order.getStatus());
        orderHour++;
        String ampm = orderHour>12 ? "pm" : "am";

        orderMin+=30;
        if(orderMin>=60){
            orderMin-=60;
            orderHour++;
        }
        orderMin = Math.max(orderMin, 10);
        orderHour = orderHour>12 ? orderHour-12 : orderHour;
        deliveryEst.setText("डिलेवरी - "+orderHour +":"+orderMin+" " +ampm +" पर्यंत.");

            Glide
                    .with(mcontext)
                    .load(order.getProductThumb())
                    .placeholder(R.drawable.placeholder)
                    //.crossFade()
                    .into(productImage);



    }
}
