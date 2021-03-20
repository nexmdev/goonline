package com.nexm.ghatanjionline.adapters;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.nexm.ghatanjionline.R;
import com.nexm.ghatanjionline.models.Address;

/**
 * Created by user on 13-02-2018.
 */

public class AddressHolder extends RecyclerView.ViewHolder {

    private final TextView name,address1,address2,address3,mo;

    private static OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }
    public void setOnItemClickListner(OnItemClickListener mlistener){
        AddressHolder.listener = mlistener;
    }



    public AddressHolder(final View itemView) {
        super(itemView);
        name = (TextView)itemView.findViewById(R.id.selected_address_name);
        address1 = (TextView) itemView.findViewById(R.id.selected_address_address1);
        address2 = (TextView) itemView.findViewById(R.id.selected_address_address2);
        address3 = (TextView) itemView.findViewById(R.id.selected_address_address3);
        mo = (TextView) itemView.findViewById(R.id.selected_address_mobile);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Triggers click upwards to the adapter on click
                if (listener != null) {
                    int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(itemView,position);
                    }
                }
            }
        });
    }
    public void bindData(Address address, Context mcontext){

       name.setText(address.getName());
       address1.setText(address.getAddress1());
       address2.setText(address.getAddress2());
       address3.setText(address.getAddress3());
       mo.setText(address.getMobile1()+"\n"+address.getMobile2());

    }
}
