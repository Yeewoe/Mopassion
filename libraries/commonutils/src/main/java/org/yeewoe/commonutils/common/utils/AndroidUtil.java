package org.yeewoe.commonutils.common.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.location.LocationManager;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.SystemClock;
import android.provider.ContactsContract;

import org.yeewoe.commonutils.android.log.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 手机信息 & MAC地址 & 开机时间
 *
 * @author wyw
 * @date 2016-03-15
 */
public class AndroidUtil {
    private static final String TAG = AndroidUtil.class.getSimpleName();

    /**
     * 获取 MAC 地址
     * <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
     */
    public static String getMacAddress(Context context) {
        //wifi mac地址
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        String mac = info.getMacAddress();
        if (Log.isPrint) {
            Log.i(TAG, " MAC：" + mac);
        }
        return mac;
    }

    /**
     * 获取 开机时间
     */
    public static String getBootTimeString() {
        long ut = SystemClock.elapsedRealtime() / 1000;
        int h = (int) ((ut / 3600));
        int m = (int) ((ut / 60) % 60);
        if (Log.isPrint) {
            Log.i(TAG, h + ":" + m);
        }
        return h + ":" + m;
    }

    public static String getSystemInfo() {
        return printSystemInfo(false);
    }

    public static String printSystemInfo(boolean isPrint) {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = dateFormat.format(date);
        StringBuilder sb = new StringBuilder();
        sb.append("_______  系统信息  ").append(time).append(" ______________");
        sb.append("\nID                 :").append(Build.ID);
        sb.append("\nBRAND              :").append(Build.BRAND);
        sb.append("\nMODEL              :").append(Build.MODEL);
        sb.append("\nRELEASE            :").append(Build.VERSION.RELEASE);
        sb.append("\nSDK                :").append(Build.VERSION.SDK);

        sb.append("\n_______ OTHER _______");
        sb.append("\nBOARD              :").append(Build.BOARD);
        sb.append("\nPRODUCT            :").append(Build.PRODUCT);
        sb.append("\nDEVICE             :").append(Build.DEVICE);
        sb.append("\nFINGERPRINT        :").append(Build.FINGERPRINT);
        sb.append("\nHOST               :").append(Build.HOST);
        sb.append("\nTAGS               :").append(Build.TAGS);
        sb.append("\nTYPE               :").append(Build.TYPE);
        sb.append("\nTIME               :").append(Build.TIME);
        sb.append("\nINCREMENTAL        :").append(Build.VERSION.INCREMENTAL);

        sb.append("\n_______ CUPCAKE-3 _______");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            sb.append("\nDISPLAY            :").append(Build.DISPLAY);
        }

        sb.append("\n_______ DONUT-4 _______");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT) {
            sb.append("\nSDK_INT            :").append(Build.VERSION.SDK_INT);
            sb.append("\nMANUFACTURER       :").append(Build.MANUFACTURER);
            sb.append("\nBOOTLOADER         :").append(Build.BOOTLOADER);
            sb.append("\nCPU_ABI            :").append(Build.CPU_ABI);
            sb.append("\nCPU_ABI2           :").append(Build.CPU_ABI2);
            sb.append("\nHARDWARE           :").append(Build.HARDWARE);
            sb.append("\nUNKNOWN            :").append(Build.UNKNOWN);
            sb.append("\nCODENAME           :").append(Build.VERSION.CODENAME);
        }

        sb.append("\n_______ GINGERBREAD-9 _______");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            sb.append("\nSERIAL             :").append(Build.SERIAL);
        }
        if (isPrint) {
            Log.i(TAG, sb.toString());
        }
        return sb.toString();
    }

    /**
     * 通过打开邮件客户端列表，发送邮件
     *
     * @param context
     * @param emailAddress
     */
    public static void sendEmail(Context context, String title, String emailAddress) {
        Uri uri = Uri.parse("mailto:" + emailAddress);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        try {
            context.startActivity(Intent.createChooser(intent, title));
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }

		/* */
    }

    /**
     * 获取不同尺寸的图片
     * drawable-ldpi (dpi=120, density=0.75)
     * drawable-mdpi (dpi=160, density=1)
     * drawable-hdpi (dpi=240, density=1.5)
     * drawable-xhdpi (dpi=320, density=2) 定义的基值
     * drawable-xxhdpi (dpi=480, density=3)
     */
    public static float getPictureDisplayMultiple(Context context) {
        Resources resources = context.getResources();
        if (resources != null) {
            float density = resources.getDisplayMetrics().density;
            int baseDensity = 2;
            if (density > baseDensity) {
                return (density - baseDensity) + 1;
            }
        }
        return 1;
    }

    public static long getAvailableHeap(){
        Runtime runtime = Runtime.getRuntime();
        return runtime.maxMemory() - runtime.totalMemory() + runtime.freeMemory();
    }
}
