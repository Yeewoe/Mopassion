package org.yeewoe.mopassion.service;

import android.content.Context;
import android.content.Intent;

import org.yeewoe.commonutils.common.utils.CatchUtil;
import org.yeewoe.mopassion.MopaApplication;

/**
 * Service组件管理器
 * Created by wyw on 2016/4/3.
 */
public class AppServiceManager {
    private static AppServiceManager instance;
    private Context context;
    private AppServiceManager() {
        context = MopaApplication.getInstance();
    }

    public static AppServiceManager getInstance() {
        if (instance == null) {
            synchronized (AppServiceManager.class) {
                if (instance == null) {
                    instance = new AppServiceManager();
                }
            }
        }
        return instance;
    }

    public void wakeUp(AppServiceType appServiceType) {
        switch (appServiceType) {
            case SYNC:
                CatchUtil.startService(context, new Intent(context, SyncAppService.class));
                break;
        }
    }

    public void stop(AppServiceType appServiceType) {
        switch (appServiceType) {
            case SYNC:
                CatchUtil.stopService(context, new Intent(context, SyncAppService.class));
                break;
        }
    }
}
