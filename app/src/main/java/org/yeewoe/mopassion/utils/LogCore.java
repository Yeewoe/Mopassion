package org.yeewoe.mopassion.utils;

import android.util.Log;

import org.yeewoe.commonutils.android.log.MopaLog;
import org.yeewoe.mopassion.MopaApplication;
import org.yeewoe.mopassion.constants.AppConstants;

/**
 * 日志核心类
 * Created by wyw on 2016/3/4.
 */
public class LogCore {

    private static final String LOGCORE_TAG = "wyw";
    private static final String LOGCORE_TEST = "wyw_interface";
    private static final String LOGCORE_WTF = "wyw_wtf";
    private static MopaLog mopaLog;
    private static MopaLog testLog;

    static {
        mopaLog = new MopaLog(LOGCORE_TAG);
        mopaLog.outputToFile(true, AppConstants.LOG_FILE_PATH, MopaApplication.getInstance().getPackageName());
        testLog = new MopaLog(LOGCORE_TEST);
        testLog.outputToFile(true, AppConstants.LOG_FILE_PATH, MopaApplication.getInstance().getPackageName());
    }

    public static void i(String msg) {
        mopaLog.i(msg);
    }

    /**
     * 用于简短日志
     */
    public static void wtf(String msg) {
        Log.i(LOGCORE_WTF, msg);
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

    public static void interfaceI(String s) {
        testLog.i(s);
    }

    public static void interfaceI(String s, Throwable e) {
        testLog.e(e, s);
    }
}
