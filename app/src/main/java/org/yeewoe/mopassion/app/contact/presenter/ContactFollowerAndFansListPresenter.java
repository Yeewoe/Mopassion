package org.yeewoe.mopassion.app.contact.presenter;

import org.yeewoe.mopassion.app.common.presenter.MopaPresenter;
import org.yeewoe.mopassion.app.common.view.interfaces.IActivityView;
import org.yeewoe.mopassion.app.contact.view.iview.IContactFriendView;

/**
 * Created by wyw on 2016/4/9.
 */
public class ContactFollowerAndFansListPresenter extends MopaPresenter {
    private final IContactFriendView view;

    public ContactFollowerAndFansListPresenter(IContactFriendView view) {
        this.view = view;
    }

    @Override public void onDestroy() {

    }
}
