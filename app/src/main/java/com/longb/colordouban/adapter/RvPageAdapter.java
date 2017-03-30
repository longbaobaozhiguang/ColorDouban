package com.longb.colordouban.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.longb.colordouban.BR;
import com.longb.colordouban.R;
import com.longb.colordouban.adapter.common.AdapterWrapper;
import com.longb.colordouban.adapter.common.BindingViewHolder;
import com.longb.colordouban.views.tool.PageScrollListener;

/**
 * Created by longb on 2017/1/23.
 */

public class RvPageAdapter<AT extends RecyclerView.Adapter> extends AdapterWrapper<RecyclerView.ViewHolder, AT> {


    private static final int TYPE_FOOT_LOADING = Integer.MAX_VALUE;

    private PageHelper mPageHelper;
    private PageScrollListener mPageScrollListener;

    public RvPageAdapter(AT adapter, PageScrollListener pageScrollListener, PageHelper pageHelper) {
        super(adapter);
        mPageScrollListener = pageScrollListener;
        mPageHelper = pageHelper;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        recyclerView.addOnScrollListener(mPageScrollListener);
    }

    @Override
    public int getWrapperPositionOffset() {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < getWrapperItemCount()) {
            return getWrapperAdapter().getItemViewType(position);
        }
        return TYPE_FOOT_LOADING;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOT_LOADING) {
            return new BindingViewHolder<>(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_loadmore, parent, false));
        }
        return getWrapperAdapter().onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position < getWrapperItemCount()) {
            getWrapperAdapter().onBindViewHolder(holder, position);
        } else {
            ((BindingViewHolder) holder).bind(mPageHelper, BR.pageHelper);
        }
    }

    @Override
    public int getItemCount() {
        return getWrapperItemCount() + 1;
    }

}
