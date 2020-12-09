package com.nexm.ghatanjionline.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nexm.ghatanjionline.GOApplication;
import com.nexm.ghatanjionline.R;
import com.nexm.ghatanjionline.models.ListItem;

/**
 * Created by user on 18-04-2017.
 */

public class DataHolder extends BaseHolder {

    private final TextView subName;
    private final ImageView subImage;
    private final TextView subOwner;
    private final TextView subRate;
    private final TextView subRatings;
    private final RatingBar subRatingBar;
    private final TextView subNoUsers;
    private final LinearLayout parent;
    private static DataHolder.OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(View itemView,int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        DataHolder.listener = listener;
    }

    public DataHolder(final View itemView) {
        super(itemView);
        this.subName = (TextView) itemView.findViewById(R.id.subCategory_name_textView);
        this.subImage = (ImageView) itemView.findViewById(R.id.subCategory_item_image);
        this.subOwner = (TextView) itemView.findViewById(R.id.subCategory_owner_textView);
        this.subRate = (TextView) itemView.findViewById(R.id.subCategory_rate_textView);
        this.subRatings = (TextView) itemView.findViewById(R.id.subCategory_ratings_textView);
        this.subRatingBar = (RatingBar)itemView.findViewById(R.id.subCategory_ratingBar);
        this.subNoUsers = (TextView)itemView.findViewById(R.id.subCategory_no_users) ;
        this.parent = (LinearLayout)itemView.findViewById(R.id.sub_category_other_options_parent_layout) ;

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
    public void bindData(ListItem data , Context mcontext,boolean visible){

        if(visible){
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                try {
                    Drawable progressDrawable = subRatingBar.getProgressDrawable();
                    if (progressDrawable != null) {
                        DrawableCompat.setTint(progressDrawable, ContextCompat.getColor(mcontext, R.color.orange));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if(data.category.matches("सर्विसेस")){
                subName.setText(data.providerNAME);
                subOwner.setText(data.itemNAME);
            }else{
                subName.setText(data.itemNAME);
                subOwner.setText(data.providerNAME);
                subOwner.append("(मालक)");
            }
            // subRate.setText("Rs : "+Integer.valueOf(data.getSubCategoryRate()).toString()+" /km");
            subRate.setText(data.itemPRICE);
            if(data.itemNO_OF_REVIEWS != 0){
                float ratings = GOApplication.round((float) data.itemRATINGS/data.itemNO_OF_REVIEWS);
                subRatings.setText(String.valueOf(ratings));
                subRatingBar.setRating(ratings);
            }else {

                subRatingBar.setRating((float) 3.9);
            }
            Glide
                    .with(mcontext)
                    .load(data.itemPHOTOURL)
                    .placeholder(R.drawable.placeholder)
                    .fitCenter()
                    //.crossFade()
                    .into(subImage);

            subRatingBar.setIsIndicator(true);

        }else{
            subName.setVisibility(View.GONE);
            subRatingBar.setVisibility(View.GONE);
            subRate.setVisibility(View.GONE);
            subRatings.setVisibility(View.GONE);
            subImage.setVisibility(View.GONE);
            subOwner.setVisibility(View.GONE);
            subNoUsers.setVisibility(View.GONE);
            parent.setVisibility(View.GONE);

        }

    }
}
