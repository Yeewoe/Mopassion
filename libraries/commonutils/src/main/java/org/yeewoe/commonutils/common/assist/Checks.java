package org.yeewoe.commonutils.common.assist;

import android.app.Activity;

import java.util.Collection;
import java.util.Map;

/**
 * 辅助判断
 *
 * @author wyw
 * @date 2016-03-15
 */
public class Checks {

    /**
     * 检查Activity不为为null并且finish
     */
    public static boolean check(Activity activity) {
        return activity != null && !activity.isFinishing();
    }

    /**
     * 检查集合不为空并且size > 0
     */
    public static boolean check(Collection collection) {
        return !isEmpty(collection);
    }

    /**
     * 检查数组不为空并且size > 0
     */
    public static boolean check(Object[] os) {
        return !isEmpty(os);
    }

    /**
     * 检查Map不为空并且size > 0
     */
    public static boolean check(Map<?, ?> m) {
        return isEmpty(m);
    }

    /**
     * 检查字符串不为空并且长度 > 0
     */
    public static boolean check(CharSequence str) {
        return !isEmpty(str);
    }



    public static boolean isEmpty(CharSequence str) {
        return isNull(str) || str.toString().trim().length() == 0;
    }

    public static boolean isEmpty(Object[] os) {
        return isNull(os) || os.length == 0;
    }

    public static boolean isEmpty(Collection<?> l) {
        return isNull(l) || l.isEmpty();
    }

    public static boolean isEmpty(Map<?, ?> m) {
        return isNull(m) || m.isEmpty();
    }

    public static boolean isNull(Object o) {
        return o == null;
    }
}
