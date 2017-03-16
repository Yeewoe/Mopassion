package org.yeewoe.mopassion.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.yeewoe.mopassion.event.NetChangeEvent;
import org.yeewoe.mopassion.service.AppServiceManager;
import org.yeewoe.mopassion.service.AppServiceType;
import org.yeewoe.mopassion.utils.LogCore;
import org.greenrobot.eventbus.EventBus;
import org.yeewoe.commonutils.common.assist.Networks;

/**
 * 网络切换广播监听器
 * Created by wyw on 2016/3/5.
 */
public class NetChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        LogCore.i("收到网络切换广播");
        EventBus.getDefault().post(new NetChangeEvent(Networks.isAvailable(context), Networks.getNetworkType(context)));
        AppServiceManager.getInstance().wakeUp(AppServiceType.SYNC);
    }
}
