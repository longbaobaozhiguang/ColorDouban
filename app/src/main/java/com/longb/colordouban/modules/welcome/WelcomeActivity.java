package com.longb.colordouban.modules.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.longb.colordouban.R;
import com.longb.colordouban.common.ContentLayout;
import com.longb.colordouban.common.base.BaseActivity;
import com.longb.colordouban.databinding.ActivityWelcomeBinding;
import com.longb.colordouban.modules.movie.MovieActivity;
import com.longb.colordouban.views.tool.ColorViewPagerListener;
import com.longb.colordouban.views.tool.DepthPageTransformer;
import com.longb.colordouban.adapter.common.SimplePageAdapter;

import java.util.ArrayList;
import java.util.List;

@ContentLayout(R.layout.activity_welcome)
public class WelcomeActivity extends BaseActivity<ActivityWelcomeBinding> {

    private int[] mImgResIds = new int[]{R.drawable.ic_local_movies, R.drawable.ic_movie_creation, R.drawable.ic_movie_filter};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding.setHandler(this);
        mBinding.vpWelcome.addOnPageChangeListener(new ColorViewPagerListener(mBinding.vpWelcome, new int[]{R.color.colorWelcomePage1, R.color.colorWelcomePage2, R.color.colorWelcomePage3}));
        mBinding.vpWelcome.setPageTransformer(true, new DepthPageTransformer());

        List<View> views = new ArrayList<>(mImgResIds.length);
        LayoutInflater layoutInflater = getLayoutInflater();
        for (int i = 0; i < mImgResIds.length; i++) {
            View v = layoutInflater.inflate(R.layout.layout_welcome_page, null);
            ImageView img = (ImageView) v.findViewById(R.id.icon);
            img.setImageResource(mImgResIds[i]);
            views.add(v);
        }

        mBinding.vpWelcome.setAdapter(new SimplePageAdapter(views));
        mBinding.vpWelcome.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 1) {
                    mBinding.btnFinish.setVisibility(View.VISIBLE);
                    mBinding.btnFinish.setAlpha(1 * positionOffset);
                    mBinding.btnFinish.setTranslationY((mBinding.btnFinish.getHeight() >> 1) * (1 - positionOffset));
                }
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    public void onBtnFinishClick() {
        startActivity(new Intent(WelcomeActivity.this, MovieActivity.class));
        finish();
    }

}
