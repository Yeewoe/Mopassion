package org.yeewoe.mopassion.app.auth.model;

import org.yeewoe.mopanet.callback.Callback;
import org.yeewoe.mopanet.callback.CallbackUtils;
import org.yeewoe.mopanet.client.MopaNetConstants;
import org.yeewoe.mopanet.protos.PBAthAlgorithm;
import org.yeewoe.mopanet.protos.PBEntryTimeSwapReq;
import org.yeewoe.mopanet.protos.PBEntryTimeSwapRsp;
import org.yeewoe.mopanet.protos.PBImSyncReq;
import org.yeewoe.mopanet.protos.PBImSyncRsp;
import org.yeewoe.mopanet.protos.PBUser;
import org.yeewoe.mopanet.protos.PBUsrAuthReq;
import org.yeewoe.mopanet.protos.PBUsrAuthRsp;
import org.yeewoe.mopanet.protos.PBUsrCreateReq;
import org.yeewoe.mopanet.protos.PBUsrCreateRsp;
import org.yeewoe.mopanet.protos.PBUsrLoginReq;
import org.yeewoe.mopanet.protos.PBUsrLoginRsp;
import org.yeewoe.mopanet.protos.PBUsrLogoutReq;
import org.yeewoe.mopanet.protos.PBUsrLogoutRsp;
import org.yeewoe.mopanet.protos.PBUsrTicketReq;
import org.yeewoe.mopanet.protos.PBUsrTicketRsp;
import org.yeewoe.mopanet.template.NetSendRequestTemplate;
import org.yeewoe.mopassion.utils.LogCore;

import java.io.IOException;
import java.util.ArrayList;

import okio.ByteString;

/**
 * 登录授权网络接口
 * Created by wyw on 2016/3/22.
 */
public class AuthProtobufNet {

    public static void login(final String account, Callback callback) {
        NetSendRequestTemplate template = new NetSendRequestTemplate() {
            @Override
            public byte[] setRequestParams() {
                PBUsrLoginReq req = new PBUsrLoginReq.Builder().account(account).build();
                return PBUsrLoginReq.ADAPTER.encode(req);
            }

            @Override
            public void handleResponse(byte[] bytes, Callback callback) throws IOException {
                PBUsrLoginRsp rsp = PBUsrLoginRsp.ADAPTER.decode(bytes);
                doCallback(callback, rsp, rsp.result);
            }
        };

        template.sendRequest(MopaNetConstants.SRVOP_USR_LOGIN_REQ, callback);
    }

    public static void auth(final ByteString md5, Callback callback) {
        NetSendRequestTemplate template = new NetSendRequestTemplate() {
            @Override
            public byte[] setRequestParams() {
                PBUsrAuthReq req = new PBUsrAuthReq.Builder().challenge_response(md5).build();
                LogCore.i("Send Auth req : " + req);
                return PBUsrAuthReq.ADAPTER.encode(req);
            }

            @Override
            public void handleResponse(byte[] bytes, Callback callback) throws IOException {
                PBUsrAuthRsp rsp = PBUsrAuthRsp.ADAPTER.decode(bytes);
                doCallback(callback, rsp, rsp.result);
            }
        };

        template.sendRequest(MopaNetConstants.SRVOP_USR_AUTH_REQ, callback);
    }

    public static void ticket(final long uid, final String seed, final ByteString md5, Callback callback) {
        NetSendRequestTemplate template = new NetSendRequestTemplate() {
            @Override
            public byte[] setRequestParams() {
                PBUsrTicketReq req = new PBUsrTicketReq.Builder().uid(uid).
                        algo(PBAthAlgorithm.ATHALGO_MD5.getValue()).
                        seed(seed).
                        verify(md5).
                        build();
                return PBUsrTicketReq.ADAPTER.encode(req);
            }

            @Override
            public void handleResponse(byte[] bytes, Callback callback) throws IOException {
                PBUsrTicketRsp rsp = PBUsrTicketRsp.ADAPTER.decode(bytes);
                doCallback(callback, rsp, rsp.result);
            }
        };

        template.sendRequest(MopaNetConstants.SRVOP_USR_TICKET_REQ, callback);
    }


    //退出登录
    public static void logout(final Long uid, Callback callback) {
        NetSendRequestTemplate template = new NetSendRequestTemplate() {
            @Override
            public byte[] setRequestParams() {
                PBUsrLogoutReq uLogoutReq = new  PBUsrLogoutReq.Builder().uid(uid).build();
                return PBUsrLogoutReq.ADAPTER.encode(uLogoutReq);
            }

            @Override
            public void handleResponse(byte[] bytes, Callback callback) throws IOException {
                PBUsrLogoutRsp rsp = PBUsrLogoutRsp.ADAPTER.decode(bytes);
                doCallback(callback, rsp, rsp.result);
            }
        };
        template.sendRequest(MopaNetConstants.SRVOP_USR_LOGOUT_REQ, callback);
    }
}
