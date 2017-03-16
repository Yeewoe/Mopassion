package org.yeewoe.mopassion.app.common.view.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

import org.yeewoe.mopassion.emotion.EmotionUtil;

/**
 * 选择对话框
 * Created by wyw on 2016/6/16.
 */
public class MopaSelectDialog {
    private AlertDialog inner;

    public MopaSelectDialog(Context context, int arrayId, OnClickListener onClickListener) {
        this(context, "", arrayId, onClickListener);
    }

    public MopaSelectDialog(Context context, int titleId, int arrayId, final OnClickListener onClickListener) {
        this(context, context.getString(titleId), arrayId, onClickListener);
    }

    public MopaSelectDialog(Context context, String title, int arrayId, final OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context).setItems(arrayId, new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
                if (onClickListener != null) {
                    onClickListener.onClick(MopaSelectDialog.this, which);
                }
            }
        });
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(EmotionUtil.parseToMsg(title, context, false));
        }
        inner = builder.create();
    }

    public void show() {
        inner.show();
    }

    public void dismiss() {
        if (inner.isShowing()) {
            inner.dismiss();
        }
    }

    public interface OnClickListener {
        void onClick(MopaSelectDialog dialog, int which);
    }
}
