package org.yeewoe.mopassion.mangers;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import org.greenrobot.eventbus.EventBus;
import org.yeewoe.mopanet.client.MopaNetConstants;
import org.yeewoe.mopanet.protos.PBImPostReq;
import org.yeewoe.mopassion.app.im.model.IMService;
import org.yeewoe.mopassion.event.PushHandleEvent;
import org.yeewoe.mopassion.utils.LogCore;

/**
 * Created by Administrator on 2016/3/30.
 * 该类负责全局消息回调.
 */
public class MsgHandlerManager {
    private static MsgHandlerManager ourInstance = new MsgHandlerManager();
    private IMService imService;

    public static MsgHandlerManager getInstance() {
        return ourInstance;
    }

    private Handler h = null;

    private MsgHandlerManager() {
        imService = new IMService();
    }

    public void init(Looper looper) {
        h = new Handler(looper) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //TODO 消息派遣.
                LogCore.i("Receive push msg : srvop " + msg.what + " object " + msg.obj);

                if (msg.what == MopaNetConstants.SRVOP_IM_POST_MSG_REQ) {
                    HandleIMPush(msg);
                }
            }
        };
    }

    public Handler getH() {
        return h;
    }

    void HandleIMPush(Message msg) {
        imService.handlePush(msg);
    }
}
