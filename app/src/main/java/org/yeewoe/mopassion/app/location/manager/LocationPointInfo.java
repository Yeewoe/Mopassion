package org.yeewoe.mopassion.app.location.manager;

import java.util.List;

/**
 * 定位位置
 */
public class LocationPointInfo {

    // 定位结果时间
    public long time;
    // 纬度
    public double latitude;
    // 经度
    public double longitude;
    // 定位精度半径,单位是米
    public float radius;
    // 文字描述的地址（原位置）
    public String addrStr;
    // 文字描述的地址 （处理后的，去掉省级以上信息，比如去掉"广东省"）
    public String addrStr_h;
    // 文字描述的地址 （处理后的，去掉市县级以上信息，比如去掉"广东省深圳市"）
    public String addrStr_h2;
    // 获取省份信息
    public String province;
    // 获取城市信息
    public String city;
    // 获取国家信息
    public String country;
    // 获取区县信息
    public String district;
    // 获取poi信息
    public List<POI> poi;
    // GPS定位速度
    public float speed;
    // GPS海拔高度状态
    public double altitude;
    // 方向
    public float direct;
    // 是否成功
    public boolean isSuccess;
    // 错误码
    public int errorCode;
    // 错误提示
    public String errorStr;
    // 是否移动
    public boolean isMove;
    /**
     * 定位类型 参考 {@link com.amap.api.location.AMapLocation#LOCATION_TYPE_GPS}等
     */
    public int locationType;
    /**
     * 定位细节
     */
    public String locationDetail;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public String getAddrStr() {
        return addrStr;
    }

    public void setAddrStr(String addrStr) {
        this.addrStr = addrStr;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public List<POI> getPoi() {
        return poi;
    }

    public void setPoi(List<POI> poi) {
        this.poi = poi;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getDerect() {
        return direct;
    }

    public void setDerect(float derect) {
        this.direct = derect;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitue) {
        this.altitude = altitue;
    }

    public String getAddrStr_h() {
        return addrStr_h;
    }

    public void setAddrStr_h(String addrStr_h) {
        this.addrStr_h = addrStr_h;
    }

    public String getAddrStr_h2() {
        return addrStr_h2;
    }

    public void setAddrStr_h2(String addrStr_h2) {
        this.addrStr_h2 = addrStr_h;
    }

    public int getLocationType() {
        return locationType;
    }

    public void setLocationType(int locationType) {
        this.locationType = locationType;
    }

    public String getLocationDetail() {
        return locationDetail;
    }

    public void setLocationDetail(String locationDetail) {
        this.locationDetail = locationDetail;
    }

    public static enum LocationType {
        AMAP, NETWORK, GPS
    }

    public class POI {
        public String addr;
        public double y;
        public double x;
        public String name;
        public String tel;
        public String dis;

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getDis() {
            return dis;
        }

        public void setDis(String dis) {
            this.dis = dis;
        }

    }

    @Override public String toString() {
        return "LocationPointInfo{" +
                "addrStr='" + addrStr + '\'' +
                ", errorCode=" + errorCode +
                ", errorStr='" + errorStr + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", radius=" + radius +
                '}';
    }
}
