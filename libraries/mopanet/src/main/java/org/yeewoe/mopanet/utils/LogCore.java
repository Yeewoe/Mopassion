package org.yeewoe.mopanet.utils;

import org.yeewoe.commonutils.android.log.MopaLog;

/**
 * Created by wyw on 2016/3/15.
 */
public class LogCore {
    private static final String LOGCORE_TAG = "mopanet";
    private static MopaLog mopaLog;

    static {
        mopaLog = new MopaLog(LOGCORE_TAG);
    }

    public static void i(String msg) {
        mopaLog.i(msg);
    }

    public static void wtf(String msg) {
        mopaLog.wtf(msg);
    }

    public static void d(String msg) {
        mopaLog.d(msg);
    }

    public static void w(String msg) {
        mopaLog.w(msg);
    }

    public static void e(String msg) {
        mopaLog.e(msg);
    }

    public static void e(Throwable e, String msg) {
        mopaLog.e(e, msg);
    }

    public static void json(String json) {
        mopaLog.json(json);
    }
}
