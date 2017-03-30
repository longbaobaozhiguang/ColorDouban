package com.longb.colordouban.modules.movie;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationListener;
import com.lapism.searchview.SearchView;
import com.longb.colordouban.BR;
import com.longb.colordouban.R;
import com.longb.colordouban.adapter.MovieSearchAdapter;
import com.longb.colordouban.adapter.PagerAdapter;
import com.longb.colordouban.adapter.common.ItemClickListener;
import com.longb.colordouban.bean.City;
import com.longb.colordouban.bean.MovieClassify;
import com.longb.colordouban.bean.MovieList;
import com.longb.colordouban.bean.Subject;
import com.longb.colordouban.bean.event.ChangeCityEvent;
import com.longb.colordouban.common.App;
import com.longb.colordouban.common.ContentLayout;
import com.longb.colordouban.common.RxUtils;
import com.longb.colordouban.common.base.BaseActivity;
import com.longb.colordouban.data.net.Api;
import com.longb.colordouban.data.net.DoubanRequest;
import com.longb.colordouban.databinding.ActivityMovieBinding;
import com.longb.colordouban.modules.SelectCityActivity;
import com.longb.colordouban.utils.L;
import com.longb.colordouban.utils.Ts;
import com.longb.colordouban.utils.rx.SearchViewSubscribe;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import kr.co.namee.permissiongen.PermissionGen;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Actions;
import rx.functions.Func1;

@ContentLayout(R.layout.activity_movie)
public class MovieActivity extends BaseActivity<ActivityMovieBinding> implements NavigationView.OnNavigationItemSelectedListener {

    private static final List<MovieClassify> MOVIE_CLASSIFIES;

    static {
        MOVIE_CLASSIFIES = new ArrayList<>();
        MOVIE_CLASSIFIES.add(new MovieClassify(Api.IN_THEATERS, App.getInstance().getString(R.string.in_theaters)));
        MOVIE_CLASSIFIES.add(new MovieClassify(Api.TOP_250, App.getInstance().getString(R.string.top250)));
    }

    private MovieSearchAdapter mSearchAdapter;
    //定位相关
    private AMapLocationClient mAMapLocationClient;

    private Menu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding.setHandler(this);
        EventBus.getDefault().register(this);
        setSupportActionBar(mBinding.toolbar);
        mBinding.vpFragments.setAdapter(new PagerAdapter(getSupportFragmentManager(), MOVIE_CLASSIFIES));
        mBinding.tablayout.setupWithViewPager(mBinding.vpFragments);
        mBinding.navView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mBinding.drawerLayout, mBinding.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mBinding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        requestPermission();
        initSearchView();
        initLocation();
    }

    /**
     * 请求权限
     */
    private void requestPermission() {
        PermissionGen.with(this)
                .permissions(Manifest.permission.ACCESS_COARSE_LOCATION
                        , Manifest.permission.ACCESS_FINE_LOCATION)
                .request();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        initLocation();
    }

    /**
     * 初始化搜索设置
     */
    private void initSearchView() {
        SearchView searchView = mBinding.searchView;
        searchView.setVoice(false);
        searchView.setArrowOnly(false);
        searchView.setVersion(SearchView.VERSION_MENU_ITEM);
        searchView.setVersionMargins(SearchView.VERSION_MARGINS_MENU_ITEM);

        mSearchAdapter = new MovieSearchAdapter(this, R.layout.item_movie, BR.subject);
        mSearchAdapter.setItemClickListener(new ItemClickListener<Subject>() {
            @Override
            public void onClick(View view, Subject data) {
                startActivity(MovieDetailActivity.bunildIntent(getActivity(), data.getId(), data.getImages().getLarge()));
            }
        });
        searchView.setAdapter(mSearchAdapter);

        Action1<Throwable> throwableAction1 = Actions.empty();
        addSubscription(Observable.create(new SearchViewSubscribe(searchView))
                .debounce(1, TimeUnit.SECONDS)
                .flatMap(new Func1<String, Observable<MovieList>>() {
                    @Override
                    public Observable<MovieList> call(String s) {
                        return DoubanRequest.getApi().searchMovies(s, null, 0, 20).compose(RxUtils.<MovieList>simple());
                    }
                })
                .subscribe(new Action1<MovieList>() {
                    @Override
                    public void call(MovieList movieList) {
                        mSearchAdapter.setDatas(movieList.getSubjects());
                    }
                }, throwableAction1));
    }

    /**
     * 初始化定位信息
     */
    private void initLocation() {
        mAMapLocationClient = new AMapLocationClient(getApplicationContext());
        mAMapLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                mAMapLocationClient.stopLocation();
                if (aMapLocation.getErrorCode() == 0) {
                    L.d(aMapLocation.getCity());
                    String realCity = App.getSp().getRealCity();
                    String currCity = aMapLocation.getCity();
                    if (!realCity.startsWith(currCity)) {
                        App.getSp().setRealCity(currCity);
                        if (!App.getSp().getCurrCity().startsWith(currCity)) {
                            askChangeCity(currCity);
                        }
                    }
                } else {
                    L.w("code:%d,info:%s", aMapLocation.getErrorCode(), aMapLocation.getErrorInfo());
                }
            }
        });
        mAMapLocationClient.startLocation();
    }

    private void askChangeCity(final String cityName) {
        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.change_city_tips, cityName))
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.ensure, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        City city = new City();
                        city.setName(cityName);
                        App.getSp().setCurrCity(cityName);
                        EventBus.getDefault().post(new ChangeCityEvent(city));
                    }
                }).show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChangeCity(ChangeCityEvent event) {
        mMenu.findItem(R.id.action_city).setTitle(event.city.getName());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                Ts.showLong(getActivity(), R.string.about);
                break;
        }
        mBinding.drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.action_city).setTitle(App.getSp().getCurrCity());
        mMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                mBinding.searchView.open(true, item);
                return true;
            case R.id.action_city:
                startActivity(new Intent(this, SelectCityActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        mAMapLocationClient.onDestroy();
    }
}
