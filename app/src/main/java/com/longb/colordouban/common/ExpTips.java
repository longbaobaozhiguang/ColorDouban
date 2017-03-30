package com.longb.colordouban.common;

import android.content.Context;

import com.longb.colordouban.R;

import java.lang.ref.SoftReference;

/**
 * Created by longb on 2017/2/17.
 */

public class ExpTips {

    private static SoftReference<Context> mContext;

    public static void init(Context context) {
        mContext = new SoftReference<Context>(context);
    }

    public static String getTips(Throwable throwable) {
        Context context = mContext.get();
        if (context == null) {
            return "";
        }
        return context.getString(R.string.unknow_error);
    }
}
