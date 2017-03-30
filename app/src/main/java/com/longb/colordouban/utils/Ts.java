package com.longb.colordouban.utils;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;

/**
 * Created by longb on 2017/2/8.
 */

public class Ts {

    public static void showLong(Context context, @StringRes int strRes) {
        if (context != null) {
            Toast.makeText(context, strRes, Toast.LENGTH_LONG).show();
        }
    }

    public static void showLong(Context context, String message) {
        if (context != null) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }
    }

    public static void showShort(Context context, @StringRes int strRes) {
        if (context != null) {
            Toast.makeText(context, strRes, Toast.LENGTH_SHORT).show();
        }
    }

    public static void showShort(Context context, String message) {
        if (context != null) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }
}
