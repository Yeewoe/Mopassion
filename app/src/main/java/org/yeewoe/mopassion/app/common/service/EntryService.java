package org.yeewoe.mopassion.app.common.service;

import org.yeewoe.mopanet.callback.Callback;
import org.yeewoe.mopanet.callback.CallbackUtils;
import org.yeewoe.mopanet.protos.PBEntryTimeSwapRsp;
import org.yeewoe.mopassion.mangers.ClientManger;
import org.yeewoe.mopassion.app.common.net.EntryProtobufNet;

/**
 * 全局Entry 服务
 * Created by wyw on 2016/4/1.
 */
public class EntryService extends BaseService {
    private static final String CLASS_NAME = "EntryService";

    public void swapTime() {
        swapTime(null);
    }

    /**
     * 获取服务端时间
     */
    public void swapTime(final Callback callback) {
        final String METHOD_NAME = "swapTime";
        logInterfaceCall(CLASS_NAME, METHOD_NAME, "");
        runOnNetTemplate(new Runnable() {
            @Override
            public void run() {
                EntryProtobufNet.swapTime(new Callback() {
                    @Override
                    public <T> void callback(CallbackInfo<T> info) {
                        if (info.bError) {
                            if (callback != null) {
                                CallbackUtils.errorCallback(callback, info.errorCode);
                            }
                            return;
                        }
                        PBEntryTimeSwapRsp rsp = (PBEntryTimeSwapRsp) info.mT;
                        logRun(CLASS_NAME, METHOD_NAME, "rsp=" + rsp);
                        ClientManger.getInstance().setServerSwapTime(rsp.srv_time);
                        if (callback != null) {
                            CallbackUtils.callbackInfoLong(callback, rsp.srv_time);
                        }
                    }
                });
            }
        }, callback);
    }
}
