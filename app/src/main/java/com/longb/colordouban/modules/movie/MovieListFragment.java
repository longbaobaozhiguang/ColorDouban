package com.longb.colordouban.modules.movie;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.longb.colordouban.BR;
import com.longb.colordouban.R;
import com.longb.colordouban.adapter.RvPageAdapter;
import com.longb.colordouban.adapter.common.DataBindingAdapter;
import com.longb.colordouban.adapter.common.ItemClickListener;
import com.longb.colordouban.bean.Subject;
import com.longb.colordouban.common.ContentLayout;
import com.longb.colordouban.common.DataKey;
import com.longb.colordouban.common.base.BaseFragmentView;
import com.longb.colordouban.databinding.FragmentMoviesBinding;
import com.longb.colordouban.utils.Utils;
import com.longb.colordouban.views.tool.PageScrollListener;

/**
 * Created by longb on 2017/2/5.
 */
@ContentLayout(R.layout.fragment_movies)
public class MovieListFragment extends BaseFragmentView<FragmentMoviesBinding, MovieListViewModel> {

    public static MovieListFragment newInstance(String api) {
        Bundle args = new Bundle();
        args.putString(DataKey.URL, api);
        MovieListFragment movieListFragment = new MovieListFragment();
        movieListFragment.setArguments(args);
        return movieListFragment;
    }

    private RvPageAdapter<DataBindingAdapter<Subject>> mPagerAdapter;
    private String mApi;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mApi = getArguments().getString(DataKey.URL);
        setViewModel(new MovieListViewModel(mApi));
        mBinding.setViewModel(mViewModel);

        Utils.setupRefreshLayout(mBinding.refreshLayout);
        mBinding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mViewModel.refreshMovie();
            }
        });

        DataBindingAdapter<Subject> moviesAdapter = new DataBindingAdapter<>(getActivity(), R.layout.item_movie, BR.subject);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mPagerAdapter = new RvPageAdapter(moviesAdapter, new PageScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore() {
                mViewModel.loadMoreMovie();
            }
        }, mViewModel.getPageHelper());
        mBinding.rvMovies.setAdapter(mPagerAdapter);
        mBinding.rvMovies.setLayoutManager(linearLayoutManager);
        mPagerAdapter.getWrapperAdapter().setItemClickListener(new ItemClickListener<Subject>() {
            @Override
            public void onClick(View view, Subject data) {
                Intent intent = MovieDetailActivity.bunildIntent(getActivity(), data.getId(), data.getImages().getLarge());
                View thumb = view.findViewById(R.id.thumb);
                startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), thumb, getString(R.string.trans_thumb_image)).toBundle());
            }
        });

        mViewModel.setErrorCallback(this);

        mViewModel.start();
    }

}
