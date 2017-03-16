package org.yeewoe.mopassion.app.im.model;

/**
 * Created by Administrator on 2016/3/30.
 */

import android.support.annotation.NonNull;

import org.greenrobot.eventbus.EventBus;
import org.yeewoe.commonutils.common.assist.Base64;
import org.yeewoe.commonutils.common.io.FileUtils;
import org.yeewoe.mopanet.callback.Callback;
import org.yeewoe.mopanet.callback.CallbackUtils;
import org.yeewoe.mopanet.client.Client;
import org.yeewoe.mopanet.client.MopaErrno;
import org.yeewoe.mopanet.protos.PBImMsg;
import org.yeewoe.mopanet.protos.PBImMsgClass;
import org.yeewoe.mopanet.protos.PBImMsgContentType;
import org.yeewoe.mopanet.protos.PBImPostReq;
import org.yeewoe.mopanet.protos.PBImPostRsp;
import org.yeewoe.mopanet.protos.PBImSyncRsp;
import org.yeewoe.mopassion.app.common.service.BaseService;
import org.yeewoe.mopassion.app.common.service.UserService;
import org.yeewoe.mopassion.app.file.model.UploadCallback;
import org.yeewoe.mopassion.app.file.service.PicBatchManager;
import org.yeewoe.mopassion.db.dao.DaoManager;
import org.yeewoe.mopassion.db.po.Media;
import org.yeewoe.mopassion.db.po.Message;
import org.yeewoe.mopassion.db.po.SendStatus;
import org.yeewoe.mopassion.event.AsyncDataProgressChangeEvent;
import org.yeewoe.mopassion.event.AsyncDataStatusChangeEvent;
import org.yeewoe.mopassion.event.PushHandleEvent;
import org.yeewoe.mopassion.mangers.ClientManger;
import org.yeewoe.mopassion.mangers.UserDataMananger;
import org.yeewoe.mopassion.template.media.TMediaFactory;
import org.yeewoe.mopassion.template.media.TMediaImage;
import org.yeewoe.mopassion.template.media.TMediaVoice;
import org.yeewoe.mopassion.utils.LogCore;
import org.yeewoe.mopassion.utils.TimeUtil;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 登录授权业务接口
 * Created by wyw on 2016/3/25.
 */
public class IMService extends BaseService {
    private static final String CLASS_NAME = "IMService";

    private UserService userService;

    public IMService() {
        this.userService = new UserService();
    }

    /**
     * 同步IM消息
     */
    public Callback.CallbackInfo<Message> syncImMsg() {
        final String METHOD_NAME = "syncImMsg";
        logInterfaceCall(CLASS_NAME, METHOD_NAME, "");
        final Callback.CallbackInfo<Message> result = new Callback.CallbackInfo<>();
        try {
            Message last = DaoManager.getMessageDao().queryLast();
            long startImMsgId = 0;
            if (last != null) {
                startImMsgId = last.getSid();
            }

            // TODO 查询条数需要优化
            IMProtobufNet.syncImMsg(startImMsgId, 200, new Callback() {
                @Override
                public <T> void callback(CallbackInfo<T> info) {
                    if (info.bError) {
                        // IM同步失败
                        CallbackUtils.buildErrorCallbackInfo(result, info);
                        return;
                    }
                    PBImSyncRsp rsp = (PBImSyncRsp) info.mT;
                    List<Message> messages = pbToMessage(rsp.imsgs);
                    DaoManager.getMessageDao().batchInsertOrUpdate(messages);
                    userService.getFromMessage(messages, true, true); // 同步联系人

                    result.mTs = messages;
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
            return (Callback.CallbackInfo<Message>) CallbackUtils.buildSqlExceptionCallbackInfo();
        }
        logInterfaceReturn(CLASS_NAME, METHOD_NAME, result);
        return result;
    }

    /**
     * 发送IM消息，本地插入同步，网络发送异步
     */
    public Callback.CallbackInfo<Message> postImMsg(@NonNull final Long fromUid, @NonNull final Long toUid, @NonNull final PBImMsgClass c, final String text) {
        final String METHOD_NAME = "postImMsg";
        logInterfaceCall(CLASS_NAME, METHOD_NAME, "fromUid=" + fromUid + ", toUid=" + toUid + ", text=" + text);

        final Callback.CallbackInfo<Message> result = new Callback.CallbackInfo<>();
        Media media = TMediaFactory.getInstance().fromT(null, 0, text);
        final PBImMsg imMsg = buildMessaegePb(fromUid, toUid, c, media);

        final Message message = pbToMessage(imMsg);
        message.setSendStatus(SendStatus.SENDING);
        result.mT = message;

        try {
            DaoManager.getMessageDao().insertOrUpdate(message, message.getSid());
            // 异步发送
            runOnImTemplate(new Runnable() {
                @Override public void run() {
                    IMProtobufNet.postImMsg(imMsg, new Callback() {
                        @Override
                        public <T> void callback(CallbackInfo<T> info) {
                            if (info.bError) {
                                message.setSendStatus(SendStatus.FAILURE);
                                try {
                                    DaoManager.getMessageDao().updateStatus(message.getSid(), SendStatus.FAILURE);
                                } catch (SQLException e) {
                                    logSqlError(CLASS_NAME, METHOD_NAME, "DaoManager.getMessageDao().updateStatus", e);
                                }
                            } else {
                                message.setSendStatus(SendStatus.SUCCESS);
                                try {
                                    DaoManager.getMessageDao().updateStatus(message.getSid(), SendStatus.SUCCESS);
                                } catch (SQLException e) {
                                    logSqlError(CLASS_NAME, METHOD_NAME, "DaoManager.getMessageDao().updateStatus", e);
                                }
                            }
                            EventBus.getDefault().post(new AsyncDataStatusChangeEvent<>(MessageVo.Convert.convert(message)));
                        }
                    });
                }
            }, null);

        } catch (SQLException e) {
            logSqlError(CLASS_NAME, METHOD_NAME, "DaoManager.getMessageDao().insertOrUpdate", e);
            CallbackUtils.buildSqlExceptionCallbackInfo(result);
        }

        logInterfaceReturn(CLASS_NAME, METHOD_NAME, result);
        return result;
    }

    /**
     * 发送IM消息，本地插入同步，网络发送异步
     */
    public Callback.CallbackInfo<Message> postImPicture(@NonNull final Long fromUid, @NonNull final Long toUid, @NonNull final PBImMsgClass c, @NonNull final File pic) {
        final String METHOD_NAME = "postImPicture";
        logInterfaceCall(CLASS_NAME, METHOD_NAME, "fromUid=" + fromUid + ", toUid=" + toUid + ", pic=" + pic);

        final Callback.CallbackInfo<Message> result = new Callback.CallbackInfo<>();
        Media media = TMediaFactory.getInstance().fromT(null, 0, TMediaFactory.getInstance().toTMediaImage(pic));
        final PBImMsg imMsg = buildMessaegePb(fromUid, toUid, c, media);
        final Message message = pbToMessage(imMsg);
        message.setSendStatus(SendStatus.SENDING);
        result.mT = message;

        try {
            DaoManager.getMessageDao().insertOrUpdate(message, message.getSid());

            // 异步发送
            runOnImTemplate(new Runnable() {
                @Override public void run() {
                    PicBatchManager.getInstance().call(pic, new UploadCallback() {
                        @Override public void progress(String key, double percent) {
                            // 图片发送进度进度
                            EventBus.getDefault().post(new AsyncDataProgressChangeEvent(message.getSid(), percent));
                        }

                        @Override public void success(String key) {
                        }

                        @Override public void fail(String key, int errorCode) {
                        }

                        @Override public void cancel(String key) {
                        }
                    }, new Callback() {
                        @Override public <T> void callback(CallbackInfo<T> info) {
                            // 图片发送完，进入数据发送流程
                            if (info.bError) {
                                message.setSendStatus(SendStatus.FAILURE);
                                try {
                                    DaoManager.getMessageDao().updateStatus(message.getSid(), SendStatus.FAILURE);
                                } catch (SQLException e) {
                                    logSqlError(CLASS_NAME, METHOD_NAME, "DaoManager.getMessageDao().updateStatus", e);
                                }
                            } else {
                                IMProtobufNet.postImMsg(imMsg, new Callback() {
                                    @Override
                                    public <T> void callback(CallbackInfo<T> info) {

                                        if (info.bError) {
                                            message.setSendStatus(SendStatus.FAILURE);
                                            try {
                                                DaoManager.getMessageDao().updateStatus(message.getSid(), SendStatus.FAILURE);
                                            } catch (SQLException e) {
                                                logSqlError(CLASS_NAME, METHOD_NAME, "DaoManager.getMessageDao().updateStatus", e);
                                            }
                                        } else {
                                            message.setSendStatus(SendStatus.SUCCESS);
                                            try {
                                                DaoManager.getMessageDao().updateStatus(message.getSid(), SendStatus.SUCCESS);
                                            } catch (SQLException e) {
                                                logSqlError(CLASS_NAME, METHOD_NAME, "DaoManager.getMessageDao().updateStatus", e);
                                            }
                                        }
                                    }
                                });
                            }

                            EventBus.getDefault().post(new AsyncDataStatusChangeEvent<>(MessageVo.Convert.convert(message)));
                        }
                    });
                }
            }, null);

        } catch (SQLException e) {
            logSqlError(CLASS_NAME, METHOD_NAME, "DaoManager.getMessageDao().insertOrUpdate", e);
            CallbackUtils.buildSqlExceptionCallbackInfo(result);
        }

        logInterfaceReturn(CLASS_NAME, METHOD_NAME, result);
        return result;
    }

    /**
     * 发送IM语音，本地插入同步，网络发送异步
     */
    public Callback.CallbackInfo<Message> postImVoice(@NonNull final Long fromUid, @NonNull final Long toUid, @NonNull final PBImMsgClass c, final String voiceFile, final long time) {
        final String METHOD_NAME = "postImMsg";
        logInterfaceCall(CLASS_NAME, METHOD_NAME, "fromUid=" + fromUid + ", toUid=" + toUid + ", voiceFile=" + voiceFile + ", time=" + time);

        final Callback.CallbackInfo<Message> result = new Callback.CallbackInfo<>();

        // 组装TMediaVoice
        File file = new File(voiceFile);
        byte[] fileBytes = null;
        try {
            fileBytes = FileUtils.readFileToByteArray(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (fileBytes == null) {
            return (Callback.CallbackInfo<Message>) CallbackUtils.buildErrorCallbackInfo(MopaErrno.LOCAL_VOICE_READ_ERROR.ordinal());
        }
        TMediaVoice tVoice = new TMediaVoice();
        long length = file.length();
        tVoice.time = time;
        tVoice.base64Bytes = Base64.encodeToString(fileBytes, Base64.DEFAULT);

        Media media = TMediaFactory.getInstance().fromT(file.getName(), length, tVoice);
        final PBImMsg imMsg = buildMessaegePb(fromUid, toUid, c, media);
        final Message message = pbToMessage(imMsg);
        message.setSendStatus(SendStatus.SENDING);
        result.mT = message;

        try {
            DaoManager.getMessageDao().insertOrUpdate(message, message.getSid());
            // 异步发送
            runOnImTemplate(new Runnable() {
                @Override public void run() {
                    IMProtobufNet.postImMsg(imMsg, new Callback() {
                        @Override
                        public <T> void callback(CallbackInfo<T> info) {
                            if (info.bError) {
                                message.setSendStatus(SendStatus.FAILURE);
                                try {
                                    DaoManager.getMessageDao().updateStatus(message.getSid(), SendStatus.FAILURE);
                                } catch (SQLException e) {
                                    logSqlError(CLASS_NAME, METHOD_NAME, "DaoManager.getMessageDao().updateStatus", e);
                                }
                            } else {
                                message.setSendStatus(SendStatus.SUCCESS);
                                try {
                                    DaoManager.getMessageDao().updateStatus(message.getSid(), SendStatus.SUCCESS);
                                } catch (SQLException e) {
                                    logSqlError(CLASS_NAME, METHOD_NAME, "DaoManager.getMessageDao().updateStatus", e);
                                }
                            }
                            EventBus.getDefault().post(new AsyncDataStatusChangeEvent<>(MessageVo.Convert.convert(message)));
                        }
                    });
                }
            }, null);

        } catch (SQLException e) {
            logSqlError(CLASS_NAME, METHOD_NAME, "DaoManager.getMessageDao().insertOrUpdate", e);
            CallbackUtils.buildSqlExceptionCallbackInfo(result);
        }

        logInterfaceReturn(CLASS_NAME, METHOD_NAME, result);
        return result;
    }

    /**
     * 获取消息列表 <br/>
     * 按发送时间逆序，不同对象之包含一条
     */
    public Callback.CallbackInfo<Message> getMessageLine() {
        final String METHOD = "getMessageLine";
        logInterfaceCall(CLASS_NAME, METHOD, "");
        Callback.CallbackInfo<Message> result = new Callback.CallbackInfo<>();
        try {
            List<Message> messages = DaoManager.getMessageDao().queryAll();
            List<Message> merge = new ArrayList<>();
            Set<Long> tempId = new HashSet<>();
            long meUid = UserDataMananger.getInstance().getMeUid();
            for (Message message : messages) {
                if (!tempId.contains(message.getFromUid()) &&
                        !tempId.contains(message.getToUid())) {
                    merge.add(message);
                    if (message.getFromUid() == meUid) {
                        tempId.add(message.getToUid());
                    } else {
                        tempId.add(message.getFromUid());
                    }
                }
            }
            result.mTs = merge;
        } catch (SQLException e) {
            logSqlError(CLASS_NAME, METHOD, "DaoManager.getMessageDao().queryAll", e);
            return (Callback.CallbackInfo<Message>) CallbackUtils.buildSqlExceptionCallbackInfo();
        }
        logInterfaceReturn(CLASS_NAME, METHOD, result);
        return result;
    }

    /**
     * 获取聊天列表 <br/>
     * 按发送时间逆序
     */
    public Callback.CallbackInfo<Message> getChatLine(long toUid) {
        final String METHOD = "getChatLine";
        logInterfaceCall(CLASS_NAME, METHOD, "");
        Callback.CallbackInfo<Message> result = new Callback.CallbackInfo<>();
        try {
            List<Message> messages = DaoManager.getMessageDao().queryAll(toUid);
            result.mTs = messages;
        } catch (SQLException e) {
            logSqlError(CLASS_NAME, METHOD, "DaoManager.getMessageDao().queryAll", e);
            return (Callback.CallbackInfo<Message>) CallbackUtils.buildSqlExceptionCallbackInfo();
        }
        logInterfaceReturn(CLASS_NAME, METHOD, result);
        return result;
    }

    /**
     * 处理推送
     */
    public void handlePush(android.os.Message message) {
        final String METHOD_NAME = "handlePush";
        PBImPostReq req = (PBImPostReq) message.obj;
        logInterfaceCall(CLASS_NAME, METHOD_NAME, "imsg=" + req.imsg);
        ArrayList<Message> messages = new ArrayList<>();
        Message msg = pbToMessage(req.imsg);
        messages.add(msg);
        new UserService().getFromMessage(messages, true, true);
        try {
            DaoManager.getMessageDao().insertOrUpdate(msg, msg.getSid());
        } catch (SQLException e) {
            logSqlError(CLASS_NAME, METHOD_NAME, "DaoManager.getMessageDao().insertOrUpdate", e);
        }
        EventBus.getDefault().post(new PushHandleEvent<>(msg));
    }


    @NonNull private Message pbToMessage(@NonNull PBImMsg pbImMsg) {
        Message result = new Message();
        if (pbImMsg.imsgid != null) {
            result.setSid(pbImMsg.imsgid);
        }
        if (pbImMsg.imsgtime != null) {
            result.setCreateTime(pbImMsg.imsgtime);
        }
        if (pbImMsg.from_uid != null) {
            result.setFromUid(pbImMsg.from_uid);
            result.setCreateBy(pbImMsg.from_uid);
            if (pbImMsg.from_uid == UserDataMananger.getInstance().getMeUid()) {
                result.setSelfUid(pbImMsg.from_uid);
            } else {
                result.setOtherUid(pbImMsg.from_uid);
            }
        }
        if (pbImMsg.from_gid != null) {
            result.setFromGid(pbImMsg.from_gid);
            result.setOtherGid(pbImMsg.from_gid);
        }
        if (pbImMsg.to_uid != null) {
            result.setToUid(pbImMsg.to_uid);
            if (pbImMsg.to_uid == UserDataMananger.getInstance().getMeUid()) {
                result.setSelfUid(pbImMsg.to_uid);
            } else {
                result.setOtherUid(pbImMsg.to_uid);
            }
        }
        if (pbImMsg.to_gid != null) {
            result.setToGid(pbImMsg.to_gid);
            result.setOtherGid(pbImMsg.from_gid);
        }
        if (pbImMsg.imsg != null) {
            result.setContent(pbImMsg.imsg.utf8());
        }

        return result;
    }

    @NonNull private List<Message> pbToMessage(List<PBImMsg> pbImMsgs) {
        if (pbImMsgs == null) {
            return new ArrayList<>();
        }
        List<Message> result = new ArrayList<>();
        for (PBImMsg pbImMsg : pbImMsgs) {
            if (pbImMsg != null) {
                result.add(pbToMessage(pbImMsg));
            }
        }
        return result;
    }

    @NonNull private PBImMsg buildMessaegePb(@NonNull Long fromUid, @NonNull Long toUid, @NonNull PBImMsgClass c, Media media) {
        return new PBImMsg.Builder().
                imsgid(ClientManger.getInstance().getServerSwapTime()). //发送方设置时间戳
                imsgtime(TimeUtil.reparseServerTime(ClientManger.getInstance().getServerSwapTime())).
                from_uid(fromUid).
                to_uid(toUid).
                imsgctype(PBImMsgContentType.IMCT_TEXT.getValue()). //TODO 这个字段后续删除
                imclass(c.getValue()).
                imsg(TMediaFactory.getInstance().toByteString(media)).build();
    }
}
