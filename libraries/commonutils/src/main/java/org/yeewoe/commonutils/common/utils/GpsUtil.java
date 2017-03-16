package org.yeewoe.commonutils.common.utils;

import android.content.Context;
import android.location.LocationManager;

/**
 * Created by wyw on 2016/4/6.
 */
public class GpsUtil {
    /**
     * 是否开启了Gps
     *
     * @param context Context
     * @return boolean
     */
    public static boolean isGpsEnable(Context context) {
        try {
            LocationManager locationManager = ((LocationManager) context
                    .getSystemService(Context.LOCATION_SERVICE));
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (SecurityException e) {
            return false;
        }
    }
}
