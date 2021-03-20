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
 * Created by user on 03-02-2018.
 */

public class AllCategoriesHolder extends RecyclerView.ViewHolder {

    private final ImageView imageView;
    private final TextView title;
    private final TextView subTitle;

    private static AllCategoriesHolder.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        AllCategoriesHolder.listener = listener;
    }

    public AllCategoriesHolder(final View itemView) {
        super(itemView);
        imageView = (ImageView)itemView.findViewById(R.id.all_categories_item_imageview);
        title = (TextView)itemView.findViewById(R.id.all_categories_item_textview_one);
        subTitle = (TextView)itemView.findViewById(R.id.all_categories_item_textview_two);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(itemView,position);
                    }
                }
            }
        });
    }
    public void bindData(Category data, Context mcontext){
        title.setText(data.getNAME());
        subTitle.setText(data.getDESCRIPTION());
        Glide
                .with(mcontext)
                .load(data.getIMAGE_URL())
                //.crossFade()
                .placeholder(R.drawable.placeholder_one_fifty)
                .into(imageView);
    }
}
