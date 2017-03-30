package com.longb.colordouban.common;

import android.support.annotation.LayoutRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by longb on 2017/1/17.
 * 布局layout注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ContentLayout {
    /**
     * 布局文件资源id
     * @return
     */
    @LayoutRes int value();
}
