package com.longb.colordouban.adapter.common;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by longb on 2017/1/21.
 */

public abstract class BaseArrayAdapter<VH extends RecyclerView.ViewHolder, DT> extends RecyclerView.Adapter<VH> implements View.OnClickListener {

    protected List<DT> mDatas;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ItemClickListener<DT> mItemListener;

    public BaseArrayAdapter(Context context) {
        this(context, null);
    }

    public BaseArrayAdapter(Context context, List<DT> datas) {
        this.mContext = context;
        this.mDatas = datas;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public Context getContext() {
        return mContext;
    }

    public void setDatas(List<DT> datas) {
        mDatas = datas;
        notifyDataSetChanged();

    }

    public void addDatas(List<DT> datas) {
        if (mDatas == null) {
            mDatas = new ArrayList<>();
        }
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public List<DT> getDatas() {
        return mDatas;
    }

    public void setItemClickListener(ItemClickListener<DT> l) {
        mItemListener = l;
    }

    protected LayoutInflater getLayoutInflater() {
        return mLayoutInflater;
    }

    @Override
    public int getItemViewType(int position) {
        return mDatas == null ? 0 : getTypedItemType(mDatas.get(position));
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        VH vh = onCreateViewHolder(mLayoutInflater, parent, viewType);
        vh.itemView.setOnClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        onBindViewHolder(mDatas.get(position), holder, position);
        holder.itemView.setTag(new TagVal<DT>(mDatas.get(position), position));
    }

    @Override
    public void onClick(View v) {
        if (mItemListener != null) {
            TagVal<DT> tagVal = (TagVal<DT>) v.getTag();
            if (getItemClickable(v, tagVal.index, tagVal.value)) {
                mItemListener.onClick(v, tagVal.value);
            }
        }
    }

    /**
     * 获取某个item是否可以被点击
     * @param v
     * @param position
     * @param data
     * @return
     */
    protected boolean getItemClickable(View v, int position, DT data) {
        return true;
    }

    public abstract VH onCreateViewHolder(LayoutInflater layoutInflater, ViewGroup parent, int viewType);

    public abstract void onBindViewHolder(DT data, VH holder, int position);

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public int getTypedItemType(DT dt) {
        return 0;
    }

    public static class TagVal<DT> {
        public TagVal(DT value, int index) {
            this.value = value;
            this.index = index;
        }

        public DT value;
        public int index;
    }
}
