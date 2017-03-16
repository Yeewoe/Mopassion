package org.yeewoe.mopassion.utils;

import android.content.Context;

import org.yeewoe.commonutils.common.utils.AndroidIntentUtil;
import org.yeewoe.commonutils.common.utils.AndroidUtil;
import org.yeewoe.commonutils.common.utils.PatternUtil;
import org.yeewoe.commonutils.common.utils.TxtUtils;
import org.yeewoe.mopassion.R;
import org.yeewoe.mopassion.app.common.view.dialog.MopaSelectDialog;

public class LinkUtil {

    public static void openEmailDialog(final Context context, String mEmail) {
        if (mEmail.startsWith(PatternUtil.PREFIX_EMAIL)) {
            mEmail = mEmail.substring(PatternUtil.PREFIX_EMAIL.length());
        }
        final String email = mEmail;
        MopaSelectDialog dialog = new MopaSelectDialog(context, email, R.array.im_email_operation_arrays, new MopaSelectDialog.OnClickListener() {
            @Override public void onClick(MopaSelectDialog dialog, int which) {
                switch (which) {
                    case 0:
                        // 默认Email帐号发送
                        AndroidUtil.sendEmail(context, email, context.getResources().getString(R.string.choose_mail_type));
                        break;
                    case 1:
                        // 复制
                        TxtUtils.copy(context, email);
                        break;
                }
            }
        });
        dialog.show();
    }

    public static void openPhoneDialog(final Context context, String mPhone) {
        if (mPhone.startsWith(PatternUtil.PREFIX_PHONE)) {
            mPhone = mPhone.substring(PatternUtil.PREFIX_PHONE.length());
        }
        final String phone = mPhone;
        MopaSelectDialog dialog = new MopaSelectDialog(context, phone + context.getString(R.string.phone_call), R.array.im_phone_operation_arrays, new MopaSelectDialog.OnClickListener() {
            @Override public void onClick(MopaSelectDialog dialog, int which) {
                switch (which) {
                    case 0:
                        // 呼叫
                        AndroidIntentUtil.call(context, phone);
                        break;
                    case 1:
                        // 复制
                        TxtUtils.copy(context, phone);
                        break;
                    case 2:
                        // 添加到手机通讯录
                        openPhoneBookDialog(context, phone);
                        break;
                }
            }
        });
        dialog.show();
    }

    public static void openPhoneBookDialog(final Context context, String mPhone) {
        if (mPhone.startsWith(PatternUtil.PREFIX_PHONE)) {
            mPhone = mPhone.substring(PatternUtil.PREFIX_PHONE.length());
        }
        final String phone = mPhone;
        MopaSelectDialog dialog = new MopaSelectDialog(context, phone + context.getString(R.string.phone_call), R.array.im_phone_book_operation_arrays, new MopaSelectDialog.OnClickListener() {
            @Override public void onClick(MopaSelectDialog dialog, int which) {
                switch (which) {
                    case 0:
                        // 创建新联系人
                        AndroidIntentUtil.createContact(context, phone);
                        break;
                    case 1:
                        // 添加到现在联系人
                        AndroidIntentUtil.updateContact(context, phone);
                        break;
                }
            }
        });
        dialog.show();
    }
}
