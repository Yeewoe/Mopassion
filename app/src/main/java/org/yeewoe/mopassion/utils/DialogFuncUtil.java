package org.yeewoe.mopassion.utils;

import android.content.Context;

import org.yeewoe.commonutils.common.utils.AndroidUtil;
import org.yeewoe.commonutils.common.utils.TxtUtils;
import org.yeewoe.mopassion.R;
import org.yeewoe.mopassion.app.common.view.dialog.MopaSelectDialog;

/**
 * 对话框功能工具
 * Created by wyw on 2016/8/23.
 */
public class DialogFuncUtil {

    /**
     * 复制对话框
     */
    public static void openCopyDialog(final Context context, final String text) {
        MopaSelectDialog dialog = new MopaSelectDialog(context, text, R.array.im_copy_operation_arrays, new MopaSelectDialog.OnClickListener() {
            @Override public void onClick(MopaSelectDialog dialog, int which) {
                switch (which) {
                    case 0:
                        // 复制
                        TxtUtils.copy(context, text);
                        break;
                }
            }
        });
        dialog.show();
    }

    /**
     * CONTAC功能对话框
     */
    public static void openContact(final Context context, final String email, final long toUid, final String nick) {
        MopaSelectDialog dialog = new MopaSelectDialog(context, "", R.array.contact_item_arrays, new MopaSelectDialog.OnClickListener() {
            @Override public void onClick(MopaSelectDialog dialog, int which) {
                switch (which) {
                    case 0:
                        // 默认Email帐号发送
                        AndroidUtil.sendEmail(context, email, context.getResources().getString(R.string.choose_mail_type));
                        break;
                    case 1:
                        // 聊天
                        IntentManager.IM.intentToChat(context, toUid, nick);
                        break;
                }
            }
        });
        dialog.show();
    }
}
