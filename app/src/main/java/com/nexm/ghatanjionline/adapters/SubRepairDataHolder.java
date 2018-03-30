package com.nexm.ghatanjionline.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nexm.ghatanjionline.GOApplication;
import com.nexm.ghatanjionline.R;
import com.nexm.ghatanjionline.models.SubRepairData;

/**
 * Created by user on 08-05-2017.
 */

public class SubRepairDataHolder extends RecyclerView.ViewHolder {

    private final TextView subRepairNameView;
    private final ImageView subRepairImageView;
    private final TextView subRepairWorkView;
    private final TextView subRepairYearsView;
    private final TextView subRepairRatingsView;
    private final RatingBar subRepairRatingBarView;

    private static SubRepairDataHolder.OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        SubRepairDataHolder.listener = listener;
    }

    public SubRepairDataHolder(final View itemView) {
        super(itemView);
        this.subRepairNameView = (TextView) itemView.findViewById(R.id.sub_repair_nameView);
        this.subRepairImageView = (ImageView) itemView.findViewById(R.id.sub_repair_imageView);
        this.subRepairWorkView = (TextView) itemView.findViewById(R.id.sub_repair_workView);
        this.subRepairYearsView= (TextView) itemView.findViewById(R.id.sub_repair_yearsView);

        this.subRepairRatingsView = (TextView) itemView.findViewById(R.id.sub_repair_ratingView);
        this.subRepairRatingBarView = (RatingBar)itemView.findViewById(R.id.sub_repair_ratingbarView);

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

    public void bindData(SubRepairData data , Context context){

        subRepairNameView.setText(data.subRepairName);
        subRepairWorkView.setText(data.subRepairWork);
        subRepairYearsView.setText(data.subRepairYears);
        if(data.subRepairNoOfUsers != 0){
            float ratings = GOApplication.round((float) data.subRepairRatings/data.subRepairNoOfUsers);
            subRepairRatingsView.setText(String.valueOf(ratings));
            subRepairRatingBarView.setRating(ratings);
        }else {

            subRepairRatingBarView.setRating((float) 0.0);
            subRepairRatingsView.setText("Unrated");
        }
        Glide
                .with(context)
                .load(data.subRepairImage_url)
                .placeholder(R.drawable.placeholder)
                .fitCenter()
                .crossFade()
                .into(subRepairImageView);

        subRepairRatingBarView.setIsIndicator(true);
    }
}
