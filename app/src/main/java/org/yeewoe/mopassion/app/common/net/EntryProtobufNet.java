package org.yeewoe.mopassion.app.common.net;

import org.yeewoe.mopanet.callback.Callback;
import org.yeewoe.mopanet.client.MopaNetConstants;
import org.yeewoe.mopanet.protos.PBEntryTimeSwapReq;
import org.yeewoe.mopanet.protos.PBEntryTimeSwapRsp;
import org.yeewoe.mopanet.template.NetSendRequestTemplate;
import org.yeewoe.mopassion.utils.LogCore;

import java.io.IOException;

/**
 * Entry网络接口
 * Created by wyw on 2016/3/22.
 */
public class EntryProtobufNet {
    public static void swapTime(Callback callback) {
        NetSendRequestTemplate template = new NetSendRequestTemplate() {
            @Override
            public byte[] setRequestParams() {
                PBEntryTimeSwapReq req = new PBEntryTimeSwapReq.Builder().clt_time(System.currentTimeMillis() / 1000).build();
                return PBEntryTimeSwapReq.ADAPTER.encode(req);
            }

            @Override
            public void handleResponse(byte[] bytes, Callback callback) throws IOException {
                PBEntryTimeSwapRsp rsp = PBEntryTimeSwapRsp.ADAPTER.decode(bytes);
                doCallback(callback, rsp, rsp.result);
            }
        };
        template.sendRequest(MopaNetConstants.SRVOP_ENTRY_TIME_SWAP_REQ, callback);
    }
}
