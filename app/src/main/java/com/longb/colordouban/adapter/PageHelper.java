package com.longb.colordouban.adapter;

import android.databinding.ObservableInt;


public class PageHelper {

    public final ObservableInt loadingState = new ObservableInt(PageState.STATE_NONE);

    public void setState(@PageState.State int state) {
        loadingState.set(state);
    }

    public boolean canLoadMore() {
        return loadingState.get() == PageState.STATE_WAITING || loadingState.get() == PageState.STATE_ERROR;
    }

    public boolean end() {
        return loadingState.get() == PageState.STATE_END;
    }


    public void onErrClick() {

    }
}
