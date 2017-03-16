package org.yeewoe.mopassion.app.setting.presenter;

import org.yeewoe.mopanet.callback.Callback;
import org.yeewoe.mopassion.app.auth.model.AuthService;
import org.yeewoe.mopassion.app.common.presenter.MopaPresenter;
import org.yeewoe.mopassion.app.setting.view.iview.ISettingMainView;

/**
 * Created by acc on 2016/5/20.
 */
public class SettingMainPresenter extends MopaPresenter {
    private final ISettingMainView view;
    private AuthService authService;

    public SettingMainPresenter(ISettingMainView view) {
        this.view = view;
        this.authService = new AuthService();
    }

    @Override public void onDestroy() {

    }

    public void logout() {
        view.showLoading();
        this.authService.logout(new Callback() {
            @Override public <T> void callback(CallbackInfo<T> info) {
                view.runOnUiThread(new Runnable() {
                    @Override public void run() {
                        view.dismissLoading();
                        view.intentToLogin();
                    }
                });
            }
        });
    }

}
