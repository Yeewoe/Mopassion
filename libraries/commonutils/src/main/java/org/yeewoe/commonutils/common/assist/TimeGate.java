package org.yeewoe.commonutils.common.assist;

import java.util.concurrent.Callable;

/**
 * 时间闸类
 * 用于控制受时间限制的行为
 * Created by wyw on 2015/4/2.
 */
public class TimeGate {
    private long gateTimeRange;
    private long last;
    private long permitCount;
    private int currentCount;

    /**
     * @param gateTimeRange 闸的时间大小，单位毫秒
     */
    public TimeGate(long gateTimeRange) {
        init(gateTimeRange);
        this.permitCount = 1;
    }

    /**
     * @param gateTimeRange 闸的时间大小，单位毫秒
     * @param permitCount   允许放行的数量，即在时间闸时间范围内，最多运行执行的任务数
     */
    public TimeGate(long gateTimeRange, int permitCount) {
        init(gateTimeRange);
        this.permitCount = permitCount;
    }

    private void init(long gateTimeRange) {
        this.gateTimeRange = gateTimeRange;
        this.last = 0;
        this.currentCount = 0;
    }

    /**
     * 设置时间闸大小
     *
     * @param gateTimeRange 闸的时间大小，单位毫秒
     */
    public void setGateTimeRange(long gateTimeRange) {
        this.gateTimeRange = gateTimeRange;
    }

    /**
     * 设置放行数量
     *
     * @param permitCount 放行数量
     */
    public void setPermitCount(long permitCount) {
        this.permitCount = permitCount;
    }

    /**
     * 时间闸重置
     */
    public void reopen() {
        this.last = 0;
        this.currentCount = 0;
    }

    /**
     * 断续进闸运行（控制某时间段内能进闸几次）
     *
     * @param successCallback 成功回调，为null不执行
     * @param failCallback    失败回调，为null不执行
     */
    public synchronized void chopGate(Callable successCallback, Callable failCallback) {
        long now = System.currentTimeMillis();
        if (now - this.last > this.gateTimeRange || now < this.last) {
            if (successCallback != null) {
                try {
                    successCallback.call();
                    this.last = now;
                    this.currentCount = 1;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (this.currentCount++ < this.permitCount) {
            if (successCallback != null) {
                try {
                    successCallback.call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            if (failCallback != null) {
                try {
                    failCallback.call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 持续进闸运行（某持续时间内不运行，才能再次进闸）
     *
     * @param successCallback 成功回调，为null不执行
     * @param failCallback    失败回调，为null不执行
     */
    public synchronized void floodGate(Callable successCallback, Callable failCallback) {
        long now = System.currentTimeMillis();
        if (now - this.last > this.gateTimeRange || now < this.last) {
            if (successCallback != null) {
                try {
                    successCallback.call();
                    this.last = now;
                    this.currentCount = 1;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            this.last = now;
            if (failCallback != null) {
                try {
                    failCallback.call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
