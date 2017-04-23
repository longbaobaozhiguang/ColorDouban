package com.longb.colordouban.common;

import android.app.Application;
import android.util.Log;

import com.facebook.stetho.Stetho;
import com.longb.colordouban.BuildConfig;
import com.longb.colordouban.data.net.DoubanRequest;
import com.longb.colordouban.utils.L;
import com.squareup.leakcanary.LeakCanary;


/**
 * Created by longb on 2017/1/17.
 */

public class App extends Application {

    private Sp mSp;

    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
        if (BuildConfig.DEBUG) {
            Stetho.initialize(
                    Stetho.newInitializerBuilder(this)
                            .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                            .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                            .build());
        }
        instance = this;
        L.setLogAdapter(new L.AndroidLogAdapter(BuildConfig.DEBUG ? Log.VERBOSE : (Log.ASSERT + 1)));
        ExpTips.init(this);
        mSp = new Sp(this);
        DoubanRequest.init(this);
    }

    public static App getInstance() {
        return instance;
    }

    public static Sp getSp() {
        return instance.mSp;
    }


}
