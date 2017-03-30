package com.longb.colordouban.modules;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.longb.colordouban.BR;
import com.longb.colordouban.R;
import com.longb.colordouban.adapter.CityAdapter;
import com.longb.colordouban.adapter.CityWrapperAdapter;
import com.longb.colordouban.adapter.common.ItemClickListener;
import com.longb.colordouban.bean.City;
import com.longb.colordouban.bean.event.ChangeCityEvent;
import com.longb.colordouban.common.App;
import com.longb.colordouban.common.ContentLayout;
import com.longb.colordouban.common.ExpTips;
import com.longb.colordouban.common.RxUtils;
import com.longb.colordouban.common.base.BaseActivity;
import com.longb.colordouban.data.local.CityDataSource;
import com.longb.colordouban.databinding.ActivitySelectCityBinding;
import com.longb.colordouban.utils.CityComparator;
import com.longb.colordouban.utils.L;
import com.longb.colordouban.utils.Ts;
import com.longb.colordouban.views.WaveSideBarView;
import com.longb.colordouban.views.tool.PinnedHeaderDecoration;

import org.greenrobot.eventbus.EventBus;

import java.util.Collections;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

@ContentLayout(R.layout.activity_select_city)
public class SelectCityActivity extends BaseActivity<ActivitySelectCityBinding> {

    private CityWrapperAdapter mCityAdapter;
    private InputMethodManager mInputMethodManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        init();
    }

    private void init() {
        setSupportActionBar(mBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBinding.cities.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mInputMethodManager.hideSoftInputFromWindow(mBinding.search.getWindowToken(), 0);
                return false;
            }
        });
        CityAdapter innerCityAdapter = new CityAdapter(getActivity(), BR.city);
        mCityAdapter = new CityWrapperAdapter(innerCityAdapter);
        mBinding.cities.setAdapter(mCityAdapter);
        mBinding.cities.setLayoutManager(new LinearLayoutManager(getActivity()));
        PinnedHeaderDecoration pinnedHeaderDecoration = new PinnedHeaderDecoration();
        pinnedHeaderDecoration.registerTypePinnedHeader(City.TYPE_LETTER, new PinnedHeaderDecoration.PinnedHeaderCreator() {
            @Override
            public boolean create(RecyclerView parent, int adapterPosition) {
                return true;
            }
        });
        mBinding.cities.addItemDecoration(pinnedHeaderDecoration);

        mBinding.sideBar.setOnTouchLetterChangeListener(new WaveSideBarView.OnTouchLetterChangeListener() {
            @Override
            public void onLetterChange(String letter) {
                int position = mCityAdapter.getLetterPosition(letter);
                if (position != -1) {
                    mBinding.cities.scrollToPosition(position);
                    LinearLayoutManager mLayoutManager =
                            (LinearLayoutManager) mBinding.cities.getLayoutManager();
                    mLayoutManager.scrollToPositionWithOffset(position, 0);
                }
            }
        });
        //选择城市处理
        mCityAdapter.setItemClickListener(new ItemClickListener<City>() {
            @Override
            public void onClick(View view, City data) {
                App.getSp().setCurrCity(data.getName());
                CityDataSource.getInstance(getApplicationContext()).addCityHistory(data);
                EventBus.getDefault().post(new ChangeCityEvent(data));
                finish();
            }
        });
        // 城市过滤
        mBinding.search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCityAdapter.getWrapperAdapter().setFilterKey(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        loadData();
    }

    private void loadData() {
        // 获取城市列表
        addSubscription(CityDataSource.getInstance(getApplicationContext()).getCities()
                .map(new Func1<List<City>, List<City>>() {
                    @Override
                    public List<City> call(List<City> cities) {
                        Collections.sort(cities, new CityComparator());
                        return cities;
                    }
                })
                .compose(RxUtils.<List<City>>simple())
                .subscribe(new Action1<List<City>>() {
                    @Override
                    public void call(List<City> cities) {
                        mCityAdapter.setCities(cities);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Ts.showLong(getActivity(), ExpTips.getTips(throwable));
                    }
                }));
        //获取历史选择
        addSubscription(CityDataSource.getInstance(getApplicationContext()).getCityHistory()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<City>>() {
                    @Override
                    public void call(List<City> cities) {
                        mCityAdapter.setHistory(cities);
                        L.i("city : %s" , cities);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Ts.showLong(getActivity(), ExpTips.getTips(throwable));
                    }
                }));
    }

}
