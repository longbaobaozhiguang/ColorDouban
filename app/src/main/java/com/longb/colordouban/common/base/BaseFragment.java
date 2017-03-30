package com.longb.colordouban.common.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.longb.colordouban.common.ContentLayout;
import com.longb.colordouban.utils.Ts;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by longb on 2017/2/5.
 */

public class BaseFragment<VBD extends ViewDataBinding> extends Fragment implements ErrorCallback {

    private CompositeSubscription mSubscription;

    protected VBD mBinding;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Integer layoutRes = getContentLayout();
        if (layoutRes != null) {
            mBinding = DataBindingUtil.inflate(inflater, layoutRes, container, false);
            return mBinding.getRoot();
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @LayoutRes
    @Nullable
    protected Integer getContentLayout() {
        Class cls = getClass();
        if (cls.isAnnotationPresent(ContentLayout.class)) {
            ContentLayout contentLayout = (ContentLayout) cls.getAnnotation(ContentLayout.class);
            return contentLayout.value();
        }
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unsubscribe();
    }

    protected VBD getBinding() {
        return mBinding;
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

    @Override
    public void showErr(String tips) {
        Ts.showShort(getActivity(), tips);
    }
}
