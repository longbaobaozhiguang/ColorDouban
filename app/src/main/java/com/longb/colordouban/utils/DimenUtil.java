package com.longb.colordouban.utils;

import com.longb.colordouban.common.App;

/**
 * @author 咖枯
 * @version 1.0 2016/7/7
 */
public class DimenUtil {
    public static float dp2px(float dp) {
        final float scale = App.getInstance().getResources().getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }

    public static int dp2pxInt(float dp) {
        final float scale = App.getInstance().getResources().getDisplayMetrics().density;
        return (int)(dp * scale + 0.5f);
    }

    public static float sp2px(float sp) {
        final float scale = App.getInstance().getResources().getDisplayMetrics().scaledDensity;
        return sp * scale;
    }

    public static int getScreenSize() {
        return App.getInstance().getResources().getDisplayMetrics().widthPixels;
    }
}
