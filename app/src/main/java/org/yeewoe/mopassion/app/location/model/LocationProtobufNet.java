package org.yeewoe.mopassion.app.location.model;

import org.yeewoe.mopanet.callback.Callback;
import org.yeewoe.mopanet.client.MopaNetConstants;
import org.yeewoe.mopanet.protos.PBLocReportReq;
import org.yeewoe.mopanet.protos.PBLocReportRsp;
import org.yeewoe.mopanet.protos.PBMapPostion;
import org.yeewoe.mopanet.template.NetSendRequestTemplate;
import org.yeewoe.mopassion.db.po.MapPosition;

import java.io.IOException;

/**
 * 位置网络接口
 * Created by wyw on 2016/3/22.
 */
public class LocationProtobufNet {
    /**
     * @param position 地址
     */
    public static void report(final MapPosition position, Callback callback) {
        NetSendRequestTemplate template = new NetSendRequestTemplate() {
            @Override
            public byte[] setRequestParams() {
                PBMapPostion loc = new PBMapPostion.Builder().lon(position.getLon()).lat(position.getLat()).addr(position.getAddress()).build();
                PBLocReportReq req = new PBLocReportReq.Builder().loc(loc).build();
                return PBLocReportReq.ADAPTER.encode(req);
            }

            @Override
            public void handleResponse(byte[] bytes, Callback callback) throws IOException {
                PBLocReportRsp rsp = PBLocReportRsp.ADAPTER.decode(bytes);
                doCallback(callback, rsp, rsp.result);
            }
        };
        template.sendRequest(MopaNetConstants.ASRVOP_LOCATION_REPORT_REQ, callback);
    }
}
