package com.longb.colordouban.common.base;

import android.databinding.BaseObservable;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * ViewModel 基类
 */
public class ViewModel extends BaseObservable {

    private CompositeSubscription mSubscription;
    private ErrorCallback mErrorCallback;


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

    public void onDestroy() {
        unsubscribe();
    }

    public void setErrorCallback (ErrorCallback errorCallback) {
        this.mErrorCallback = errorCallback;
    }

    protected void showErr(String tips){
        if (mErrorCallback != null) {
            mErrorCallback.showErr(tips);
        }
    }

}
