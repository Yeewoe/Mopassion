package org.yeewoe.mopassion.utils;

import org.yeewoe.mopassion.MopaApplication;
import org.yeewoe.mopassion.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 时间工具类
 * Created by wyw on 2016/3/29.
 */
public class TimeUtil {

    public static final String GMT_0 = "GMT+0"; // 零时区
    public static final String GMT_8 = "GMT+8"; // 东八区

    public static final long SECOND = 1000;
    public static final long MINUTE = 60 * SECOND;
    public static final long HOUR = 60 * MINUTE;
    public static final long DAY = 24 * HOUR;
    public static final long DAY_HALF = DAY / 2;

    public static String minutePattern = "HH:mm";
    public static String weekPattern = "EEEE";
    public static String pattern2 = "MM-dd";
    public static String pattern3 = "EEEE HH:mm";
    public static String pattern4 = "MM-dd HH:mm";
    public static String pattern5 = "yyyy-MM-dd";
    public static String pattern6 = "yyyy-MM-dd HH:mm:ss";
    public static String pattern_voice_record = "mm:ss";
    public static String pattern_pic_file = "yyyyMMdd_HHmmss";
    public static String pattern_voice_file = "yyyyMMdd_HHmmss";
    public static String pattern_crash_file = "yyyy-MM-dd_HH_mm";

    /**
     * 获取东八区时间Calendar
     */
    public static Calendar getCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(getTimeZone()); // 东八区
        return calendar;
    }

    /**
     * 获取东八区Zone
     */
    public static TimeZone getTimeZone() {
        return TimeZone.getTimeZone(GMT_8);
    }

    /**
     * 获取中国Locale
     */
    public static Locale getLocale() {
        return Locale.CHINA;
    }

    /**
     * 转换时间
     */
    public static String parseTime(long timeMillis, String pattern) {
        return parseTime(timeMillis, pattern, null);
    }

    /**
     * 转换时间
     */
    public static String parseTime(long timeMillis, String pattern, TimeZone timeZone) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, getLocale());
        if (timeZone != null) {
            sdf.setTimeZone(timeZone);
        } else {
            sdf.setTimeZone(getTimeZone());
        }
        return sdf.format(timeMillis);
    }

    /**
     * 消息列表时间规则
     */
    public static String parseItemListTime(long timemillis, boolean isList) {
        Calendar mCurCalendar = Calendar.getInstance();
        Calendar mParCalendar = Calendar.getInstance();
        mParCalendar.setTimeInMillis(timemillis);
        int curDay = mCurCalendar.get(Calendar.DAY_OF_YEAR);
        int parDay = mParCalendar.get(Calendar.DAY_OF_YEAR);
        int curYear = mCurCalendar.get(Calendar.YEAR);
        int parYear = mParCalendar.get(Calendar.YEAR);
        int diffDays = 0;
        if (curYear == parYear) {
            diffDays = curDay - parDay;
            if (diffDays == 0) {
                //今天内 显示 XX：XX
                return parseTime(timemillis, minutePattern);
            } else if (diffDays == 1) {
                //昨天显示昨天
                return MopaApplication.getInstance().getString(R.string.yesterday) + " " + (isList ? "" : parseTime(timemillis, minutePattern));
//            } else if (diffDays >= 2 && diffDays <= 7) {
//                //最近第三天到第七天 显示星期几
//                return parseTime(timemillis, weekPattern) + " " + (isList ? "" : parseTime(timemillis, minutePattern));
//            } else if (diffDays >= 8) {
//                //最近第八天显示 XX-XX
//                return parseTime(timemillis, pattern2) + " " + (isList ? "" : parseTime(timemillis, minutePattern));
            } else {
                return parseTime(timemillis, pattern2) + " " + (isList ? "" : parseTime(timemillis, minutePattern));
            }
        } else {
            //如果年份不一样，则算出差值
            diffDays = 365 - parDay + curDay;
            //今年以前显示xx-xx-xx
            return parseTime(timemillis, pattern2) + " "/*+(isList?"":parseTime(timemillis,minutePattern))*/;
        }
    }

    /**
     * 打印时间日志
     *
     * @param time
     * @return
     */
    public static String logTime(long time) {
        return parseTime(time, pattern6, getTimeZone());
    }

    /**
     * 录音时间
     */
    public static String parseVoiceRecord(long time) {
        return parseTime(time, pattern_voice_record);
    }

    /**
     * 转换服务器下发的时间
     */
    public static long parseServerTime(long time) {
        return time * 1000;
    }

    /**
     * 逆转换服务器下发的时间
     */
    public static long reparseServerTime(long time) {
        return time / 1000;
    }
}
