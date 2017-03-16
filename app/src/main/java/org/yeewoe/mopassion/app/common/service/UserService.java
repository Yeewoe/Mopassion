package org.yeewoe.mopassion.app.common.service;

import android.os.UserManager;
import android.support.annotation.NonNull;

import org.yeewoe.commonutils.common.assist.Checks;
import org.yeewoe.commonutils.common.utils.JsonUtil;
import org.yeewoe.mopanet.callback.Callback;
import org.yeewoe.mopanet.callback.CallbackUtils;
import org.yeewoe.mopanet.protos.PBLocNearUserRsp;
import org.yeewoe.mopanet.protos.PBLocUser;
import org.yeewoe.mopanet.protos.PBUser;
import org.yeewoe.mopanet.protos.PBUserFilter;
import org.yeewoe.mopanet.protos.PBUsrBatchGetRsp;
import org.yeewoe.mopanet.protos.PBUsrFansListRsp;
import org.yeewoe.mopanet.protos.PBUsrFollowListRsp;
import org.yeewoe.mopanet.protos.PBUsrFriendListRsp;
import org.yeewoe.mopanet.protos.PBUsrGetRsp;
import org.yeewoe.mopanet.protos.PBUsrSetRsp;
import org.yeewoe.mopassion.app.common.net.UserProtobufNet;
import org.yeewoe.mopassion.app.file.model.UploadCallback;
import org.yeewoe.mopassion.app.file.service.PicBatchManager;
import org.yeewoe.mopassion.app.nearby.model.LocUser;
import org.yeewoe.mopassion.db.core.DaoFactory;
import org.yeewoe.mopassion.db.dao.DaoManager;
import org.yeewoe.mopassion.db.dao.UserDaoImpl;
import org.yeewoe.mopassion.db.po.BasePo;
import org.yeewoe.mopassion.db.po.MapPosition;
import org.yeewoe.mopassion.db.po.Message;
import org.yeewoe.mopassion.db.po.User;
import org.yeewoe.mopassion.mangers.UserDataMananger;
import org.yeewoe.mopassion.template.media.TMediaFactory;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import okio.ByteString;

/**
 * 用户接口
 * Created by wyw on 2016/4/2.
 */
public class UserService extends BaseService {
    private static final String CLASS_NAME = "UserService";
    private static final int COUNT_NEAR_USER_REQUEST = 2000;
    private CommonService commonService = new CommonService();
    private List<PBLocUser> bufferLocUserList;

    public Callback.CallbackInfo<User> getNet(long sid) {
        final String METHOD_NAME = "getNet";
        logInterfaceCall(CLASS_NAME, METHOD_NAME, "sid=" + sid);
        final Callback.CallbackInfo<User> result = new Callback.CallbackInfo<>();
        UserProtobufNet.get(sid, new Callback() {
            @Override
            public <T> void callback(CallbackInfo<T> info) {
                if (info.bError) {
                    CallbackUtils.buildErrorCallbackInfo(result, info);
                    return;
                }
                PBUsrGetRsp rsp = (PBUsrGetRsp) info.mT;
                PBUser pbUser = rsp.user;
                if (pbUser != null) {
                    User user = pbToUser(pbUser);
                    try {
                        DaoManager.getUserDao().insertOrUpdate(user, user.getSid());
                        if (user.getSid() == UserDataMananger.getInstance().getMeUid()) {
                            UserDataMananger.getInstance().setMe(user);
                        }
                        result.mT = user;
                    } catch (SQLException e) {
                        logSqlError(CLASS_NAME, METHOD_NAME, "DaoManager.getUserDao().insertOrUpdate", e);
                    }
                }
            }
        });
        logInterfaceReturn(CLASS_NAME, METHOD_NAME, result);
        return result;
    }

    public User getLocal(long sid) {
        final String METHOD_NAME = "getLocal";
        try {
            return DaoManager.getUserDao().query(sid);
        } catch (SQLException e) {
            logSqlError(CLASS_NAME, METHOD_NAME, "DaoManager.getUserDao().query", e);
            return null;
        }
    }

    /**
     * 批量查询
     *
     * @param sids       id列表
     * @param net        是否查询网络
     * @param checkLocal 是否检查本地。传true的话，如果本地有，不再请求网路
     */
    public List<User> get(Set<Long> sids, boolean net, boolean checkLocal) {
        final String METHOD_NAME = "get";
        logInterfaceCall(CLASS_NAME, METHOD_NAME, "sids=" + sids + ", net=" + net + ", checkLocal=" + checkLocal);

        Set<Long> tempSids = new HashSet<>();
        if (sids != null) {
            tempSids.addAll(sids);
        }

        List<User> localUsers = new ArrayList<>();
        if (!net || checkLocal) {
            // 本地有了不再请求网络
            try {
                localUsers = DaoManager.getUserDao().query(new ArrayList<>(tempSids));
                for (User localUser : localUsers) {
                    tempSids.remove(localUser.getSid());
                }
            } catch (SQLException e) {
                logSqlError(CLASS_NAME, METHOD_NAME, "DaoManager.getUserDao().query", e);
            }
        }

        final List<User> result = new ArrayList<>();
        if (net) {
            final List<User> finalLocalUsers = localUsers;
            UserProtobufNet.batchGet(new ArrayList<>(tempSids), new Callback() {
                @Override
                public <T> void callback(CallbackInfo<T> info) {
                    if (info.bError) {
                        result.addAll(finalLocalUsers);
                        return;
                    }
                    PBUsrBatchGetRsp rsp = (PBUsrBatchGetRsp) info.mT;
                    List<PBUser> pbUser = rsp.users;
                    List<User> users = pbToUser(pbUser);
                    try {
                        DaoManager.getUserDao().batchInsertOrUpdate(users);
                    } catch (SQLException e) {
                        logSqlError(CLASS_NAME, METHOD_NAME, "DaoManager.getUserDao().batchInsertOrUpdate", e);
                    }

                    users.addAll(finalLocalUsers);
                    result.addAll(users);
                }
            });
        } else {
            result.addAll(localUsers);
        }
        logInterfaceReturn(CLASS_NAME, METHOD_NAME, result);
        return result;
    }


    /**
     * 批量查询，消息专用
     *
     * @param messages   消息列表
     * @param net        是否查询网络
     * @param checkLocal 查询网络时，是否检查本地。传true的话，如果本地有，不再请求网路
     */
    public List<User> getFromMessage(List<Message> messages, boolean net, boolean checkLocal) {
        Set<Long> userIds = new HashSet<>();
        userIds.add(UserDataMananger.getInstance().getMeUid());

        for (Message message : messages) {
            if (message.getOtherUid() != null && message.getOtherUid() > 0L) {
                userIds.add(message.getOtherUid());
                userIds.add(message.getFromUid());
            }
        }

        return get(userIds, net, checkLocal);
    }


    /**
     * 批量查询
     *
     * @param basePos    po列表，将提取createBy进行用户同步
     * @param net        是否查询网络
     * @param checkLocal 查询网络时，是否检查本地。传true的话，如果本地有，不再请求网路
     */
    public <T extends BasePo> List<User> getFromPo(List<T> basePos, boolean net, boolean checkLocal) {
        Set<Long> sids = new HashSet<>();
        if (Checks.check(basePos)) {
            for (T basePo : basePos) {
                if (basePo.getCreateBy() > 0L) {
                    sids.add(basePo.getCreateBy());
                }
            }
        }
        return get(sids, net, checkLocal);
    }

    /**
     * 好友列表查询，网络同步
     */
    public Callback.CallbackInfo<User> getFriendLineNet() {
        final String METHOD_NAME = "getFriendLineNet";
        logInterfaceCall(CLASS_NAME, METHOD_NAME, "");

        final Callback.CallbackInfo<User> result = new Callback.CallbackInfo<>();
        UserProtobufNet.getFriendList(new Callback() {
            @Override
            public <T> void callback(CallbackInfo<T> info) {
                if (info.bError) {
                    return;
                }
                PBUsrFriendListRsp rsp = (PBUsrFriendListRsp) info.mT;
                List<Long> friendIds = rsp.uids;
                List<User> users = get(new HashSet<Long>(friendIds), true, true);
                try {
                    DaoManager.getUserDao().batchInsertOrUpdate(users);
                } catch (SQLException e) {
                    logSqlError(CLASS_NAME, METHOD_NAME, "DaoManager.getUserDao().batchInsertOrUpdate", e);
                }

                result.mTs = users;
            }
        });

        logInterfaceReturn(CLASS_NAME, METHOD_NAME, result);
        return result;
    }

    /**
     * 关注列表查询，网络同步
     */
    public Callback.CallbackInfo<User> getFollowLineNet() {
        final String METHOD_NAME = "getFollowLineNet";
        logInterfaceCall(CLASS_NAME, METHOD_NAME, "");

        final Callback.CallbackInfo<User> result = new Callback.CallbackInfo<>();
        UserProtobufNet.getFollowList(new Callback() {
            @Override
            public <T> void callback(CallbackInfo<T> info) {
                if (info.bError) {
                    return;
                }
                PBUsrFollowListRsp rsp = (PBUsrFollowListRsp) info.mT;
                List<Long> friendIds = rsp.uids;
                List<User> users = get(new HashSet<Long>(friendIds), true, true);
                try {
                    DaoManager.getUserDao().batchInsertOrUpdate(users);
                } catch (SQLException e) {
                    logSqlError(CLASS_NAME, METHOD_NAME, "DaoManager.getUserDao().batchInsertOrUpdate", e);
                }

                result.mTs = users;
            }
        });

        logInterfaceReturn(CLASS_NAME, METHOD_NAME, result);
        return result;
    }

    /**
     * 粉丝列表查询，网络同步
     */
    public Callback.CallbackInfo<User> getFansLineNet(int skip, int count) {
        final String METHOD_NAME = "getFansLineNet";
        logInterfaceCall(CLASS_NAME, METHOD_NAME, "");

        final Callback.CallbackInfo<User> result = new Callback.CallbackInfo<>();
        UserProtobufNet.getFansList(skip, count, new Callback() {
            @Override
            public <T> void callback(CallbackInfo<T> info) {
                if (info.bError) {
                    return;
                }
                PBUsrFansListRsp rsp = (PBUsrFansListRsp) info.mT;
                List<Long> friendIds = rsp.uids;
                List<User> users = get(new HashSet<Long>(friendIds), true, true);
                try {
                    DaoManager.getUserDao().batchInsertOrUpdate(users);
                } catch (SQLException e) {
                    logSqlError(CLASS_NAME, METHOD_NAME, "DaoManager.getUserDao().batchInsertOrUpdate", e);
                }

                result.mTs = users;
            }
        });

        logInterfaceReturn(CLASS_NAME, METHOD_NAME, result);
        return result;
    }

    /**
     * 附近的人列表，网络同步
     */
    public Callback.CallbackInfo<LocUser> getNearUserLineNet(long min, long max, int startIndex, int count, MapPosition position) {
        final String METHOD_NAME = "getNearUserLineNet";
        logInterfaceCall(CLASS_NAME, METHOD_NAME, "min=" + min + ", max=" + max + ", startIndex=" + startIndex + ", count=" + count + ", position=" + position);
        final Callback.CallbackInfo<LocUser> result = new Callback.CallbackInfo<>();
        result.mTs = new ArrayList<>();
        if (startIndex <= 0) {
            UserProtobufNet.getNearUser(min, max, COUNT_NEAR_USER_REQUEST, new CommonService().mapPositionToPb(position), new Callback() {
                @Override public <T> void callback(CallbackInfo<T> info) {
                    if (info.bError) {
                        return;
                    }
                    PBLocNearUserRsp rsp = (PBLocNearUserRsp) info.mT;
                    bufferLocUserList = new ArrayList<>();
                    if (rsp.users != null) {
                        for (PBLocUser user : rsp.users) {
                            bufferLocUserList.add(user);
                        }
                    }

                    // 过滤自己
                    long meUid = UserDataMananger.getInstance().getMeUid();
                    for (int i = 0; i < bufferLocUserList.size(); i++) {
                        if (bufferLocUserList.get(i).uid != null && bufferLocUserList.get(i).uid == meUid) {
                            bufferLocUserList.remove(i);
                            break;
                        }
                    }

                }
            });
        }
        if (!result.bError && Checks.check(bufferLocUserList)) {
            int end = startIndex + count;
            List<PBLocUser> tempLocUserList = bufferLocUserList.subList(startIndex, bufferLocUserList.size() <= end ? bufferLocUserList.size() : end);
            logRun(CLASS_NAME, METHOD_NAME, "tempLocUserList=" + tempLocUserList);
            result.mTs = pbToLocUser(tempLocUserList);
        }
        logInterfaceReturn(CLASS_NAME, METHOD_NAME, result);
        return result;
    }

    /**
     * 关注
     */
    public void follow(long sid, final Callback callback) {
        final String METHOD_NAME = "follow";
        logInterfaceCall(CLASS_NAME, METHOD_NAME, "sid=" + sid);
        UserProtobufNet.follow(sid, new Callback() {
            @Override public <T> void callback(CallbackInfo<T> info) {
                logInterfaceReturn(CLASS_NAME, METHOD_NAME, "");
                callback.callback(info);
            }
        });
    }

    /**
     * 编辑用户信息
     */
    public void modify(final User user, List<File> fileList, final Callback callback) {
        final String METHOD_NAME = "modify";
        logInterfaceCall(CLASS_NAME, METHOD_NAME, "user=" + user + ", fileList=" + fileList);

        if (user == null) {
            CallbackUtils.invalidCallback(callback);
            return ;
        }

        PicBatchManager.getInstance().call(fileList, new UploadCallback() {
            @Override public void progress(String key, double percent) {

            }

            @Override public void success(String key) {

            }

            @Override public void fail(String key, int errorCode) {

            }

            @Override public void cancel(String key) {

            }
        }, new Callback() {
            @Override public <T> void callback(CallbackInfo<T> info) {
                if (info.bError) {
                    CallbackUtils.errorCallback(callback, info.errorCode);
                    return;
                }

                UserProtobufNet.set(userToPb(user), null, new Callback() {
                    @Override public <T> void callback(CallbackInfo<T> info) {
                        if (info.bError) {
                            CallbackUtils.errorCallback(callback, info.errorCode);
                            return ;
                        }

                        try {
                            User exist = DaoManager.getUserDao().query(user.getSid());
                            if (exist != null) {
                                user.setCreateTime(exist.getCreateTime());
                            }

                            DaoManager.getUserDao().insertOrUpdate(user, user.getSid());
                            if (user.getSid() == UserDataMananger.getInstance().getMeUid()) {
                                UserDataMananger.getInstance().setMe(user);
                            }
                        } catch (SQLException e) {
                            logSqlError(CLASS_NAME, METHOD_NAME, "DaoManager.getUserDao().insertOrUpdate", e);
                        }

                        logInterfaceReturn(CLASS_NAME, METHOD_NAME, "");
                        CallbackUtils.callbackInfoVoid(callback);
                    }
                });
            }
        });
    }

    @NonNull private List<User> pbToUser(List<PBUser> pbUsers) {
        if (pbUsers == null) {
            return new ArrayList<>();
        }

        List<User> result = new ArrayList<>();
        for (PBUser pbUser : pbUsers) {
            result.add(pbToUser(pbUser));
        }
        return result;
    }

    @NonNull private User pbToUser(@NonNull PBUser pbUser) {
        User result = new User();
        if (pbUser.uid != null) {
            result.setSid(pbUser.uid);
        }
        result.setAccount(pbUser.account);
        result.setNick(pbUser.name);
        result.setAge(pbUser.age);
        result.setGender(pbUser.sex);
        result.setStar(pbUser.star);
        result.setSignature(pbUser.signature);
        result.setAddress(pbUser.homeland);
        result.setHeads(TMediaFactory.getInstance().fromByteStringToList(pbUser.photo));
        if (pbUser.regtime != null) {
            result.setCreateTime(pbUser.regtime);
        }
        return result;
    }

    @NonNull private PBUser userToPb(@NonNull User user) {
        PBUser.Builder builder = new PBUser.Builder();
        builder.uid(user.getSid());
        builder.account(user.getAccount());
        builder.signature(user.getSignature());
        builder.name(user.getNick());
        builder.photo(TMediaFactory.getInstance().toByteString(user.getHeads()));
        builder.homeland(user.getAddress());
        builder.age(user.getAge());
        return builder.build();
    }

    @NonNull private List<LocUser> pbToLocUser(List<PBLocUser> users) {
        if (users == null) {
            return new ArrayList<>();
        }
        List<LocUser> result = new ArrayList<>();
        for (PBLocUser user : users) {
            LocUser locUser = new LocUser();
            if (user.uid != null) {
                locUser.setCreateBy(user.uid);
            }
            if (user.dist != null) {
                locUser.setDistance(user.dist);
            }
            if (user.atime != null) {
                locUser.setLocTime(user.atime);
            }
            locUser.setMapPosition(commonService.pbToMapPosition(user.loc));
            result.add(locUser);
        }
        return result;
    }

}
