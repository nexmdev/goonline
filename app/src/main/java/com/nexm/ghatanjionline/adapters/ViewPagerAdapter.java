package com.nexm.ghatanjionline.adapters;

import android.content.Context;
import androidx.viewpager.widget.PagerAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.nexm.ghatanjionline.R;

/**
 * Created by user on 23-03-2017.
 */

public class ViewPagerAdapter extends PagerAdapter {

    private Context mContext;
    private String[] mResources;
    private static ViewPagerAdapter.OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        ViewPagerAdapter.listener = listener;
    }

    public ViewPagerAdapter(Context mContext, String[] mResources) {
        this.mContext = mContext;
        this.mResources = mResources;
    }

    @Override
    public int getCount() {
        return mResources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.pager_item, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.img_pager_item);
        Glide
                .with(mContext)
                .load(mResources[position])
                .placeholder(R.drawable.placeholder)
                //.crossFade()
                .into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onItemClick(position);
                }
            }
        });
        container.addView(itemView);

        return itemView;
    }

   @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
       //listener = null;
    }
    @Override
    public float getPageWidth(int position) {
        return 0.99f;
    }
}