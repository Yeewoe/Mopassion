package org.yeewoe.mopassion.app.nearby.model;

import org.yeewoe.mopassion.db.po.BasePo;
import org.yeewoe.mopassion.db.po.MapPosition;

/**
 * Created by wyw on 2016/4/25.
 */
public class LocUser extends BasePo {
    private double distance;
    private MapPosition mapPosition;
    private long locTime;
    private String signature;

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public MapPosition getMapPosition() {
        return mapPosition;
    }

    public void setMapPosition(MapPosition mapPosition) {
        this.mapPosition = mapPosition;
    }

    public long getLocTime() {
        return locTime;
    }

    public void setLocTime(long locTime) {
        this.locTime = locTime;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
