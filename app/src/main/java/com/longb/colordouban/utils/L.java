package com.longb.colordouban.utils;

import android.support.annotation.Nullable;
import android.util.Log;


/**
 * 系统 {@link Log} 包装工具类
 */
public class L {
    private static final int SKIP_DEPTH = 6;


    private static LogAdapter mLogAdapter = new AndroidLogAdapter();

    public static void setLogAdapter(LogAdapter logAdapter) {
        mLogAdapter = logAdapter;
    }

    public static void v(Object msg) {
        writeLog(Log.VERBOSE, msg);
    }

    public static void v(String msg, Object... args) {
        writeLog(Log.VERBOSE, msg, args);
    }

    public static void d(Object msg) {
        writeLog(Log.DEBUG, msg);
    }

    public static void d(String msg, Object... args) {
        writeLog(Log.DEBUG, msg, args);
    }

    public static void i(Object msg) {
        writeLog(Log.INFO, msg);
    }

    public static void i(String msg, Object... args) {
        writeLog(Log.INFO, msg, args);
    }

    public static void w(Object msg) {
        writeLog(Log.WARN, msg);
    }

    public static void w(String msg, Object... args) {
        writeLog(Log.WARN, msg, args);
    }

    public static void e(Throwable msg) {
        writeLog(Log.ERROR, msg);
    }

    public static void e(String msg, Object... args) {
        writeLog(Log.ERROR, msg, args);
    }

    public static void e(Throwable throwable, String msg, Object... args) {
        writeLog(Log.ERROR, throwable, msg, args);
    }


    public static void writeLog(int priority, Object msg, Object... args) {
        writeLog(priority, null, msg, args);
    }

    public static void writeLog(int priority, Throwable throwable, Object msg, Object... args) {
        if (mLogAdapter.isLoggable(priority)) {
            String tag = getDefaultTag();
            String message = (msg == null ? null : msg.toString());
            mLogAdapter.log(priority, tag, throwable, message, args);
        }
    }

    /**
     * 获取默认的TAG名称.
     */
    public static String getDefaultTag() {
        final StackTraceElement trace = Thread.currentThread().getStackTrace()[SKIP_DEPTH];
        return "c:" + trace.getFileName() + "|m:" + trace.getMethodName()
                + "|l:" + trace.getLineNumber();
    }

    public static abstract class LogAdapter {

        public boolean isLoggable(int priority) {
            return true;
        }

        protected String format(@Nullable String msg, Object... args) {
            if (msg == null) {
                return "";
            }
            return String.format(msg, args);
        }

        protected String format(@Nullable Throwable throwable, @Nullable String msg, Object... args) {
            msg = format(msg, args);
            if (throwable != null) {
                msg += ":" + Log.getStackTraceString(throwable);
            }
            return msg;
        }

        protected String format(@Nullable Throwable throwable, @Nullable String msg) {
            if (msg == null) {
                msg = "";
            }
            if (throwable != null) {
                msg += ":" + Log.getStackTraceString(throwable);
            }
            return msg;
        }

        public void log(int priority, String tag, @Nullable Throwable throwable, @Nullable String msg, Object... args) {
            this.log(priority, tag, throwable, format(msg, args));
        }

        public abstract void log(int priority, String tag, @Nullable Throwable throwable, @Nullable String msg);
    }

    public static class AndroidLogAdapter extends LogAdapter {

        private int priority;

        public AndroidLogAdapter() {
            this(Log.VERBOSE);
        }

        public AndroidLogAdapter(int priority) {
            this.priority = priority;
        }

        @Override
        public boolean isLoggable(int priority) {
            return priority >= this.priority;
        }

        private void writeLog(int priority, String tag, String msg) {
            if (msg.length() > 3000) {
                Log.println(priority, tag, msg.substring(0, 3000));
                writeLog(priority, tag, msg.substring(3000));
            } else {
                Log.println(priority, tag, msg);
            }
        }

        @Override
        public void log(int priority, String tag, @Nullable Throwable throwable, @Nullable String msg) {
            writeLog(priority, tag, format(throwable, msg));
        }
    }
}
