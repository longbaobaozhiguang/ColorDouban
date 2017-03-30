package com.longb.colordouban.bean.event;

import com.longb.colordouban.bean.City;

/**
 * 修改城市事件
 */
public class ChangeCityEvent {
    public City city;

    public ChangeCityEvent(City city) {
        this.city = city;
    }
}
