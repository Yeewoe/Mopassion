package org.yeewoe.mopassion.event;

import android.os.Parcelable;

/**
 * OnResult回调事件
 * Created by wyw on 2016/4/8.
 */
public class OnActivityResultReceiveEvent {
    public Parcelable parcelable;
    public OnActivityResultReceiveEvent(Parcelable parcelable) {
        this.parcelable = parcelable;
    }

    @Override public String toString() {
        return "OnActivityResultReceiveEvent{" +
                "parcelable=" + parcelable +
                '}';
    }
}
