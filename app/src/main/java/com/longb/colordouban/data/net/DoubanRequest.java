package com.longb.colordouban.data.net;

import android.content.Context;
import android.util.Log;

import com.longb.colordouban.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by longb on 2017/1/21.
 */

public class DoubanRequest {


    private static OkHttpClient mHttpClient;

    public static void init(Context context) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            Log.i("DoubanRequest","add interceptor");
            builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        }
        mHttpClient = builder.build();
    }

    private static MovieApi mDoubanApi;
    private static Object LOCK = new Object();

    public static MovieApi getApi() {
        if (mDoubanApi == null) {
            synchronized (LOCK) {
                if (mDoubanApi == null) {
                    mDoubanApi = new Retrofit.Builder()
                            .baseUrl("http://api.douban.com/v2/")
                            .client(mHttpClient)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .build()
                            .create(MovieApi.class);
                }
            }
        }
        return mDoubanApi;
    }
}
