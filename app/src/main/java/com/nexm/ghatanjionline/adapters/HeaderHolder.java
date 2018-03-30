package com.nexm.ghatanjionline.adapters;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;

import com.nexm.ghatanjionline.R;
import com.nexm.ghatanjionline.models.AdData;
import com.nexm.ghatanjionline.util.ClickableViewPager;
import com.nexm.ghatanjionline.util.SetViewPager;

/**
 * Created by user on 24-12-2017.
 */

public class HeaderHolder extends BaseHolder {
    private final ViewPager viewPager;
    private SetViewPager setViewPager;
    private static HeaderHolder.OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        HeaderHolder.listener = listener;
    }

    public HeaderHolder(final View itemView) {

        super(itemView);
        viewPager = (ViewPager) itemView.findViewById(R.id.home_r_top_viewpager);
        setViewPager = new SetViewPager(itemView,viewPager);

    }
    public void bindData(AdData data, Context mcontext) {

        String[] urls ={data.adType_url_1,data.selectedTag_url_2,data.listItemID_url_3};
        final ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(mcontext,urls);
        viewPagerAdapter.setOnItemClickListener(new ViewPagerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if(listener != null){
                    listener.onItemClick(position);
                }
            }
        });
        setViewPager.populateViewPager(viewPagerAdapter);
        viewPager.setCurrentItem(1);
    }
}
