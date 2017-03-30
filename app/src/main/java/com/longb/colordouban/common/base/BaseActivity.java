package com.longb.colordouban.common.base;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.longb.colordouban.common.ContentLayout;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by longb on 2017/1/17.
 */

public abstract class BaseActivity<VDB extends ViewDataBinding> extends AppCompatActivity {

    private CompositeSubscription mSubscription;

    protected VDB mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onSetContentView();
    }

    protected void onSetContentView() {
        Class cls = getClass();
        if (cls.isAnnotationPresent(ContentLayout.class)) {
            ContentLayout contentLayout = (ContentLayout) cls.getAnnotation(ContentLayout.class);
            mBinding = DataBindingUtil.setContentView(this,contentLayout.value());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unsubscribe();
    }

    protected void addSubscription(Subscription subscription) {
        if (mSubscription == null) {
            mSubscription = new CompositeSubscription();
        }
        mSubscription.add(subscription);
    }

    protected void unsubscribe() {
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
        mSubscription = null;
    }

    protected Activity getActivity() {
        return this;
    }

}
