package org.yeewoe.mopassion.app.auth.view;

import android.app.Activity;
import android.os.Handler;

import org.yeewoe.commonutils.android.async.MyAsyncTask;
import org.yeewoe.commonutils.common.assist.Checks;
import org.yeewoe.mopanet.client.MopaNetClient;
import org.yeewoe.mopanet.net.IReconnectHandle;
import org.yeewoe.mopassion.R;
import org.yeewoe.mopassion.app.auth.presenter.LauncherPresenter;
import org.yeewoe.mopassion.app.auth.view.iview.ILauncherView;
import org.yeewoe.mopassion.app.common.view.acitivty.MopaActivity;
import org.yeewoe.mopassion.mangers.MsgHandlerManager;
import org.yeewoe.mopassion.utils.IntentManager;
import org.yeewoe.mopassion.utils.LogCore;

public class LauncherActivity extends MopaActivity<LauncherPresenter> implements ILauncherView {

    public static final int AUTH_TIME_OUT = 5000;
    public static final int ACTION_TIME_OUT = 2000;
    private boolean isAuthed = false;

    @Override
    protected int getViewRootResId() {
        return R.layout.activity_launcher;
    }

    @Override
    protected void bindIntent() {

    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void bindLister() {

    }

    @Override
    protected LauncherPresenter getPresenter() {
        return new LauncherPresenter(this);
    }

    @Override
    protected void bindData() {
        //TODO @zjm 这里有时间重构一下，严格控制好时序问题
        MsgHandlerManager.getInstance().init(this.getMainLooper());
        new Thread(new Runnable() {
            @Override public void run() {
                MopaNetClient.getInstance().Init(new IReconnectHandle() {
                    @Override public void OnReconnectOK() {
                        mPresenter.swapTime();
                        mPresenter.auth();
                    }

                    @Override public void OnReconnectFailed() {
                        LogCore.w("重连失败...");
                    }
                });
                MopaNetClient.getInstance().setServerPushHandle(MsgHandlerManager.getInstance().getH());  //增加消息推送回调.
                mPresenter.swapTime();
                mPresenter.auth();
            }
        }).start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isAuthed) {
                    intentToLogin();
                }
            }
        }, AUTH_TIME_OUT);
    }

    @Override
    protected void afterOnStart() {

    }

    @Override
    protected void afterOnResume() {

    }

    @Override
    protected void beforeOnPause() {

    }

    @Override
    protected void beforOnStop() {

    }

    @Override
    protected void beforeOnDestroy() {

    }

    @Override
    protected boolean isRegisterEvent() {
        return false;
    }


    @Override public void intentToLogin() {
        runOnUiThread(new Runnable() {
            @Override public void run() {
                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        if (Checks.check(LauncherActivity.this)) {
                            IntentManager.intentToLoginActivity(LauncherActivity.this);
                            finish();
                        }
                    }
                }, ACTION_TIME_OUT);

            }
        });
    }

    @Override public void intentToMain() {
        runOnUiThread(new Runnable() {
            @Override public void run() {
                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        if (Checks.check(LauncherActivity.this)) {
                            IntentManager.intentToMainTab(LauncherActivity.this);
                            finish();
                        }
                    }
                }, ACTION_TIME_OUT);
            }
        });
    }

    @Override public void endAuth() {
        isAuthed = true;
        intentToMain();
    }

    @Override public Activity getActivity() {
        return this;
    }
}
