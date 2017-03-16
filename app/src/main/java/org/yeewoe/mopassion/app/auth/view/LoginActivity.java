package org.yeewoe.mopassion.app.auth.view;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import org.yeewoe.commonutils.common.assist.Checks;
import org.yeewoe.commonutils.common.assist.UIVerifier;
import org.yeewoe.mopassion.R;
import org.yeewoe.mopassion.app.auth.model.LoginParam;
import org.yeewoe.mopassion.app.auth.presenter.LoginPresenter;
import org.yeewoe.mopassion.app.auth.view.iview.ILoginView;
import org.yeewoe.mopassion.app.common.view.acitivty.MopaActivity;
import org.yeewoe.mopassion.app.common.view.interfaces.IWaitView;
import org.yeewoe.mopassion.utils.IntentManager;
import org.yeewoe.mopassion.utils.UIErrorUtil;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 登录页面
 */
public class LoginActivity extends MopaActivity<LoginPresenter> implements ILoginView, IWaitView {

    @Bind(value = R.id.edit_account) EditText mAccountView;
    @Bind(value = R.id.edit_password) EditText mPasswordView;
    @Bind(value = R.id.fb_btn_login) LoginButton mFbBtnLogin;
    @Bind(value = R.id.tt_btn_login) TwitterLoginButton mTtBtnLogin;

    private UIVerifier mVerifier;
    private CallbackManager mCallbackManager;
    private ProfileTracker mProfileTracker;
    private LoginResult mLoginResult;

    @Override
    protected int getViewRootResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void bindIntent() {

    }

    @Override
    protected void initTitle() {
    }

    @Override
    protected void bindLister() {

        // facebook
        mProfileTracker = new ProfileTracker() {
            @Override protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                if (currentProfile != null) {
                    mPresenter.registerFb(mLoginResult, currentProfile);
                }
            }
        };
        mProfileTracker.startTracking();

        mCallbackManager = CallbackManager.Factory.create();
//        mFbBtnLogin.setReadPermissions("user_friends");
        mFbBtnLogin.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override public void onSuccess(LoginResult loginResult) {
                showLoading();
                LoginActivity.this.mLoginResult = loginResult;
            }

            @Override public void onCancel() {
                showToast(getString(R.string.login_cancel_facebook_auth));
            }

            @Override public void onError(FacebookException error) {
                showToast(getString(R.string.login_error_facebook_auth, error.toString()));
            }
        });


        // twitter
        mTtBtnLogin.setCallback(new Callback<TwitterSession>() {
            @Override public void success(Result<TwitterSession> result) {
                showLoading();
                mPresenter.registerTt(result);
            }

            @Override public void failure(TwitterException e) {
                e.printStackTrace();
                showToast(getString(R.string.login_error_twitter_auth, e.getMessage()));
            }
        });

    }

    @Override protected void onDestroy() {
        super.onDestroy();
        mProfileTracker.stopTracking();
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        mTtBtnLogin.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected LoginPresenter getPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected void bindData() {
        mVerifier = new UIVerifier();
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

    @OnClick(value = R.id.btn_email_sign_in)
    public void onSignInButtonClick(View view) {
        attemptLogin();
    }

    @OnClick(value = R.id.btn_register)
    public void onRegisterButtonClick(View view) {
        mPresenter.register();
    }

    private void attemptLogin() {
        if (mPresenter.isLogging()) {
            return;
        }

        mAccountView.setError(null);
        mPasswordView.setError(null);

        boolean cancel = false;
        View focusView = null;

        if (!mVerifier.verifyEmpty(mPasswordView) || !mVerifier.verifyLength(mPasswordView, 4, -1)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if (!mVerifier.verifyEmpty(mAccountView)) {
            mAccountView.setError(getString(R.string.error_field_required));
            focusView = mAccountView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showLoading();
            //TODO @zjm mock 默认密码为用户名，方便测试
            String password = mPasswordView.getText().toString();
            String username = mAccountView.getText().toString();
            password = username + "\0";

            mPresenter.submit(new LoginParam(username, password));
        }
    }

    @Override
    public void intentToRegister() {
        IntentManager.intentToRegister(this);
    }

    @Override
    public void intentToMain() {
        if (Checks.check(this)) {
            IntentManager.intentToMainTab(LoginActivity.this);
            finish();
        }
    }

    @Override
    public void setPasswordError(int errorCode) {
        if (Checks.check(this)) {
            mPasswordView.setError(UIErrorUtil.getErrorInfo(this, errorCode));
            mPasswordView.requestFocus();
        }
    }

    @Override public void intentToLogin() {
        IntentManager.intentToLoginActivity(this);
    }

    @Override public void onLoadingDismiss() {
        mPresenter.cancelSubmit();
    }

    @Override public Activity getActivity() {
        return this;
    }
}

