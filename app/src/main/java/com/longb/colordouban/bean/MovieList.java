
package com.longb.colordouban.bean;

import java.util.List;

public class MovieList {

    private int count;
    private int start;
    private int total;
    private List<Subject> subjects = null;
    private String title;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public MovieList withCount(int count) {
        this.count = count;
        return this;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public MovieList withStart(int start) {
        this.start = start;
        return this;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public MovieList withTotal(int total) {
        this.total = total;
        return this;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public MovieList withSubjects(List<Subject> subjects) {
        this.subjects = subjects;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MovieList withTitle(String title) {
        this.title = title;
        return this;
    }

}
