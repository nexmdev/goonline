package com.nexm.ghatanjionline.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nexm.ghatanjionline.R;
import com.nexm.ghatanjionline.models.AdData;


/**
 * Created by user on 01-01-2018.
 */

public class HomeRecycleHolder extends BaseHolder {

    private final ImageView imageView;

    private static HomeRecycleHolder.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        HomeRecycleHolder.listener = listener;
    }

    public HomeRecycleHolder(final View itemView){

        super(itemView);
        imageView = (ImageView)itemView.findViewById(R.id.home_r_view_image);
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
    public void bindData(AdData data , Context mcontext){

        Glide
                .with(mcontext)
                .load(data.adImageUrl_title_2)
                .crossFade()
                .into(imageView);
    }
}
