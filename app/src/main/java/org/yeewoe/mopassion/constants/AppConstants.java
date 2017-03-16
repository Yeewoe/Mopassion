package org.yeewoe.mopassion.constants;

import android.os.Environment;

import java.io.File;

/**
 * 系统配置
 * Created by wyw on 2016/3/25.
 */
public class AppConstants {
    public static final String SP_NAME = "mopa_sp";

    public static final int  COUNT_ALL = -1;
    public static final long PREV_HOME = 0;

    public static final int HEAD_GRID_MAX_COLUMN_COUNT = 4;
    public static final int HEAD_GRID_MAX_RAW_COUNT = -1;

    public static final String APP_ROOT_DIR = Environment.getExternalStorageDirectory().getAbsoluteFile().getAbsolutePath() + File.separator + "mopassion";
    public static final String APP_TEMP_PIC_DIR = APP_ROOT_DIR + File.separator + "temp_pic";
    public static final String VOICE_FILE_PATH =  APP_ROOT_DIR + File.separator + "voice";
    public static final String CRASH_FILE_PATH =  APP_ROOT_DIR + File.separator + "crash";
    public static final String LOG_FILE_PATH =  APP_ROOT_DIR + File.separator + "log";


}
