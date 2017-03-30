package com.longb.colordouban.adapter.common;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by longb on 2017/1/21.
 */

public class SimplePageAdapter extends PagerAdapter {
    private List<View> mViews;

    public SimplePageAdapter(List<View> views) {
        this.mViews = views;
    }

    @Override
    public int getCount() {
        return this.mViews == null ? 0 : mViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View v = mViews.get(position);
        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
