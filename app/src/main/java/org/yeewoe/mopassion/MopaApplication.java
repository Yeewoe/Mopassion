package org.yeewoe.mopassion;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

import org.yeewoe.commonutils.common.io.SharePreferenceUtils;
import org.yeewoe.commonutils.common.utils.AppUtil;
import org.yeewoe.mopassion.auth.FacebookUtil;
import org.yeewoe.mopassion.auth.TwitterUtil;
import org.yeewoe.mopassion.constants.AppConstants;
import org.yeewoe.mopassion.photo.fresco_lib.ImagePipelineConfigUtils;
import org.yeewoe.mopassion.utils.LogCore;

/**
 * 核心Application
 * Created by wyw on 2016/3/4.
 */
public class MopaApplication extends Application {
    private static MopaApplication innerInstance;
    private SharePreferenceUtils sp;
    @Override
    public void onCreate() {
        super.onCreate();
        innerInstance = this;

        LogCore.i("初始化Application... 当前进程名为" + AppUtil.getProcessName(this));

        if (AppUtil.isMainProcess(this)) { // 在主进程中
            LogCore.i("初始化主进程...");
            // 初始化Fresco
            Fresco.initialize(this, ImagePipelineConfigUtils.getDefaultImagePipelineConfig(this));

            sp = new SharePreferenceUtils(this, AppConstants.SP_NAME);

            // 初始化全局crash捕捉类
            MopaCrashHandler crashHandler = MopaCrashHandler.getInstance();
            crashHandler.init(getApplicationContext());

            // 初始化facebook sdk
            FacebookUtil.init(getApplicationContext());
            TwitterUtil.init(getApplicationContext());
        }
    }

    public static MopaApplication getInstance() {
        return innerInstance;
    }

    public SharePreferenceUtils getSp() {
        return sp;
    }

    @Override protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
}
