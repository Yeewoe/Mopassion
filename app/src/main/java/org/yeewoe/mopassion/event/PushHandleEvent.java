package org.yeewoe.mopassion.event;

/**
 * 监听推送事件
 * Created by wyw on 2016/4/28.
 */
public class PushHandleEvent<T> {
    public T data;

    public PushHandleEvent(T data) {
        this.data = data;
    }

    @Override public String toString() {
        return "PushHandleEvent{" +
                "data=" + data +
                '}';
    }
}
