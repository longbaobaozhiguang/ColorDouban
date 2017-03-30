package com.longb.colordouban.adapter.common;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by longb on 2017/2/11.
 */

public class DataBindingAdapter<DT> extends BaseArrayAdapter<BindingViewHolder<DT>, DT> {

    private int mItemLayout;
    private int mVariableId;

    public DataBindingAdapter(Context context, @LayoutRes int itemLayout,int variableId) {
        super(context);
        this.mItemLayout = itemLayout;
        this.mVariableId = variableId;
    }

    public DataBindingAdapter(Context context, @LayoutRes int itemLayout, List<DT> datas) {
        super(context, datas);
        this.mItemLayout = itemLayout;
    }

    @Override
    public BindingViewHolder<DT> onCreateViewHolder(LayoutInflater layoutInflater, ViewGroup parent, int viewType) {
        return new BindingViewHolder<>(DataBindingUtil.inflate(layoutInflater, mItemLayout, parent, false));
    }

    @Override
    public void onBindViewHolder(DT data, BindingViewHolder<DT> holder, int position) {
        holder.bind(data,mVariableId);
    }

}
