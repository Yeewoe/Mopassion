package org.yeewoe.mopassion.app.nearby.view;

import android.content.Context;
import android.os.Bundle;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.yeewoe.mopassion.R;
import org.yeewoe.mopassion.app.common.adapter.MopaAdapter;
import org.yeewoe.mopassion.app.common.view.fragment.MopaListViewFragment;
import org.yeewoe.mopassion.app.common.view.widget.MopaListView;
import org.yeewoe.mopassion.app.common.view.widget.MopaLoadMoreListViewContainer;
import org.yeewoe.mopassion.app.common.view.widget.MopaPtrFrameLayout;
import org.yeewoe.mopassion.app.contact.view.iview.IContactFriendView;
import org.yeewoe.mopassion.app.nearby.presenter.NearByUserPresenter;
import org.yeewoe.mopassion.app.nearby.view.adapter.NearByUserLineAdapter;
import org.yeewoe.mopassion.constants.ContactConstants;
import org.yeewoe.mopassion.event.LocationChangeEvent;
import org.yeewoe.mopanet.protos.PBTrendsPurpose;

import butterknife.Bind;

/**
 * 附近-人 TAB
 */
public class NearByUserFragment extends MopaListViewFragment<NearByUserPresenter> implements IContactFriendView {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public NearByUserFragment() {
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
    public static NearByUserFragment newInstance(String param1, String param2) {
        NearByUserFragment fragment = new NearByUserFragment();
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
        return R.layout.common_listview;
    }

    @Override protected void onListViewCreateViewInit(Bundle savedInstanceState) {

    }

    @Override protected MopaAdapter getAdapter() {
        return new NearByUserLineAdapter(getContext());
    }

    @Override protected NearByUserPresenter getPresenter() {
        return new NearByUserPresenter(this, (NearByUserLineAdapter) mAdapterMain, ContactConstants.COUNT_USER_LIST_FIRST_PAGE, ContactConstants.COUNT_USER_LIST_NEXT_PAGE);
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


    @Override protected void onBind() {
        mPresenter.loadFirstPage(PBTrendsPurpose.TRDP_M4W.getValue());
    }

    @Subscribe(threadMode = ThreadMode.MAIN) public void onLocationChange(LocationChangeEvent event) {
        mPresenter.loadAllCurrentPage(null);
    }
}
