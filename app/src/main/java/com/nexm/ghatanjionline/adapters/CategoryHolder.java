package com.nexm.ghatanjionline.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nexm.ghatanjionline.R;
import com.nexm.ghatanjionline.models.Category;

/**
 * Created by user on 13-02-2018.
 */

public class CategoryHolder extends RecyclerView.ViewHolder {

    private final TextView categoryName;
    private final ImageView categoryImage;
    private static OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }
    public void setOnItemClickListner(OnItemClickListener mlistener){
        CategoryHolder.listener = mlistener;
    }



    public CategoryHolder(final View itemView) {
        super(itemView);
        categoryName = (TextView)itemView.findViewById(R.id.category_item_textView);
        categoryImage = (ImageView)itemView.findViewById(R.id.category_item_imageView);
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
    public void bindData(Category category, Context mcontext){

        categoryName.setText(category.getNAME());

            Glide
                    .with(mcontext)
                    .load(category.getIMAGE_URL())
                    .placeholder(R.drawable.placeholder)
                    //.crossFade()
                    .into(categoryImage);


    }
}
