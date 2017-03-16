package org.yeewoe.mopassion.app.im.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.FrameLayout;

import org.yeewoe.mopassion.R;
import org.yeewoe.mopassion.app.common.presenter.MopaPresenter;
import org.yeewoe.mopassion.app.common.view.fragment.MopaFragment;
import org.yeewoe.mopassion.listener.OnFragmentCreateListener;

import butterknife.Bind;

/**
 * 包括最近与所有表情显示界面
 *
 * @author wyw
 */
public class ChatExpressionFragment extends MopaFragment implements OnCheckedChangeListener, OnFragmentCreateListener {

    public static final String TAG_SEND = "send";

    public static final String ADJUST_TO_KEYBORAD = "ADJUST_TO_KEYBORAD";

    private View mRootView;

    @Bind(android.R.id.tabhost) FragmentTabHost mFragmentTabHost;
    @Bind(R.id.radio_container) View mViewRadioContainer;

    @Bind(R.id.radio_expression) AppCompatRadioButton mExpressButton;

    @Bind(R.id.frame_expression) FrameLayout mExpressLayout;

    @Bind(R.id.radio_momo_expression) AppCompatRadioButton mMomoExpressButton;

    @Bind(R.id.frame_momo_expression) FrameLayout mMomoExpressLayout;

    @Bind(R.id.radio_nod_expression) AppCompatRadioButton mNodExpressButton;

    @Bind(R.id.frame_nod_expression) FrameLayout mNodExpressLayout;

    @Bind(R.id.btn_send) Button mSendBtn;//btn_send

    private BaseExpressionFragment.OnChatAppendExpressListener mAppendExpressListener;

    private boolean isShowSend = true;

    public static ChatExpressionFragment getInstance() {
        ChatExpressionFragment f = new ChatExpressionFragment();
        return f;
    }

    @Override
    protected int getViewRootResId() {
        return R.layout.view_expression_bar;
    }

    @Override
    protected void onCreateViewInit(Bundle savedInstanceState) {
        mViewRadioContainer.getLayoutParams().height = getResources()
                .getDimensionPixelOffset(R.dimen.im_chat_express_tab_height);
        mFragmentTabHost.setup(this.getActivity(), getChildFragmentManager(),
                R.id.realtabcontent);
        mFragmentTabHost.addTab(mFragmentTabHost.newTabSpec("emoji")
                .setIndicator("emoji"), EmojiExpressionFragment.class, null);
        mFragmentTabHost.addTab(mFragmentTabHost.newTabSpec("zem")
                .setIndicator("zem"), ZemExpressionFragment.class, null);
        mFragmentTabHost.addTab(mFragmentTabHost.newTabSpec("nod")
                .setIndicator("nod"), NodExpressionFragment.class, null);
        mFragmentTabHost.getTabWidget().setVisibility(View.GONE);
        mFragmentTabHost.setCurrentTab(0);
        mExpressButton.setChecked(true);
        if (!isShowSend) {
            mSendBtn.setVisibility(View.INVISIBLE);
        }
        mExpressButton.setButtonDrawable(null);
        mMomoExpressButton.setButtonDrawable(null);
        mNodExpressButton.setButtonDrawable(null);

        mExpressButton.setOnCheckedChangeListener(this);
        mMomoExpressButton.setOnCheckedChangeListener(this);
        mNodExpressButton.setOnCheckedChangeListener(this);
        mSendBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                mAppendExpressListener.onChatAppendExpress(TAG_SEND);
            }
        });
    }

    @Override
    protected MopaPresenter getPresenter() {
        return null;
    }

//    /** 对于旧的布局，需要固定键盘高度*/
//    public void computeTabhostHeight() {
//        boolean isAdiustToKeyBorad;
//        Bundle args = getArguments();
//        if(null == args)
//            isAdiustToKeyBorad = false;
//        else
//            isAdiustToKeyBorad = args.getBoolean(ADJUST_TO_KEYBORAD,false);
//
//        if( !isAdiustToKeyBorad ){//判断是否要动态调整表情键盘的高度
//
//            mRootView.getLayoutParams().height =  AttachHeightCompute.getAttachHeight(getResources())
//                    + getResources().getDimensionPixelOffset(
//                    dimen.im_chat_attach_indicator_height);
//            mFragmentTabHost.getLayoutParams().height = AttachHeightCompute.getAttachHeight(getResources())
//                    + getResources().getDimensionPixelOffset(
//                    dimen.im_chat_attach_indicator_height)
//                    - getResources().getDimensionPixelOffset(
//                    dimen.im_chat_express_tab_height);
//        }
//    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getView();
    }

    public void setSendBtnVisibility(boolean isShow) {
        isShowSend = isShow;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mAppendExpressListener = (BaseExpressionFragment.OnChatAppendExpressListener) activity;
    }

    @Override
    public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
        switch (arg0.getId()) {
            case R.id.radio_expression:
                if (arg1) {
                    // TODO 需要重构表情容器
                    mFragmentTabHost.setCurrentTab(0);
                    mExpressButton.setChecked(true);
                    mMomoExpressButton.setChecked(false);
                    mNodExpressButton.setChecked(false);
                    mExpressLayout.setBackgroundResource(R.drawable.express_tab_bg_checked);
                    mMomoExpressLayout.setBackgroundResource(R.drawable.express_tab_bg_normal);
                    mNodExpressLayout.setBackgroundResource(R.drawable.express_tab_bg_normal);
                }
                break;
            case R.id.radio_momo_expression:
                if (arg1) {
                    // TODO 需要重构表情容器
                    mFragmentTabHost.setCurrentTab(1);
                    mExpressButton.setChecked(false);
                    mMomoExpressButton.setChecked(true);
                    mNodExpressButton.setChecked(false);
                    mExpressLayout.setBackgroundResource(R.drawable.express_tab_bg_normal);
                    mMomoExpressLayout.setBackgroundResource(R.drawable.express_tab_bg_checked);
                    mNodExpressLayout.setBackgroundResource(R.drawable.express_tab_bg_normal);
                }
                break;
            case R.id.radio_nod_expression:
                // TODO 需要重构表情容器
                if (arg1) {
                    mFragmentTabHost.setCurrentTab(2);
                    mExpressButton.setChecked(false);
                    mMomoExpressButton.setChecked(false);
                    mNodExpressButton.setChecked(true);
                    mExpressLayout.setBackgroundResource(R.drawable.express_tab_bg_normal);
                    mMomoExpressLayout.setBackgroundResource(R.drawable.express_tab_bg_normal);
                    mNodExpressLayout.setBackgroundResource(R.drawable.express_tab_bg_checked);
                }
                break;
        }
    }

    @Override
    public void onFragmentCreate(Fragment fragment) {
        if (fragment == null) return;
    }
}