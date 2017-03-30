package com.longb.colordouban.common;

import com.longb.colordouban.utils.L;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by longb on 2017/1/21.
 */

public class RxUtils {

    private static Action1<Throwable> errorHandler = new Action1<Throwable>() {
        @Override
        public void call(Throwable throwable) {
            L.e(throwable);
        }
    };

    /**
     * 对Observable进行io到mainThread的线程调度设置
     *
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<T, T> simple() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnError(errorHandler);
            }
        };
    }
}
