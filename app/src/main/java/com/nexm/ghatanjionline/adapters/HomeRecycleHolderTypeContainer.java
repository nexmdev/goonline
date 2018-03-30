package com.nexm.ghatanjionline.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nexm.ghatanjionline.R;
import com.nexm.ghatanjionline.models.AdData;

/**
 * Created by user on 14-01-2018.
 */

public class HomeRecycleHolderTypeContainer extends BaseHolder {

    private final TextView title,seeAll,t1,t2,t3,t4,t5,t6,t7,t8;
    private final ImageView i1,i2,i3,i4;
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (listener != null) {
                int position = getAdapterPosition();
                int itemNo = 1;
                switch (v.getId()){
                    case R.id.home_r_view_one_imageview:
                    case R.id.home_r_view_itemone_textview:
                    case R.id.home_r_view_itemtwo_textview:
                        itemNo = 1;

                        break;
                    case R.id.home_r_view_two_imageview:
                    case R.id.home_r_view_itemthree_textview:
                    case R.id.home_r_view_itemfour_textview:
                        itemNo = 2;
                        break;
                    case R.id.home_r_view_three_imageview:
                    case R.id.home_r_view_itemfive_textview:
                    case R.id.home_r_view_itemsix_textview:
                        itemNo = 3;
                        break;
                    case R.id.home_r_view_four_imageview:
                    case R.id.home_r_view_itemseven_textview:
                    case R.id.home_r_view_itemeight_textview:
                        itemNo = 4;
                        break;
                    case R.id.home_r_view_title_textview:
                    case R.id.home_r_view_seeall_textview:
                        itemNo = 0;

                }

                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(itemNo,position);
                }
            }
        }
    };

    private static HomeRecycleHolderTypeContainer.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int itemNo, int position);
    }
    public void setOnItemClickListener(HomeRecycleHolderTypeContainer.OnItemClickListener listener) {
        HomeRecycleHolderTypeContainer.listener = listener;
    }

    public HomeRecycleHolderTypeContainer(final View itemView){

        super(itemView);
        i1 = (ImageView)itemView.findViewById(R.id.home_r_view_one_imageview);
        i2 = (ImageView)itemView.findViewById(R.id.home_r_view_two_imageview);
        i3 = (ImageView)itemView.findViewById(R.id.home_r_view_three_imageview);
        i4 = (ImageView)itemView.findViewById(R.id.home_r_view_four_imageview);

        title = (TextView)itemView.findViewById(R.id.home_r_view_title_textview) ;
        seeAll = (TextView)itemView.findViewById(R.id.home_r_view_seeall_textview) ;
        t1 = (TextView)itemView.findViewById(R.id.home_r_view_itemone_textview) ;
        t2 = (TextView)itemView.findViewById(R.id.home_r_view_itemtwo_textview) ;
        t3 = (TextView)itemView.findViewById(R.id.home_r_view_itemthree_textview) ;
        t4 = (TextView)itemView.findViewById(R.id.home_r_view_itemfour_textview) ;
        t5 = (TextView)itemView.findViewById(R.id.home_r_view_itemfive_textview) ;
        t6 = (TextView)itemView.findViewById(R.id.home_r_view_itemsix_textview) ;
        t7 = (TextView)itemView.findViewById(R.id.home_r_view_itemseven_textview) ;
        t8 = (TextView)itemView.findViewById(R.id.home_r_view_itemeight_textview) ;

        /*itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Triggers click upwards to the adapter on click
                if (listener != null) {
                    int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(1,position);
                    }
                }
            }
        });*/
        i1.setOnClickListener(clickListener);
        i2.setOnClickListener(clickListener);
        i3.setOnClickListener(clickListener);
        i4.setOnClickListener(clickListener);
        t1.setOnClickListener(clickListener);
        t2.setOnClickListener(clickListener);
        t3.setOnClickListener(clickListener);
        t4.setOnClickListener(clickListener);
        t5.setOnClickListener(clickListener);
        t6.setOnClickListener(clickListener);
        t7.setOnClickListener(clickListener);
        t8.setOnClickListener(clickListener);
        title.setOnClickListener(clickListener);
        seeAll.setOnClickListener(clickListener);

    }
    public void bindData(AdData data , Context mcontext){

        Glide
                .with(mcontext)
                .load(data.adType_url_1)
                .placeholder(R.drawable.placeholder)
                .crossFade()
                .into(i1);
        Glide
                .with(mcontext)
                .load(data.selectedTag_url_2)
                .placeholder(R.drawable.placeholder)
                .crossFade()
                .into(i2);
        Glide
                .with(mcontext)
                .load(data.listItemID_url_3)
                .placeholder(R.drawable.placeholder)
                .crossFade()
                .into(i3);
        Glide
                .with(mcontext)
                .load(data.start_date_url_4)
                .placeholder(R.drawable.placeholder)
                .crossFade()
                .into(i4);
        title.setText(data.category);
        t1.setText(data.end_date_title_1);
        t3.setText(data.adImageUrl_title_2);
        t5.setText(data.webUrl_title_3);
        t7.setText(data.client_name_title_4);
        t2.setText(data.client_mono_sub_title_1);
        t4.setText(data.sub_title_2);
        t6.setText(data.sub_title_3);
        t8.setText(data.sub_title_4);
    }
}
