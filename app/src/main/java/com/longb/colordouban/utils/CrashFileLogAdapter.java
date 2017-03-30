package com.longb.colordouban.utils;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.util.Pools;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;

/**
 * Created by longb on 2017/2/7.
 * 文件log
 */

public class CrashFileLogAdapter extends L.LogAdapter {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat nowFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private SerializedSubject<LogInfo, LogInfo> mLogSubject = new SerializedSubject<>(PublishSubject.<LogInfo>create());
    private Pools.SynchronizedPool<LogInfo> mLogInfoPool = new Pools.SynchronizedPool<>(10);

    // 系统信息
    private String mSysModel;// 手机型号
    private int mSdkVer;// sdk版本
    private String mSysVer; //系统版本
    private String mAppVer; //软件版本

    private String mSysInfo;

    private String mLogPath;

    public CrashFileLogAdapter(Application application, String logPath) {
        this.mLogPath = logPath;
        initSysInfo(application);
        mLogSubject.observeOn(Schedulers.io())
                .subscribe(new Action1<LogInfo>() {
                    @Override
                    public void call(LogInfo logInfo) {
                        try {
                            // write to file
                            writeLogToFile(logInfo);
                        } finally {
                            mLogInfoPool.release(logInfo);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        // ignore log error
                    }
                });
    }

    private void initSysInfo(Application application) {
        mSysModel = Build.MODEL;
        mSdkVer = Build.VERSION.SDK_INT;
        mSysVer = Build.VERSION.RELEASE;
        try {
            PackageInfo packageInfo = application.getPackageManager().getPackageInfo(application.getPackageName(), 0);
            mAppVer = packageInfo.versionName + "_" + packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        mSysInfo = String.format("app_ver:%s,sys_model:%s,sdk_ver:%d,sys_ver:%s", mAppVer, mSysModel, mSdkVer, mSysVer);
    }

    @Override
    public boolean isLoggable(int priority) {
        return priority == Log.ERROR || priority == Log.WARN;
    }

    @Override
    public void log(int priority, String tag, @Nullable Throwable throwable, @Nullable String msg) {
        LogInfo logInfo = mLogInfoPool.acquire();
        if (logInfo == null) {
            logInfo = new LogInfo();
        }
        logInfo.tag = tag;
        logInfo.throwable = throwable;
        logInfo.msg = msg;
        mLogSubject.onNext(logInfo);
    }

    private void writeLogToFile(LogInfo logInfo) {
        Date date = new Date();
        String filePath = mLogPath + "/" + dateFormat.format(date);
        File file = new File(filePath);
        if (!file.exists()) {
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
        }

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file, true);
            fileWriter.write(nowFormat.format(date) + "\n");
            fileWriter.write(mSysInfo + "\n");
            fileWriter.write(logInfo.tag + "\n");
            fileWriter.write(format(logInfo.throwable, logInfo.msg) + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    static class LogInfo {

        public String tag;
        public Throwable throwable;
        public String msg;
    }
}
