package com.longb.colordouban.modules.movie;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.longb.colordouban.BR;
import com.longb.colordouban.R;
import com.longb.colordouban.adapter.common.DataBindingAdapter;
import com.longb.colordouban.adapter.common.ItemClickListener;
import com.longb.colordouban.bean.Cast;
import com.longb.colordouban.bean.Work;
import com.longb.colordouban.common.ContentLayout;
import com.longb.colordouban.common.DataKey;
import com.longb.colordouban.common.ExpTips;
import com.longb.colordouban.common.RxUtils;
import com.longb.colordouban.common.base.BaseActivity;
import com.longb.colordouban.databinding.ActivityCastDetailBinding;
import com.longb.colordouban.data.net.DoubanRequest;
import com.longb.colordouban.utils.Ts;
import com.longb.colordouban.utils.glide.PaletteBitmap;
import com.longb.colordouban.utils.glide.PaletteBitmapTranscoder;

import rx.functions.Action1;

@ContentLayout(R.layout.activity_cast_detail)
public class CastDetailActivity extends BaseActivity<ActivityCastDetailBinding> {

    private DataBindingAdapter<Work> mWorkDataBindingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        Intent intent = getIntent();
        String castId = intent.getStringExtra(DataKey.CAST_ID);
        String avatarUrl = intent.getStringExtra(DataKey.CAST_AVATAR);

        setSupportActionBar(mBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.finishAfterTransition(getActivity());
            }
        });

        mWorkDataBindingAdapter = new DataBindingAdapter<Work>(getActivity(), R.layout.item_cast_movie, BR.work);
        mBinding.rvMovies.setNestedScrollingEnabled(false);
        mBinding.rvMovies.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mBinding.rvMovies.setAdapter(mWorkDataBindingAdapter);
        mWorkDataBindingAdapter.setItemClickListener(new ItemClickListener<Work>() {
            @Override
            public void onClick(View view, Work data) {
                Intent intent = new Intent(new Intent(getActivity(), MovieDetailActivity.class));
                intent.putExtra(DataKey.SUBJECT_ID, data.getSubject().getId());
                intent.putExtra(DataKey.THUMB_URL, data.getSubject().getImages().getLarge());
                View thumb = view.findViewById(R.id.iv_movie);
                ActivityCompat.startActivity(getActivity(), intent, ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), thumb, getString(R.string.trans_thumb_image)).toBundle());
            }
        });

        Glide.with(this)
                .load(avatarUrl)
                .asBitmap()
                .transcode(new PaletteBitmapTranscoder(this), PaletteBitmap.class)
                .into(new ImageViewTarget<PaletteBitmap>(mBinding.avatar) {
                    @Override
                    protected void setResource(PaletteBitmap resource) {
                        super.view.setImageBitmap(resource.bitmap);
                        int themeColor = resource.palette.getLightMutedColor(ActivityCompat.getColor(getActivity(), R.color.colorPrimary));
                        mBinding.appBar.setBackgroundColor(themeColor);
                        mBinding.toolbarLayout.setContentScrimColor(themeColor);
                        mBinding.toolbarLayout.setStatusBarScrimColor(themeColor);
                        Palette.Swatch swatch = resource.palette.getLightMutedSwatch();
                        if (swatch != null) {
                            mBinding.toolbarLayout.setExpandedTitleColor(swatch.getBodyTextColor());
                            Toolbar toolbar = mBinding.toolbar;
                            Drawable drawable = toolbar.getNavigationIcon();
                            drawable.setColorFilter(swatch.getBodyTextColor(), PorterDuff.Mode.SRC_ATOP);
                        }
                    }
                });


        addSubscription(DoubanRequest.getApi().getCastDetail(castId)
                .compose(RxUtils.<Cast>simple())
                .subscribe(new Action1<Cast>() {
                    @Override
                    public void call(Cast cast) {
                        mBinding.setCast(cast);
                        mWorkDataBindingAdapter.setDatas(cast.getWorks());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Ts.showLong(getActivity(), ExpTips.getTips(throwable));
                    }
                }));
    }
}
