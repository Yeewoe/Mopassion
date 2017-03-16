package org.yeewoe.mopassion.app.setting.view;

import android.app.Activity;
import android.view.View;

import org.yeewoe.mopassion.R;
import org.yeewoe.mopassion.app.common.view.acitivty.MopaActivity;
import org.yeewoe.mopassion.app.common.view.builder.TitleBuilder;
import org.yeewoe.mopassion.app.common.view.dialog.MopaSelectDialog;
import org.yeewoe.mopassion.app.setting.presenter.SettingMainPresenter;
import org.yeewoe.mopassion.app.setting.view.iview.ISettingMainView;
import org.yeewoe.mopassion.utils.IntentManager;

import butterknife.OnClick;

/**
 * 设置页
 * Created by wyw on 2016/5/20.
 */
public class SettingMainActivity extends MopaActivity<SettingMainPresenter> implements ISettingMainView {
    @Override protected int getViewRootResId() {
        return R.layout.activity_setting_main;
    }

    @Override protected void bindIntent() {

    }

    @Override protected void initTitle() {
        new TitleBuilder(this).modeToBack(this).changeCenterTxt(R.string.setting);
    }

    @Override protected void bindLister() {

    }

    @Override protected SettingMainPresenter getPresenter() {
        return new SettingMainPresenter(this);
    }

    @Override protected void bindData() {

    }

    @Override protected void afterOnStart() {

    }

    @Override protected void afterOnResume() {

    }

    @Override protected void beforeOnPause() {

    }

    @Override protected void beforOnStop() {

    }

    @Override protected void beforeOnDestroy() {

    }

    @OnClick(R.id.rl_logout) void onLogoutClick(View view) {
        mPresenter.logout();
    }

    @Override public void intentToLogin() {
        IntentManager.intentToLoginActivity(this);
    }

    @Override public Activity getActivity() {
        return this;
    }
}
