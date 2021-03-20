package com.nexm.ghatanjionline.adapters;

import android.content.Context;
import android.view.View;

import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.nexm.ghatanjionline.R;
import com.nexm.ghatanjionline.models.Category;

/**
 * Created by user on 03-02-2018.
 */

public class SubCategoryHorizontalHolder extends RecyclerView.ViewHolder {


    private final TextView title;


    private static SubCategoryHorizontalHolder.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        SubCategoryHorizontalHolder.listener = listener;
    }

    public SubCategoryHorizontalHolder(final View itemView) {
        super(itemView);

        title = (TextView)itemView.findViewById(R.id.sub_category_horizontal_item_textView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(itemView,position);
                        title.setTextColor(itemView.getContext().getResources().getColor(android.R.color.black));
                    }
                }
            }
        });
    }
    public void bindData(Category data, Context mcontext){
        title.setText(data.getNAME());

    }
}
