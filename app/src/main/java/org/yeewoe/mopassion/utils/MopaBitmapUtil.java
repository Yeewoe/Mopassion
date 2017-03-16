package org.yeewoe.mopassion.utils;

import android.graphics.Bitmap;

import org.yeewoe.commonutils.common.io.FileUtils;
import org.yeewoe.commonutils.common.utils.BitmapUtil;
import org.yeewoe.commonutils.common.utils.MD5Util;
import org.yeewoe.mopassion.constants.AppConstants;
import org.yeewoe.mopassion.photo.fresco_lib.KeyUrlHelper;
import org.yeewoe.mopassion.template.media.TMediaFactory;
import org.yeewoe.mopassion.template.media.TMediaImage;

import java.io.File;
import java.io.IOException;

/**
 * Created by wyw on 2016/4/26.
 */
public class MopaBitmapUtil {
    private static final int COMPRESS_WIDTH = 480;
    private static final int COMPRESS_HEIGHT = 720;

    public static File compress(File pic) {
        File result = new File(AppConstants.APP_TEMP_PIC_DIR, pic.getName());
        Bitmap smallBitmap = BitmapUtil.getSmallBitmap(pic.getPath(), COMPRESS_WIDTH, COMPRESS_HEIGHT);
        BitmapUtil.saveBitmap(smallBitmap, result);

        String key = MD5Util.md5Str(result);
        KeyUrlHelper.getInstance().saveUrl(key, result);
        return result;
    }
}
