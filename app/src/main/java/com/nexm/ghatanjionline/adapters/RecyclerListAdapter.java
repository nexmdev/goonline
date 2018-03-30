package com.nexm.ghatanjionline.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nexm.ghatanjionline.R;
import com.nexm.ghatanjionline.models.CategoryItemData;
import com.nexm.ghatanjionline.models.subCategoryData;

import java.util.ArrayList;

/**
 * Created by user on 13-04-2017.
 */

public class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerListAdapter.MyViewHolder> {

    private final ArrayList<subCategoryData> dataSet;
    private Context mcontext;

    private static OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        RecyclerListAdapter.listener = listener;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        final TextView subName;
        final ImageView subImage;
        final TextView subOwner;
        final TextView subModel;
        final TextView subRate;
        final TextView subRatings;
        final RatingBar subRatingBar;


        public MyViewHolder(final View itemView) {
            super(itemView);
            this.subName = (TextView) itemView.findViewById(R.id.subCategory_name_textView);
            this.subImage = (ImageView) itemView.findViewById(R.id.subCategory_item_image);
            this.subOwner = (TextView) itemView.findViewById(R.id.subCategory_owner_textView);
            this.subModel = (TextView) itemView.findViewById(R.id.subCategory_model_textView);
            this.subRate = (TextView) itemView.findViewById(R.id.subCategory_rate_textView);
            this.subRatings = (TextView) itemView.findViewById(R.id.subCategory_ratings_textView);
            this.subRatingBar = (RatingBar)itemView.findViewById(R.id.subCategory_ratingBar);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Triggers click upwards to the adapter on click
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(itemView, position);
                        }
                    }
                }
            });
        }
    }

    public RecyclerListAdapter(ArrayList<subCategoryData> data,Context context) {
        this.dataSet = data;
        this.mcontext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sub_category_item_layout, parent, false);

        //view.setOnClickListener(MainActivity.myOnClickListener);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView name = holder.subName;
        ImageView image = holder.subImage;
        TextView model = holder.subModel;
        TextView owner = holder.subOwner;
        TextView rate = holder.subRate;
        TextView ratings = holder.subRatings;
        RatingBar bar = holder.subRatingBar;


        name.setText(dataSet.get(listPosition).getSubCategoryName());
        model.setText("Model : "+dataSet.get(listPosition).getSubCategoryModel());
        owner.setText(dataSet.get(listPosition).getSubCategoryOwner());
        owner.append("(Owner)");
        rate.setText("Rs : "+Integer.valueOf(dataSet.get(listPosition).getSubCategoryRate()).toString()+" /km");
        ratings.setText("3.9");
        Glide
                .with(mcontext)
                .load(dataSet.get(listPosition).getSubCategoryUrl())
                .placeholder(R.drawable.placeholder)
                .fitCenter()
                .crossFade()
                .into(image);
        bar.setRating((float) 3.5);
        bar.setIsIndicator(true);

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
