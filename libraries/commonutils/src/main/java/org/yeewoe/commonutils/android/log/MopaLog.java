package org.yeewoe.commonutils.android.log;

import android.text.TextUtils;
import android.util.Log;

import com.orhanobut.logger.Logger;

import org.yeewoe.commonutils.common.io.FileUtils;
import org.yeewoe.commonutils.common.utils.AndroidUtil;
import org.yeewoe.commonutils.common.utils.SdCardUtil;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 日志核心类
 * Created by wyw on 2016/3/4.
 */
public class MopaLog {

    private static final String LOG_CORE_FILE_NAME = MopaLog.class.getName();

    private static final String LOG_NAME_PATTERN = "yyyy-MM-dd";
    private static final String LOG_MSG_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final Executor EXECUTOR = Executors.newSingleThreadExecutor();

    private String tag;
    private boolean isOutputToFile;
    private String logFilePath;
    private String packageName;
    public static final int METHOD_COUNT = 8;

    public MopaLog(String tag) {
        this.tag = tag;
    }

    private void init() {
        Logger.init(this.tag)
                .methodCount(METHOD_COUNT);
    }

    public void outputToFile(boolean isOutputToFile, String path, String packageName) {
        this.isOutputToFile = isOutputToFile;
        this.logFilePath = path;
        this.packageName = packageName;
    }

    public void i(String msg) {
        init();
        Logger.i(msg);
        if (isOutputToFile) {
            printToLogFile(msg);
        }
    }

    public void wtf(String msg) {
        Log.wtf(tag, msg);
        if (isOutputToFile) {
            printToLogFile(msg);
        }
    }

    public void d(String msg) {
        Log.d(this.tag, msg);
    }

    public void w(String msg) {
        init();
        Logger.w(msg);
        if (isOutputToFile) {
            printToLogFile(msg);
        }
    }

    public void e(String msg) {
        init();
        Logger.e(msg);
        if (isOutputToFile) {
            printToLogFile(msg);
        }
    }

    public void e(Throwable e, String msg) {
        e.printStackTrace();
        if (isOutputToFile) {
            String sb = msg + "\n" +
                    Log.getStackTraceString(e);
            printToLogFile(sb);
        }
    }

    public void json(String json) {
        Logger.json(json);
        if (isOutputToFile) {
            printToLogFile(json);
        }
    }

    private void printToLogFile(final String msg) {
        if (TextUtils.isEmpty(logFilePath)) {
            throw new IllegalAccessError("logFilePath is empty or null");
        }

        EXECUTOR.execute(new Runnable() {
            @Override public void run() {
                if (SdCardUtil.getSDCardInfo().isExist) {
                    // 有sdcard卡
                    File dir = new File(logFilePath);
                    if (!dir.exists()) {
                        if (!dir.mkdir()) {
                            Log.w(tag, "log dir mkdir fail");
                            return ;
                        }
                    }

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(LOG_NAME_PATTERN, Locale.CHINA);
                    String formatName = simpleDateFormat.format(new Date());
                    File logFile = new File(dir, formatName);

                    StringBuilder sb = new StringBuilder();
                    if (!logFile.exists()) {
                        sb.append(AndroidUtil.getSystemInfo());
                    }
                    sb.append("\n\n").append(buildLogMsg(msg));
                    try {
                        FileUtils.write(logFile, sb.toString(), true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private String buildLogMsg(String msg) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(LOG_MSG_PATTERN, Locale.CHINA);
        String timeInfo = simpleDateFormat.format(new Date());
        StringBuilder threadInfo = new StringBuilder();
        try {
            Thread thread = Thread.currentThread();
            threadInfo.append("[tid=").append(thread.getId()).append(", tname=").append(thread.getName()).append("]");
        } catch (OutOfMemoryError e) {
            Log.i("OutOfMemoryError", Log.getStackTraceString(e));
        }

        StringBuilder stackTraceInfo = new StringBuilder();
        Throwable s = new Throwable();
        try {
            StackTraceElement[] stackTraceElements = s.getStackTrace();
            if (stackTraceElements != null && stackTraceElements.length > 0) {
                for (StackTraceElement ste : stackTraceElements) {
                    if (ste == null) {
                        continue;
                    }
                    String fileName = ste.getFileName();
                    String declaringClass = ste.getClassName();
                    if (fileName == null || declaringClass == null) {
                        continue;
                    }
                    if (LOG_CORE_FILE_NAME.equals(declaringClass)) {
                    } else if (declaringClass.contains(this.packageName)) {
                        stackTraceInfo.append("{").append(fileName).append(":").append(ste.getMethodName()).append(":").append(ste.getLineNumber()).append("}");
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return timeInfo + "\t" + tag + "\t" + msg + "\t" + stackTraceInfo.toString() + "\t" + threadInfo.toString();
    }

}
