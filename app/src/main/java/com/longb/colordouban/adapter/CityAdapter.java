package com.longb.colordouban.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.longb.colordouban.R;
import com.longb.colordouban.adapter.common.BindingViewHolder;
import com.longb.colordouban.adapter.common.DataBindingAdapter;
import com.longb.colordouban.bean.City;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 城市信息adapter
 */
public class CityAdapter extends DataBindingAdapter<City> {

    private HashMap<String, Integer> mLetterPositionCache = new HashMap<>(13);
    private List<City> mFullCities;
    private List<City> mFilterCities;
    private String mFilterKey;

    public CityAdapter(Context context, int variableId) {
        super(context, 0, variableId);
    }

    @Override
    public int getTypedItemType(City city) {
        return city.getType();
    }

    @Override
    public BindingViewHolder<City> onCreateViewHolder(LayoutInflater layoutInflater, ViewGroup parent, int viewType) {
        if (viewType == City.TYPE_CITY) {
            return new BindingViewHolder<>(DataBindingUtil.inflate(layoutInflater, R.layout.item_city, parent, false));
        }
        return new BindingViewHolder<>(DataBindingUtil.inflate(layoutInflater, R.layout.item_letter, parent, false));
    }

    public int getLetterPosition(String letter) {
        Integer position = mLetterPositionCache.get(letter);
        if (position != null) {
            return position;
        }
        int itemCount = getItemCount();
        for (int i = 0; i < itemCount; i++) {
            if (getDatas().get(i).getType() == 1 && getDatas().get(i).getPys().equalsIgnoreCase(letter)) {
                position = i;
            }
        }
        mLetterPositionCache.put(letter, position);
        return position == null ? -1 : position;
    }

    @Override
    protected boolean getItemClickable(View v, int position, City data) {
        return data.getType() != City.TYPE_LETTER;
    }

    private List<City> transformData(List<City> cities, boolean notifyDataSetChanged) {
        mFullCities = cities;
        mFilterCities = new ArrayList<>(cities.size());
        setFilterKey(mFilterKey, notifyDataSetChanged);
        return mFilterCities;
    }

    public void setFilterKey(String key) {
        setFilterKey(key, true);
    }

    /**
     * 过滤
     * @param key
     * @param notifyDataSetChanged
     */
    private void setFilterKey(String key, boolean notifyDataSetChanged) {
        mFilterKey = key;
        mFilterCities.clear();
        if (!TextUtils.isEmpty(mFilterKey)) {
            for (City city : mFullCities) {
                if (city.getPys().startsWith(key) || city.getPy().startsWith(key) || city.getName().startsWith(key)) {
                    mFilterCities.add(city);
                }
            }
        } else {
            mFilterCities.addAll(mFullCities);
        }
        if (notifyDataSetChanged) {
            notifyDataSetChanged();
        }
    }

    @Override
    public void setDatas(List<City> datas) {
        super.setDatas(transformData(datas, false));
    }
}
