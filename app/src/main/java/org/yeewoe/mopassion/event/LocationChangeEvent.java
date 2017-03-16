package org.yeewoe.mopassion.event;

import org.yeewoe.mopassion.app.location.manager.LocationPointInfo;

/**
 * 定位位置变化事件
 * Created by wyw on 2016/4/9.
 */
public class LocationChangeEvent {
    LocationPointInfo location;

    public LocationChangeEvent(LocationPointInfo location) {
        this.location = location;
    }

    @Override public String toString() {
        return "LocationChangeEvent{" +
                "location=" + location +
                '}';
    }
}
