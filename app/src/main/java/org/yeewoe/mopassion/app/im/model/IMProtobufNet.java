package org.yeewoe.mopassion.app.im.model;

import org.yeewoe.mopanet.callback.Callback;
import org.yeewoe.mopanet.client.MopaNetConstants;
import org.yeewoe.mopanet.protos.PBImMsg;
import org.yeewoe.mopanet.protos.PBImPostReq;
import org.yeewoe.mopanet.protos.PBImPostRsp;
import org.yeewoe.mopanet.protos.PBImSyncReq;
import org.yeewoe.mopanet.protos.PBImSyncRsp;
import org.yeewoe.mopanet.template.NetSendRequestTemplate;

import java.io.IOException;

/**
 * Created by Administrator on 2016/3/30.
 */
public class IMProtobufNet {



    //发送IM消息
    public static void postImMsg(final PBImMsg pbImMsg, Callback callback) {
        NetSendRequestTemplate template = new NetSendRequestTemplate() {
            @Override
            public byte[] setRequestParams() {
                PBImPostReq imPostReq = new PBImPostReq.Builder().imsg(pbImMsg).build();
                return PBImPostReq.ADAPTER.encode(imPostReq);
            }

            @Override
            public void handleResponse(byte[] bytes, Callback callback) throws IOException {
                PBImPostRsp rsp = PBImPostRsp.ADAPTER.decode(bytes);
                doCallback(callback, rsp, rsp.result);
            }
        };
        template.sendRequest(MopaNetConstants.SRVOP_IM_POST_MSG_REQ, callback);
    }

    //刚登陆时，同步离线消息.
    public static void syncImMsg(final Long startImMsgId, final Integer count,  Callback callback) {
        NetSendRequestTemplate template = new NetSendRequestTemplate() {
            @Override
            public byte[] setRequestParams() {
                PBImSyncReq imSyncReq = new PBImSyncReq.Builder().count(count).start_imsgid(startImMsgId).build();
                return PBImSyncReq.ADAPTER.encode(imSyncReq);
            }

            @Override
            public void handleResponse(byte[] bytes, Callback callback) throws IOException {
                PBImSyncRsp rsp = PBImSyncRsp.ADAPTER.decode(bytes);
                doCallback(callback, rsp, rsp.result);
            }
        };
        template.sendRequest(MopaNetConstants.SRVOP_IM_SYNC_REQ, callback);
    }
}
