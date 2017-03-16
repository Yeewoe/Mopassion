package org.yeewoe.mopassion.event;

/**
 * 进度变化事件
 * Created by wyw on 2016/4/24.
 */
public class AsyncDataProgressChangeEvent {
    public long sid;
    public double percent;

    public AsyncDataProgressChangeEvent(long sid, double percent) {
        this.sid = sid;
        this.percent = percent;
    }

    @Override public String toString() {
        return "AsyncDataProgressChangeEvent{" +
                "sid=" + sid +
                ", percent=" + percent +
                '}';
    }
}
