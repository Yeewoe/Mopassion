package org.yeewoe.mopassion.app.nearby.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import org.yeewoe.mopassion.app.common.model.MediaLineVo;
import org.yeewoe.mopassion.app.location.manager.LocationPointInfo;
import org.yeewoe.mopassion.app.location.manager.LocationUtils;
import org.yeewoe.mopassion.db.po.MapPosition;
import org.yeewoe.mopassion.db.po.Media;
import org.yeewoe.mopassion.db.po.Trend;
import org.yeewoe.mopassion.db.po.User;
import org.yeewoe.mopassion.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 动态列表VO
 * Created by wyw on 2016/4/7.
 */
public class TrendLineVo extends MediaLineVo<Trend> implements Parcelable {

    private String time;
    private long timeMillis;
    private String site;
    private double distance;
    private String city;
    private String country;

    protected TrendLineVo(Parcel in) {
        super(in);
        time = in.readString();
        timeMillis = in.readLong();
        site = in.readString();
        distance = in.readDouble();
        city = in.readString();
        country = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(time);
        dest.writeLong(timeMillis);
        dest.writeString(site);
        dest.writeDouble(distance);
        dest.writeString(city);
        dest.writeString(country);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TrendLineVo> CREATOR = new Creator<TrendLineVo>() {
        @Override
        public TrendLineVo createFromParcel(Parcel in) {
            return new TrendLineVo(in);
        }

        @Override
        public TrendLineVo[] newArray(int size) {
            return new TrendLineVo[size];
        }
    };

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getTimeMillis() {
        return timeMillis;
    }

    public void setTimeMillis(long timeMillis) {
        this.timeMillis = timeMillis;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
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

    private TrendLineVo(Trend source, Long uid, List<User> users, List<Media> medias) {
        super(source, uid, users, medias);
    }

    public static class Convertor {
        public static TrendLineVo convert(@NonNull Trend trend, List<User> users) {
            TrendLineVo result = new TrendLineVo(trend, trend.getCreateBy(), users, trend.getContents());
            result.time = TimeUtil.parseItemListTime(trend.getCreateTime(), true);
            result.timeMillis = trend.getCreateTime();
            if (trend.getPosition() != null) {
                result.site = trend.getPosition().getAddress();
                result.city = trend.getPosition().getCity();
                result.country = trend.getPosition().getCountry();
                LocationPointInfo myLocation = LocationUtils.loadLocation();
                if (myLocation != null) {
                    result.distance = LocationUtils.getDistance(new MapPosition(myLocation), trend.getPosition());
                }
            }

            return result;
        }

        @NonNull public static List<TrendLineVo> convert(List<Trend> trends, List<User> users) {
            if (trends == null) {
                return new ArrayList<>();
            }
            List<TrendLineVo> result = new ArrayList<>();
            for (Trend trend : trends) {
                result.add(convert(trend, users));
            }
            return result;
        }
    }
}
