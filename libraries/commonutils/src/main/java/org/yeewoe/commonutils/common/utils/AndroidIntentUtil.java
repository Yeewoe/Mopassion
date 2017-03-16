package org.yeewoe.commonutils.common.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Intents;
import android.provider.MediaStore;
import android.provider.Settings;

import java.io.File;

public class AndroidIntentUtil {

    public static final String TAG = "AndroidIntentUtils";

    /**
     * 拨打电话
     *
     * @param context
     * @param phonenum
     */
    public static void call(final Context context, final String phonenum) {
        if (phonenum.contains("-")) {
            phonenum.replace("-", "");
        }
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phonenum));
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void call(final Context context, final String phonenum, final CallPhoneCallback callback) {

        if (phonenum.contains("-")) {
            phonenum.replace("-", "");
        }

        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phonenum));
        try {
            context.startActivity(intent);
            callback.onCallSuccess(phonenum);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }

    }

    public interface CallPhoneCallback {
        void onCallSuccess(String phoneNumber);
    }

    /**
     * 发短信
     *
     * @param context
     * @param phoneNumber
     */
    public static void message(Context context, String phoneNumber) {
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
        smsIntent.addCategory(Intent.CATEGORY_DEFAULT);
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.setData(Uri.parse("sms:" + phoneNumber));
        try {
            context.startActivity(smsIntent);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 调用系统浏览器
     *
     * @param context
     * @param url
     */
    public static void openBrowser(Context context, String url) {
        Intent viewIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(url));
        try {
            context.startActivity(viewIntent);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 新建联系人，并传入指定电话号码
     *
     * @param context
     * @param phoneNumber
     */
    public static void createContact(Context context, String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_INSERT,
                Uri.withAppendedPath(Uri.parse("content://com.android.contacts"), "contacts"));

        intent.setType("vnd.android.cursor.dir/person");
        intent.setType("vnd.android.cursor.dir/contact");
        intent.setType("vnd.android.cursor.dir/raw_contact");
        intent.putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, "Mobile");
        intent.putExtra(ContactsContract.Intents.Insert.PHONE, phoneNumber);
        context.startActivity(intent);
    }

    /**
     * 添加到现有联系人
     */
    public static void updateContact(Context context, String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_INSERT_OR_EDIT);
        intent.setType("vnd.android.cursor.item/person");
        intent.setType("vnd.android.cursor.item/contact");
        intent.setType("vnd.android.cursor.item/raw_contact");
        //    intent.putExtra(android.provider.ContactsContract.Intents.Insert.NAME, name);
        intent.putExtra(android.provider.ContactsContract.Intents.Insert.PHONE, phoneNumber);
        intent.putExtra(android.provider.ContactsContract.Intents.Insert.PHONE_TYPE, 3);
        context.startActivity(intent);
    }

    /**
     * 通过打开邮件客户端列表，发送邮件
     *
     * @param context
     * @param emailAddress
     */
    public static void sendEmail(Context context, String emailAddress, String title) {
        Uri uri = Uri.parse("mailto:" + emailAddress);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        try {
            context.startActivity(Intent.createChooser(intent, title));
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }

		/* */
    }

    /**
     * 调用相册
     *
     * @param context
     * @param actionCode
     */
    public static void dispathGalleryIntent(Context context, int actionCode) throws Exception {
        Intent galleryIntent = new Intent();
//        if (Build.VERSION.SDK_INT <19) {
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
//        }else {
//            galleryIntent.setAction(Intent.ACTION_OPEN_DOCUMENT);
//        }
        galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
        galleryIntent.setType("image/*");
        galleryIntent.putExtra("return-data", true);
        ((Activity) context).startActivityForResult(galleryIntent, actionCode);
    }

    /**
     * 调用拍照
     *
     * @param context
     * @param actionCode
     * @return 图片的file对象
     */
    public static void dispatchCameraIntent(Activity context, int actionCode) throws Exception {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        context.startActivityForResult(takePictureIntent, actionCode);
    }

    /**
     * 调用拍照
     *
     * @param context
     * @param actionCode
     * @return 图片的file对象
     */
    public static File dispatchCameraIntent(Activity context, int actionCode, File pic) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File f;
        f = pic;
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        takePictureIntent.putExtra("return-data", true);
        context.startActivityForResult(takePictureIntent, actionCode);
        return f;
    }

    /**
     * 跳往GPS设置页面，失败跳往设置页
     *
     * @param context
     * @param isSettings 失败是否跳往设置页
     */
    public static void openGps(Context context, boolean isSettings) {
        try {
            context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        } catch (Exception e) {
            if (isSettings) {
                try {
                    context.startActivity(new Intent(Settings.ACTION_SETTINGS));
                } catch (Exception e2) {
                }
            }
        }
    }

    /**
     * 跳往Wi-fi设置页面，失败跳往设置页
     *
     * @param context
     * @param isSettings 失败是否跳往设置页
     */
    public static void openWifi(Context context, boolean isSettings) {
        try {
            context.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
        } catch (Exception e) {
            if (isSettings) {
                try {
                    context.startActivity(new Intent(Settings.ACTION_SETTINGS));
                } catch (Exception e2) {
                }
            }
        }
    }

}
