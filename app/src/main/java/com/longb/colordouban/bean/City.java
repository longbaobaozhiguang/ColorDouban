package com.longb.colordouban.bean;

import android.content.ContentValues;
import android.database.Cursor;

import rx.functions.Func1;

@com.longb.mapper.CursorMapper
public class City {

    public static final byte TYPE_LETTER = 1;
    public static final byte TYPE_CITY = 0;
    int id;
    int parent;
    String name;
    String py;
    String pys;
    int type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPy() {
        return py;
    }

    public void setPy(String py) {
        this.py = py;
    }

    public String getPys() {
        return pys;
    }

    public void setPys(String pys) {
        this.pys = pys;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static Func1<Cursor, City> getMapper() {
        return CityMapper.CityMapper;
    }

    public static ContentValues map2ContentValues(City city) {
        return CityMapper.map2ContentValues(city);
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", parent=" + parent +
                ", name='" + name + '\'' +
                ", py='" + py + '\'' +
                ", pys='" + pys + '\'' +
                ", type=" + type +
                '}';
    }
}
