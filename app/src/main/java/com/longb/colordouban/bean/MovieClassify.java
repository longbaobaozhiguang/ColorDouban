package com.longb.colordouban.bean;

/**
 * Created by longb on 2017/2/5.
 * 电影分类信息
 */

public class MovieClassify {

    private String url;

    private String title;

    public MovieClassify(String url, String title) {
        this.url = url;
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
