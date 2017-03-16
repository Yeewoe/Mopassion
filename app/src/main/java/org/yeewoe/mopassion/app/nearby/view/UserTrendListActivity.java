package org.yeewoe.mopassion.app.nearby.view;

import org.yeewoe.mopassion.R;
import org.yeewoe.mopassion.app.common.adapter.MopaAdapter;
import org.yeewoe.mopassion.app.common.view.acitivty.MopaListViewActivity;
import org.yeewoe.mopassion.app.common.view.builder.TitleBuilder;
import org.yeewoe.mopassion.app.contact.model.UserDetailVo;
import org.yeewoe.mopassion.app.nearby.presenter.UserTrendPresenter;
import org.yeewoe.mopassion.app.nearby.view.adapter.UserTrendLineAdapter;
import org.yeewoe.mopassion.app.nearby.view.iview.IUserTrendView;

/**
 * 个人动态页面
 * Created by wyw on 2016/5/9.
 */
public class UserTrendListActivity extends MopaListViewActivity<UserTrendPresenter> implements IUserTrendView {
    public static final String EXTRA_USER_DETAIL = "extra_user_detail";
    public static final String EXTRA_USER_SID = "extra_user_sid";

    private UserDetailVo mUserDetailVo;

    @Override protected Object getLoadParam() {
        return mUserDetailVo.getSid();
    }

    @Override protected MopaAdapter getAdapter() {
        return new UserTrendLineAdapter(this);
    }

    @Override protected int getViewRootResId() {
        return R.layout.activity_user_trend_list;
    }

    @Override protected void bindIntent() {
        mUserDetailVo = new UserDetailVo(null);
        UserDetailVo tempVo = getIntent().getParcelableExtra(EXTRA_USER_DETAIL);
        if (tempVo != null) {
            this.mUserDetailVo.setNickname(tempVo.getNickname());
            this.mUserDetailVo.setSid(tempVo.getSid());
        } else {
            long sid = getIntent().getLongExtra(EXTRA_USER_SID, -1);
            this.mUserDetailVo.setSid(sid);
        }
    }

    @Override protected void initTitle() {
        new TitleBuilder(this).modeToBack(this).changeCenterTxt(getString(R.string.user_trend_list_title, mUserDetailVo.getNickname()));
    }

    @Override protected void bindLister() {

    }

    @Override protected UserTrendPresenter getPresenter() {
        return new UserTrendPresenter(this, (UserTrendLineAdapter) mAdapterMain);
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
}
