package org.yeewoe.mopassion.app.help.view;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.TextView;

import org.yeewoe.mopassion.R;
import org.yeewoe.mopassion.app.common.view.acitivty.MopaActivity;
import org.yeewoe.mopassion.app.common.view.builder.TitleBuilder;
import org.yeewoe.mopassion.app.help.presenter.HelpMainPresenter;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 帮助页
 * Created by wyw on 2016/5/20.
 */
public class HelpMainActivity extends MopaActivity<HelpMainPresenter> {
    @Bind(R.id.txt_current_version) TextView mTxtCurrentVersion;

    @Override protected int getViewRootResId() {
        return R.layout.activity_help_main;
    }

    @Override protected void bindIntent() {

    }

    @Override protected void initTitle() {
        new TitleBuilder(this).modeToBack(this).changeCenterTxt(R.string.help).getCenterTxt().setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                int i = 5 / 0;
            }
        });
    }

    @Override protected void bindLister() {

    }

    @Override protected HelpMainPresenter getPresenter() {
        return null;
    }

    @Override protected void bindData() {
        try {
            PackageManager pm = getPackageManager();
            PackageInfo pi = pm.getPackageInfo(getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);
            mTxtCurrentVersion.setText(getString(R.string.help_main_version_current, pi.versionName));
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    @OnClick(R.id.rl_version_check) void onVersionCheckClick(View view) {
        showToast(R.string.unfinish_func);
    }

    @OnClick(R.id.rl_version_intro) void onVersionIntroClick(View view) {
        showToast(R.string.unfinish_func);
    }

    @OnClick(R.id.rl_protocol) void onProtocolClick(View view) {
        showToast(R.string.unfinish_func);
    }
}
