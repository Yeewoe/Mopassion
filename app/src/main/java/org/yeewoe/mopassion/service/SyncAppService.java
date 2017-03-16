package org.yeewoe.mopassion.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import org.greenrobot.eventbus.EventBus;
import org.yeewoe.commonutils.android.async.AsyncExecutor;
import org.yeewoe.commonutils.common.assist.TimeGate;
import org.yeewoe.mopassion.app.im.model.IMService;
import org.yeewoe.mopassion.event.ImSyncEvent;
import org.yeewoe.mopassion.utils.TimeUtil;

import java.util.concurrent.Callable;

/**
 * 同步服务
 * Created by wyw on 2016/4/3.
 */
public class SyncAppService extends Service {
    private IMService imService;
    private AsyncExecutor asyncExecutor; // 任务执行器
    private TimeGate timeGate; // 控制频率

    @Override public void onCreate() {
        super.onCreate();
        asyncExecutor = new AsyncExecutor();
        imService = new IMService();
        timeGate = new TimeGate(20 * TimeUtil.SECOND);
    }

    @Override public void onDestroy() {
        super.onDestroy();
    }

    @Override public int onStartCommand(Intent intent, int flags, int startId) {

        timeGate.chopGate(new Callable() {
            @Override public Object call() throws Exception {
                syncIm();
                syncUser();
                return null;
            }
        }, new Callable() {
            @Override public Object call() throws Exception {
                return null;
            }
        });

        return START_STICKY;
    }

    private void syncUser() {
        // TODO 这里要同步联系人
    }

    private void syncIm() {
        asyncExecutor.execute(new AsyncExecutor.Worker<Object>() {
            @Override
            protected Object doInBackground() {
                imService.syncImMsg();
                return null;
            }

            @Override
            protected void onPostExecute(Object data) {
                EventBus.getDefault().post(new ImSyncEvent());
            }
        });
    }

    @Nullable @Override public IBinder onBind(Intent intent) {
        return null;
    }

}
