package org.yeewoe.mopassion.app.common.view.widget;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.widget.Toast;

/**
 * 公共Toast
 * Created by wyw on 2016/3/11.
 */
public class MopaToast extends Toast {
    /**
     * Construct an empty Toast object.  You must call {@link #setView} before you
     * can call {@link #show}.
     *
     * @param context The context to use.  Usually your {@link Application}
     *                or {@link Activity} object.
     */
    public MopaToast(Context context) {
        super(context);
    }
}
