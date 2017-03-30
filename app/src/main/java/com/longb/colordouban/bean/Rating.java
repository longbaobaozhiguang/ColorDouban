
package com.longb.colordouban.bean;


public class Rating {

    private int max;
    private float average;
    private String stars;
    private int min;

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public Rating withMax(int max) {
        this.max = max;
        return this;
    }

    public float getAverage() {
        return average;
    }

    public void setAverage(float average) {
        this.average = average;
    }

    public Rating withAverage(float average) {
        this.average = average;
        return this;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public Rating withStars(String stars) {
        this.stars = stars;
        return this;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public Rating withMin(int min) {
        this.min = min;
        return this;
    }

}
