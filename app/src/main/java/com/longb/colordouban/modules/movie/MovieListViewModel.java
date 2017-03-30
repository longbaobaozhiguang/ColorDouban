package com.longb.colordouban.modules.movie;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;

import com.longb.colordouban.adapter.PageHelper;
import com.longb.colordouban.adapter.PageState;
import com.longb.colordouban.bean.MovieList;
import com.longb.colordouban.bean.Subject;
import com.longb.colordouban.bean.event.ChangeCityEvent;
import com.longb.colordouban.common.App;
import com.longb.colordouban.common.Constants;
import com.longb.colordouban.common.ExpTips;
import com.longb.colordouban.common.RxUtils;
import com.longb.colordouban.common.base.ViewModel;
import com.longb.colordouban.data.net.DoubanRequest;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import rx.functions.Action1;

/**
 * Created by longb on 2017/2/21.
 */

public class MovieListViewModel extends ViewModel {

    public final ObservableList<Subject> movies = new ObservableArrayList<>();
    public final ObservableBoolean refreshing = new ObservableBoolean(false);
    private final PageHelper mPageHelper;


    private String mApi;
    private int mPage = 0;
    private int mPageCount = Constants.PAGE_COUNT;
    private String mCity;

    public MovieListViewModel(String api) {
        mApi = api;
        mPageHelper = new PageHelper() {
            @Override
            public void onErrClick() {
                loadMoreMovie();
            }
        };
        mCity = App.getSp().getCurrCity();
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChangeCity(ChangeCityEvent event){
        mCity = event.city.getName();
        refreshMovie();
    }


    public PageHelper getPageHelper() {
        return mPageHelper;
    }

    public void start() {
        refreshMovie();
    }


    public void refreshMovie() {
        mPage = 0;
        refreshing.set(true);
        loadMovie(true);
    }

    public void loadMoreMovie() {
        if (!mPageHelper.canLoadMore()) {
            return;
        }
        mPageHelper.setState(PageState.STATE_LOADING);
        loadMovie(false);
    }

    private void loadMovie(final boolean cleanData) {
        addSubscription(DoubanRequest.getApi().getMovies(mApi, mCity, mPage * mPageCount, mPageCount)
                .compose(RxUtils.<MovieList>simple())
                .subscribe(new Action1<MovieList>() {
                               @Override
                               public void call(MovieList movieList) {
                                   refreshing.set(false);
                                   if (cleanData) {
                                       movies.clear();
                                   }
                                   movies.addAll(movieList.getSubjects());
                                   mPage++;
                                   mPageHelper.setState(movieList.getSubjects().size() < mPageCount ? PageState.STATE_END : PageState.STATE_WAITING);
                               }
                           },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                refreshing.set(false);
                                if (movies.size() > 0) {
                                    mPageHelper.setState(PageState.STATE_ERROR);
                                }
                                showErr(ExpTips.getTips(throwable));
                            }
                        }));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
