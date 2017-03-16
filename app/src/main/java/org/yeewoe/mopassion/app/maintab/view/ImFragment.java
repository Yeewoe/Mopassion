package org.yeewoe.mopassion.app.maintab.view;

import android.content.Context;
import android.os.Bundle;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.yeewoe.mopassion.R;
import org.yeewoe.mopassion.app.common.view.builder.TitleBuilder;
import org.yeewoe.mopassion.app.common.view.fragment.MopaFragment;
import org.yeewoe.mopassion.app.common.view.widget.MopaListView;
import org.yeewoe.mopassion.app.maintab.presenter.ImPresenter;
import org.yeewoe.mopassion.app.maintab.view.apdaters.ImListAdapter;
import org.yeewoe.mopassion.app.maintab.view.iview.IImView;
import org.yeewoe.mopassion.db.po.Message;
import org.yeewoe.mopassion.event.PushHandleEvent;
import org.yeewoe.mopassion.utils.LogCore;

import butterknife.Bind;

/**
 * IM TAB
 */
public class ImFragment extends MopaFragment<ImPresenter> implements IImView {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    @Bind(R.id.listview_main) MopaListView mListViewMain;

    private String mParam1;
    private String mParam2;
    private ImListAdapter mAdapter;
    private TitleBuilder mTitleBuilder;

    public ImFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ImFragment.
     */
    public static ImFragment newInstance(String param1, String param2) {
        ImFragment fragment = new ImFragment();
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
        return R.layout.fragment_main_im;
    }

    @Override
    protected void onCreateViewInit(Bundle savedInstanceState) {
        mTitleBuilder = new TitleBuilder(getRootView());
        mTitleBuilder.leftGone().changeCenterTxt(R.string.im_fragment_title);
        mAdapter = new ImListAdapter(getContext());
        mListViewMain.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected ImPresenter getPresenter() {
        return new ImPresenter(this, mAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.loadFirstPage(null);
    }

    @Override public void onResume() {
        super.onResume();
        mEventBus.register(this);
    }

    @Override public void onPause() {
        mEventBus.unregister(this);
        super.onPause();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Subscribe(threadMode = ThreadMode.MAIN) public void OnPushHandleEvent(PushHandleEvent<Message> event) {
        LogCore.i("监听到事件, event=" + event);
        mPresenter.loadFirstPage(null);
    }

    @Override public void setTitle(int size) {
        mTitleBuilder.changeCenterTxt(getString(R.string.im_fragment_title_format, size));
    }
}
