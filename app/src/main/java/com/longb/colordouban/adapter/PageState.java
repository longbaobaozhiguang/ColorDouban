package com.longb.colordouban.adapter;

import android.support.annotation.IntDef;

public class PageState {

    public static final int STATE_ERROR = -1;
    public static final int STATE_NONE = 0;
    public static final int STATE_WAITING = 1;
    public static final int STATE_LOADING = 2;
    public static final int STATE_END = 3;

    @IntDef({STATE_ERROR, STATE_NONE, STATE_WAITING, STATE_LOADING, STATE_END})
    public @interface State {
    }
}
