package org.yeewoe.mopassion.app.location.manager;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import org.yeewoe.mopassion.MopaApplication;
import org.yeewoe.mopassion.utils.LogCore;
import org.yeewoe.mopassion.utils.TimeUtil;

import java.text.DateFormat;
import java.util.Date;

/**
 * 定位核心类
 * @author wyw
 */
public class MopaLocationClient {

    public static final int GEO_RETRY_TIMES = 3; // 逆地址解析失败重试次数

    public interface NotifyLocationListener {
        void notify(LocationPointInfo locationPointInfo);
    }

    enum RequestType {
        NORMAL, NO_CACHE
    }

    public class MopaLocationListener implements AMapLocationListener {

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
        @Override
        public void onLocationChanged(AMapLocation location) {

            LocationPointInfo info = new LocationPointInfo();
            if (MapCodeHelper.getInstance().verifyNull(location, info)) {
                LogCore.wtf("定位结果为空");
                notifyInfo(info);
            }

            if (!MapCodeHelper.getInstance().isSucces(location.getErrorCode())) {
                LogCore.wtf("获取定位结果失败: 错误码: " + location.getErrorCode() + ", 错误原因: " + location.getErrorInfo() + ", 定位细节: " + location.getLocationDetail());
            }

            // just a test
//            if (BuildConfig.DEBUG) {
//                location.setLongitude(118.12713);
//                location.setLatitude(30.309118);
//                location.setAddress("龙王殿");
//            }

            // 预处理错误
            if (MapCodeHelper.getInstance().verifyAmapError(location, info) ||
                    MapCodeHelper.getInstance().verifyMock(location, info) ||
                    MapCodeHelper.getInstance().verifyAccuracy(location, info) ||
                    MapCodeHelper.getInstance().verifyLatLon(location, info)) {
                notifyInfo(info);
                return;
            }

            if (MapCodeHelper.getInstance().isSucces(location.getErrorCode())) {
                if (requestType == RequestType.NO_CACHE &&
                        isCache(location)) {
                    // 考虑缓存模式下
                    LogCore.wtf("考虑缓存时间，且定位结果的位置时间在缓存范围外~: 位置时间是=" +
                            DateFormat.getInstance().format(new Date(location.getTime())));
                    return;
                }

                MapCodeHelper.getInstance().buildPointInfo(location, info);

                LogCore.wtf("获取到定位结果: 纬度： " + location.getLatitude() +
                        "； 经度： " + info.longitude +
                        "； 精度： " + info.radius +
                        "； 地址： " + info.addrStr +
                        "； 省份： " + info.province +
                        "； 城市： " + info.city +
                        "； 国家： " + info.country +
                        "； 地址h（去掉省后）： " + info.addrStr_h +
                        "； 地址h2（去掉市县级后）： " + info.addrStr_h2 +
                        "； 定位类型： " + LocationUtils.getTypeInfo(info.locationType, info.locationDetail) +
                        "； 定位细节： " + info.locationDetail +
                        "； 位置时间： " + TimeUtil.logTime(info.time));
                LocationUtils.saveLocation(info);
            } else {
                info.isSuccess = false;
                info.errorStr = location.getErrorInfo();
                info.errorCode = location.getErrorCode();
                info.locationDetail = location.getLocationDetail();
            }

            notifyInfo(info);
        }
    }

    private Context mApplicationContext;
    private static MopaLocationClient mopaLocationClient = null;
    private AMapLocationClient mLocationManagerProxy;
    private AMapLocationClientOption mLocationOption;

    private MopaLocationListener mRequestLocationListener = new MopaLocationListener();

    private NotifyLocationListener mLocationChangeListener;

    private RequestType requestType;

    private long timeAccepted;
    private long maxTimeRange;
    private long lastGetLocationTime;

    private MopaLocationClient() {
        init();
    }

    private void init() {
        mApplicationContext = MopaApplication.getInstance().getApplicationContext();
        mLocationManagerProxy = new AMapLocationClient(mApplicationContext);
        mLocationManagerProxy.setLocationListener(mRequestLocationListener);
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setNeedAddress(true);
        mLocationOption.setOnceLocation(true);
        mLocationOption.setWifiActiveScan(true);
        mLocationOption.setMockEnable(true);
        // mLocationOption.setKillProcess(); // 设置退出时是否杀死service 默认值:false, 不杀死
    }

    public static MopaLocationClient getInstance() {
        if (mopaLocationClient == null) {
            synchronized (MopaLocationClient.class) {
                if (mopaLocationClient == null) {
                    mopaLocationClient = new MopaLocationClient();
                }
            }
        }
        return mopaLocationClient;
    }

    /**
     * 启动定位服务
     */
    public synchronized void startClient() {
        LogCore.wtf("启动定位服务");
        if (mLocationManagerProxy == null) {
            init();
        }
    }

    /**
     * 停止定位服务
     */
    public synchronized void stopClient() {
        LogCore.wtf("停止定位服务");
        if (mLocationManagerProxy != null) {
            mLocationManagerProxy.stopLocation();
            mLocationManagerProxy.onDestroy();
        }
        mLocationManagerProxy = null;
    }

    /**
     * 重启定位服务
     */
    public synchronized void restartClient() {
        stopClient();
        startClient();
    }

    /**
     * 是否属于考虑的缓存范围内
     */
    private boolean isCache(AMapLocation location) {
        long current = System.currentTimeMillis();

        if (location.getLocationType() == AMapLocation.LOCATION_TYPE_GPS ||
                location.getLocationType() == AMapLocation.LOCATION_TYPE_WIFI ||
                location.getLocationType() == AMapLocation.LOCATION_TYPE_CELL ||
                location.getLocationType() == AMapLocation.LOCATION_TYPE_AMAP) {
            lastGetLocationTime = current;
            return false;
        } else if (maxTimeRange != -1 && current - lastGetLocationTime
                >= maxTimeRange) {
            // 已超过最大容许定位时间
            lastGetLocationTime = current;
            return false;
        } else if (current - location.getTime() <= timeAccepted) {
            // 在可接受的缓存时间范围内
            lastGetLocationTime = current;
            return false;
        }

        return true;
    }

    private void notifyInfo(LocationPointInfo info) {
        if (!info.isSuccess) {
            MapCodeHelper.getInstance().logWifiAndStation();
        }

        if (mLocationChangeListener != null) {
            mLocationChangeListener.notify(info);
        }
    }


    private boolean mockVerify() {

        switch (LocationUtils.isMock()) {
            case NONE:
                return false;
//            case EMULATOR:
//                LocationPointInfo info = new LocationPointInfo();
//                info.isSuccess = false;
//                info.errorCode = 204;
//                info.errorStr = MapCodeHelper.getCodeStr(info.errorCode); // 使用了模拟器定位失败，请使用真机进行定位
//                notifyInfo(info);
//                return true;
            case OPEN_MOCVK:
                LocationPointInfo info2 = new LocationPointInfo();
                info2.isSuccess = false;
                info2.errorCode = MapCodeHelper.CODE_OPEN_MOCK_OPTION;
                info2.errorStr = MapCodeHelper.getInstance().getCodeStr(info2.errorCode); // 定位失败，请关闭开关：开发者选择->模拟位置
                notifyInfo(info2);
                return true;
        }

        return false;
    }

    /**
     * 单次定位
     *
     * @param geocode  是否对地址为空的情况（如gps位置）自动进行逆地址解析，解析失败将返回整体失败
     * @param listener 定位回调
     */
    public void requestLocation(final boolean geocode, final NotifyLocationListener listener) {
        restartClient();
        geocode(geocode, listener);
        requestLocation();
    }

    /**
     * 持续定位
     *
     * @param minTime  定位时间间隔
     * @param geocode  是否对地址为空的情况（如gps位置）自动进行逆地址解析，解析失败将返回整体失败
     * @param listener 定位回调
     */
    public void requestLocation(long minTime, final boolean geocode, final NotifyLocationListener listener) {
        restartClient();
        geocode(geocode, listener);
        requestLocation(minTime);
    }

    /**
     * 最大可能无缓存的持续定位
     *
     * @param minTime      定位时间间隔
     * @param timeAccepted 能接受的缓存时间范围（ms）
     * @param maxTimeRange 最大不使用缓存的时间范围（ms）（-1时为永不使用不符合timeDiscard的缓存）
     * @param geocode      是否对地址为空的情况（如gps位置）自动进行逆地址解析，解析失败将返回整体失败
     * @param listener     定位回调
     */
    public void requestLocationNoCache(long minTime, long timeAccepted, long maxTimeRange, final boolean geocode, final NotifyLocationListener listener) {
        restartClient();
        geocode(geocode, listener);
        requestLocation(minTime);
    }

    int reGeocodeTimes;

    private void geocode(boolean geocode, final NotifyLocationListener listener) {
        if (!geocode) {
            setNotifyLocationListener(listener);
        } else {
            setNotifyLocationListener(new NotifyLocationListener() {
                @Override
                public void notify(final LocationPointInfo locationPointInfo) {
                    if (locationPointInfo.isSuccess) {
                        if (TextUtils.isEmpty(locationPointInfo.addrStr)) {
                            LogCore.wtf("获取的地址为空，进行逆地址解析");

                            reGeocodeTimes = 0;
                            geo(locationPointInfo);
                        } else {
                            listener.notify(locationPointInfo);
                        }
                    } else {
                        listener.notify(locationPointInfo);
                    }
                }

                private void geo(final LocationPointInfo locationPointInfo) {
                    new GeocodeLocation(locationPointInfo.latitude, locationPointInfo.longitude).
                            geocodeAddressNotNull(mApplicationContext, new GeocodeLocation.CallBackDetailListener() {
                                @Override
                                public void getDetail(boolean isSuccess, int errorCode, String errorMsg, String address, String address_h, String address_h2, String province, String city) {
                                    if (!isSuccess && reGeocodeTimes++ < GEO_RETRY_TIMES) {
                                        LogCore.wtf("逆地址解析失败, reGeocodeTimes=" + reGeocodeTimes + " errorCode=" + errorCode + ", errorMsg=" + errorMsg);
                                        geo(locationPointInfo);
                                        return;
                                    }

                                    LogCore.wtf("逆地址解析成功, address=" + address + ", province=" + province + ", city=" + city);
                                    locationPointInfo.addrStr = address;
                                    locationPointInfo.addrStr_h = address_h;
                                    locationPointInfo.addrStr_h2 = address_h2;
                                    locationPointInfo.province = province;
                                    locationPointInfo.city = city;

                                    listener.notify(locationPointInfo);
                                }
                            });
                }
            });
        }
    }

    private void setNotifyLocationListener(NotifyLocationListener listener) {
        this.mLocationChangeListener = listener;
    }

    private void requestLocation() {
        LogCore.wtf("开始单次定位");
        if (mockVerify()) return;

        try {
            if (mLocationManagerProxy != null) {
                MapCodeHelper.getInstance().logWifiAndStation();
                mLocationOption.setOnceLocation(true);
                mLocationManagerProxy.setLocationOption(mLocationOption);
                requestType = RequestType.NORMAL;
                mLocationManagerProxy.startLocation();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void requestLocation(long minTime) {
        LogCore.wtf("开始持续定位，间隔时间=" + minTime);
        if (mockVerify()) return;

        try {
            if (mLocationManagerProxy != null) {
                MapCodeHelper.getInstance().logWifiAndStation();
                mLocationOption.setOnceLocation(false);
                mLocationOption.setInterval(minTime);
                mLocationManagerProxy.setLocationOption(mLocationOption);
                requestType = RequestType.NORMAL;
                mLocationManagerProxy.startLocation();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void cancelRequestLocation() {
        LogCore.wtf("取消定位");
        if (mLocationManagerProxy != null) {
            mLocationManagerProxy.stopLocation();
        }
    }


}
