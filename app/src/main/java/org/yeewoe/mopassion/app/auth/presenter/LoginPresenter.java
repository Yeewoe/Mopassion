package org.yeewoe.mopassion.app.auth.presenter;

import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterSession;

import org.yeewoe.commonutils.android.async.MyAsyncTask;
import org.yeewoe.mopanet.callback.Callback;
import org.yeewoe.mopassion.app.auth.model.AuthService;
import org.yeewoe.mopassion.app.auth.model.LoginParam;
import org.yeewoe.mopassion.app.auth.view.iview.ILoginView;
import org.yeewoe.mopassion.app.common.presenter.FormPresenter;
import org.yeewoe.mopassion.app.im.model.IMService;
import org.yeewoe.mopassion.auth.FacebookUtil;
import org.yeewoe.mopassion.auth.TwitterUtil;
import org.yeewoe.mopassion.db.po.Gender;
import org.yeewoe.mopassion.utils.LogCore;

/**
 * 登录Presenter
 * Created by wyw on 2016/3/21.
 */
public class LoginPresenter extends FormPresenter<LoginParam> {

    private ILoginView view;
    private UserLoginTask mAuthTask = null;
    private AuthService authService;
    private IMService imService;


    public LoginPresenter(ILoginView view) {
        this.view = view;
        this.authService = new AuthService();
        this.imService = new IMService();
    }

    public boolean isLogging() {
        return mAuthTask != null;
    }

    public void register() {
        view.intentToRegister();
    }

    @Override
    public void onDestroy() {
        try {
            if (mAuthTask != null) {
                mAuthTask.cancel(true);
                mAuthTask = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override public void submit(LoginParam param) {
        mAuthTask = new UserLoginTask(param.account, param.password);
        mAuthTask.execute((Void) null);

    }

    @Override public void cancelSubmit() {
        onDestroy();
    }

    public void registerFb(LoginResult loginResult, Profile currentProfile) {
        register(FacebookUtil.getAccount(loginResult), FacebookUtil.getPassword(loginResult), currentProfile.getName());
    }

    public void registerTt(Result<TwitterSession> result) {
        if (result.data == null) {
            return ;
        }
        register(TwitterUtil.getAccount(result.data), TwitterUtil.getPassword(result.data), result.data.getUserName());
    }

    /**
     * 第三方注册, 性别默认是女
     * @param account
     * @param password
     * @param nickname
     */
    public void register(final  String account, final String password, final  String nickname) {

        MyAsyncTask<Void, Integer, Callback.CallbackInfo<Void>> asyncTask = new MyAsyncTask<Void, Integer, Callback.CallbackInfo<Void>>() {

            @Override
            protected void onPreExecute() {
//                view.showLoading();
            }

            @Override
            protected Callback.CallbackInfo<Void> doInBackground(Void... params) {
                return authService.register(account, nickname, Gender.FEMALE);
            }

            @Override
            protected void onPostExecute(Callback.CallbackInfo<Void> voidCallbackInfo) {
                view.dismissLoading();
                view.intentToMain();
            }
        };
        asyncTask.execute();

    }


    private class UserLoginTask extends MyAsyncTask<Void, Void, Callback.CallbackInfo<Void>> {

        private final String mEmail;
        private final String mPassword;

        public UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Callback.CallbackInfo<Void> doInBackground(Void... params) {
            LogCore.i("UserLoginTask doInBackground执行");
            return authService.login(mEmail, mPassword);
        }

        @Override
        protected void onPostExecute(final Callback.CallbackInfo<Void> result) {
            mAuthTask = null;
            view.dismissLoading();
            if (!result.bError) {
                view.intentToMain();
            } else {
                view.setPasswordError(result.errorCode);
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            view.dismissLoading();
        }
    }




}
