package com.longb.colordouban.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RawRes;

import com.google.common.io.CharStreams;
import com.google.gson.reflect.TypeToken;
import com.longb.colordouban.R;
import com.longb.colordouban.bean.City;
import com.longb.colordouban.utils.GsonUtil;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.schedulers.Schedulers;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 城市信息数据操作类
 */
public class CityDataSource {

    @Nullable
    private static CityDataSource instance;

    @NonNull
    private BriteDatabase mDbHelper;

    @NonNull
    private Scheduler mScheduler;

    private Context mContext;

    public CityDataSource(@NonNull Context context, @NonNull Scheduler scheduler) {
        checkNotNull(context, "context cannot be null");
        checkNotNull(scheduler, "scheduler cannot be null");
        mContext = context;
        mScheduler = scheduler;
        DoubanDbHelper doubanDbHelper = new DoubanDbHelper(context);
        SqlBrite sqlBrite = new SqlBrite.Builder().build();
        mDbHelper = sqlBrite.wrapDatabaseHelper(doubanDbHelper, scheduler);
    }

    public static CityDataSource getInstance(@NonNull Context context) {
        if (instance == null) {
            instance = new CityDataSource(context, Schedulers.io());
        }
        return instance;
    }

    /**
     * 获取城市选择历史（最新6条）
     *
     * @return
     */
    public Observable<List<City>> getCityHistory() {
        return mDbHelper.createQuery(DoubanDbHelper.TABLE_CITY_HISTORY, "select * from "  + DoubanDbHelper.TABLE_CITY_HISTORY + " order by select_time desc limit 6")
                .mapToList(City.getMapper());
    }

    /**
     * 添加城市选择历史
     */
    public void addCityHistory(@NonNull City city) {
        checkNotNull(city, "city cannot be null");
        ContentValues contentValues = City.map2ContentValues(city);
        contentValues.put("select_time", System.currentTimeMillis());
        mDbHelper.insert(DoubanDbHelper.TABLE_CITY_HISTORY, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
    }

    /**
     * 获取省份列表
     *
     * @return
     */
    public Observable<List<City>> getProvince() {
        return getLocation(R.raw.provinces).subscribeOn(mScheduler);
    }

    /**
     * 获取城市列表
     *
     * @return
     */
    public Observable<List<City>> getCities() {
        return getLocation(R.raw.cities).subscribeOn(mScheduler);
    }

    /**
     * 获取城市列表
     *
     * @param resId
     * @return
     */
    public Observable<List<City>> getLocation(@RawRes int resId) {
        final InputStream inputStream = mContext.getResources().openRawResource(resId);
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
}
