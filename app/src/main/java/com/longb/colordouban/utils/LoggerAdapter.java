package com.longb.colordouban.utils;

import android.support.annotation.Nullable;

import com.orhanobut.logger.Logger;

/**
 * 使用Logger作为log输出
 * Created by longb on 2017/2/7.
 */
public class LoggerAdapter extends L.LogAdapter {

    private int priority;

    public LoggerAdapter(int priority) {
        this.priority = priority;
        Logger.init().methodOffset(5);
    }

    @Override
    public boolean isLoggable(int priority) {
        return priority >= this.priority;
    }

    @Override
    public void log(int priority, String tag, @Nullable Throwable throwable, @Nullable String msg) {
        Logger.log(priority, tag, msg, throwable);
    }
}
