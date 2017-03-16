package org.yeewoe.mopassion.app.maintab.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.View;

import org.yeewoe.commonutils.common.cache.FragmentCacheCenter;
import org.yeewoe.mopassion.R;
import org.yeewoe.mopassion.app.common.presenter.MopaPresenter;
import org.yeewoe.mopassion.app.common.view.fragment.MopaFragment;
import org.yeewoe.mopassion.app.common.view.widget.MopaNearByFilterPopupWindow;
import org.yeewoe.mopassion.app.nearby.view.NearByTrendFragment;
import org.yeewoe.mopassion.utils.IntentManager;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 附近TAB
 */
public class NearByFragment extends MopaFragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int REQUEST_TREND_PUBLISH = 0x666;

    private String mParam1;
    private String mParam2;

    private MopaNearByFilterPopupWindow mNearByFilterPopupWindow;

    @Bind(R.id.ll_title) View mLLTitle;

    public NearByFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NearByFragment.
     */
    public static NearByFragment newInstance(String param1, String param2) {
        NearByFragment fragment = new NearByFragment();
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
        return R.layout.fragment_main_near_by;
    }

    @Override
    protected void onCreateViewInit(Bundle savedInstanceState) {

    }

    @Override
    protected MopaPresenter getPresenter() {
        return null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @OnClick(value = R.id.imgbtn_publish_trend) void onPublishTrendOnClick(View view) {
        IntentManager.Trend.intentToPublishTrend(getActivity(), MainTabActivity.REQUEST_TREND_PUBLISH);
    }

    @OnClick(value = R.id.title_center_txt) void onTitleCenterClick(View view) {
        clickMenu();
    }

    @OnClick(value = R.id.imgvi_menu_arrow) void onMenuArrowClick(View view) {
        clickMenu();
    }

    private void clickMenu() {
        if (mNearByFilterPopupWindow == null) {
            mNearByFilterPopupWindow = new MopaNearByFilterPopupWindow(getContext());
            mNearByFilterPopupWindow.setOnItemClickListener(new MopaNearByFilterPopupWindow.OnItemClickListener() {
                @Override public void menSeekMen() {

                    NearByTrendFragment fragment = (NearByTrendFragment) getChildFragmentManager().findFragmentById(R.id.fragment_nearby_trend);
                    if (fragment != null) {
                        fragment.menSeekMen();
                    }
                }

                @Override public void menSeekWomen() {
                    NearByTrendFragment fragment = (NearByTrendFragment) getChildFragmentManager().findFragmentById(R.id.fragment_nearby_trend);
                    if (fragment != null) {
                        fragment.menSeekWomen();
                    }
                }

                @Override public void womenSeekMen() {
                    NearByTrendFragment fragment = (NearByTrendFragment) getChildFragmentManager().findFragmentById(R.id.fragment_nearby_trend);
                    if (fragment != null) {
                        fragment.womenSeekMen();
                    }
                }

                @Override public void womenSeekWomen() {
                    NearByTrendFragment fragment = (NearByTrendFragment) getChildFragmentManager().findFragmentById(R.id.fragment_nearby_trend);
                    if (fragment != null) {
                        fragment.womenSeekWomen();
                    }
                }
            });

        }
        if (mNearByFilterPopupWindow.isShowing()) {
            mNearByFilterPopupWindow.dismiss();
        }

        mNearByFilterPopupWindow.setFocusable(true);
        mNearByFilterPopupWindow.showAsDropDown(mLLTitle, getActivity().getWindowManager().getDefaultDisplay().getWidth() / 2 - mNearByFilterPopupWindow.getWidth() / 2, 0);
    }
}
