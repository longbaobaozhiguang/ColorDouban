package com.longb.colordouban.utils;

import com.longb.colordouban.bean.City;

import java.util.Comparator;

public class CityComparator implements Comparator<City> {

    @Override
    public int compare(City lhs, City rhs) {
        if (lhs == null || rhs == null) {
            return 0;
        }
        return lhs.getPy().charAt(0) - rhs.getPy().charAt(0);
    }
}
