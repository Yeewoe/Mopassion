package org.yeewoe.commonutils.common.utils;

import java.io.UnsupportedEncodingException;

/**
 * 字符串帮助类
 * Created by wyw on 2016/4/8.
 */
public class StringUtil {
    public static String decode(byte[] bytes) {
        try {
            return new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return new String(bytes);
        }
    }
}
