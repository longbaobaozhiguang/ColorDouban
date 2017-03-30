package com.longb.colordouban.views.tool;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by longb on 2017/1/17.
 * 让ViewPager背景渐变的处理
 */
public class ColorViewPagerListener implements ViewPager.OnPageChangeListener {

    private ViewPager mViewPager;
    /**
     * 渐变颜色
     */
    private int[] colors;
    private ArgbEvaluator mArgbEvaluator;

    public ColorViewPagerListener(ViewPager viewPager, @ColorRes int[] colorRes) {
        this.mViewPager = viewPager;
        this.mArgbEvaluator = new ArgbEvaluator();
        Context context = mViewPager.getContext();
        colors = new int[colorRes.length + 1];
        for (int i = 0; i < colorRes.length; i++) {
            colors[i] = ContextCompat.getColor(context, colorRes[i]);
        }
        colors[colors.length - 1] = colors[colors.length - 2];
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // 计算中间颜色
        int evaluate = (Integer) mArgbEvaluator.evaluate(positionOffset, colors[position], colors[position + 1]);
        // 为ViewPager的父容器设置背景色
        ((View) mViewPager.getParent()).setBackgroundColor(evaluate);
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
