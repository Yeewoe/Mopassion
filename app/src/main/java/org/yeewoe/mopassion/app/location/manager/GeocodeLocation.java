package org.yeewoe.mopassion.app.location.manager;

import android.content.Context;
import android.text.TextUtils;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;

import java.text.NumberFormat;

/**
 * 逆地址解析
 */
public class GeocodeLocation {
    private double mLatitude;
    private double mLongitude;
    private CallBackListener mListener;

    public GeocodeLocation(double lat, double lon) {
        mLatitude = lat;
        mLongitude = lon;
    }

    public void geocodeAddress(Context context, CallBackListener listener) {
        if (listener == null) {
            return;
        }
        mListener = listener;
        RegeocodeQuery query = new RegeocodeQuery(new LatLonPoint(
                mLatitude, mLongitude), 200,
                GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        GeocodeSearch locationGeocoderSearch = new GeocodeSearch(context);
        locationGeocoderSearch.getFromLocationAsyn(query);// 设置同步逆地理编码请求
        locationGeocoderSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {

            @Override
            public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
                String address = "";
                if (MapCodeHelper.getInstance().isSucces(rCode)
                        && result != null
                        && result.getRegeocodeAddress() != null
                        && result.getRegeocodeAddress().getFormatAddress() != null) {
                    String provice = "";
                    if (!TextUtils.isEmpty(result.getRegeocodeAddress().getProvince())) {
                        provice = result.getRegeocodeAddress().getProvince();
                    }
                    address = LocationUtils.parseAddress(
                            result.getRegeocodeAddress()
                                    .getFormatAddress(), provice);
                }
                if (TextUtils.isEmpty(address)) {
                    NumberFormat nf = NumberFormat.getInstance();
                    nf.setMinimumFractionDigits(4);
                    address = nf.format(mLongitude) + "," + nf.format(mLatitude);
                }
                mListener.getAddress(address);
            }

            @Override
            public void onGeocodeSearched(GeocodeResult arg0, int arg1) {
            }
        });
    }

    /**
     * 同步逆地址解析
     * 保证有结果
     */
    public void geocodeAddressNotNull(Context context, final CallBackDetailListener listener) {
        if (listener == null) {
            return;
        }
        RegeocodeQuery query = new RegeocodeQuery(new LatLonPoint(
                mLatitude, mLongitude), 200,
                GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        GeocodeSearch locationGeocoderSearch = new GeocodeSearch(context);
        locationGeocoderSearch.getFromLocationAsyn(query);// 设置异步逆地理编码请求
        locationGeocoderSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {

            @Override
            public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
                String address = "";
                String address_h = "";
                String address_h2 = "";
                String provice = "";
                String city = "";
                boolean isSuccess = true;
                String errorMsg = "";
                if (MapCodeHelper.getInstance().isSucces(rCode)
                        && result != null
                        && result.getRegeocodeAddress() != null
                        && result.getRegeocodeAddress().getFormatAddress() != null) {
                    if (!TextUtils.isEmpty(result.getRegeocodeAddress().getProvince())) {
                        provice = result.getRegeocodeAddress().getProvince();
                    }
                    if (!TextUtils.isEmpty(result.getRegeocodeAddress().getCity())) {
                        city = result.getRegeocodeAddress().getCity();
                    }
                    address = result.getRegeocodeAddress().getFormatAddress();
                    address_h = LocationUtils.parseAddress(address, provice);
                    address_h2 = LocationUtils.parseAddress2(address, city);
                } else {
                    isSuccess = false;
                    errorMsg = MapCodeHelper.getInstance().getCodeStr(rCode);
                }
                if (TextUtils.isEmpty(address)) {
                    NumberFormat nf = NumberFormat.getInstance();
                    nf.setMinimumFractionDigits(4);
                    address = nf.format(mLongitude) + "," + nf.format(mLatitude);
                    address_h = address;
                    address_h2 = address;
                }

                listener.getDetail(isSuccess, rCode, errorMsg, address, address_h, address_h2, provice, city);
            }

            @Override
            public void onGeocodeSearched(GeocodeResult arg0, int arg1) {
            }
        });
    }

    /**
     * 同步逆地址解析
     * 可能失败
     */
    public void geocodeAddress(Context context, final CallBackDetailListener listener) {
        if (listener == null) {
            return;
        }
        RegeocodeQuery query = new RegeocodeQuery(new LatLonPoint(
                mLatitude, mLongitude), 200,
                GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        GeocodeSearch locationGeocoderSearch = new GeocodeSearch(context);
        locationGeocoderSearch.getFromLocationAsyn(query);// 设置异步逆地理编码请求
        locationGeocoderSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {

            @Override
            public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
                String address = "";
                String address_h = "";
                String address_h2 = "";
                String provice = "";
                String city = "";
                boolean isSuccess = true;
                String errorMsg = "";
                if (MapCodeHelper.getInstance().isSucces(rCode)
                        && result != null
                        && result.getRegeocodeAddress() != null
                        && result.getRegeocodeAddress().getFormatAddress() != null) {
                    if (!TextUtils.isEmpty(result.getRegeocodeAddress().getProvince())) {
                        provice = result.getRegeocodeAddress().getProvince();
                    }
                    if (!TextUtils.isEmpty(result.getRegeocodeAddress().getCity())) {
                        city = result.getRegeocodeAddress().getCity();
                    }

                    address = result.getRegeocodeAddress().getFormatAddress();
                    address_h = LocationUtils.parseAddress(address, provice);
                    address_h2 = LocationUtils.parseAddress2(address, city);
                } else {
                    isSuccess = false;
                    errorMsg = MapCodeHelper.getInstance().getCodeStr(rCode);
                }

                if (listener != null) {
                    listener.getDetail(isSuccess, rCode, errorMsg, address, address_h, address_h2, provice, city);
                }
            }

            @Override
            public void onGeocodeSearched(GeocodeResult arg0, int arg1) {
            }
        });
    }

    public interface CallBackListener {
        void getAddress(String address);
    }

    public interface CallBackDetailListener {
        /**
         * 结果回调
         *
         * @param address    原地址
         * @param address_h  去掉省份
         * @param address_h2 去掉市县级
         */
        void getDetail(boolean isSuccess, int errorCode, String errorMsg, String address, String address_h, String address_h2, String province, String city);
    }

}
