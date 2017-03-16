package org.yeewoe.mopassion.app.location.manager;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;

import com.amap.api.location.AMapLocation;

import org.yeewoe.commonutils.common.assist.Networks;
import org.yeewoe.commonutils.common.utils.GpsUtil;
import org.yeewoe.mopassion.MopaApplication;
import org.yeewoe.mopassion.utils.LogCore;

import java.util.HashMap;
import java.util.List;

/**
 * 高德地图状态码帮助类
 *
 * @author wyw
 */
public class MapCodeHelper {
    private static final String TAG = "location";
    private static final String INVALID_MAC_ADDRESS = "00:00:00:00:00:00";
    private static final int MAX_LONGTITUDE = 180;
    private static final int MAX_LANGTITUDE = 90;

    private static MapCodeHelper mInstance;

    private static long stime = 0L;
    private static int count33 = 0;

    private static final long TIME_SPACE_33 = 60 * 1000; // 1分钟容错时间

    private static final int COUNT_SPACE_33 = 3; // 容错次数

    @SuppressLint("UseSparseArrays")
    private HashMap<Integer, Integer> amapCodeMap = new HashMap<>();

    @SuppressLint("UseSparseArrays")
    private HashMap<Integer, String> codeErrorStrMap = new HashMap<Integer, String>();

    // 以下是本地错误
    public static final int CODE_NET_SLOW = 101;

    public static final int COCE_IO_ERROR = 102;

    public static final int CODE_AMAP_SERVER_ERROR = 103;

    public static final int CODE_LOCATION_SERVICE_ERROR = 104;

    public static final int CODE_LOCATION_ERROR = 105;

    public static final int CODE_OVERFLOW_ERROR = 106;

    public static final int CODE_ACCURACY_GT_MAX = 201;

    public static final int CODE_LATLON_ERROR = 202;

    public static final int CODE_ACCURACY_LT_ZERO = 203;

    public static final int CODE_USE_VIR = 204;

    public static final int CODE_USE_MOCK_APP = 205;

    public static final int CODE_OPEN_MOCK_OPTION = 206;

    public static final int CODE_TAKE_CARE_SAFE_APP = 207;

    public static final int CODE_SIM_NET_ERROR = 208;

    public static final int CODE_WIFI_ERROR = 209;

    public static final int CODE_PSEUDO_BASE_STATION = 210;

    public static final int CODE_WIFI_UN_WORK = 211;

    public static final int CODE_WIFI_NET_SLOW = 212;

    public static final int CODE_GEOCODE_FAIL = 213;

    public static final int CODE_FAILURE_WIFI_INFO = 301;

    public static final int CODE_FAILURE_LOCATION = 302;

    public static final int CODE_SERVICE_FAIL = 303;

    public static final int CODE_FAILURE_CELL = 304;

    public static final int CODE_FAILURE_PERMISSION = 305;


    private MapCodeHelper() {
        initAmapError();
        initLocalError();
    }

    public static synchronized MapCodeHelper getInstance() {
        if (mInstance == null) {
            mInstance = new MapCodeHelper();
        }

        return mInstance;
    }


    /**
     * 本地错误与错误提示映射
     */
    private void initLocalError() {
        codeErrorStrMap.put(CODE_NET_SLOW, "网络异常，请检查您的网络");
        codeErrorStrMap.put(COCE_IO_ERROR, "定位数据读写失败，请检查存储空间是否不足");
        codeErrorStrMap.put(CODE_AMAP_SERVER_ERROR, "高德服务器异常，请稍后再试");
        codeErrorStrMap.put(CODE_LOCATION_SERVICE_ERROR, "定位服务异常，请重启应用或重启手机再试");
        codeErrorStrMap.put(CODE_OVERFLOW_ERROR, "定位次数超出配额，请稍后重试，或联系我们解决");
        codeErrorStrMap.put(CODE_LOCATION_ERROR, "定位key鉴权失败，请联系我们进行解决");
        codeErrorStrMap.put(CODE_ACCURACY_GT_MAX, "定位精度大于2000，建议使用gps或者wifi环境下定位");
        codeErrorStrMap.put(CODE_LATLON_ERROR, "经纬度异常，请重启网络和GPS后重试");
        codeErrorStrMap.put(CODE_ACCURACY_LT_ZERO, "定位精度异常，请重启网络和GPS后重试");
        codeErrorStrMap.put(CODE_USE_VIR, "使用了模拟器，请使用真机进行定位");
        codeErrorStrMap.put(CODE_USE_MOCK_APP, "使用了第三方软件进行位置模拟，请关闭后再使用");
        codeErrorStrMap.put(CODE_OPEN_MOCK_OPTION, "开启了模拟位置开关，请关闭“设置》开发者选项》模拟位置开关”后再使用");
        codeErrorStrMap.put(CODE_TAKE_CARE_SAFE_APP, "定位失败，获取基站/WiFi信息为空或失败, 请确保没有禁用定位权限");
        codeErrorStrMap.put(CODE_SIM_NET_ERROR, "定位失败，请检查您的网络是否正常，稍后重试，并确保没有禁用定位权限");
        codeErrorStrMap.put(CODE_WIFI_ERROR, "定位失败，请检查您接入的WiFi网络连接是否正常，并确保没有禁用定位权限");
        codeErrorStrMap.put(CODE_PSEUDO_BASE_STATION, "定位失败，建议用WiFi网络或移动一下重试，并确保没有禁用定位权限。");
        codeErrorStrMap.put(CODE_WIFI_UN_WORK, "定位失败，您接入的WiFi不可用，请检查网络是否正常，并确保没有禁用定位权限");
        codeErrorStrMap.put(CODE_WIFI_NET_SLOW, "定位失败，您所连接的WiFi网络不佳，请稍后重试");
        codeErrorStrMap.put(CODE_GEOCODE_FAIL, "定位失败，网络异常导致地址解析失败，请检查您的网络");


        codeErrorStrMap.put(CODE_FAILURE_WIFI_INFO, "定位失败，由于仅扫描到单个WiFi，且没有获取带基站信息，请换个WiFi密集的地方重试");
        codeErrorStrMap.put(CODE_FAILURE_LOCATION, "定位失败，请换个WiFi或者位置重试，这是您当前位置没有被记录导致");
        codeErrorStrMap.put(CODE_SERVICE_FAIL, "定位失败，请重试");
        codeErrorStrMap.put(CODE_FAILURE_CELL, "定位失败，请检查移动网络是否良好，或者连接WiFi重试");
        codeErrorStrMap.put(CODE_FAILURE_PERMISSION, "定位失败，请检查网络并确保没有使用系统或者第三方APP禁用了定位权限");
    }

    /**
     * 高德错误与本地错误映射
     */
    private void initAmapError() {
        amapCodeMap.put(AMapLocation.ERROR_CODE_INVALID_PARAMETER, CODE_LOCATION_SERVICE_ERROR); //  一些重要参数为空，如context；请对定位传递的参数进行非空判断。
        amapCodeMap.put(AMapLocation.ERROR_CODE_FAILURE_WIFI_INFO, CODE_FAILURE_WIFI_INFO); // 定位失败，由于仅扫描到单个wifi，且没有基站信息。
        amapCodeMap.put(AMapLocation.ERROR_CODE_FAILURE_LOCATION_PARAMETER, CODE_LOCATION_SERVICE_ERROR); // 获取到的请求参数为空，可能获取过程中出现异常。
        amapCodeMap.put(AMapLocation.ERROR_CODE_FAILURE_CONNECTION, CODE_NET_SLOW); // 请求服务器过程中的异常，多为网络情况差，链路不通导致，请检查设备网络是否通畅。
        amapCodeMap.put(AMapLocation.ERROR_CODE_FAILURE_PARSER, CODE_WIFI_UN_WORK); // 返回的XML格式错误，解析失败。
        amapCodeMap.put(AMapLocation.ERROR_CODE_FAILURE_LOCATION, CODE_FAILURE_LOCATION); // 定位服务返回定位失败，如果出现该异常，请将errorDetail信息通过API@autonavi.com反馈给我们。
        amapCodeMap.put(AMapLocation.ERROR_CODE_FAILURE_AUTH, CODE_LOCATION_ERROR); // KEY建权失败，请仔细检查key绑定的sha1值与apk签名sha1值是否对应。
        amapCodeMap.put(AMapLocation.ERROR_CODE_UNKNOWN, CODE_LOCATION_SERVICE_ERROR); // Android exception通用错误，请将errordetail信息通过API@autonavi.com反馈给我们。
        amapCodeMap.put(AMapLocation.ERROR_CODE_FAILURE_INIT, CODE_LOCATION_SERVICE_ERROR); // 定位初始化时出现异常，请重新启动定位。
        amapCodeMap.put(AMapLocation.ERROR_CODE_SERVICE_FAIL, CODE_SERVICE_FAIL); // 定位客户端启动失败，请检查AndroidManifest.xml文件是否配置了APSService定位服务
        amapCodeMap.put(AMapLocation.ERROR_CODE_FAILURE_CELL, CODE_FAILURE_CELL); // 定位时的基站信息错误，请检查是否安装SIM卡，设备很有可能连入了伪基站网络。
        amapCodeMap.put(AMapLocation.ERROR_CODE_FAILURE_LOCATION_PERMISSION, CODE_FAILURE_PERMISSION); // 缺少定位权限，请在设备的设置中开启app的定位权限。
    }

    public boolean isSucces(int rCode) {
        return rCode == AMapLocation.LOCATION_SUCCESS;
    }

    public boolean isCodeMock(int errorCode) {
        return errorCode == CODE_USE_VIR || errorCode == CODE_USE_MOCK_APP || errorCode == CODE_OPEN_MOCK_OPTION;
    }

    public boolean isCodeBan(int errorCode) {
        return errorCode == CODE_TAKE_CARE_SAFE_APP || errorCode == CODE_FAILURE_PERMISSION;
    }

    public boolean isCodeSimNetError(int errorCode) {
        return errorCode == CODE_NET_SLOW || errorCode == CODE_SIM_NET_ERROR || errorCode == CODE_PSEUDO_BASE_STATION || errorCode == CODE_FAILURE_CELL;
    }

    public boolean isCodeWifiError(int errorCode) {
        return errorCode == CODE_NET_SLOW || errorCode == CODE_WIFI_ERROR || errorCode == CODE_WIFI_UN_WORK || errorCode == CODE_WIFI_NET_SLOW || errorCode == CODE_FAILURE_WIFI_INFO;
    }

    public boolean isWifiOrNetErrorErrorCode(int errorCode) {
        return errorCode == CODE_FAILURE_WIFI_INFO || errorCode == CODE_FAILURE_CELL;
    }

    public String getCodeStr(int rCode) {
        if (amapCodeMap.containsKey(rCode)) {
            rCode = amapCodeMap.get(rCode);
        }
        if (!codeErrorStrMap.containsKey(rCode)) {
            return null;
        }

        return codeErrorStrMap.get(rCode);
    }

    public boolean verifyAmapError(AMapLocation aMapLocation, LocationPointInfo locationPointInfo) {
        boolean result = false;

        int errorCode = aMapLocation.getErrorCode();

        if (!isSucces(errorCode) && amapCodeMap.containsKey(errorCode)) {
            Integer localCode = amapCodeMap.get(errorCode);
            buildError(locationPointInfo, localCode, aMapLocation.getLocationDetail());
            result = true;
        }

        return result;
    }

    private void buildError(LocationPointInfo locationPointInfo, Integer localCode, String locationDetail) {
        locationPointInfo.isSuccess = false;
        locationPointInfo.errorCode = localCode;
        locationPointInfo.errorStr = getCodeStr(localCode);
        locationPointInfo.locationDetail = locationDetail;
        LogCore.wtf("buildError, errorCode: " + locationPointInfo.errorCode +
                " errorStr: " + locationPointInfo.errorStr);
    }

    /**
     * 判断wifi数据是否获取失败
     *
     * @return
     */
    public boolean verifyWifiError(AMapLocation aMapLocation, LocationPointInfo locationPointInfo) {
        boolean result = false;
        boolean isWifiEnabled = false;
        if (isWifiOrNetErrorErrorCode(aMapLocation.getErrorCode())) {
            WifiManager wm = (WifiManager) MopaApplication.getInstance().getSystemService(Context.WIFI_SERVICE);
            if (wm != null) {
                if (wm.isWifiEnabled()) {
                    isWifiEnabled = true;
                    WifiInfo connectionInfo = wm.getConnectionInfo();
                    if (connectionInfo != null) {
                        int ipAddress = connectionInfo.getIpAddress();
                        String macAddress = connectionInfo.getMacAddress();
                        if ((ipAddress <= 0 && macAddress.equals(INVALID_MAC_ADDRESS))) {
                            result = true;
                        }
                    }
                }
            }
        } else if (aMapLocation.getErrorCode() == AMapLocation.ERROR_CODE_FAILURE_CONNECTION) {
            WifiManager wm = (WifiManager) MopaApplication.getInstance().getSystemService(Context.WIFI_SERVICE);
            if (wm != null) {
                if (wm.isWifiEnabled()) {
                    isWifiEnabled = true;
                    WifiInfo connectionInfo = wm.getConnectionInfo();
                    if (connectionInfo != null) {
                        int ipAddress = connectionInfo.getIpAddress();
                        String macAddress = connectionInfo.getMacAddress();
                        if ((ipAddress <= 0)) {
                            result = true;
                        }
                    }
                }
            }
        }

        if (result) {
            if (aMapLocation.getErrorCode() == AMapLocation.ERROR_CODE_FAILURE_CONNECTION) {
                buildError(locationPointInfo, MapCodeHelper.CODE_WIFI_UN_WORK, aMapLocation.getLocationDetail());
            } else {
                buildError(locationPointInfo, MapCodeHelper.CODE_WIFI_ERROR, aMapLocation.getLocationDetail());
            }
        } else if (isWifiEnabled) {
            buildError(locationPointInfo, MapCodeHelper.CODE_WIFI_NET_SLOW, aMapLocation.getLocationDetail());
        }

        return result;
    }

    /**
     * 判断基站数据是否获取失败
     *
     * @return
     */
    public boolean verifySimNetError(AMapLocation aMapLocation, LocationPointInfo locationPointInfo) {
        boolean result = false;

        int mcc = -1;
        int mnc = -1;

        if (isWifiOrNetErrorErrorCode(aMapLocation.getErrorCode())) {
            WifiManager wm = (WifiManager) MopaApplication.getInstance().getSystemService(Context.WIFI_SERVICE);
            if (wm != null) {
                if (!wm.isWifiEnabled()) {
                    final TelephonyManager tm = (TelephonyManager) MopaApplication.getInstance().getSystemService(
                            Context.TELEPHONY_SERVICE);
                    if (tm != null) {
                        int lac = -1;
                        int cellId = -1;


                        String operator = tm.getNetworkOperator();
                        if (!TextUtils.isEmpty(operator)) {
                            try {
                                mcc = Integer.parseInt(operator.substring(0, 3));
                                mnc = Integer.parseInt(operator.substring(3));
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                        } else {
                            LogCore.wtf("getNetworkOperator() is empty or null!!!");
                        }

                        // 中国移动和中国联通获取LAC、CID的方式
                        try {
                            GsmCellLocation location = (GsmCellLocation) tm.getCellLocation();
                            if (location != null) {
                                lac = location.getLac();
                                cellId = location.getCid();
                            }
                        } catch (Exception e) {
                        }

                        // 中国电信获取LAC、CID的方式
                        try {
                            CdmaCellLocation location1 = (CdmaCellLocation) tm.getCellLocation();
                            if (location1 != null) {
                                lac = location1.getNetworkId();
                                cellId = location1.getBaseStationId();
                                cellId /= 16;
                            }
                        } catch (Exception e) {
                        }

                        if (lac == -1 || cellId == -1 || cellId == 0 || cellId == 65535) {
                            result = true;
                        }
                    }
                }
            }
        }

        if (result) {
            if (mcc != -1 && mnc != -1) {
                buildError(locationPointInfo, MapCodeHelper.CODE_PSEUDO_BASE_STATION, aMapLocation.getLocationDetail());
            } else {
                buildError(locationPointInfo, MapCodeHelper.CODE_SIM_NET_ERROR, aMapLocation.getLocationDetail());
            }
        }

        return result;
    }

    /**
     * 过滤禁止权限问题
     *
     * @param location
     * @return
     */
    public synchronized boolean verfiyBan(AMapLocation location, LocationPointInfo locationPointInfo) {
        boolean result = false;
        if (location == null) {
            return result;
        }

        if (isWifiOrNetErrorErrorCode(location.getErrorCode())) {
            // 1分钟内出现超过3次
            long current = System.currentTimeMillis();
            if (stime == 0 || stime >= current || current - stime > TIME_SPACE_33) {
                stime = current;
                count33 = 0;
            } else {
                ++count33;
                if (count33 >= COUNT_SPACE_33) {
                    stime = current;
                    result = true;
                }
            }
        } else if (isSucces(location.getErrorCode()) && location.getLongitude() == 1 && location.getLatitude() == 1) {
            // 360禁止定位返回的特殊值
            result = true;
        }

        if (result) {
            buildError(locationPointInfo, MapCodeHelper.CODE_TAKE_CARE_SAFE_APP, location.getLocationDetail());
        }

        return result;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public boolean verifyMock(AMapLocation aMapLocation, LocationPointInfo locationPointInfo) {
        boolean result = false;
        if (isSucces(aMapLocation.getErrorCode()) &&
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2 && aMapLocation.isFromMockProvider()) {
            result = true;
        }

        if (result) {
            buildError(locationPointInfo, MapCodeHelper.CODE_USE_MOCK_APP, aMapLocation.getLocationDetail());
        }

        return result;
    }

    public boolean verifyAccuracy(AMapLocation aMapLocation, LocationPointInfo locationPointInfo) {
        boolean result = false;

        if (isSucces(aMapLocation.getErrorCode()) &&
                aMapLocation.hasAccuracy() && aMapLocation.getAccuracy() <= 0) {
            LogCore.wtf("onLocationChanged: accuracy <= 0");
            result = true;
        }

        if (result) {
            buildError(locationPointInfo, MapCodeHelper.CODE_ACCURACY_LT_ZERO, aMapLocation.getLocationDetail());
        }

        return result;
    }

    public boolean verifyLatLon(AMapLocation aMapLocation, LocationPointInfo locationPointInfo) {
        boolean result = false;
        if (isSucces(aMapLocation.getErrorCode()) &&
                (((int) aMapLocation.getLatitude()) == 0 && ((int) aMapLocation.getLongitude()) == 0
                        || aMapLocation.getLatitude() > MAX_LANGTITUDE
                        || aMapLocation.getLongitude() > MAX_LONGTITUDE)) {
            LogCore.wtf("onLocationChanged: lan and lon invalid");
            result = true;
        }

        if (result) {
            buildError(locationPointInfo, MapCodeHelper.CODE_LATLON_ERROR, aMapLocation.getLocationDetail());
        }

        return result;
    }

    public void buildPointInfo(AMapLocation location, LocationPointInfo info) {
        info.isSuccess = true;
        if (location.hasAccuracy()) {
            info.radius = location.getAccuracy();
        }
        info.time = location.getTime();
        info.latitude = location.getLatitude();
        info.longitude = location.getLongitude();

        if (TextUtils.isEmpty(location.getAddress())
                || location.getAddress().equals("null")) {
            LogCore.wtf("[buildPointInfo]address is 'null'");
            info.addrStr = "";
            info.addrStr_h = "";
            info.addrStr_h2 = "";
        } else {
            info.addrStr = location.getAddress();
            info.addrStr_h = LocationUtils.parseAddress(
                    location.getAddress(), location.getProvince());
            info.addrStr_h2 = LocationUtils.parseAddress2(
                    location.getAddress(), location.getCity());
        }

        info.province = location.getProvince();
        info.city = location.getCity();
        info.country = location.getCountry();
        info.district = location.getDistrict();
        info.speed = location.getSpeed();
        if (location.hasBearing()) {
            info.direct = location.getBearing();
        }
        if (location.hasAltitude()) {
            info.altitude = location.getAltitude();
        }
        info.locationType = location.getLocationType();
        info.locationDetail = location.getLocationDetail();
    }

    /**
     * 打印wifi和基站信息
     */
    public void logWifiAndStation() {
        String log = "";
        try {
            log += "手机网络状态：" + Networks.getNetworkType(MopaApplication.getInstance()).name();
        } catch (Exception e) {
            e.printStackTrace();
        }

        LogCore.wtf(log + "\tGPS是否打开? " + GpsUtil.isGpsEnable(MopaApplication.getInstance()));

        WifiManager wm = (WifiManager) MopaApplication.getInstance().getSystemService(Context.WIFI_SERVICE);
        if (wm == null) {
            LogCore.wtf("WifiManager is null!!! ");
        } else {
            LogCore.wtf("WiFi是否打开? " + wm.isWifiEnabled());
            if (wm.isWifiEnabled()) {
                WifiInfo connectionInfo = wm.getConnectionInfo();
                if (connectionInfo != null) {
                    LogCore.wtf("WiFi ip address: " + connectionInfo.getIpAddress() +
                            "\tMAC address: " + connectionInfo.getMacAddress() + "\tNetWork ID:" +
                            connectionInfo.getNetworkId() + "\tSSID: " + connectionInfo.getSSID() +
                            "\tBSSID: " + connectionInfo.getBSSID() + "\t连接速度: " +
                            connectionInfo.getLinkSpeed() + "mbps; ");
                } else {
                    LogCore.wtf("wifi connection info is null!!!");
                }
                List<ScanResult> scanResults = wm.getScanResults();
                if (scanResults != null) {
                    long scanSize = scanResults.size();
                    LogCore.wtf("WiFi Scan Results size: " + scanSize);
                    if (scanSize > 0) {
                        StringBuilder stringBuilder = new StringBuilder("WiFi Scan Results list: ");
                        for (ScanResult scanResult : scanResults) {
                            stringBuilder.append(scanResult.SSID).append("(").append(scanResult.BSSID).append(") ");
                        }
                        LogCore.wtf(stringBuilder.toString());
                    }
                } else {
                    LogCore.wtf("WiFi Scan Results is null!!!");
                }
            }
        }
        final TelephonyManager tm = (TelephonyManager) MopaApplication.getInstance().getSystemService(
                Context.TELEPHONY_SERVICE);

        if (tm == null) {
            LogCore.wtf("TelephonyManager is null!!! ");
        } else {
            int mcc = -1;
            int mnc = -1;
            int lac = -1;
            int cellId = -1;

            String operator = tm.getNetworkOperator();
            if (!TextUtils.isEmpty(operator)) {
                try {
                    mcc = Integer.parseInt(operator.substring(0, 3));
                    mnc = Integer.parseInt(operator.substring(3));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            } else {
                LogCore.wtf("getNetworkOperator() is empty or null!!!");
            }

            // 中国移动和中国联通获取LAC、CID的方式
            try {
                GsmCellLocation location = (GsmCellLocation) tm.getCellLocation();
                if (location != null) {
                    lac = location.getLac();
                    cellId = location.getCid();
                } else {
                    LogCore.wtf("getGsmCellLocation() is null!!!");
                }

            } catch (Exception e) {
            }

            // 中国电信获取LAC、CID的方式
            try {
                CdmaCellLocation location1 = (CdmaCellLocation) tm.getCellLocation();
                if (location1 != null) {
                    lac = location1.getNetworkId();
                    cellId = location1.getBaseStationId();
                    cellId /= 16;
                } else {
                    LogCore.wtf("getCdmaCellLocation() is null!!!");
                }
            } catch (Exception e) {
            }

            LogCore.wtf(" 移动国家代码（MCC） = " + mcc + "\t 移动网络号码（MNC） = " + mnc +
                    "\t 位置区域码（LAC） = " + lac + "\t 基站编号（CID） = " + cellId);

        }


    }

    public boolean verifyNull(AMapLocation location, LocationPointInfo info) {
        if (location == null) {
            buildError(info, CODE_NET_SLOW, "");
            return true;
        }
        return false;
    }


}
