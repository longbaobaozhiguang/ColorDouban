package com.longb.colordouban.adapter.common;

import android.support.v7.widget.RecyclerView;

/**
 * Created by longb on 2017/2/2.
 */

public abstract class AdapterWrapper<VH extends RecyclerView.ViewHolder,AT extends RecyclerView.Adapter> extends RecyclerView.Adapter<VH> {

    private AT mWrapperAdapter;

    public AdapterWrapper(AT adapter) {
        this.mWrapperAdapter = adapter;
        this.mWrapperAdapter.registerAdapterDataObserver(mDataObserver);
    }

    public AT getWrapperAdapter() {
        return mWrapperAdapter;
    }

    public int getWrapperItemCount () {
        return mWrapperAdapter.getItemCount();
    }

    public abstract int getWrapperPositionOffset();

    private RecyclerView.AdapterDataObserver mDataObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            super.onItemRangeChanged(positionStart, itemCount);
            notifyItemRangeChanged(positionStart + getWrapperPositionOffset(), itemCount);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            notifyItemRangeInserted(positionStart + getWrapperPositionOffset(), itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            notifyItemRangeRemoved(positionStart + getWrapperPositionOffset(), itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);
            int offset = getWrapperPositionOffset();
            notifyItemRangeChanged(fromPosition + offset, toPosition + offset + itemCount);
        }
    };
}
