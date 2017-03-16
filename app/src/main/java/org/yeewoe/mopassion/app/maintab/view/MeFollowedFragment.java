package org.yeewoe.mopassion.app.maintab.view;

import android.os.Bundle;

import org.yeewoe.mopassion.R;
import org.yeewoe.mopassion.app.common.adapter.MopaAdapter;
import org.yeewoe.mopassion.app.common.view.fragment.MopaListViewFragment;
import org.yeewoe.mopassion.app.contact.presenter.ContactFollowerPresenter;
import org.yeewoe.mopassion.app.contact.view.adapter.UserLineAdapter;
import org.yeewoe.mopassion.app.maintab.view.iview.IMeFollowedView;

/**
 * Created by acc on 2016/7/27.
 */
public class MeFollowedFragment extends MopaListViewFragment<ContactFollowerPresenter> implements IMeFollowedView {
    @Override protected void onListViewCreateViewInit(Bundle savedInstanceState) {

    }

    @Override protected MopaAdapter getAdapter() {
        return new UserLineAdapter(getContext());
    }

    @Override protected int getViewRootResId() {
        return R.layout.common_listview;
    }

    @Override protected ContactFollowerPresenter getPresenter() {
        return new ContactFollowerPresenter(this, (UserLineAdapter) mAdapterMain);
    }
}
