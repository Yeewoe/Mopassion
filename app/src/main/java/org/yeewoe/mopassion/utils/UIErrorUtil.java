package org.yeewoe.mopassion.utils;

import android.content.Context;

import org.yeewoe.mopassion.R;

/**
 * UI错误处理工具
 * Created by wyw on 2016/3/25.
 */
public class UIErrorUtil {

    public static String getErrorInfo(Context context, int errorCode) {
        return context.getString(R.string.error_default, errorCode);
    }
}
