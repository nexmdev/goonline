package com.nexm.ghatanjionline.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nexm.ghatanjionline.R;

/**
 * Created by user on 23-03-2017.
 */

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.MyViewHolder> {

    private Context mContext;
    private String[] mResources;
    private static ViewPagerAdapter.OnItemClickListener listener;

    public ViewPagerAdapter(Context mContext, String[] mResources) {
        this.mContext = mContext;
        this.mResources = mResources;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        final ImageView adImage;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            adImage = itemView.findViewById(R.id.img_pager_item);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.pager_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewPagerAdapter.MyViewHolder holder,int position) {
        Glide
                .with(mContext)
                .load(mResources[holder.getBindingAdapterPosition()])
                .placeholder(R.drawable.placeholder)
                //.crossFade()
                .into(holder.adImage);
        holder.adImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mResources.length;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        ViewPagerAdapter.listener = listener;
    }




/*
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

   */
}