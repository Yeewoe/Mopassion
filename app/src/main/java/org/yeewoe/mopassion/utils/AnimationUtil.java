package org.yeewoe.mopassion.utils;

import android.app.Activity;

import org.yeewoe.mopassion.R;

/**
 * 动画帮助类
 * Created by wyw on 2016/8/17.
 */
public class AnimationUtil {
    public static void scaleIn(Activity activity) {
        if (activity != null) {
            activity.overridePendingTransition(R.anim.scale_enter, R.anim.scale_enter_nochange);
        }
    }

    public static void scaleOut(Activity activity) {
        if (activity != null) {
            activity.overridePendingTransition(R.anim.scale_enter_nochange, R.anim.scale_exit);
        }
    }
}
