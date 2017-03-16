package org.yeewoe.mopassion.app.nearby.model;

import org.yeewoe.mopanet.callback.Callback;
import org.yeewoe.mopanet.client.MopaNetConstants;
import org.yeewoe.mopanet.protos.PBMapPostion;
import org.yeewoe.mopanet.protos.PBTrdAddReq;
import org.yeewoe.mopanet.protos.PBTrdAddRsp;
import org.yeewoe.mopanet.protos.PBTrdGetReq;
import org.yeewoe.mopanet.protos.PBTrdGetRsp;
import org.yeewoe.mopanet.protos.PBTrdNearReq;
import org.yeewoe.mopanet.protos.PBTrdNearRsp;
import org.yeewoe.mopanet.protos.PBTrdPullUserReq;
import org.yeewoe.mopanet.protos.PBTrdPullUserRsp;
import org.yeewoe.mopanet.protos.PBTrends;
import org.yeewoe.mopanet.template.NetSendRequestTemplate;
import org.yeewoe.mopassion.db.po.MapPosition;

import java.io.IOException;
import java.util.List;

/**
 * 动态网络接口
 * Created by wyw on 2016/3/22.
 */
public class TrendProtobufNet {

    /**
     * 创建动态
     */
    public static void add(final PBTrends trend, Callback callback) {
        NetSendRequestTemplate template = new NetSendRequestTemplate() {
            @Override
            public byte[] setRequestParams() {
                PBTrdAddReq req =  new PBTrdAddReq.Builder().trend(trend).build();
                return PBTrdAddReq.ADAPTER.encode(req);
            }

            @Override
            public void handleResponse(byte[] bytes, Callback callback) throws IOException {
                PBTrdAddRsp rsp = PBTrdAddRsp.ADAPTER.decode(bytes);
                doCallback(callback, rsp, rsp.result);
            }
        };
        template.sendRequest(MopaNetConstants.SRVOP_TRENDS_ADD_REQ, callback);
    }

    /**
     * 批量查询动态
     */
    public static void get(final List<Long> ids, Callback callback) {
        NetSendRequestTemplate template = new NetSendRequestTemplate() {
            @Override
            public byte[] setRequestParams() {
                PBTrdGetReq req =  new PBTrdGetReq.Builder().ids(ids).build();
                return PBTrdGetReq.ADAPTER.encode(req);
            }

            @Override
            public void handleResponse(byte[] bytes, Callback callback) throws IOException {
                PBTrdGetRsp rsp = PBTrdGetRsp.ADAPTER.decode(bytes);
                doCallback(callback, rsp, rsp.result);
            }
        };
        template.sendRequest(MopaNetConstants.SRVOP_TRENDS_GET_REQ, callback);
    }


    /**
     * 获取附近的动态
     * @param position 地址
     * @param min     最小范围
     * @param max     最大范围
     */
    public static void getNear(final MapPosition position, final int purpose, final long min, final long max, final int count, Callback callback) {
        NetSendRequestTemplate template = new NetSendRequestTemplate() {
            @Override
            public byte[] setRequestParams() {
                PBMapPostion centerLoc = new PBMapPostion.Builder().addr(position.getAddress()).
                        lon(position.getLon()).lat(position.getLat()).build();
                PBTrdNearReq.Builder reqBuilder = new PBTrdNearReq.Builder();
                reqBuilder.center_loc(centerLoc).min_distance(min).max_distance(max).purpose(purpose);
                if (count >= 0) {
                    reqBuilder.num(count);
                }
                PBTrdNearReq req = reqBuilder.build();
                return PBTrdNearReq.ADAPTER.encode(req);
            }

            @Override
            public void handleResponse(byte[] bytes, Callback callback) throws IOException {
                PBTrdNearRsp rsp = PBTrdNearRsp.ADAPTER.decode(bytes);
                doCallback(callback, rsp, rsp.result);
            }
        };
        template.sendRequest(MopaNetConstants.SRVOP_TRENDS_NEAR_REQ, callback);
    }

    /**
     * 获取指定用户动态
     */
    public static void getUserTrends(final long uid, final long startId, final long count, final  Callback callback) {
        NetSendRequestTemplate template = new NetSendRequestTemplate() {
            @Override
            public byte[] setRequestParams() {
                PBTrdPullUserReq.Builder reqBuilder = new PBTrdPullUserReq.Builder();
                reqBuilder.uid(uid);
                if (startId > 0) {
                    reqBuilder.start_id(startId - 1);
                }
                if (count > 0) {
                    reqBuilder.count(count);
                }
                PBTrdPullUserReq req = reqBuilder.build();
                return PBTrdPullUserReq.ADAPTER.encode(req);
            }

            @Override
            public void handleResponse(byte[] bytes, Callback callback) throws IOException {
                PBTrdPullUserRsp rsp = PBTrdPullUserRsp.ADAPTER.decode(bytes);
                doCallback(callback, rsp, rsp.result);
            }
        };
        template.sendRequest(MopaNetConstants.SRVOP_TRENDS_PULL_USER_REQ, callback);
    }

}
