package org.yeewoe.mopassion.app.contact.view;

import org.yeewoe.mopassion.R;
import org.yeewoe.mopassion.app.common.view.acitivty.MopaListViewActivity;
import org.yeewoe.mopassion.app.common.view.builder.TitleBuilder;
import org.yeewoe.mopassion.app.contact.presenter.ContactFollowerPresenter;
import org.yeewoe.mopassion.app.contact.view.adapter.UserLineAdapter;
import org.yeewoe.mopassion.app.contact.view.iview.IUserFollowerView;

/**
 * 关注列表
 * Created by wyw on 2016/5/7.
 */
public class ContactFollowerListActivity extends MopaListViewActivity<ContactFollowerPresenter> implements IUserFollowerView {
    @Override protected int getViewRootResId() {
        return R.layout.activity_follower;
    }

    @Override protected UserLineAdapter getAdapter() {
        return new UserLineAdapter(this);
    }

    @Override protected void bindIntent() {

    }

    @Override protected void initTitle() {
        new TitleBuilder(this).modeToBack(this).changeCenterTxt(R.string.follower_list_title);
    }

    @Override protected void bindLister() {

    }

    @Override protected ContactFollowerPresenter getPresenter() {
        return new ContactFollowerPresenter(this, (UserLineAdapter) mAdapterMain);
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
