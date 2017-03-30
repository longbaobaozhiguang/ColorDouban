package com.longb.colordouban.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.longb.colordouban.R;

/**
 * Created by longb on 2017/1/21.
 */

public class Sp {

    private SharedPreferences mSp;
    private Context mContext;

    public Sp(Context context) {
        mContext = context.getApplicationContext();
        mSp = PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * 设置是否第一次打开app
     *
     * @param isFirstOpen
     * @return
     */
    public Sp setIsFirstOpen(boolean isFirstOpen) {
        mSp.edit().putBoolean("isFirstOpen", isFirstOpen).commit();
        return this;
    }

    /**
     * 获取是否第一次打开app
     *
     * @return
     */
    public boolean isFirstOpen() {
        return mSp.getBoolean("isFirstOpen", true);
    }

    /**
     * 获取当前城市
     * @return
     */
    public String getCurrCity() {
        return mSp.getString("currCity",mContext.getString(R.string.default_location));
    }

    /**
     * 设置当前城市
     * @param city
     * @return
     */
    public Sp setCurrCity(String city) {
        mSp.edit().putString("currCity",city).commit();
        return this;
    }

    /**
     * 获取真实所在城市
     * @return
     */
    public String getRealCity() {
        return mSp.getString("realCity",mContext.getString(R.string.default_location));
    }

    /**
     * 设置真实所在城市
     * @param city
     * @return
     */
    public Sp setRealCity(String city) {
        mSp.edit().putString("realCity",city).commit();
        return this;
    }
}
