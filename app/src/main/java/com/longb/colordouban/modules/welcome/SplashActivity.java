package com.longb.colordouban.modules.welcome;

import android.content.Intent;
import android.os.Bundle;

import com.longb.colordouban.common.App;
import com.longb.colordouban.common.base.BaseActivity;
import com.longb.colordouban.modules.movie.MovieActivity;

/**
 * 启动页面
 */
public class SplashActivity extends BaseActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (App.getSp().isFirstOpen()) {
            startActivity(new Intent(this, WelcomeActivity.class));
            App.getSp().setIsFirstOpen(false);
        } else {
            startActivity(new Intent(this, MovieActivity.class));
        }
        finish();
    }

}
