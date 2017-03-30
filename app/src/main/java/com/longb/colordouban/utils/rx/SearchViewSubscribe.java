package com.longb.colordouban.utils.rx;

import com.lapism.searchview.SearchView;

import rx.Observable;
import rx.Subscriber;
import rx.android.MainThreadSubscription;

/**
 * Created by longb on 2017/3/5.
 */

public class SearchViewSubscribe implements Observable.OnSubscribe<String> {

    private SearchView mSearchView;

    public SearchViewSubscribe(SearchView searchView) {
        this.mSearchView = searchView;
    }

    @Override
    public void call(final Subscriber<? super String> subscriber) {
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext(newText);
                }
                return true;
            }
        });

        subscriber.add(new MainThreadSubscription() {
            @Override
            protected void onUnsubscribe() {
                mSearchView.setOnQueryTextListener(null);
            }
        });
    }
}
