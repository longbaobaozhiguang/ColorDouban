package com.longb.colordouban.databinding;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * 通用BindingAdapter
 */
public class BindAdapters {

    @BindingAdapter({"imageUrl", "placeHolder"})
    public static void setImageUrl(ImageView imageView, String url, Drawable placeHolder) {
        Glide.with(imageView.getContext()).load(url).crossFade().placeholder(placeHolder).into(imageView);
    }

    @BindingAdapter({"imageUrl"})
    public static void setImageUrl(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).crossFade().into(imageView);
    }

    @BindingAdapter("android:onRefresh")
    public static void setSwipeRefreshLayoutOnRefreshListener(SwipeRefreshLayout view, SwipeRefreshLayout.OnRefreshListener listener) {
        view.setOnRefreshListener(listener);
    }



}
