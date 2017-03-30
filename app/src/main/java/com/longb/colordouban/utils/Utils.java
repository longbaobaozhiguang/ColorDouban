package com.longb.colordouban.utils;

import android.support.v4.widget.SwipeRefreshLayout;

/**
 * Created by longb on 2017/1/23.
 */

public class Utils {

    /**
     * 设置 {@link SwipeRefreshLayout}
     *
     * @param layout
     */
    public static void setupRefreshLayout(SwipeRefreshLayout layout) {
        layout.setProgressViewOffset(true, DimenUtil.dp2pxInt(5), DimenUtil.dp2pxInt(80));
        layout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    public static String combineArr(Iterable<? extends Object> arr, String splitStr) {
        if (arr == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder(20);
        for (Object o : arr) {
            sb.append(o.toString());
            sb.append(splitStr);
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - splitStr.length());
        }
        return sb.toString();
    }

}
