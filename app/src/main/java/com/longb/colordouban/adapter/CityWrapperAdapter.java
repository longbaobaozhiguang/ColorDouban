package com.longb.colordouban.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.longb.colordouban.R;
import com.longb.colordouban.adapter.common.AdapterWrapper;
import com.longb.colordouban.adapter.common.BindingViewHolder;
import com.longb.colordouban.adapter.common.ItemClickListener;
import com.longb.colordouban.bean.City;

import java.util.List;

/**
 * Created by longb on 2017/3/27.
 */

public class CityWrapperAdapter extends AdapterWrapper<RecyclerView.ViewHolder, CityAdapter> implements ItemClickListener<City> {

    private static final int POSITION_OFFSET = 1;

    private static final int TYPE_HISTORY = 10001;

    private List<City> mHistory;
    private ItemClickListener<City> mItemClickListener;

    public CityWrapperAdapter(CityAdapter adapter) {
        super(adapter);
        adapter.setItemClickListener(this);
    }

    /**
     * 设置历史记录
     *
     * @param history
     */
    public void setHistory(List<City> history) {
        mHistory = history;
        notifyItemChanged(0);
    }

    /**
     * 设置城市数据
     *
     * @param cities
     */
    public void setCities(List<City> cities) {
        getWrapperAdapter().setDatas(cities);
    }

    public void setItemClickListener(ItemClickListener<City> l) {
        this.mItemClickListener = l;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < POSITION_OFFSET) {
            return TYPE_HISTORY;
        }
        return getWrapperAdapter().getItemViewType(position - POSITION_OFFSET);
    }

    @Override
    public int getWrapperPositionOffset() {
        return POSITION_OFFSET;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HISTORY) {
            return new HistoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_history, parent, false), this);
        }
        return getWrapperAdapter().createViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position < POSITION_OFFSET) {
            if (holder instanceof HistoryViewHolder) {
                ((HistoryViewHolder) holder).setHistory(mHistory);
            }
        } else {
            getWrapperAdapter().onBindViewHolder((BindingViewHolder<City>) holder, position - POSITION_OFFSET);
        }
    }

    @Override
    public int getItemCount() {
        return getWrapperItemCount() + POSITION_OFFSET;
    }

    public int getLetterPosition(String letter) {
        int position = getWrapperAdapter().getLetterPosition(letter);
        if (position != -1) {
            position += POSITION_OFFSET;
        }
        return position;
    }

    @Override
    public void onClick(View view, City data) {
        if (mItemClickListener != null) {
            mItemClickListener.onClick(view, data);
        }
    }

    static class HistoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private int[] ids = new int[]{R.id.history1, R.id.history2, R.id.history3, R.id.history4, R.id.history5, R.id.history6};
        private CityWrapperAdapter mAdapter;

        public HistoryViewHolder(View itemView, CityWrapperAdapter adapter) {
            super(itemView);
            mAdapter = adapter;
        }

        /**
         * 设置历史数据
         *
         * @param cities
         */
        public void setHistory(List<City> cities) {
            if (cities == null || cities.size() == 0) {
                itemView.findViewById(R.id.history_title).setVisibility(View.GONE);
                itemView.findViewById(R.id.history_panel_1).setVisibility(View.GONE);
                itemView.findViewById(R.id.history_panel_2).setVisibility(View.GONE);
                return;
            }
            itemView.findViewById(R.id.history_title).setVisibility(View.VISIBLE);
            itemView.findViewById(R.id.history_panel_1).setVisibility(View.VISIBLE);
            itemView.findViewById(R.id.history_panel_2).setVisibility(View.VISIBLE);
            for (int i = 0; i < ids.length; i++) {
                TextView tv = (TextView) itemView.findViewById(ids[i]);
                if (i < cities.size()) {
                    tv.setText(cities.get(i).getName());
                    tv.setTag(cities.get(i));
                    tv.setOnClickListener(this);
                } else {
                    tv.setVisibility(View.INVISIBLE);
                }
            }
            if (cities.size() < 4) {
                itemView.findViewById(R.id.history_panel_2).setVisibility(View.GONE);
            }
        }

        @Override
        public void onClick(View v) {
            City city = (City) v.getTag();
            mAdapter.onClick(v, city);
        }
    }
}
