package org.yeewoe.commonutils.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentTransaction;

/**
 * 异常捕捉工具
 * Created by wyw on 2016/3/22.
 */
public class CatchUtil {
    public static void startActivity(Context context, Intent intent) {
        try {
            if (context != null) {
                context.startActivity(intent);
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    public static void startActivityForResult(Activity context, Intent intent, int requestCode) {
        try {
            if (context != null) {
                context.startActivityForResult(intent, requestCode);
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }


    public static void startService(Context context, Intent intent) {
        try {
            if (context != null) {
                context.startService(intent);
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    public static void stopService(Context context, Intent intent) {
        try {
            if (context != null) {
                context.stopService(intent);
            }
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    public static void transactionCommit(FragmentTransaction transaction) {
        try {
            if (transaction != null) {
                transaction.commitAllowingStateLoss();
            }
        } catch(Exception | Error e) {
            e.printStackTrace();
        }
    }


    public static Bitmap resourcesDecode(Resources resources, int resId) {
        return null;
    }
}
