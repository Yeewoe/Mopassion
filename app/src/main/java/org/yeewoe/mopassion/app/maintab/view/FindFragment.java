package org.yeewoe.mopassion.app.maintab.view;

import android.content.Context;
import android.os.Bundle;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.yeewoe.mopassion.R;
import org.yeewoe.mopassion.app.common.adapter.MopaAdapter;
import org.yeewoe.mopassion.app.common.view.builder.TitleBuilder;
import org.yeewoe.mopassion.app.common.view.fragment.MopaGridViewFragment;
import org.yeewoe.mopassion.app.contact.view.iview.IContactFriendView;
import org.yeewoe.mopassion.app.nearby.presenter.NearByUserGridPresenter;
import org.yeewoe.mopassion.app.nearby.view.adapter.NearByUserGridAdapter;
import org.yeewoe.mopassion.constants.ContactConstants;
import org.yeewoe.mopassion.event.LocationChangeEvent;

/**
 * 附近-人 TAB
 */
public class FindFragment extends MopaGridViewFragment<NearByUserGridPresenter> implements IContactFriendView {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public FindFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MeFragment.
     */
    public static FindFragment newInstance(String param1, String param2) {
        FindFragment fragment = new FindFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    protected int getViewRootResId() {
        return R.layout.fragment_main_discovery;
    }

    @Override protected MopaAdapter getAdapter() {
        return new NearByUserGridAdapter(getContext());
    }

    @Override protected NearByUserGridPresenter getPresenter() {
        return new NearByUserGridPresenter(this, (NearByUserGridAdapter) mAdapterMain, ContactConstants.COUNT_USER_LIST_FIRST_PAGE, ContactConstants.COUNT_USER_LIST_NEXT_PAGE);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override public void onResume() {
        super.onResume();
        mEventBus.register(this);
    }

    @Override public void onPause() {
        mEventBus.unregister(this);
        super.onPause();
    }

    @Subscribe(threadMode = ThreadMode.MAIN) public void onLocationChange(LocationChangeEvent event) {
        mPresenter.loadAllCurrentPage(null);
    }

    @Override protected void onGridViewCreateViewInit(Bundle savedInstanceState) {
        new TitleBuilder(getRootView()).leftGone().changeCenterTxt(R.string.discovery_fragment_title);
    }
}
