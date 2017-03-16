package org.yeewoe.mopassion.app.location.model;

import org.yeewoe.commonutils.common.assist.TimeGate;
import org.yeewoe.mopanet.callback.Callback;
import org.yeewoe.mopassion.app.common.service.BaseService;
import org.yeewoe.mopassion.db.po.MapPosition;
import org.yeewoe.mopassion.utils.LogCore;
import org.yeewoe.mopassion.utils.TimeUtil;

import java.util.concurrent.Callable;

/**
 * 位置接口
 * Created by wyw on 2016/4/5.
 */
public class LocationService extends BaseService {
    final String CLASS_NAME = "LocationService";

    // 用于控制上报位置频率
    private static TimeGate locationTimeGate = new TimeGate(30 * TimeUtil.SECOND);

    /**
     * 上报位置，异步接口
     *
     * @param address 位置
     * @param lon     经度
     * @param lat     纬度
     */
    public void report(final String address, final double lon, final double lat) {
        locationTimeGate.chopGate(new Callable() {
            @Override public Object call() throws Exception {
                final String METHOD_NAME = "report";

                logInterfaceCall(CLASS_NAME, METHOD_NAME, "address=" + address + ", lon=" + lon + ", lat=" + lat);
                runOnNetTemplate(new Runnable() {
                    @Override public void run() {
                        LocationProtobufNet.report(new MapPosition(address, lon, lat), new Callback() {
                            @Override public <T> void callback(CallbackInfo<T> info) {
                            }
                        });
                        logInterfaceReturn(CLASS_NAME, METHOD_NAME, "");
                    }
                }, null);

                return null;
            }
        }, new Callable() {
            @Override public Object call() throws Exception {
                LogCore.wtf("频率控制，不上报位置");
                return null;
            }
        });
    }
}
