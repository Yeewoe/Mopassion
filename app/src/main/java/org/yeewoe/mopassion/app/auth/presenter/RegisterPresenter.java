package org.yeewoe.mopassion.app.auth.presenter;

import org.yeewoe.commonutils.android.async.MyAsyncTask;
import org.yeewoe.mopanet.callback.Callback;
import org.yeewoe.mopassion.app.auth.model.AuthService;
import org.yeewoe.mopassion.app.auth.view.iview.IRegisterView;
import org.yeewoe.mopassion.app.common.presenter.MopaPresenter;

/**
 * 注册Presenter
 * Created by wyw on 2016/3/21.
 */
public class RegisterPresenter extends MopaPresenter {

    private final IRegisterView view;
    private AuthService authService;
    private MyAsyncTask<Void, Integer, Callback.CallbackInfo<Void>> asyncTask;

    public RegisterPresenter(IRegisterView view) {
        this.view = view;
        this.authService = new AuthService();
    }

    public void register(final  String account, final String password, final  String nickname, final int gender) {

        asyncTask = new MyAsyncTask<Void, Integer, Callback.CallbackInfo<Void>>() {

            @Override
            protected void onPreExecute() {
                view.showLoading();
            }

            @Override
            protected Callback.CallbackInfo<Void> doInBackground(Void... params) {
                return authService.register(account, nickname, gender);
            }

            @Override
            protected void onPostExecute(Callback.CallbackInfo<Void> voidCallbackInfo) {
                view.dismissLoading();
                view.intentToLogin();
            }
        };
        asyncTask.execute();

    }

    @Override
    public void onDestroy() {
        if (asyncTask != null) {
            asyncTask.cancel(true);
        }
    }
}
