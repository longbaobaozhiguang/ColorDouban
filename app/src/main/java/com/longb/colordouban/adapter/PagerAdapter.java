package com.longb.colordouban.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.longb.colordouban.bean.MovieClassify;
import com.longb.colordouban.modules.movie.MovieListFragment;

import java.util.List;

/**
 * Created by longb on 2017/2/5.
 */

public class PagerAdapter extends FragmentPagerAdapter {

    private List<MovieClassify> mMovieClassifies;

    public PagerAdapter(FragmentManager fm, List<MovieClassify> movieClassifies) {
        super(fm);
        mMovieClassifies = movieClassifies;
    }

    @Override
    public Fragment getItem(int position) {
        return MovieListFragment.newInstance(mMovieClassifies.get(position).getUrl());
    }

    @Override
    public int getCount() {
        return mMovieClassifies.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mMovieClassifies.get(position).getTitle();
    }
}
