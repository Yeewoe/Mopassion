package org.yeewoe.mopassion.app.common.net;

import org.yeewoe.mopanet.callback.Callback;
import org.yeewoe.mopanet.client.MopaNetConstants;
import org.yeewoe.mopanet.protos.PBLocNearUserReq;
import org.yeewoe.mopanet.protos.PBLocNearUserRsp;
import org.yeewoe.mopanet.protos.PBMapPostion;
import org.yeewoe.mopanet.protos.PBUser;
import org.yeewoe.mopanet.protos.PBUserFilter;
import org.yeewoe.mopanet.protos.PBUsrBatchGetReq;
import org.yeewoe.mopanet.protos.PBUsrBatchGetRsp;
import org.yeewoe.mopanet.protos.PBUsrCreateReq;
import org.yeewoe.mopanet.protos.PBUsrCreateRsp;
import org.yeewoe.mopanet.protos.PBUsrFansListReq;
import org.yeewoe.mopanet.protos.PBUsrFansListRsp;
import org.yeewoe.mopanet.protos.PBUsrFollowListReq;
import org.yeewoe.mopanet.protos.PBUsrFollowListRsp;
import org.yeewoe.mopanet.protos.PBUsrFollowOp;
import org.yeewoe.mopanet.protos.PBUsrFollowReq;
import org.yeewoe.mopanet.protos.PBUsrFollowRsp;
import org.yeewoe.mopanet.protos.PBUsrFriendListReq;
import org.yeewoe.mopanet.protos.PBUsrFriendListRsp;
import org.yeewoe.mopanet.protos.PBUsrGetReq;
import org.yeewoe.mopanet.protos.PBUsrGetRsp;
import org.yeewoe.mopanet.protos.PBUsrSetReq;
import org.yeewoe.mopanet.protos.PBUsrSetRsp;
import org.yeewoe.mopanet.template.NetSendRequestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户相关接口
 * Created by wyw on 2016/4/2.
 */
public class UserProtobufNet {

    //创建用户
    public static void createUser(final String  account, final String nickname, final int gender, Callback callback) {
        NetSendRequestTemplate template = new NetSendRequestTemplate() {
            @Override
            public byte[] setRequestParams() {
                ArrayList<PBUser> users = new ArrayList<PBUser>();
                users.add(new PBUser.Builder().uid(0L).account(account).name(nickname).sex(gender).build());
                PBUsrCreateReq ucreq = new PBUsrCreateReq.Builder().users(users).build();
                return PBUsrCreateReq.ADAPTER.encode(ucreq);
            }

            @Override
            public void handleResponse(byte[] bytes, Callback callback) throws IOException {
                PBUsrCreateRsp rsp = PBUsrCreateRsp.ADAPTER.decode(bytes);
                doCallback(callback, rsp, rsp.result);
            }
        };
        template.sendRequest(MopaNetConstants.SRVOP_USR_CREATE_REQ, callback);
    }

    // 编辑用户
    public static void set(final PBUser user, final PBUserFilter userFilter, Callback callback) {
        NetSendRequestTemplate template = new NetSendRequestTemplate() {
            @Override
            public byte[] setRequestParams() {
                PBUsrSetReq ucreq = new PBUsrSetReq.Builder().user(user).filter(userFilter).build();
                return PBUsrSetReq.ADAPTER.encode(ucreq);
            }

            @Override
            public void handleResponse(byte[] bytes, Callback callback) throws IOException {
                PBUsrSetRsp rsp = PBUsrSetRsp.ADAPTER.decode(bytes);
                doCallback(callback, rsp, rsp.result);
            }
        };
        template.sendRequest(MopaNetConstants.SRVOP_USR_SET_REQ, callback);
    }

    // 获取单个用户
    public static void get(final Long sid, Callback callback) {
        NetSendRequestTemplate template = new NetSendRequestTemplate() {
            @Override
            public byte[] setRequestParams() {
                PBUsrGetReq req = new PBUsrGetReq.Builder().uid(sid).build();
                return PBUsrGetReq.ADAPTER.encode(req);
            }

            @Override
            public void handleResponse(byte[] bytes, Callback callback) throws IOException {
                PBUsrGetRsp rsp = PBUsrGetRsp.ADAPTER.decode(bytes);
                doCallback(callback, rsp, rsp.result);
            }
        };
        template.sendRequest(MopaNetConstants.SRVOP_USR_GET_REQ, callback);
    }

    // 获取多个用户
    public static void batchGet(final List<Long> sids, Callback callback) {
        NetSendRequestTemplate template = new NetSendRequestTemplate() {
            @Override
            public byte[] setRequestParams() {
                PBUsrBatchGetReq req = new PBUsrBatchGetReq.Builder().uids(sids).build();
                return PBUsrBatchGetReq.ADAPTER.encode(req);
            }

            @Override
            public void handleResponse(byte[] bytes, Callback callback) throws IOException {
                PBUsrBatchGetRsp rsp = PBUsrBatchGetRsp.ADAPTER.decode(bytes);
                doCallback(callback, rsp, rsp.result);
            }
        };
        template.sendRequest(MopaNetConstants.SRVOP_USR_BATCH_GET_REQ, callback);
    }

    // 获取好友列表
    public static void getFriendList(Callback callback) {
        NetSendRequestTemplate template = new NetSendRequestTemplate() {
            @Override
            public byte[] setRequestParams() {
                PBUsrFriendListReq req = new PBUsrFriendListReq.Builder().reserve(0).build();
                return PBUsrFriendListReq.ADAPTER.encode(req);
            }

            @Override
            public void handleResponse(byte[] bytes, Callback callback) throws IOException {
                PBUsrFriendListRsp rsp = PBUsrFriendListRsp.ADAPTER.decode(bytes);
                doCallback(callback, rsp, rsp.result);
            }
        };
        template.sendRequest(MopaNetConstants.SRVOP_USR_FRIEND_LIST_REQ, callback);
    }

    // 获取关注列表
    public static void getFollowList(Callback callback) {
        NetSendRequestTemplate template = new NetSendRequestTemplate() {
            @Override
            public byte[] setRequestParams() {
                PBUsrFollowListReq req = new PBUsrFollowListReq.Builder().reserve(0).build();
                return PBUsrFollowListReq.ADAPTER.encode(req);
            }

            @Override
            public void handleResponse(byte[] bytes, Callback callback) throws IOException {
                PBUsrFollowListRsp rsp = PBUsrFollowListRsp.ADAPTER.decode(bytes);
                doCallback(callback, rsp, rsp.result);
            }
        };
        template.sendRequest(MopaNetConstants.SRVOP_USR_FOLLOW_LIST_REQ, callback);
    }

    // 获取粉丝列表
    public static void getFansList(final int skip, final int count, Callback callback) {
        NetSendRequestTemplate template = new NetSendRequestTemplate() {
            @Override
            public byte[] setRequestParams() {
                PBUsrFansListReq.Builder builder = new PBUsrFansListReq.Builder();
                if (skip >= 0) {
                    builder.skip(skip);
                }
                if (count >= 0) {
                    builder.limit(count);
                }
                PBUsrFansListReq req = builder.build();
                return PBUsrFansListReq.ADAPTER.encode(req);
            }

            @Override
            public void handleResponse(byte[] bytes, Callback callback) throws IOException {
                PBUsrFansListRsp rsp = PBUsrFansListRsp.ADAPTER.decode(bytes);
                doCallback(callback, rsp, rsp.result);
            }
        };
        template.sendRequest(MopaNetConstants.SRVOP_USR_FANS_LIST_REQ, callback);
    }

    // 获取附近用户
    public static void getNearUser(final long min, final long max, final int num, final PBMapPostion mapPostion, Callback callback) {
        NetSendRequestTemplate template = new NetSendRequestTemplate() {
            @Override
            public byte[] setRequestParams() {
                PBLocNearUserReq req = new PBLocNearUserReq.Builder().center_loc(mapPostion)
                        .min_distance(min).max_distance(max).num(num).build();
                return PBLocNearUserReq.ADAPTER.encode(req);
            }

            @Override
            public void handleResponse(byte[] bytes, Callback callback) throws IOException {
                PBLocNearUserRsp rsp = PBLocNearUserRsp.ADAPTER.decode(bytes);
                doCallback(callback, rsp, rsp.result);
            }
        };
        template.sendRequest(MopaNetConstants.ASRVOP_LOCATION_NEAR_USER_REQ, callback);
    }

    // 关注
    public static void follow(final long sid, Callback callback) {
        NetSendRequestTemplate template = new NetSendRequestTemplate() {
            @Override
            public byte[] setRequestParams() {
                List<Long> ids = new ArrayList<>();
                ids.add(sid);
                PBUsrFollowReq req = new PBUsrFollowReq.Builder().uids(ids).op(PBUsrFollowOp.UFOP_ADD.getValue()).build();
                return PBUsrFollowReq.ADAPTER.encode(req);
            }

            @Override
            public void handleResponse(byte[] bytes, Callback callback) throws IOException {
                PBUsrFollowRsp rsp = PBUsrFollowRsp.ADAPTER.decode(bytes);
                doCallback(callback, rsp, rsp.result);
            }
        };
        template.sendRequest(MopaNetConstants.SRVOP_USR_FOLLOW_REQ, callback);
    }
}
