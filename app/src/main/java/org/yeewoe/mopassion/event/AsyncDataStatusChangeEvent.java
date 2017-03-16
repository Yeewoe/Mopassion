package org.yeewoe.mopassion.event;

/**
 * 异步发送状态改变时间
 * Created by wyw on 2016/4/23.
 */
public class AsyncDataStatusChangeEvent<T> {
    public T data;

    public AsyncDataStatusChangeEvent(T data) {
        this.data = data;
    }

    @Override public String toString() {
        return "AsyncDataStatusChangeEvent{" +
                "data=" + data +
                '}';
    }
}
