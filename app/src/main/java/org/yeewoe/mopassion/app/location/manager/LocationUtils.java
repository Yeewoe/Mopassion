package org.yeewoe.mopassion.app.location.manager;

import android.content.Context;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

import com.amap.api.location.AMapLocation;

import org.yeewoe.commonutils.common.assist.Checks;
import org.yeewoe.commonutils.common.io.SharePreferenceUtils;
import org.yeewoe.mopassion.MopaApplication;
import org.yeewoe.mopassion.constants.SpConstants;
import org.yeewoe.mopassion.db.po.MapPosition;

/**
 * 定位工具
 */
public class LocationUtils {
    private static final long INVALID_TIME_RANGE = 15 * 60 * 1000; // 15分钟
    public static final String TYPE_INFO_GPS = "GPS定位结果";
    public static final String TYPE_INFO_LAST = "前次定位结果";
    public static final String TYPE_INFO_CACHE = "缓存定位结果";
    public static final String TYPE_INFO_WIFI = "Wifi定位结果";
    public static final String TYPE_INFO_CELL = "基站定位结果";
    public static final String TYPE_INFO_AMAP = "高德混合定位结果";
    public static final String TYPE_INFO_OFFLINE = "离线定位结果";
    public static final String TYPE_INFO_UNKNWON = "未知定位类型";
    public static final String TYPE_INFO_APPEND_WIFI = "[缓存的是Wifi定位结果]";
    public static final String TYPE_INFO_APPEND_CELL = "[缓存的是基站定位结果]";

    /**
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
     *
     * @return true 表示开启
     */
    public static final boolean isOPen(Context context) {
        LocationManager locationManager = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }
        return false;
    }

    public static final boolean isGPSOpen(Context context) {
        LocationManager locationManager = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        return gps;
    }

    public static final boolean isNetOpen(Context context) {
        LocationManager locationManager = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        return network;
    }

    /**
     * 判断定位时间是否失效
     *
     * @param time
     * @return
     */
    public static boolean verifyTime(long time) {
        return Math.abs(System.currentTimeMillis() - time) <= INVALID_TIME_RANGE;
    }

//    /**
//     * 计算两点距离（与服务端算法一致）
//     *
//     * @param p1
//     * @param p2
//     * @return
//     */
//    public static double getDistance(LatLng p1, LatLng p2) {
//        double earthRadius = 6378.137;
//        double radLat1 = ((p1.latitude) * Math.PI) / 180.0;
//        double radLat2 = ((p2.latitude) * Math.PI) / 180.0;
//        double a = radLat1 - radLat2;
//        double b = (((p1.longitude) * Math.PI) / 180.0)
//                - (((p2.longitude) * Math.PI) / 180.0);
//        double s = 2 * Math.sin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
//                + Math.cos(radLat1) * Math.cos(radLat2)
//                * Math.pow(Math.sin(b / 2), 2)));
//        s *= earthRadius;
//        s = (double) Math.round(s * 10000) / 10000;
//        return s * 1000;
//    }

    public static double getDistance(MapPosition p1, MapPosition p2) {
        if (p1 == null || p2 == null) return -1;
        double earthRadius = 6378.137;
        double radLat1 = ((p1.getLat()) * Math.PI) / 180.0;
        double radLat2 = ((p2.getLat()) * Math.PI) / 180.0;
        double a = radLat1 - radLat2;
        double b = (((p1.getLon()) * Math.PI) / 180.0)
                - (((p2.getLon()) * Math.PI) / 180.0);
        double s = 2 * Math.sin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s *= earthRadius;
        s = (double) Math.round(s * 10000) / 10000;
        return s * 1000;
    }

    /**
     * 位置处理（去掉包括省之前的地址描述内容）
     *
     * @param address
     * @param province
     * @return
     */
    public static String parseAddress(String address, String province) {
        String result = address;
        try {
            if (!TextUtils.isEmpty(address)
                    && !address.equals(province)) {
                if (address.contains(province)) {
                    result = address.substring(address.indexOf(province)
                            + province.length(), address.length());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 位置处理（去掉包括市县级前的地址描述内容）
     *
     * @param address
     * @param city
     * @return
     */
    public static String parseAddress2(String address, String city) {
        String result = address;
        try {
            if (!TextUtils.isEmpty(address)
                    && !address.equals(city)) {
                if (address.contains(city)) {
                    result = address.substring(address.indexOf(city)
                            + city.length(), address.length());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 是否可能模拟位置
     *
     * @return
     */
    public static MockType isMock() {

//        // 是否是原生模拟器的brand
//        String[] mockBrands = new String[]{"generic"};
//        // 部分主流模拟器的model
//        String[] mockModels = new String[]{"sdk", "google_sdk"};
//        // 部分主流模拟器硬件名（如海马）
//        String[] mockHardwares = new String[]{"vbox86", "vbox64"};
//        String brand = Build.BRAND;
//        String model = Build.MODEL;
//        String hw = Build.HARDWARE;
//
//        if (TextUtils.isEmpty(brand)) {
//            return MockType.NONE;
//        }
//
//        if (brand != null) {
//            for (String mockBrand : mockBrands) {
//                if (brand.equalsIgnoreCase(mockBrand)) {
//                    return MockType.EMULATOR;
//                }
//            }
//        }
//
//        if (model != null) {
//            for (String mockModel : mockModels) {
//                if (model.equalsIgnoreCase(mockModel)) {
//                    return MockType.EMULATOR;
//                }
//            }
//
//        }
//
//        if (hw != null) {
//            for (String mockHardware : mockHardwares) {
//                if (hw.equalsIgnoreCase(mockHardware)) {
//                    return MockType.EMULATOR;
//                }
//            }
//        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            // 是否开启了模拟位置选项
            boolean isOpenMock = Settings.Secure.getInt(MopaApplication.getInstance().getContentResolver(),
                    Settings.Secure.ALLOW_MOCK_LOCATION, 0) != 0;
            if (isOpenMock) {
                return MockType.OPEN_MOCVK;
            }
        }

        return MockType.NONE;
    }

    public static String getTypeInfo(int type, String detail) {
        String result;
        switch (type) {
            case AMapLocation.LOCATION_TYPE_GPS:
                result = TYPE_INFO_GPS;
                break;
            case AMapLocation.LOCATION_TYPE_SAME_REQ:
                result = TYPE_INFO_LAST;
                result = appendTypeInfo(detail, result);
                break;
            case AMapLocation.LOCATION_TYPE_FIX_CACHE:
                result = TYPE_INFO_CACHE;
                result = appendTypeInfo(detail, result);
                break;
            case AMapLocation.LOCATION_TYPE_WIFI:
                result = TYPE_INFO_WIFI;
                break;
            case AMapLocation.LOCATION_TYPE_CELL:
                result = TYPE_INFO_CELL;
                break;
            case AMapLocation.LOCATION_TYPE_AMAP:
                result = TYPE_INFO_AMAP;
                break;
            case AMapLocation.LOCATION_TYPE_OFFLINE:
                result = TYPE_INFO_OFFLINE;
                break;
            default:
                result = TYPE_INFO_UNKNWON;
                break;
        }
        return result;
    }

    /**
     * 保存位置
     */
    public static void saveLocation(LocationPointInfo info) {
        try {
            SharePreferenceUtils spUtil = MopaApplication.getInstance()
                    .getSp();
            spUtil.saveSharedPreferences(SpConstants.Location.LAT,
                    Double.valueOf(info.latitude).floatValue());
            spUtil.saveSharedPreferences(SpConstants.Location.LON,
                    Double.valueOf(info.longitude).floatValue());
            spUtil.saveSharedPreferences(
                    SpConstants.Location.ADDRESS, info.addrStr_h);
            if (!TextUtils.isEmpty(info.city)) {
                spUtil.saveSharedPreferences(
                        SpConstants.Location.CITY_NAME,
                        info.city);
            }
            if (!TextUtils.isEmpty(info.country)) {
                spUtil.saveSharedPreferences(
                        SpConstants.Location.COUNTRY_NAME,
                        info.country);
            }
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
    }

    /**
     * 读取位置
     */
    public static LocationPointInfo loadLocation() {
        SharePreferenceUtils spUtil = MopaApplication.getInstance()
                .getSp();
        LocationPointInfo result = new LocationPointInfo();
        result.latitude = spUtil.loadFloatSharedPreference(SpConstants.Location.LAT);
        result.longitude = spUtil.loadFloatSharedPreference(SpConstants.Location.LON);
        result.addrStr = spUtil.loadStringSharedPreference(SpConstants.Location.ADDRESS);
        if (result.latitude == 0 && result.longitude == 0) {
            // 没有地址
            return null;
        }
        if (!Checks.check(result.addrStr)) {
            result.addrStr = "测试";
        }
        result.city = spUtil.loadStringSharedPreference(SpConstants.Location.CITY_NAME);
        result.country = spUtil.loadStringSharedPreference(SpConstants.Location.COUNTRY_NAME);

        return result;
    }

    private static String appendTypeInfo(String detail, String result) {
        if (!TextUtils.isEmpty(detail)) {
            if (detail.equals("-5") || detail.equals("1") || detail.equals("2") ||
                    detail.equals("14") || detail.equals("24") || detail.equals("-1")) {
                // 这六个都是WIFI定位的缓存定位标志【高德研发说明的】
                result += TYPE_INFO_APPEND_WIFI;
            }
        } else {
            result += TYPE_INFO_APPEND_CELL;
        }
        return result;
    }


    public static enum MockType {
        /**
         * 正常
         */
        NONE,
        /**
         * 模拟器
         */
        EMULATOR,
        /**
         * 开启了模拟位置
         */
        OPEN_MOCVK
    }
}
