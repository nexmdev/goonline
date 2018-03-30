package com.nexm.ghatanjionline.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nexm.ghatanjionline.R;
import com.nexm.ghatanjionline.models.CategoryItemData;

import java.util.ArrayList;

/**
 * Created by user on 13-04-2017.
 */

public class CustomRecyclerAdapter extends RecyclerView.Adapter<CustomRecyclerAdapter.MyViewHolder> {

    private final ArrayList<CategoryItemData> dataSet;
    private static OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        CustomRecyclerAdapter.listener = listener;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        final TextView categoryName;
        final ImageView categoryImage;


        public MyViewHolder(final View itemView) {
            super(itemView);
            this.categoryName = (TextView) itemView.findViewById(R.id.category_item_textView);
            this.categoryImage = (ImageView) itemView.findViewById(R.id.category_item_imageView);


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

    public CustomRecyclerAdapter(ArrayList<CategoryItemData> data) {
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_item_layout, parent, false);

        //view.setOnClickListener(MainActivity.myOnClickListener);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView name = holder.categoryName;

        ImageView image = holder.categoryImage;


        name.setText(dataSet.get(listPosition).getName());
        if(dataSet.get(0).getName().matches("Cars")){

            image.setScaleType(ImageView.ScaleType.FIT_CENTER);
        }

        image.setImageResource(dataSet.get(listPosition).getImageID());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}

