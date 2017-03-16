package org.yeewoe.mopassion.db.po;

import android.support.annotation.NonNull;

import org.yeewoe.mopassion.app.location.manager.LocationPointInfo;

import java.io.Serializable;

/**
 * 地理位置
 * Created by wyw on 2016/4/5.
 */
public class MapPosition implements Serializable {
    public static final String ADDRESS_SPLIT = ":::";
    private String address;
    private double lon;
    private double lat;
    private String country;
    private String city;

    public String getAddress() {
        return address == null ? "" : address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getCity() {
        return city == null ? "" : city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country == null ? "" : country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * 构造方法，
     * @param formattedAddress 格式化后的地址， 格式: address:::city:::country
     */
    public MapPosition(String formattedAddress, Double lon, Double lat) {
        if (formattedAddress != null) {
            String[] split = formattedAddress.split(ADDRESS_SPLIT);
            if (split.length > 1) {
                city = split[1];
                if (split.length > 2) {
                    country = split[2];
                }
            } else {
                this.address = formattedAddress;
            }
        }
        if (lon != null) {
            this.lon = lon;
        } else {
            this.lon = 0;
        }
        if (lat != null) {
            this.lat = lat;
        } else {
            this.lat = 0;
        }
    }

    public MapPosition(@NonNull LocationPointInfo pointInfo) {
        this.address = pointInfo.addrStr;
        this.city = pointInfo.city;
        this.country = pointInfo.country;
        this.lon = pointInfo.longitude;
        this.lat = pointInfo.latitude;
    }

    /**
     * 获取格式化后的地址
     */
    public String toFormatedAddress() {
        return address + ADDRESS_SPLIT + city + ADDRESS_SPLIT + country;
    }

    @Override public String toString() {
        return "MapPosition{" +
                "address='" + address + '\'' +
                ", lon=" + lon +
                ", lat=" + lat +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
