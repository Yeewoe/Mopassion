package org.yeewoe.mopassion.utils;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import org.yeewoe.commonutils.common.assist.Base64;
import org.yeewoe.commonutils.common.io.FileUtils;
import org.yeewoe.commonutils.common.utils.SdCardUtil;
import org.yeewoe.mopassion.MopaApplication;
import org.yeewoe.mopassion.constants.AppConstants;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 文件帮助类
 * Created by wyw on 2016/5/4.
 */
public class MopaFileUtil {

    private static final String FORMAT_PIC = ".jpg";
    private static final String VOICE_AMR_FILE_PREFIX = "AMR_";
    private static final String UNIQUE_VOICE = "voice";

    /**
     * 创建声音缓存文件
     */
    public static File createVoiceCacheFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmssSSS", Locale.CHINA).format(new Date());
        String amrFileName = VOICE_AMR_FILE_PREFIX + timeStamp;
        //File albumF = getDiskCacheDir(MoaApplication.getInstance(), "voicebackup");
        File albumF;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            albumF = new File(AppConstants.VOICE_FILE_PATH);
        } else {
            albumF = getDiskCacheDir(MopaApplication.getInstance(), UNIQUE_VOICE);
        }
        if (!albumF.exists()) {
            albumF.mkdirs();
        }
        return new File(albumF, amrFileName);
    }

    /**
     * 创建声音缓存文件，并写入内容
     */
    public static String createVoiceCacheFile(String base64Bytes) {
        byte[] bytes = Base64.decode(base64Bytes, Base64.DEFAULT);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmssSSS", Locale.CHINA).format(new Date());
        String amrFileName = VOICE_AMR_FILE_PREFIX + timeStamp;
        //File albumF = getDiskCacheDir(MoaApplication.getInstance(), "voicebackup");
        File albumF;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            albumF = new File(AppConstants.VOICE_FILE_PATH);
        } else {
            albumF = getDiskCacheDir(MopaApplication.getInstance(), UNIQUE_VOICE);
        }
        if (!albumF.exists()) {
            albumF.mkdirs();
        }
        File file = new File(albumF, amrFileName);
        try {
            FileUtils.writeByteArrayToFile(file, bytes);
            return file.getPath();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 创建拍照文件
     * @return
     */
    public static File createCameraFile() {
        return new File(Environment.getExternalStorageDirectory(), TimeUtil.parseTime(System.currentTimeMillis(), TimeUtil.pattern_pic_file) + FORMAT_PIC);
    }

    /**
     * 创建奔溃日志文件
     * @return
     */
    public static File createCrashFile() {
        try {
            String time = TimeUtil.parseTime(System.currentTimeMillis(), TimeUtil.pattern_crash_file);
            String fileName = "crash-" + time + ".txt";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String path = AppConstants.CRASH_FILE_PATH;
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File file = new File(path, fileName);
                return file;
            }
            return null;
        } catch (Exception e) {
            LogCore.e(e, "an error occured while writing file...");
            return null;
        }
    }

    /**
     * 获取缓存文件目录
     * @param context
     * @param uniqueName
     * @return
     */
    public static File getDiskCacheDir(Context context, String uniqueName) {
        // Check if media is mounted or storage is built-in, if so, try
        // and use external cache dir
        // otherwise use internal cache dir

        try {
            String cachePath = getCachePath(context);

            return new File(cachePath + File.separator + uniqueName);
        } catch (NullPointerException e) {
            File externalStorageDirectory = Environment.getExternalStorageDirectory();
            return new File(externalStorageDirectory.getAbsolutePath() + File.separator + uniqueName);
        }
    }

    private static String getCachePath(Context context) {
        String cachePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || SdCardUtil.getSDCardInfo().isExist) {
            cachePath = getExternalCacheDir(context).getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return cachePath;
    }

    public static File getExternalCacheDir(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            return context.getExternalCacheDir();
        }

        // Before Froyo we need to construct the external cache dir
        // ourselves
        final String cacheDir = "/Android/data/" + context.getPackageName() + "/cache/";
        return new File(Environment.getExternalStorageDirectory().getPath() + cacheDir);
    }


}
