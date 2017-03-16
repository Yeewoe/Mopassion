package org.yeewoe.mopassion.app.maintab.model;

import org.yeewoe.mopanet.callback.Callback;
import org.yeewoe.mopanet.protos.*;
import org.yeewoe.mopassion.app.common.service.BaseService;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/31.
 */
public class MaintabService extends BaseService {
    private static final String CLASS_NAME = "MaintabService";

    /**
     * 获取用户信息
     * @param  uids 用户的UID列表
     */
    public static  Callback.CallbackInfo<PBUsrBatchGetRsp> GetUserInfo(final  ArrayList<Long> uids) {
        final String METHOD_NAME = "GetUserInfo";
        logInterfaceCall(CLASS_NAME, METHOD_NAME, "uids =" + uids);
        final   Callback.CallbackInfo<PBUsrBatchGetRsp> result = new  Callback.CallbackInfo<>();
        MaintabProtoNet.GetUserInfo(uids, new Callback() {
            @Override
            public <T> void callback(CallbackInfo<T> info) {
                if (info.bError) {
                    result.bError = true;
                    result.errorCode = info.errorCode;
                    return;
                }
                PBUsrBatchGetRsp rsp = (PBUsrBatchGetRsp)info.mT;
                result.mT = rsp;
            }
        });

        logInterfaceReturn(CLASS_NAME, METHOD_NAME, result);
        return result;
    }

}
