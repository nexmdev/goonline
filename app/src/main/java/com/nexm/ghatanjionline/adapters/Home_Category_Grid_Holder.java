package com.nexm.ghatanjionline.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nexm.ghatanjionline.R;
import com.nexm.ghatanjionline.models.AdData;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by user on 22-01-2018.
 */

public class Home_Category_Grid_Holder extends BaseHolder {

    private final TextView t1, t2, t3, t4, t5, t6;

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (listener != null) {
                int position = getAdapterPosition();
                int itemNo = 1;
                switch (v.getId()) {
                    case R.id.home_r_grid_one:
                        itemNo = 1;
                        break;
                    case R.id.home_r_grid_two:
                        itemNo = 2;
                        break;
                    case R.id.home_r_grid_three:
                        itemNo = 3;
                        break;
                    case R.id.home_r_grid_four:
                        itemNo = 4;
                        break;
                    case R.id.home_r_grid_five:
                        itemNo = 5;
                        break;
                    case R.id.home_r_grid_six:
                        itemNo = 6;
                        break;

                }

                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(itemNo, position);
                }
            }
        }
    };

    private static Home_Category_Grid_Holder.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int itemNo, int position);
    }

    public void setOnItemClickListener(Home_Category_Grid_Holder.OnItemClickListener listener) {
        Home_Category_Grid_Holder.listener = listener;
    }

    public Home_Category_Grid_Holder(final View itemView) {

        super(itemView);

        t1 = (TextView) itemView.findViewById(R.id.home_r_grid_one);
        t2 = (TextView) itemView.findViewById(R.id.home_r_grid_two);
        t3 = (TextView) itemView.findViewById(R.id.home_r_grid_three);
        t4 = (TextView) itemView.findViewById(R.id.home_r_grid_four);
        t5 = (TextView) itemView.findViewById(R.id.home_r_grid_five);
        t6 = (TextView) itemView.findViewById(R.id.home_r_grid_six);

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

        t1.setOnClickListener(clickListener);
        t2.setOnClickListener(clickListener);
        t3.setOnClickListener(clickListener);
        t4.setOnClickListener(clickListener);
        t5.setOnClickListener(clickListener);
        t6.setOnClickListener(clickListener);


    }

    public void bindData(AdData data, Context mcontext) {


        t1.setText(data.end_date_title_1);
        t3.setText(data.webUrl_title_3);
        t5.setText(data.subCategory);
        t2.setText(data.adImageUrl_title_2);
        t4.setText(data.client_name_title_4);


    }




}

