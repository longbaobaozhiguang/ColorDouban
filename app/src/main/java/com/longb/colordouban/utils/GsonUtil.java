package com.longb.colordouban.utils;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Created by longb on 2017/3/7.
 */

public class GsonUtil {

    private static Gson gson;

    static {
        gson = new Gson();
    }

    public static <T> T fromJson(String json, Class<T> cls) {
        return gson.fromJson(json, cls);
    }

    public static <T> T fromJson(String json, Type type) {
        return gson.fromJson(json, type);
    }
}
