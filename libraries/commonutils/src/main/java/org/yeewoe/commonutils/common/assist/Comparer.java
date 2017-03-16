package org.yeewoe.commonutils.common.assist;

/**
 * 比较器
 * Created by wyw on 2016/3/15.
 */
public class Comparer {
    public static boolean equals(CharSequence a, CharSequence b) {
        boolean emptyA = Checks.isEmpty(a);
        boolean emptB = Checks.isEmpty(b);
        return emptyA && emptB || emptyA == emptB && a.equals(b);
    }
}
