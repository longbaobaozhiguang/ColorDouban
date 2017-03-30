
package com.longb.colordouban.bean;


public class Director {

    private String alt;
    private Picture avatars;
    private String name;
    private String id;

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public Director withAlt(String alt) {
        this.alt = alt;
        return this;
    }

    public Picture getAvatars() {
        return avatars;
    }

    public void setAvatars(Picture avatars) {
        this.avatars = avatars;
    }

    public Director withAvatars(Picture avatars) {
        this.avatars = avatars;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Director withName(String name) {
        this.name = name;
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Director withId(String id) {
        this.id = id;
        return this;
    }

}
