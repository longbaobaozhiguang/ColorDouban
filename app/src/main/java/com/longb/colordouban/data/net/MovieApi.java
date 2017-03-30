package com.longb.colordouban.data.net;

import android.support.annotation.Nullable;

import com.longb.colordouban.bean.Cast;
import com.longb.colordouban.bean.MovieList;
import com.longb.colordouban.bean.Subject;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by longb on 2017/1/21.
 */

public interface MovieApi {

    /**
     * 获取电影信息
     *
     * @param city  地区，中文，如：北京
     * @param start 开始下标
     * @param count 条数
     * @return
     */
    @GET
    Observable<MovieList> getMovies(@Url String url, @Query("city") @Nullable String city, @Query("start") @Nullable Integer start, @Query("count") @Nullable Integer count);

    /**
     * 获取电影的详细信息
     *
     * @param subjectId 电影唯一id
     * @return
     */
    @GET("movie/subject/{subjectId}")
    Observable<Subject> getMovieDetail(@Path("subjectId") String subjectId);

    /**
     * 获取影人详细信息
     * @param castId 影人id
     * @return
     */
    @GET("/v2/movie/celebrity/{castId}")
    Observable<Cast> getCastDetail(@Path("castId") String castId);

    /**
     * 搜索电影
     * @param q 查询关键字
     * @param tag 电影tag
     * @param start 开始下标
     * @param count 条数
     * @return
     */
    @GET("movie/search")
    Observable<MovieList> searchMovies(@Query("q") String q,@Query("tag") @Nullable String tag,@Query("start") @Nullable Integer start, @Query("count") @Nullable Integer count);
}
