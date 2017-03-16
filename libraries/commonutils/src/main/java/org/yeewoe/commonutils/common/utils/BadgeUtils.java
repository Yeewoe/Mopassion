package org.yeewoe.commonutils.common.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import org.yeewoe.commonutils.android.log.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 应用启动图标未读消息数显示 工具类 (效果如：QQ、微信、未读短信 等应用图标)<br/>
 * 依赖于第三方手机厂商(如：小米、三星)的Launcher定制、原生系统不支持该特性<br/>
 * 该工具类 支持的设备有 小米、三星、索尼
 * <p/>
 * Created by wyw on 2016/2/26.
 */
public class BadgeUtils {
    /**
     * 重置Badge
     *
     * @param context context
     */
    public static void resetBadgeCount(Context context) {
        setBadgeCount(context, 0);
    }

    /**
     * 设置Badge 目前支持Launcher:
     * <p/>
     * MIUI
     * Sony
     * Samsung
     * LG
     * HTC
     * Nova
     *
     * @param context context
     * @param count   count
     */
    public static void setBadgeCount(Context context, int count) {
        try {
            if (count <= 0) {
                count = 0;
            } else {
                count = Math.max(0, Math.min(count, 99));
            }
            if (Build.MANUFACTURER.equalsIgnoreCase("xiaomi")) {
                setBadgeOfMIUI(context, count);
            } else if (Build.MANUFACTURER.equalsIgnoreCase("sony")) {
                setBadgeOfSony(context, count);
            } else if (Build.MANUFACTURER.toLowerCase().contains("samsung") ||
                    Build.MANUFACTURER.toLowerCase().contains("lg")) {
                setBadgeOfSumsung(context, count);
            } else if (Build.MANUFACTURER.toLowerCase().contains("htc")) {
                setBadgeOfHTC(context, count);
            } else if (Build.MANUFACTURER.toLowerCase().contains("vivo")) {
                setBadgeOfVivo(context, count);
            } else {
                setBadgeOfSumsung(context, count);
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置MIUI的Badge
     *
     * @param context context
     * @param count   count
     */
    private static void setBadgeOfMIUI(Context context, int count) {
        Log.d("Launcher : MIUI");
        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle("title").setContentText("text").setSmallIcon(context.getApplicationInfo().icon);
        Notification notification = builder.build();
        try {
            Field field = notification.getClass().getDeclaredField("extraNotification");
            Object extraNotification = field.get(notification);
            Method method = extraNotification.getClass().getDeclaredMethod("setMessageCount", int.class);
            method.invoke(extraNotification, count);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mNotificationManager.notify(0, notification);
    }

    /**
     * 设置索尼的Badge
     * <p/>
     * 需添加权限：<uses-permission android:name="com.sonyericsson.home.permission.BROADCAST_BADGE" />
     *
     * @param context context
     * @param count   count
     */
    private static void setBadgeOfSony(Context context, int count) {
        String launcherClassName = AppUtil.getLauncherClassName(context);
        if (launcherClassName == null) {
            return;
        }
        boolean isShow = true;
        if (count == 0) {
            isShow = false;
        }
        Intent localIntent = new Intent();
        localIntent.setAction("com.sonyericsson.home.action.UPDATE_BADGE");
        localIntent.putExtra("com.sonyericsson.home.intent.extra.badge.SHOW_MESSAGE", isShow);//是否显示
        localIntent.putExtra("com.sonyericsson.home.intent.extra.badge.ACTIVITY_NAME", launcherClassName);//启动页
        localIntent.putExtra("com.sonyericsson.home.intent.extra.badge.MESSAGE", String.valueOf(count));//数字
        localIntent.putExtra("com.sonyericsson.home.intent.extra.badge.PACKAGE_NAME", context.getPackageName());//包名
        context.sendBroadcast(localIntent);
    }

    /**
     * 设置三星的Badge\设置LG的Badge
     *
     * @param context context
     * @param count   count
     */
    private static void setBadgeOfSumsung(Context context, int count) {
        // 获取你当前的应用
        String launcherClassName = AppUtil.getLauncherClassName(context);
        if (launcherClassName == null) {
            return;
        }
        Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
        intent.putExtra("badge_count", count);
        intent.putExtra("badge_count_package_name", context.getPackageName());
        intent.putExtra("badge_count_class_name", launcherClassName);
        context.sendBroadcast(intent);
    }

    /**
     * 设置Vivo的Badge
     *
     * @param context context
     * @param count   count
     */
    private static void setBadgeOfVivo(Context context, int count) {
        // 获取你当前的应用
        String launcherClassName = AppUtil.getLauncherClassName(context);
        if (launcherClassName == null) {
            return;
        }
        Intent intent = new Intent("launcher.action.CHANGE_APPLICATION_NOTIFICATION_NUM");
        intent.putExtra("notificationNum", count);
        intent.putExtra("packageName", context.getPackageName());
        intent.putExtra("className", launcherClassName);
        context.sendBroadcast(intent);
    }

    /**
     * 设置HTC的Badge
     *
     * @param context context
     * @param count   count
     */
    private static void setBadgeOfHTC(Context context, int count) {
        Intent intentNotification = new Intent("com.htc.launcher.action.SET_NOTIFICATION");
        ComponentName localComponentName = new ComponentName(context.getPackageName(),
                AppUtil.getLauncherClassName(context));
        intentNotification.putExtra("com.htc.launcher.extra.COMPONENT", localComponentName.flattenToShortString());
        intentNotification.putExtra("com.htc.launcher.extra.COUNT", count);
        context.sendBroadcast(intentNotification);

        Intent intentShortcut = new Intent("com.htc.launcher.action.UPDATE_SHORTCUT");
        intentShortcut.putExtra("packagename", context.getPackageName());
        intentShortcut.putExtra("count", count);
        context.sendBroadcast(intentShortcut);
    }

}
