package org.yeewoe.commonutils.common.test;

import android.util.Log;

/**
 * 测试帮助类
 * <p/>
 * Created by wyw on 2015/4/24.
 */
public class TestTimeUtils {
    private static String TAG = TestConstants.COMMON_TAG;

    private static TestTimeUtils mInstance;
    private long mLastTime;
    private boolean debug;

    private TestTimeUtils(boolean debug) {
        this.debug = debug;
    }

    public static synchronized TestTimeUtils getInstance(boolean debug) {
        if (mInstance == null) {
            mInstance = new TestTimeUtils(debug);
        }
        return mInstance;
    }

    public synchronized void clear() {
        mLastTime = 0;
    }

    /**
     * 记录时间点
     */
    public synchronized void tag() {
        if (debug) {
            mLastTime = System.currentTimeMillis();
        }
    }

    /**
     * 获取与记录点的时间差
     */
    public synchronized long getTik() {
        if (debug) {
            long current = System.currentTimeMillis();
            long result = current - mLastTime;
            mLastTime = current;
            return result;
        }
        return 0;
    }

    /**
     * 打印与记录点的时间差，并重新记录时间点
     */
    public synchronized void tik() {
        if (debug) {
            long tik = getTik();
            Log.i(TAG, "tik time millis: " + tik);
        }
    }

    /**
     * 打印与记录点的时间差，并重新记录时间点
     *
     * @param tag 日志Tag
     */
    public synchronized void tik(String tag) {
        if (debug) {
            long current = System.currentTimeMillis();
            Log.i(tag, "tik time millis: " + (current - mLastTime));
            mLastTime = current;
        }
    }

    /**
     * 打印与记录点的时间差，并重新记录时间点
     *
     * @param tag  日志Tag
     * @param prev 日志前缀
     */
    public synchronized void tik(String tag, String prev) {
        if (debug) {
            long current = System.currentTimeMillis();
            Log.i(tag, "tik: [" + prev + "]tik time millis: " + (current - mLastTime));
            mLastTime = current;
        }
    }
}
