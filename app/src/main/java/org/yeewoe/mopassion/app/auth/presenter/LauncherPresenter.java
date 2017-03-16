package org.yeewoe.mopassion.app.auth.presenter;

import org.yeewoe.mopanet.callback.Callback;
import org.yeewoe.mopassion.app.auth.model.AuthService;
import org.yeewoe.mopassion.app.auth.view.iview.ILauncherView;
import org.yeewoe.mopassion.app.common.presenter.MopaPresenter;
import org.yeewoe.mopassion.app.common.service.EntryService;

/**
 * Created by wyw on 2016/3/21.
 */
public class LauncherPresenter extends MopaPresenter {

    private final ILauncherView view;
    private EntryService entryService;
    private AuthService authService;

    public LauncherPresenter(ILauncherView view) {
        entryService = new EntryService();
        authService = new AuthService();
        this.view = view;
    }

    @Override
    public void onDestroy() {

    }

    public void swapTime() {
        entryService.swapTime();
    }

    public void auth() {
        Callback.CallbackInfo<Void> auth = authService.auth();
        if (auth.bError) {
            view.intentToLogin();
        } else {
            view.endAuth();
        }
    }
}
