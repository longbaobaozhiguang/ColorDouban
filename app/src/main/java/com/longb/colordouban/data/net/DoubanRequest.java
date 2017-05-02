package com.longb.colordouban.data.net;

import android.content.Context;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.longb.colordouban.common.App;
import com.longb.colordouban.utils.Utils;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by longb on 2017/1/21.
 */
public class DoubanRequest {

    private static OkHttpClient mHttpClient;

    public static void init(Context context, boolean isDebug) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (isDebug) {
            builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        }

        //设置Http缓存
        Cache cache = new Cache(new File(context.getApplicationContext()
                .getCacheDir(), "HttpCache"), 1024 * 1024 * 10);
        builder.cache(cache)
                .addInterceptor(new CacheRequestInterceptor())
                .addNetworkInterceptor(new CacheResponseInterceptor());

        if (isDebug) {
            builder.addNetworkInterceptor(new StethoInterceptor());
        }
        mHttpClient = builder.build();

    }

    private static MovieApi mDoubanApi;

    public static MovieApi getApi() {
        if (mDoubanApi == null) {
            synchronized (DoubanRequest.class) {
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

    static class CacheRequestInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (Utils.isNetworkAvailable(App.getInstance())) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_NETWORK)
                        .build();
            } else {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }
            return chain.proceed(request);
        }
    }

    // 有网络时缓存失效时间
    static final int NETWORK_AVAILABLE_MAX_AGE = 60 * 60;
    // 无网络时缓存可用时间
    static final int NETWORK_UNAVAILABLE_MAX_STALE = 60 * 60 * 24;

    static class CacheResponseInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Response response = chain.proceed(chain.request());
            return toCacheResponse(response);
        }
    }

    private static Response toCacheResponse(Response response) {
        if (Utils.isNetworkAvailable(App.getInstance())) {
            response = response.newBuilder()
                    .removeHeader("Pragma") //去除no-cache
                    .header("Cache-Control", "public, max-age=" + NETWORK_AVAILABLE_MAX_AGE) // 添加缓存失效时间
                    .build();
        } else {
            response = response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + NETWORK_UNAVAILABLE_MAX_STALE)
                    .build();
        }
        return response;
    }
}
