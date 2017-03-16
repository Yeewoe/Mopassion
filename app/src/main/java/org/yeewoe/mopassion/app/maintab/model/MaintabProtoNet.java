package org.yeewoe.mopassion.app.maintab.model;

import org.yeewoe.mopanet.callback.Callback;
import org.yeewoe.mopanet.client.MopaNetConstants;
import org.yeewoe.mopanet.protos.*;
import org.yeewoe.mopanet.template.NetSendRequestTemplate;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/31.
 */
public class MaintabProtoNet {

    public static void GetUserInfo(final  ArrayList<Long> uids,  Callback callback)
    {
        //获取用户信息列表
        NetSendRequestTemplate template = new NetSendRequestTemplate() {
            @Override
            public byte[] setRequestParams() {
                PBUsrBatchGetReq usrBatchGetReq = new PBUsrBatchGetReq.Builder().uids(uids).build();
                return PBUsrBatchGetReq.ADAPTER.encode(usrBatchGetReq);
            }

            @Override
            public void handleResponse(byte[] bytes, Callback callback) throws IOException {
                PBUsrBatchGetRsp rsp = PBUsrBatchGetRsp.ADAPTER.decode(bytes);
                doCallback(callback, rsp, rsp.result);
            }
        };
        template.sendRequest(MopaNetConstants.SRVOP_USR_BATCH_GET_REQ, callback);
    }
}
