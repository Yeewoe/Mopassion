package org.yeewoe.mopassion.event;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据事件
 * Created by wyw on 2016/3/29.
 */
public class KeyValueChangeEvent {
    Map<String, Object> map;

    public KeyValueChangeEvent() {
        map = new HashMap<>();
    }

    public void put(@NonNull String key, Object value) {
        map.put(key, value);
    }

    public Object get(@NonNull String key) {
        return map.get(key);
    }

    public boolean contains(@NonNull String key) {
        return map.containsKey(key);
    }

    @Override
    public String toString() {
        return "KeyValueChangeEvent{" +
                "map=" + map +
                '}';
    }
}
