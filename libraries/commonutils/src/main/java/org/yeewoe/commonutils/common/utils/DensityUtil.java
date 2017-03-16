package org.yeewoe.commonutils.common.utils;

import android.content.res.Resources;
import android.util.TypedValue;

public class DensityUtil {

    public static float pixel2Dp(Resources resources, int value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, value,
                resources.getDisplayMetrics());
    }

    public static float dp2Pixel(Resources resources, int value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value,
                resources.getDisplayMetrics());
    }

    public static float dp2Pixel(Resources resources, float value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value,
                resources.getDisplayMetrics());
    }

}
