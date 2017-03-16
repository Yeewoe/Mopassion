package org.yeewoe.mopassion.app.common.presenter;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import org.greenrobot.eventbus.EventBus;
import org.yeewoe.commonutils.common.io.FileUtils;
import org.yeewoe.commonutils.common.utils.AndroidIntentUtil;
import org.yeewoe.commonutils.common.utils.BitmapUtil;
import org.yeewoe.commonutils.common.utils.SdCardUtil;
import org.yeewoe.mopassion.R;
import org.yeewoe.mopassion.app.common.view.interfaces.IActivityView;
import org.yeewoe.mopassion.app.location.manager.LocationPointInfo;
import org.yeewoe.mopassion.app.location.manager.MopaLocationClient;
import org.yeewoe.mopassion.app.location.model.LocationService;
import org.yeewoe.mopassion.event.LocationChangeEvent;
import org.yeewoe.mopassion.utils.MopaFileUtil;
import org.yeewoe.mopassion.utils.LogCore;
import org.yeewoe.mopassion.utils.MopaBitmapUtil;

import java.io.File;
import java.io.IOException;

/**
 * 公共抽象基类Presenter <br />
 * 目前提供了：
 * 1. 销毁逻辑
 * 2. 定位逻辑
 * Created by wyw on 2016/3/4.
 */
public abstract class MopaPresenter implements MopaLocationClient.NotifyLocationListener {

    private final static int REQUEST_OPEN_CAMERA = 0x707;
    private final static int REQUEST_OPEN_GALLERY = 0x708;
    protected IActivityView activityView;

    public MopaPresenter() {

    }

    public MopaPresenter(IActivityView view) {
        this.activityView = view;
    }

    public void requestLocation() {
        // TODO 权限处理
        MopaLocationClient.getInstance().requestLocation(true, this);
    }

    public void stopLocation() {
        MopaLocationClient.getInstance().stopClient();
    }


    public abstract void onDestroy();

    /**
     * 不要继承此方法，请继承{@link #onLocationChange(LocationPointInfo)}
     */
    @Override public void notify(LocationPointInfo locationPointInfo) {
        // 统一定位回调
        if (locationPointInfo != null && locationPointInfo.isSuccess) {
            // 发送位置变化事件
            EventBus.getDefault().post(new LocationChangeEvent(locationPointInfo));
            // 位置上报
            new LocationService().report(locationPointInfo.addrStr, locationPointInfo.longitude, locationPointInfo.latitude);
        }

        onLocationChange(locationPointInfo);
    }

    protected void onLocationChange(LocationPointInfo locationPointInfo) {

    }

    /**
     * 调用系统拍照，需要在{@link Activity#onActivityResult(int, int, Intent)}调用{@link #handleActivityResult(int, int, Intent)}方法进行拦截处理，
     * 在{@link #handleCamera(File)} (File)}中可获得图片文件
     */
    public void openCamera() {
        if (SdCardUtil.getSDCardInfo().isExist) {
            try {
                AndroidIntentUtil.dispatchCameraIntent(activityView.getActivity(), REQUEST_OPEN_CAMERA);
            } catch (Exception e) {
                e.printStackTrace();
                activityView.showToast(activityView.getActivity().getString(R.string.error_camera));
            }
        } else {
            activityView.showToast(activityView.getActivity().getString(R.string.error_sdcard_exist));
        }
    }

    /**
     * 调用系统相册，需要在{@link Activity#onActivityResult(int, int, Intent)}调用{@link #handleActivityResult(int, int, Intent)}方法进行拦截处理，
     * 在{@link #handleGallery(File)}中可获得图片文件
     */
    public void openGallery() {
        if (SdCardUtil.getSDCardInfo().isExist) {
            try {
                AndroidIntentUtil.dispathGalleryIntent(activityView.getActivity(), REQUEST_OPEN_GALLERY);
            } catch (Exception e) {
                e.printStackTrace();
                activityView.showToast(activityView.getActivity().getString(R.string.error_galley));
            }
        } else {
            activityView.showToast(activityView.getActivity().getString(R.string.error_sdcard_exist));
        }
    }

    /**
     * 用于处理需要界面回调的结果，比如拍照
     *
     * @return 是否已经处理
     */
    public boolean handleActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_OPEN_CAMERA) {
                File picFile = MopaFileUtil.createCameraFile();
                if (!picFile.exists()) {
                    try {
                        if (picFile.createNewFile()) {
                            Bundle extras = data.getExtras();
                            Bitmap bitmap = (Bitmap) extras.get("data");
                            FileUtils.writeByteArrayToFile(picFile, BitmapUtil.bitmapToByte(bitmap));
                            handleCamera(picFile);
                            return true;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        activityView.showToast(activityView.getActivity().getString(R.string.error_camera));
                    }
                }
            } else if (requestCode == REQUEST_OPEN_GALLERY) {
                Uri selectedImage = data.getData();
                String path = getPath(activityView.getActivity(), selectedImage);
                if (path != null) {
                    handleGallery(new File(path));
                } else {
                    activityView.showToast(activityView.getActivity().getString(R.string.error_galley));
                }
            }
        }

        return false;
    }

    /**
     * 继承可处理拍照后获得的图片
     */
    protected void handleCamera(File pic) {
        LogCore.wtf("handleCamera: pic=" + pic + ", size=" + pic.length());
        handleCompress(MopaBitmapUtil.compress(pic));
    }

    /**
     * 继承可处理选择相册后获得的图片
     */
    protected void handleGallery(File pic) {
        LogCore.wtf("handleGallery: pic=" + pic + ", size=" + pic.length());
        handleCompress(MopaBitmapUtil.compress(pic));
    }

    /**
     * 继承可处理拍照或者选择相册后通用压缩过的图片图片
     */
    protected void handleCompress(File pic) {
        LogCore.wtf("handleCompress: pic=" + pic + ", size=" + pic.length());
    }

    private static String getPath(final Context context, final Uri uri) {

        // DocumentProvider
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

//                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
//                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }


    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }


    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }


    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public void setActivitiyView(IActivityView activityView) {
        this.activityView = activityView;
    }
}
