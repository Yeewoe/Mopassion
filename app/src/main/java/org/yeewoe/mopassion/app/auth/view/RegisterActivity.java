package org.yeewoe.mopassion.app.auth.view;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import org.greenrobot.eventbus.Subscribe;
import org.yeewoe.commonutils.common.cache.FragmentCacheCenter;
import org.yeewoe.mopassion.R;
import org.yeewoe.mopassion.app.auth.presenter.RegisterPresenter;
import org.yeewoe.mopassion.app.auth.view.iview.IRegisterView;
import org.yeewoe.mopassion.app.common.view.acitivty.MopaActivity;
import org.yeewoe.mopassion.event.KeyValueChangeEvent;
import org.yeewoe.mopassion.event.TabChangeEvent;
import org.yeewoe.mopassion.utils.IntentManager;
import org.yeewoe.mopassion.utils.LogCore;

import butterknife.Bind;

public class RegisterActivity extends MopaActivity<RegisterPresenter> implements IRegisterView {

    @Bind(R.id.fl_main_content)
    FrameLayout mFlMainContent;

    private FragmentCacheCenter fragmentCacheCenter = new FragmentCacheCenter();
    private int mCurrentPosition;

    private Class[] fragmentClasses = new Class[]{
            RegisterAccountFragment.class,
            RegisterPasswordFragment.class,
            RegisterGenderFragment.class
    };

    private String mDataNick;
    private String mDataAccount;
    private String mDataPassword;
    private Integer mDataGender;


    @Override
    protected int getViewRootResId() {
        return R.layout.activity_register;
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
    protected RegisterPresenter getPresenter() {
        return new RegisterPresenter(this);
    }


    @Override
    protected void bindData() {
        changeTab(0);
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
        return true;
    }

    @Override
    public boolean handleBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            finish();
        } else {
            getSupportFragmentManager().popBackStack();
        }
        return true;
    }

    public void changeTab(int position) {
        Class fragmentClass = fragmentClasses[position];

        Fragment fragmentTab;
        fragmentTab = fragmentCacheCenter.get(fragmentClass);
        boolean isFirstPage = false;
        if (fragmentTab == null) {
            if (fragmentClass == RegisterPasswordFragment.class) {
                fragmentTab = RegisterPasswordFragment.newInstance("", "");
            } else if (fragmentClass == RegisterGenderFragment.class) {
                fragmentTab = RegisterGenderFragment.newInstance("", "");
            } else {
                fragmentTab = RegisterAccountFragment.newInstance();
                isFirstPage = true;
            }
        }
        fragmentCacheCenter.put(fragmentClass, fragmentTab);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_right_out)
                .replace(R.id.fl_main_content, fragmentTab);
        if (!isFirstPage) {
            transaction.addToBackStack(null);
        }
        transaction.commit();

        this.mCurrentPosition = position;
    }

    @Override
    public void changeToPre() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void changeToNext() {
        changeTab(this.mCurrentPosition + 1);
    }

    @Subscribe
    public void onTabChangeEvent(@NonNull TabChangeEvent event) {
        LogCore.i("onTabChangeEvent:" + event.toString());
        changeTab(event.position);
    }

    @Subscribe
    public void onKeyValueChangeEvent(@NonNull KeyValueChangeEvent event) {
        LogCore.i("onKeyValueChangeEvent:" + event.toString());
        if (event.contains(IRegisterView.DATA_KEY_NICK_NAME)) {
            mDataNick = (String) event.get(IRegisterView.DATA_KEY_NICK_NAME);
        }
        if (event.contains(IRegisterView.DATA_KEY_ACCOUNT)) {
            mDataAccount = (String) event.get(IRegisterView.DATA_KEY_ACCOUNT);
        }
        if (event.contains(IRegisterView.DATA_KEY_PASSWORD)) {
            mDataPassword = (String) event.get(IRegisterView.DATA_KEY_PASSWORD);
        }
        if (event.contains(IRegisterView.DATA_KEY_GENDER)) {
            mDataGender = (Integer) event.get(IRegisterView.DATA_KEY_GENDER);
        }

        if (mDataNick != null && mDataAccount != null && mDataPassword != null && mDataGender != null) {
            mPresenter.register(mDataAccount, mDataPassword, mDataNick, mDataGender);
        }
    }

    public void intentToLogin() {
        IntentManager.intentToLoginActivity(RegisterActivity.this);
    }
}
