package org.yeewoe.mopassion.app.common.view.dialog;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by wyw on 2016/3/26.
 */
public class MopaLoadingDialog extends ProgressDialog {
    public MopaLoadingDialog(Context context) {
        super(context);
    }

    public MopaLoadingDialog(Context context, int theme) {
        super(context, theme);
    }
}
