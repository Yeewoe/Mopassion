package org.yeewoe.mopassion.utils;

import android.webkit.URLUtil;

/**
 * WebView工具类
 * Created by wyw on 2016/6/6.
 */
public class WebViewUtil {
    public static String convertUrl(String url) {
        if (url == null) {
            url = "";
        }
        url = url.trim();
        if (!URLUtil.isNetworkUrl(url)) {
            url = "http://" + url;
        }
        return url;
    }
}
