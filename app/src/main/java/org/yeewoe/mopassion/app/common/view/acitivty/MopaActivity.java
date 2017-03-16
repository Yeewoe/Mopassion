package org.yeewoe.mopassion.app.common.view.acitivty;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import org.greenrobot.eventbus.EventBus;
import org.yeewoe.commonutils.common.assist.Checks;
import org.yeewoe.commonutils.common.assist.UIVerifier;
import org.yeewoe.mopassion.R;
import org.yeewoe.mopassion.app.common.presenter.MopaPresenter;
import org.yeewoe.mopassion.utils.AnimationUtil;
import org.yeewoe.mopassion.utils.LogCore;
import org.yeewoe.mopassion.app.common.view.dialog.MopaLoadingDialog;
import org.yeewoe.mopassion.app.common.view.widget.MopaToast;
import org.yeewoe.mopassion.utils.UIErrorUtil;

import butterknife.ButterKnife;

/**
 * 公共基类Activity
 * Created by wyw on 2016/3/4.
 */
public abstract class MopaActivity<T extends MopaPresenter> extends FragmentActivity {

    protected EventBus mEventBus;
    protected T mPresenter;
    private MopaLoadingDialog mLoadingDialog;
    protected UIVerifier mVerifier = new UIVerifier();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogCore.i("进入Activity: " + getClass().getSimpleName());
        beforeSetContentView();
        setContentView(getViewRootResId());
        ButterKnife.bind(this);
        if (isScaleIn()) {
            AnimationUtil.scaleIn(this);
        }
        bindIntent();
        initTitle();
        initSupperView();
        mEventBus = EventBus.getDefault();
        mPresenter = getPresenter();
        bindLister();
        bindData();
        bindData(savedInstanceState);
    }

    @Override public void finish() {
        super.finish();
        if (isScaleIn()) {
            AnimationUtil.scaleOut(this);
        }
    }

    /** 是否是用渐进动画进入，默认使用全局切换动画 **/
    protected boolean isScaleIn() {
        return false;
    }

    protected void beforeSetContentView() {

    }

    protected void initSupperView() {

    }

    protected abstract int getViewRootResId();

    protected abstract void bindIntent();

    protected abstract void initTitle();

    protected abstract void bindLister();

    protected abstract T getPresenter();

    protected abstract void bindData();

    protected void bindData(Bundle savedInstanceState) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        LogCore.d("onStart: " + getClass().getSimpleName());
        afterOnStart();
    }

    protected abstract void afterOnStart();

    @Override
    protected void onResume() {
        super.onResume();
        LogCore.d("onResume: " + getClass().getSimpleName());
        if (isRegisterEvent()) {
            mEventBus.register(this);
        }
        afterOnResume();
    }

    protected abstract void afterOnResume();

    @Override
    protected void onPause() {
        LogCore.d("onPause: " + getClass().getSimpleName());
        if (isRegisterEvent()) {
            mEventBus.unregister(this);
        }
        beforeOnPause();
        super.onPause();
    }

    protected abstract void beforeOnPause();

    @Override
    protected void onStop() {
        LogCore.d("onStop: " + getClass().getSimpleName());
        beforOnStop();
        super.onStop();
    }

    protected abstract void beforOnStop();

    @Override
    protected void onDestroy() {
        LogCore.i("退出Activity:　" + getClass().getSimpleName());
        beforeOnDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        super.onDestroy();
    }

    protected abstract void beforeOnDestroy();

    @Override
    public void onBackPressed() {
        if(!handleBackPressed()) {
            super.onBackPressed();
        }
    }

    public void showToast(String text) {
        MopaToast.makeText(this, text, MopaToast.LENGTH_SHORT).show();
    }

    public void showToast(int resId) {
        MopaToast.makeText(this, resId, MopaToast.LENGTH_SHORT).show();
    }

    public void showLoading() {
        try {
            runOnUiThread(new Runnable() {
                @Override public void run() {
                    if (Checks.check(MopaActivity.this)) {
                        if (mLoadingDialog == null) {
                            mLoadingDialog = new MopaLoadingDialog(MopaActivity.this);
                            mLoadingDialog.setCancelable(false);
                        }
                        if (!mLoadingDialog.isShowing()) {
                            mLoadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                            mLoadingDialog.setMessage(getString(R.string.please_wait));
                            mLoadingDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override public void onDismiss(DialogInterface dialog) {
                                    onLoadingDismiss();
                                }
                            });
                            mLoadingDialog.show();
                        }
                    }
                }
            });

        } catch (Exception ignored) {
        }
    }

    public void onLoadingDismiss() {

    }

    public void dismissLoading() {
        runOnUiThread(new Runnable() {
            @Override public void run() {
                if (Checks.check(MopaActivity.this)) {
                    if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
                        mLoadingDialog.dismiss();
                        mLoadingDialog = null;
                    }
                }
            }
        });

    }

    public void showError(int errorCode) {
        showToast(UIErrorUtil.getErrorInfo(this, errorCode));
    }

    public boolean handleBackPressed(){
        return false;
    }

    protected boolean isRegisterEvent() {
        return false;
    }
}
