package org.yeewoe.mopassion.app.nearby.view;

import android.content.Context;
import android.os.Bundle;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.yeewoe.mopassion.R;
import org.yeewoe.mopassion.app.common.adapter.MopaAdapter;
import org.yeewoe.mopassion.app.common.view.fragment.MopaListViewFragment;
import org.yeewoe.mopassion.app.nearby.model.TrendLineVo;
import org.yeewoe.mopassion.app.nearby.presenter.NearByTrendPresenter;
import org.yeewoe.mopassion.app.nearby.view.adapter.NearTrendLineAdapter;
import org.yeewoe.mopassion.app.nearby.view.iview.INearByTrend;
import org.yeewoe.mopassion.event.LocationChangeEvent;
import org.yeewoe.mopassion.event.OnActivityResultReceiveEvent;
import org.yeewoe.mopanet.protos.PBTrendsPurpose;

/**
 * 附近-动态 TAB
 */
public class NearByTrendFragment extends MopaListViewFragment<NearByTrendPresenter> implements INearByTrend {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private int mPurpose = 1;  //过滤条件

    private String mParam1;
    private String mParam2;

    public NearByTrendFragment() {
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
    public static NearByTrendFragment newInstance(String param1, String param2) {
        NearByTrendFragment fragment = new NearByTrendFragment();
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
        return new NearTrendLineAdapter(getContext());
    }

    @Override
    protected NearByTrendPresenter getPresenter() {
        return new NearByTrendPresenter(this, (NearTrendLineAdapter) mAdapterMain);
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
        mPresenter.loadAllCurrentPage(mPurpose);
    }

    @Subscribe(threadMode = ThreadMode.MAIN) public void onActivityResultReceive(OnActivityResultReceiveEvent event) {
        showToast(event.toString());
        if (event.parcelable instanceof TrendLineVo) {
            mPresenter.addLast((TrendLineVo) event.parcelable);
        }
    }
    @Override
    protected Object getLoadParam() {
        return mPurpose;
    }


    public void menSeekMen() {
        mPurpose =   PBTrendsPurpose.TRDP_M4M.getValue();
        mPresenter.loadFirstPage(mPurpose);
    }

    public void menSeekWomen() {
        mPurpose =   PBTrendsPurpose.TRDP_M4W.getValue();
        mPresenter.loadFirstPage(mPurpose);
    }

    public void womenSeekMen() {
        mPurpose =   PBTrendsPurpose.TRDP_W4M.getValue();
        mPresenter.loadFirstPage(mPurpose);
    }

    public void womenSeekWomen() {
        mPurpose =   PBTrendsPurpose.TRDP_W4W.getValue();
        mPresenter.loadFirstPage(mPurpose);
    }
}
