package org.yeewoe.mopassion.app.maintab.view;

import android.os.Bundle;

import org.yeewoe.mopassion.R;
import org.yeewoe.mopassion.app.common.adapter.MopaAdapter;
import org.yeewoe.mopassion.app.common.view.fragment.MopaListViewFragment;
import org.yeewoe.mopassion.app.maintab.view.iview.IMeTrendView;
import org.yeewoe.mopassion.app.nearby.presenter.UserTrendPresenter;
import org.yeewoe.mopassion.app.nearby.view.adapter.UserTrendLineAdapter;
import org.yeewoe.mopassion.mangers.UserDataMananger;

/**
 * Created by acc on 2016/7/27.
 */
public class UserTrendFragment extends MopaListViewFragment<UserTrendPresenter> implements IMeTrendView {
    private static final String ARG_UID = "key_uid";
    private long mUid;

    public UserTrendFragment() {

    }

    public static UserTrendFragment newInstantce(long uid) {
        UserTrendFragment fragment = new UserTrendFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_UID, uid);
        fragment.setArguments(args);
        return fragment;
    }


    @Override protected void onListViewCreateViewInit(Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            mUid = arguments.getLong(ARG_UID, 0L);
        }

        if (mUid <= 0) {
            mUid = UserDataMananger.getInstance().getMeUid();
        }
    }

    @Override protected MopaAdapter getAdapter() {
        return new UserTrendLineAdapter(getContext());
    }

    @Override protected int getViewRootResId() {
        return R.layout.common_listview;
    }

    @Override protected UserTrendPresenter getPresenter() {
        return new UserTrendPresenter(this, (UserTrendLineAdapter) mAdapterMain);
    }

    @Override protected Object getLoadParam() {
        return mUid;
    }
}
