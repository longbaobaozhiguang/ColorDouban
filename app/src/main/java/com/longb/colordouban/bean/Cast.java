
package com.longb.colordouban.bean;


import java.util.List;

public class Cast {

    private String alt;
    private Picture avatars;
    private String name;
    private String id;
    private String name_en;
    private String gender;
    private String born_place;
    private List<Work> works;

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public Cast withAlt(String alt) {
        this.alt = alt;
        return this;
    }

    public Picture getAvatars() {
        return avatars;
    }

    public void setAvatars(Picture avatars) {
        this.avatars = avatars;
    }

    public Cast withAvatars(Picture avatars) {
        this.avatars = avatars;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Cast withName(String name) {
        this.name = name;
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Cast withId(String id) {
        this.id = id;
        return this;
    }

    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBorn_place() {
        return born_place;
    }

    public void setBorn_place(String born_place) {
        this.born_place = born_place;
    }

    public List<Work> getWorks() {
        return works;
    }

    public void setWorks(List<Work> works) {
        this.works = works;
    }

    @Override
    public String toString() {
        return "Cast{" +
                "alt='" + alt + '\'' +
                ", avatars=" + avatars +
                ", name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
