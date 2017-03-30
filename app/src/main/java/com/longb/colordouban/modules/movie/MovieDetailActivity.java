package com.longb.colordouban.modules.movie;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionSet;
import android.view.Gravity;
import android.view.View;

import com.bumptech.glide.Glide;
import com.longb.colordouban.BR;
import com.longb.colordouban.R;
import com.longb.colordouban.adapter.common.DataBindingAdapter;
import com.longb.colordouban.adapter.common.ItemClickListener;
import com.longb.colordouban.bean.Cast;
import com.longb.colordouban.bean.Subject;
import com.longb.colordouban.common.ContentLayout;
import com.longb.colordouban.common.DataKey;
import com.longb.colordouban.common.RxUtils;
import com.longb.colordouban.common.base.BaseActivity;
import com.longb.colordouban.databinding.ActivityMovieDetailBinding;
import com.longb.colordouban.data.net.DoubanRequest;
import com.longb.colordouban.utils.Ts;

import jp.wasabeef.glide.transformations.BlurTransformation;
import rx.functions.Action1;

@ContentLayout(R.layout.activity_movie_detail)
public class MovieDetailActivity extends BaseActivity<ActivityMovieDetailBinding> {

    public static Intent bunildIntent(Context context, String subjectId, String thumbUrl) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(DataKey.SUBJECT_ID, subjectId);
        intent.putExtra(DataKey.THUMB_URL, thumbUrl);
        return intent;
    }

    private DataBindingAdapter<Cast> mCastsAdapter;

    private String mSubjectId;
    private String mThumbUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupTransition();
        init();
    }

    private void setupTransition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            TransitionSet transitionSet = new TransitionSet();
            transitionSet.addTransition(new Slide(Gravity.TOP).addTarget(mBinding.appBar))
                    .addTransition(new Fade().addTarget(mBinding.appBar))
                    .addTransition(new Slide(Gravity.BOTTOM).addTarget(mBinding.contentView))
                    .addTransition(new Fade().addTarget(mBinding.contentView));
            transitionSet.setDuration(500);
            getWindow().setEnterTransition(transitionSet);
            getWindow().setExitTransition(transitionSet);
        }
    }

    private void init() {
        mBinding.setSubject(null);
        setSupportActionBar(mBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.finishAfterTransition(getActivity());
            }
        });

        mBinding.rvCasts.setNestedScrollingEnabled(false);
        mCastsAdapter = new DataBindingAdapter<>(this, R.layout.item_cast, BR.cast);
        mBinding.rvCasts.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mBinding.rvCasts.setAdapter(mCastsAdapter);
        mCastsAdapter.setItemClickListener(new ItemClickListener<Cast>() {
            @Override
            public void onClick(View view, Cast data) {
                Intent intent = new Intent(getActivity(), CastDetailActivity.class);
                intent.putExtra(DataKey.CAST_ID, data.getId());
                intent.putExtra(DataKey.CAST_AVATAR, data.getAvatars().getLarge());
                View thumb = view.findViewById(R.id.iv_cast_img);
                ActivityCompat.startActivity(getActivity(), intent, ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), thumb, getString(R.string.trans_cast_thumb)).toBundle());
            }
        });

        Intent intent = getIntent();
        mSubjectId = intent.getStringExtra(DataKey.SUBJECT_ID);
        mThumbUrl = intent.getStringExtra(DataKey.THUMB_URL);
        mBinding.toolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.primary_text_white));
        mBinding.toolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, R.color.white));

        Glide.with(this)
                .load(mThumbUrl)
                .bitmapTransform(new BlurTransformation(getActivity()))
                .crossFade()
                .into(mBinding.ivMovieBg);

        Glide.with(this)
                .load(mThumbUrl)
                .crossFade()
                .into(mBinding.ivThumb);

        addSubscription(DoubanRequest.getApi().getMovieDetail(mSubjectId)
                .compose(RxUtils.<Subject>simple())
                .subscribe(new Action1<Subject>() {
                    @Override
                    public void call(Subject subject) {
                        mBinding.setSubject(subject);
                        mCastsAdapter.setDatas(subject.getCasts());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Ts.showLong(getActivity(), R.string.net_err);
                    }
                }));
    }
}
