package com.nexm.ghatanjionline.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.nexm.ghatanjionline.Constants.ConstantRef;
import com.nexm.ghatanjionline.R;
import com.nexm.ghatanjionline.models.Category;

/**
 * Created by user on 15-09-2018.
 */

public class add_ur_item_category_recycler_holder extends RecyclerView.ViewHolder {
    private final TextView categoryName,description;
    private static OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }
    public void setOnItemClickListner(OnItemClickListener mlistener){
        add_ur_item_category_recycler_holder.listener = mlistener;
    }
    public add_ur_item_category_recycler_holder(final View itemView) {
        super(itemView);
        categoryName = (TextView) itemView.findViewById(R.id.add_ur_item_category_recyclerview_item_textview);
        description = (TextView)itemView.findViewById(R.id.add_ur_item_category_recyclerview_item_textview2);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Triggers click upwards to the adapter on click
                if (listener != null) {
                    int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(itemView, position);
                        //categoryName.setBackgroundResource(R.drawable.button);
                    }
                }
            }
        });
    }
    public void bindData(Category category, Context mcontext){

        categoryName.setText(category.categoryID);
        String note = "";
        switch (category.categoryID){
            case ConstantRef.TRANSPORT:
                note = "Select if you have a vehicle - car,cruiser,auto,pick up,407 etc to give on rent ";
                break;
            case ConstantRef.FOOD:
                note = "Select if you want to sell a food item - breakfast,meal,papad,picle,special dish etc";
                break;
            case ConstantRef.OLD_SHOP:
                note ="Select if you want to sell any old thing - 2/ 4 wheelar,mobile,electronics,Tv,cycle,mixer,coolar or anything in household";
                break;
            case ConstantRef.SERVICES :
                note = "Select if you want to list a service - lic/rd/any other agents,coaching classes,tutors,guides,mechanics,painters etc";
                break;
            case ConstantRef.NEWS:
                note = "Select if you want to share a news / article / photo / anything beneficial to people";
                break;

        }
        description.setText(note);
    }

}

