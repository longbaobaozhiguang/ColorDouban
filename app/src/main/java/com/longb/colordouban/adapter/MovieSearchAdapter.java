package com.longb.colordouban.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;

import com.longb.colordouban.adapter.common.DataBindingAdapter;
import com.longb.colordouban.bean.Subject;

import java.util.List;

/**
 * Created by longb on 2017/3/11.
 */

public class MovieSearchAdapter extends DataBindingAdapter<Subject> {
    public MovieSearchAdapter(Context context, @LayoutRes int itemLayout, int variableId) {
        super(context, itemLayout, variableId);
    }

    public MovieSearchAdapter(Context context, @LayoutRes int itemLayout, List<Subject> datas) {
        super(context, itemLayout, datas);
    }

    @Override
    public void setDatas(List<Subject> datas) {
        if (mDatas == null || mDatas.size() == 0) {
            mDatas = datas;
            notifyDataSetChanged();
        } else {
            int previousSize = mDatas.size();
            int nextSize = datas.size();
            mDatas = datas;
            if (previousSize == nextSize && nextSize != 0)
                notifyItemRangeChanged(0, previousSize);
            else if (previousSize > nextSize) {
                if (nextSize == 0)
                    notifyItemRangeRemoved(0, previousSize);
                else {
                    notifyItemRangeChanged(0, nextSize);
                    notifyItemRangeRemoved(nextSize - 1, previousSize);
                }
            } else {
                notifyItemRangeChanged(0, previousSize);
                notifyItemRangeInserted(previousSize, nextSize - previousSize);
            }
        }
    }
}
