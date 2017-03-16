package org.yeewoe.mopassion.app.auth.model;

import com.facebook.AccessTokenTracker;
import com.facebook.login.LoginManager;

import org.yeewoe.commonutils.common.assist.Base64;
import org.yeewoe.commonutils.common.assist.Checks;
import org.yeewoe.commonutils.common.assist.Networks;
import org.yeewoe.mopanet.callback.Callback;
import org.yeewoe.mopanet.callback.CallbackUtils;
import org.yeewoe.mopanet.client.MopaErrno;
import org.yeewoe.mopanet.protos.PBUsrAuthRsp;
import org.yeewoe.mopanet.protos.PBUsrLoginRsp;
import org.yeewoe.mopanet.protos.PBUsrLogoutRsp;
import org.yeewoe.mopassion.MopaApplication;
import org.yeewoe.mopassion.app.common.net.UserProtobufNet;
import org.yeewoe.mopassion.app.common.service.UserService;
import org.yeewoe.mopassion.mangers.ClientManger;
import org.yeewoe.mopassion.mangers.UserDataMananger;
import org.yeewoe.mopassion.db.po.User;
import org.yeewoe.mopassion.app.common.service.BaseService;
import org.yeewoe.mopassion.utils.LogCore;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Formatter;

import okio.ByteString;

/**
 * 登录授权业务接口
 * Created by wyw on 2016/3/25.
 */
public class AuthService extends BaseService {
    private static final String CLASS_NAME = "AuthService";

    private UserService userService;

    public AuthService() {
        this.userService = new UserService();
    }

    /**
     * 登录
     *
     * @param account  用户名
     * @param password 密码
     */
    public Callback.CallbackInfo<Void> login(String account, final String password) {
        // TODO 后期要把这些敏感数据不打印到日志上
        final String METHOD_NAME = "login";
        logInterfaceCall(CLASS_NAME, METHOD_NAME, "account=" + account + ", password=" + password);

        final Callback.CallbackInfo<Void> result = new Callback.CallbackInfo<>();

        // 1. 登录
        AuthProtobufNet.login(account, new Callback() {
            @Override
            public <T> void callback(CallbackInfo<T> info) {
                try {
                    if (info.bError) {
                        CallbackUtils.buildErrorCallbackInfo(result, info);
                        return;
                    }
                    PBUsrLoginRsp rsp = (PBUsrLoginRsp) info.mT;

                    byte[] passwd = password.getBytes("UTF8");
                    byte[] seed = rsp.challenge_seed.toByteArray();
                    byte[] merge = new byte[passwd.length + seed.length];
                    System.arraycopy(passwd, 0, merge, 0, passwd.length);
                    System.arraycopy(seed, 0, merge, passwd.length, seed.length);
                    MessageDigest md = MessageDigest.getInstance("MD5");
                    byte[] buff = md.digest(merge);
                    ByteString md5 = ByteString.of(buff);

                    logRun(CLASS_NAME, METHOD_NAME, "AuthProtobufNet.auth 开始执行，md5=" + md5);
                    // 2. 认证
                    AuthProtobufNet.auth(md5, new Callback() {
                        @Override
                        public <T> void callback(CallbackInfo<T> info) {
                            if (info.bError) {
                                CallbackUtils.buildErrorCallbackInfo(result, info);
                                return;
                            }
                            PBUsrAuthRsp rsp = (PBUsrAuthRsp) info.mT;

                            // 3. 获取个人信息
                            CallbackInfo<User> userCallbackInfo = userService.getNet(rsp.uid);
                            if (!userCallbackInfo.bError) {
                                User me = userCallbackInfo.mT;
                                UserDataMananger.getInstance().setMe(me);
                                LogCore.wtf("save ticket: " + rsp.ticket.base64() + ", bytes length: " + rsp.ticket.base64().getBytes().length) ;
                                ClientManger.getInstance().setTicket(rsp.ticket.base64());
                                logRun(CLASS_NAME, METHOD_NAME, "me: " + me);
                            } else {
                                // 获取用户失败也认为失败
                                CallbackUtils.buildErrorCallbackInfo(result, userCallbackInfo);
                            }
                        }
                    });
                } catch (Exception e) {
                    logUnknown(e);
                    CallbackUtils.buildThreadInterruptedCallbackInfo(result);
                }
            }
        });

        logInterfaceReturn(CLASS_NAME, METHOD_NAME, result);
        return result;
    }

    /**
     * 注册
     *
     * @param account  用户名
     * @param nickname 昵称
     * @param gender   性别
     */
    public Callback.CallbackInfo<Void> register(final String account, String nickname, int gender) {
        final String METHOD_NAME = "register";
        logInterfaceCall(CLASS_NAME, METHOD_NAME, "account=" + account + ", nickname=" + nickname);
        final Callback.CallbackInfo<Void> result = new Callback.CallbackInfo<>();

        UserProtobufNet.createUser(account, nickname, gender, new Callback() {
            @Override
            public <T> void callback(CallbackInfo<T> info) {
                if (info.bError) {
                    CallbackUtils.buildErrorCallbackInfo(result, info);
                    return;
                }

                CallbackInfo<Void> login = login(account, account + "\0");
                result.bError = login.bError;
                result.errorCode = login.errorCode;
            }
        });

        logInterfaceReturn(CLASS_NAME, METHOD_NAME, result);
        return result;
    }

    /**
     * 走ticket 认证
     */
    public Callback.CallbackInfo<Void> auth() {
        final String METHOD_NAME = "auth";
        logInterfaceCall(CLASS_NAME, METHOD_NAME, "");

        long meUid = UserDataMananger.getInstance().getMeUid();
        if (meUid <= 0) {
            // 本地没有保存过uid
            return (Callback.CallbackInfo<Void>) CallbackUtils.buildErrorCallbackInfo(-1);
        }
        String ticketStr = ClientManger.getInstance().getTicket();
        if (!Checks.check(ticketStr)) {
            // 本地没有保存过ticket
            return (Callback.CallbackInfo<Void>) CallbackUtils.buildErrorCallbackInfo(-1);
        }
        byte[] ticket = Base64.decode(ticketStr, Base64.DEFAULT);
        LogCore.wtf("load ticket: " + ticketStr + ", bytes length:" + ticket.length);
        int seed = (int) ((new Date().getTime()) / 1000); //���ֵ����
        Formatter fm = new Formatter();
        fm.format("%d", seed);
        String seedstr = fm.toString();
        byte[] seedbytes = new byte[0];

        final Callback.CallbackInfo<Void> result = new Callback.CallbackInfo<>();
        try {
            seedbytes = seedstr.getBytes("UTF8");
            byte[] buff = new byte[seedbytes.length + ticket.length];
            System.arraycopy(ticket, 0, buff, 0, ticket.length);
            System.arraycopy(seedbytes, 0, buff, ticket.length, seedbytes.length);
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] buffmd5 = md.digest(buff);
            ByteString md5 = ByteString.of(buffmd5);

            AuthProtobufNet.ticket(meUid, seedstr, md5, new Callback() {
                @Override public <T> void callback(CallbackInfo<T> info) {
                    if (info.bError) {
                        if (info.errorCode == MopaErrno.LOCAL_NET_ERROR.ordinal() ||
                                info.errorCode == MopaErrno.LOCAL_NETWORK_ERROR.ordinal() ||
                                info.errorCode == MopaErrno.LOCAL_NETWORK_UNAVAILABLE.ordinal()) {
                            // 网络问题
                            return ;
                        }
                        // TODO 非网络情况，认证失败
                         CallbackUtils.buildErrorCallbackInfo(result, info.errorCode);
                    }
                }
            });
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            return (Callback.CallbackInfo<Void>) CallbackUtils.buildErrorCallbackInfo(-1);
        }

        logInterfaceReturn(CLASS_NAME, METHOD_NAME, result);
        return result;
    }

    /**
     * 注销
     */
    public void logout(final Callback callback) {
        final String METHOD_NAME = "logout";
        logInterfaceCall(CLASS_NAME, METHOD_NAME, "");
        // 无网络直接回调
        if (!Networks.isAvailable(MopaApplication.getInstance())) {
            CallbackUtils.callbackInfoVoid(callback);
        }

        runOnNetTemplate(new Runnable() {
            @Override public void run() {
                AuthProtobufNet.logout(UserDataMananger.getInstance().getMeUid(), new Callback() {
                    @Override public <T> void callback(CallbackInfo<T> info) {
                        if (info.bError) {
                            CallbackUtils.errorCallback(callback, info.errorCode);
                            return ;
                        }
                        if (UserDataMananger.getInstance().isFacebook()) {
                            // fb 退出
                            LoginManager.getInstance().logOut();
                        } else if (UserDataMananger.getInstance().isTwitter()) {
                            // TODO twitter 退出

                        }

                        logInterfaceReturn(CLASS_NAME, METHOD_NAME, "");
                        CallbackUtils.callbackInfoVoid(callback);
                    }
                });
            }
        }, callback);

    }
}
