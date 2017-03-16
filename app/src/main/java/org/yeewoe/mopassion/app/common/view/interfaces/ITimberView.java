package org.yeewoe.mopassion.app.common.view.interfaces;

/**
 * View接口，公共提示
 * Created by wyw on 2016/4/7.
 */
public interface ITimberView extends IMopaView {
    void showToast(String text);
    void showToast(int resId);
    void showLoading();
    void onLoadingDismiss();
    void dismissLoading();
    void showError(int errorCode);
}
