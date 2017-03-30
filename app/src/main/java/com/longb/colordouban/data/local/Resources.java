package com.longb.colordouban.data.local;

import android.content.Context;
import android.support.annotation.RawRes;

import com.google.common.io.CharStreams;
import com.google.gson.reflect.TypeToken;
import com.longb.colordouban.R;
import com.longb.colordouban.bean.City;
import com.longb.colordouban.utils.GsonUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * App 本地资源
 */
public class Resources {

    /**
     * 获取城市列表
     *
     * @param context
     * @param resId
     * @return
     */
    public static Observable<List<City>> getLocation(Context context, @RawRes int resId) {
        final InputStream inputStream = context.getResources().openRawResource(resId);
        return Observable.create(new Observable.OnSubscribe<List<City>>() {
            @Override
            public void call(Subscriber<? super List<City>> subscriber) {
                try {
                    String res = CharStreams.toString(new InputStreamReader(inputStream));
                    List<City> cities = GsonUtil.fromJson(res, new TypeToken<ArrayList<City>>() {
                    }.getType());
                    subscriber.onNext(cities);
                    subscriber.onCompleted();
                } catch (IOException e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    /**
     * 获取省份列表
     *
     * @param context
     * @return
     */
    public static Observable<List<City>> getProvince(Context context) {
        return getLocation(context, R.raw.provinces);
    }

    /**
     * 获取城市列表
     *
     * @param context
     * @return
     */
    public static Observable<List<City>> getCities(Context context) {
        return getLocation(context, R.raw.cities);
    }
}
