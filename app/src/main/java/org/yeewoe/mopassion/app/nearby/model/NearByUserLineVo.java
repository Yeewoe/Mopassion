package org.yeewoe.mopassion.app.nearby.model;

import android.support.annotation.NonNull;

import org.yeewoe.mopassion.app.common.model.BaseLineVo;
import org.yeewoe.mopassion.db.po.User;
import org.yeewoe.mopassion.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyw on 2016/4/25.
 */
public class NearByUserLineVo extends BaseLineVo<LocUser> {
    private String locTime;
    private double distance;

    public NearByUserLineVo(LocUser source, Long uid, List<User> users) {
        super(source, uid, users);
        locTime = TimeUtil.parseItemListTime(source.getLocTime(), true);
        distance = source.getDistance() / 1000;
        source.setSignature("test signature");
    }

    public String getLocTime() {
        return locTime;
    }

    public double getDistance() {
        return distance;
    }

    public static class Convertor {
        @NonNull public static List<NearByUserLineVo> convert(@NonNull List<LocUser> locUsers, @NonNull List<User> users) {
            List<NearByUserLineVo> result = new ArrayList<>();
            for (LocUser locUser : locUsers) {
                result.add(new NearByUserLineVo(locUser, locUser.getCreateBy(), users));

            }
            return result;
        }
    }
}
