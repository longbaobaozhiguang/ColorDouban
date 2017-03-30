package com.longb.colordouban.views.tool;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by longb on 2017/2/3.
 * recyclerview 分页加载
 */

public abstract class PageScrollListener extends RecyclerView.OnScrollListener {

    private LinearLayoutManager mLinearLayoutManager;


    public PageScrollListener(LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
    }


    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            int itemCount = mLinearLayoutManager.getItemCount();
            int lastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();
            if ((lastVisibleItem + 1) == itemCount) {
                onLoadMore();
            }
        }
    }

    public abstract void onLoadMore();
}
