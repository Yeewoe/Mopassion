package org.yeewoe.mopassion.event;

import org.yeewoe.commonutils.common.assist.Networks;

/**
 * 网络切换事件
 * Created by wyw on 2016/3/5.
 */
public class NetChangeEvent {
    /**
     * 网络是否可用
     */
    public boolean isNetAvailable;

    /**
     * 网络类型
     */
    public Networks.NetWorkType netType;

    public NetChangeEvent(boolean isNetAvailable, Networks.NetWorkType netType) {
        this.isNetAvailable = isNetAvailable;
        this.netType = netType;
    }

    @Override
    public String toString() {
        return "NetChangeEvent{" +
                "isNetAvailable=" + isNetAvailable +
                ", netType=" + netType +
                '}';
    }
}
