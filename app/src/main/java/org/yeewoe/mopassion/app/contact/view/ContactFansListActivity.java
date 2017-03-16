package org.yeewoe.mopassion.app.contact.view;

import android.app.Activity;

import org.yeewoe.mopassion.R;
import org.yeewoe.mopassion.app.common.view.acitivty.MopaListViewActivity;
import org.yeewoe.mopassion.app.common.view.builder.TitleBuilder;
import org.yeewoe.mopassion.app.contact.presenter.ContactFansPresenter;
import org.yeewoe.mopassion.app.contact.view.adapter.UserLineAdapter;
import org.yeewoe.mopassion.app.contact.view.iview.IContactFansView;
import org.yeewoe.mopassion.constants.ContactConstants;

/**
 * 粉丝列表
 * Created by wyw on 2016/5/7.
 */
public class ContactFansListActivity extends MopaListViewActivity<ContactFansPresenter> implements IContactFansView {
    @Override protected int getViewRootResId() {
        return R.layout.activity_fans;
    }

    @Override protected UserLineAdapter getAdapter() {
        return new UserLineAdapter(this);
    }

    @Override protected void bindIntent() {

    }

    @Override protected void initTitle() {
        new TitleBuilder(this).modeToBack(this).changeCenterTxt(R.string.fans_list_title);
    }

    @Override protected void bindLister() {

    }

    @Override protected ContactFansPresenter getPresenter() {
        return new ContactFansPresenter(this, (UserLineAdapter) mAdapterMain, ContactConstants.COUNT_FOLLOWER_LIST_FIRST_PAGE, ContactConstants.COUNT_FOLLOWER_LIST_NEXT_PAGE);
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

    @Override public Activity getActivity() {
        return this;
    }


}
