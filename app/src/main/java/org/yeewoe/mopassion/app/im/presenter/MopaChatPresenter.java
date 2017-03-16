package org.yeewoe.mopassion.app.im.presenter;

import org.yeewoe.commonutils.android.async.MyAsyncTask;
import org.yeewoe.commonutils.android.async.MyLocalAsyncTask;
import org.yeewoe.mopanet.callback.Callback;
import org.yeewoe.mopanet.callback.CallbackUtils;
import org.yeewoe.mopanet.protos.PBImMsgClass;
import org.yeewoe.mopassion.app.common.presenter.LinePresenter;
import org.yeewoe.mopassion.app.common.service.UserService;
import org.yeewoe.mopassion.app.im.model.IMService;
import org.yeewoe.mopassion.app.im.model.MessageVo;
import org.yeewoe.mopassion.app.im.view.adapter.MopaChatAdapter;
import org.yeewoe.mopassion.app.im.view.iview.IMopaChatView;
import org.yeewoe.mopassion.asynctask.LineAsyncTask;
import org.yeewoe.mopassion.constants.ImConstants;
import org.yeewoe.mopassion.db.po.Message;
import org.yeewoe.mopassion.db.po.User;
import org.yeewoe.mopassion.event.PushHandleEvent;
import org.yeewoe.mopassion.mangers.UserDataMananger;
import org.yeewoe.mopassion.utils.LogCore;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by acc on 2016/3/29.
 */
public class MopaChatPresenter extends LinePresenter<MessageVo> {

    private IMService imService;
    private IMopaChatView view;
    private long toUid;
    private User toUser;

    public MopaChatPresenter(IMopaChatView view, MopaChatAdapter adapter, long toUid) {
        super(view, adapter, ImConstants.COUNT_CHAT_FIRST_PAGE, ImConstants.COUNT_CHAT_NEXT_PAGE);
        imService = new IMService();
        this.view = view;
        this.toUid = toUid;
        this.toUser = new UserService().getLocal(toUid);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void send(final String msg) {
        new MyLocalAsyncTask<Void, Integer, Callback.CallbackInfo<MessageVo>>() {
            @Override
            protected Callback.CallbackInfo<MessageVo> doInBackground(Void... params) {
                Callback.CallbackInfo<Message> callbackInfo = imService.postImMsg(UserDataMananger.getInstance().getMeUid(), toUid, PBImMsgClass.IMCLSS_U2U, msg);
                Callback.CallbackInfo<MessageVo> result = new Callback.CallbackInfo<>();
                if (callbackInfo.bError) {
                    CallbackUtils.buildErrorCallbackInfo(result, callbackInfo);
                } else {
                    result.mT = MessageVo.Convert.convert(callbackInfo.mT);
                }
                return result;
            }

            @Override
            protected void onPostExecute(Callback.CallbackInfo<MessageVo> result) {
                if (result.bError) {
                    view.showError(result.errorCode);
                } else {
                    addLast(result.mT);
                    view.scrollToLast();
                }
            }
        }.execute();
    }

    public void sendVoice(final String voicefile, final long time) {
        new MyLocalAsyncTask<Void, Integer, Callback.CallbackInfo<MessageVo>>() {
            @Override
            protected Callback.CallbackInfo<MessageVo> doInBackground(Void... params) {
                Callback.CallbackInfo<Message> callbackInfo = imService.postImVoice(UserDataMananger.getInstance().getMeUid(), toUid, PBImMsgClass.IMCLSS_U2U, voicefile, time);
                Callback.CallbackInfo<MessageVo> result = new Callback.CallbackInfo<>();
                if (callbackInfo.bError) {
                    CallbackUtils.buildErrorCallbackInfo(result, callbackInfo);
                } else {
                    result.mT = MessageVo.Convert.convert(callbackInfo.mT);
                }
                return result;
            }

            @Override
            protected void onPostExecute(Callback.CallbackInfo<MessageVo> result) {
                if (result.bError) {
                    view.showError(result.errorCode);
                } else {
                    addLast(result.mT);
                    view.scrollToLast();
                }
            }
        }.execute();
    }

    @Override protected LineAsyncTask<MessageVo> initAsyncTask() {
        return new LineAsyncTask<MessageVo>(view, adapter) {
            @Override protected Callback.CallbackInfo<MessageVo> doInBackgroundAfterInit(Object param, long prev, int count) {
                Callback.CallbackInfo<Message> chatLine = imService.getChatLine((Long) param);

                Callback.CallbackInfo<MessageVo> result = new Callback.CallbackInfo<>();
                if (chatLine.bError) {
                    CallbackUtils.buildErrorCallbackInfo(result, chatLine);
                } else {
                    List<User> users = userService.getFromMessage(chatLine.mTs, false, false);
                    result.mTs = MessageVo.Convert.convert(chatLine.mTs, users);
                }
                return result;
            }
        };
    }

    @Override protected void handleCompress(final File pic) {
        super.handleCompress(pic);
        new MyLocalAsyncTask<Void, Integer, Callback.CallbackInfo<MessageVo>>() {
            @Override
            protected Callback.CallbackInfo<MessageVo> doInBackground(Void... params) {
                Callback.CallbackInfo<Message> callbackInfo = imService.postImPicture(UserDataMananger.getInstance().getMeUid(), toUid, PBImMsgClass.IMCLSS_U2U, pic);
                Callback.CallbackInfo<MessageVo> result = new Callback.CallbackInfo<>();
                if (callbackInfo.bError) {
                    CallbackUtils.buildErrorCallbackInfo(result, callbackInfo);
                } else {
                    result.mT = MessageVo.Convert.convert(callbackInfo.mT);
                }
                return result;
            }

            @Override
            protected void onPostExecute(Callback.CallbackInfo<MessageVo> result) {
                if (result.bError) {
                    view.showError(result.errorCode);
                } else {
                    addLast(result.mT);
                    view.scrollToLast();
                }
            }
        }.execute();
    }

    public void updateProgress(long sid, double percent) {
        ((MopaChatAdapter) adapter).update(sid, percent);
    }

    public void onPushHandleEvent(PushHandleEvent<Message> event) {
        LogCore.i("监听到事件, event=" + event);
        ArrayList<User> users = new ArrayList<>();
        if (toUser != null) {
            users.add(toUser);
        }
        addLast(MessageVo.Convert.convert(event.data, users));
        view.scrollToLast();
    }


    public boolean isEarModel() {
        return false;
    }
}
