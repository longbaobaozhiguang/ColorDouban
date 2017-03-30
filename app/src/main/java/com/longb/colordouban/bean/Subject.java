
package com.longb.colordouban.bean;

import android.util.SparseArray;

import java.util.List;

public class Subject {

    private Rating rating;
    private List<String> genres = null;
    private String title;
    private List<Cast> casts = null;
    private int collectCount;
    private String originalTitle;
    private String subtype;
    private List<Director> directors = null;
    private String year;
    private Picture images;
    private String alt;
    private String id;
    private List<String> aka;
    private int ratings_count;
    private int wish_count;
    private int collect_count;
    private String summary;

    public int getWish_count() {
        return wish_count;
    }

    public void setWish_count(int wish_count) {
        this.wish_count = wish_count;
    }

    public int getCollect_count() {
        return collect_count;
    }

    public void setCollect_count(int collect_count) {
        this.collect_count = collect_count;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    private List<String> countries;

    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }

    private SparseArray<Object> extras;

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public Subject withRating(Rating rating) {
        this.rating = rating;
        return this;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public Subject withGenres(List<String> genres) {
        this.genres = genres;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Subject withTitle(String title) {
        this.title = title;
        return this;
    }

    public List<Cast> getCasts() {
        return casts;
    }

    public void setCasts(List<Cast> casts) {
        this.casts = casts;
    }

    public Subject withCasts(List<Cast> casts) {
        this.casts = casts;
        return this;
    }

    public int getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(int collectCount) {
        this.collectCount = collectCount;
    }

    public Subject withCollectCount(int collectCount) {
        this.collectCount = collectCount;
        return this;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public Subject withOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
        return this;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public Subject withSubtype(String subtype) {
        this.subtype = subtype;
        return this;
    }

    public List<Director> getDirectors() {
        return directors;
    }

    public void setDirectors(List<Director> directors) {
        this.directors = directors;
    }

    public Subject withDirectors(List<Director> directors) {
        this.directors = directors;
        return this;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Subject withYear(String year) {
        this.year = year;
        return this;
    }

    public Picture getImages() {
        return images;
    }

    public void setImages(Picture images) {
        this.images = images;
    }

    public Subject withImages(Picture images) {
        this.images = images;
        return this;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public Subject withAlt(String alt) {
        this.alt = alt;
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Subject withId(String id) {
        this.id = id;
        return this;
    }

    public List<String> getAka() {
        return aka;
    }

    public void setAka(List<String> aka) {
        this.aka = aka;
    }

    public int getRatings_count() {
        return ratings_count;
    }

    public void setRatings_count(int ratings_count) {
        this.ratings_count = ratings_count;
    }

    public Subject putExtra(int key, Object value) {
        if (extras == null) {
            extras = new SparseArray<>(1);
        }
        extras.append(key, value);
        return this;
    }

    public Object getExtra(int key) {
        if (extras == null) {
            extras = new SparseArray<>(1);
        }
        return extras.get(key);
    }

}
