package org.yeewoe.mopassion.photo.fresco_lib;

import org.yeewoe.commonutils.common.io.SharePreferenceUtils;
import org.yeewoe.mopassion.MopaApplication;

import java.io.File;

/**
 * key url存储工具类
 * Created by wyw on 2016/5/31.
 */
public class KeyUrlHelper {

    private static KeyUrlHelper instance;
    private final SharePreferenceUtils sp;

    public KeyUrlHelper() {
        sp = new SharePreferenceUtils(MopaApplication.getInstance(), "key_url");
    }

    public static KeyUrlHelper getInstance() {
        if (instance == null) {
            synchronized (KeyUrlHelper.class) {
                if (instance == null) {
                    instance = new KeyUrlHelper();
                }
            }
        }
        return instance;
    }

    public String loadUrl(String key) {
        return sp.loadStringSharedPreference(key, "");
    }

    public void saveUrl(String key, String url) {
        sp.saveSharedPreferences(key, url);
    }

    public void saveUrl(String key, File pic) {
        saveUrl(key, "file://" + pic.getPath());
    }
}
