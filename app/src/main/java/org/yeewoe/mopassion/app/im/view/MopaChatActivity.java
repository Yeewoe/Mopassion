package org.yeewoe.mopassion.app.im.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.yeewoe.commonutils.common.utils.CatchUtil;
import org.yeewoe.mopassion.R;
import org.yeewoe.mopassion.app.common.view.acitivty.MopaActivity;
import org.yeewoe.mopassion.app.common.view.builder.TitleBuilder;
import org.yeewoe.mopassion.app.common.view.widget.ChatEditText;
import org.yeewoe.mopassion.app.common.view.widget.KeyBroadListenerFrameLayout;
import org.yeewoe.mopassion.app.common.view.widget.MopaListView;
import org.yeewoe.mopassion.app.common.view.widget.PanelLayout;
import org.yeewoe.mopassion.app.im.model.MessageVo;
import org.yeewoe.mopassion.app.im.presenter.MopaChatPresenter;
import org.yeewoe.mopassion.app.im.view.adapter.MopaChatAdapter;
import org.yeewoe.mopassion.app.im.view.iview.IMopaChatView;
import org.yeewoe.mopassion.db.po.Message;
import org.yeewoe.mopassion.db.po.User;
import org.yeewoe.mopassion.emotion.EmotionUtil;
import org.yeewoe.mopassion.event.AsyncDataProgressChangeEvent;
import org.yeewoe.mopassion.event.AsyncDataStatusChangeEvent;
import org.yeewoe.mopassion.event.PushHandleEvent;
import org.yeewoe.mopassion.utils.LogCore;
import org.yeewoe.mopassion.utils.SoftWareUtil;

import java.io.File;

import butterknife.Bind;
import butterknife.OnClick;

public class MopaChatActivity extends MopaActivity<MopaChatPresenter> implements IMopaChatView,
        AttachmentFragment.OnChatAttachTypeChooseListener, BaseExpressionFragment.OnChatAppendExpressListener,
        KeyBroadListenerFrameLayout.OnKeyboardShowingListener, VoiceRecordFragment.OnVoiceRecordListener, TextWatcher {

    public static final String EXTRA_TO_UID = "to_uid";
    public static final String EXTRA_TO_NICK = "to_nick";

    @Bind(R.id.listview_main) MopaListView mListViewMain;
    @Bind(R.id.send_expression_attachment_container) PanelLayout mPanelLayout;
    @Bind(R.id.edttxt_content) ChatEditText mEditChat;
    private BottomChatBarViewHolder mChatBarViewHolder;
    private TitleBuilder titleBuilder;
    private Toast mEarToast;
    private TextView mToastText;

    private ChatBarEnum curChatBarEnum = ChatBarEnum.NONE;
    private boolean isKeyboardShow;
    private MopaChatAdapter mAdapterMain;
    private long toUid;
    private String toNick;


    @Override
    protected int getViewRootResId() {
        return R.layout.activity_im_chat;
    }

    @Override
    protected void bindIntent() {
        if (getIntent() != null) {
            toUid = getIntent().getLongExtra(EXTRA_TO_UID, 0L);
            toNick = getIntent().getStringExtra(EXTRA_TO_NICK);
        }
    }

    @Override
    protected void initTitle() {
        titleBuilder = new TitleBuilder(this);
        titleBuilder.changeCenterTxt(toNick);
        titleBuilder.getLeftImgBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void bindLister() {
        mEditChat.addTextChangedListener(this);
    }

    @Override
    protected MopaChatPresenter getPresenter() {
        mAdapterMain = new MopaChatAdapter(this);
        return new MopaChatPresenter(this, mAdapterMain, toUid);
    }

    @Override
    protected void bindData() {
        mChatBarViewHolder = new BottomChatBarViewHolder();
        mChatBarViewHolder.mAttachmentFragment = AttachmentFragment
                .newInstance(false, false, false);
        mChatBarViewHolder.mVoiceRecordFragment = new VoiceRecordFragment();
        mChatBarViewHolder.mExpressionFragment = new ChatExpressionFragment();
        mChatBarViewHolder.mMsgContentEditText = (ChatEditText) findViewById(R.id.edttxt_content);
        mChatBarViewHolder.mCancelText = (TextView) findViewById(R.id.txt_cancel);
        mChatBarViewHolder.mFrameEnabledContainer = (FrameLayout) findViewById(R.id.frame_list);
        mChatBarViewHolder.mBottomLeftShowMenuImg = (ImageView) findViewById(R.id.imgbtn_send_menu);
        mChatBarViewHolder.mBottomRightVoiceRecordImg = (ImageView) findViewById(R.id.imgbtn_send_voice);
        mChatBarViewHolder.mExpressImg = (ImageView) findViewById(R.id.imgbtn_send_express);
        mChatBarViewHolder.mBtnSendContent = (Button) findViewById(R.id.btn_send_content);

        mListViewMain.setAdapter(mAdapterMain);
        mAdapterMain.notifyDataSetChanged();

        mPresenter.loadFirstPage(toUid);
    }

    @Override
    protected void afterOnStart() {

    }

    @Override
    protected void afterOnResume() {
    }

    @Override
    protected void beforeOnPause() {

    }

    @Override
    protected void beforOnStop() {

    }

    @Override
    protected void beforeOnDestroy() {
    }

    @Override public void onWindowFocusChanged(boolean hasFocus) {
        if (mChatBarViewHolder != null && mChatBarViewHolder.mVoiceRecordFragment != null && mChatBarViewHolder.mVoiceRecordFragment.isAdded()) {
            mChatBarViewHolder.mVoiceRecordFragment.releaseVoiceRecord();
        }
    }

    @OnClick(R.id.imgbtn_send_menu) public void onPluseBtnClick(View view) {
        clickPluseMenuBtn(ChatBarEnum.ATTACHMENT);
    }

    @OnClick(R.id.imgbtn_send_express) public void onExpressBtnClick(View view) {
        clickPluseMenuBtn(ChatBarEnum.EXPRESSION);
    }

    @OnClick(R.id.imgbtn_send_voice) public void onVoiceBtnClick(View view) {
        clickPluseMenuBtn(ChatBarEnum.VOICERECORD);
    }

    @Override protected boolean isRegisterEvent() {
        return true;
    }

    @OnClick(R.id.btn_send_content) public void onSendBtnClick(View view) {
        mPresenter.send(mEditChat.getText().toString());
        mEditChat.getText().clear();
    }

    private void clickPluseMenuBtn(ChatBarEnum mBarEnum) {
        switch (mBarEnum) {
            case NONE:
                if (curChatBarEnum != ChatBarEnum.NONE) {
                    detachBottomFragment(curChatBarEnum, false);
                    curChatBarEnum = ChatBarEnum.NONE;
                }

                break;
            case EXPRESSION:
            case ATTACHMENT:
            case VOICERECORD:
                SoftWareUtil.hideSoftWare(this);
                processMenuFragment(mBarEnum);

                break;
        }
    }

    public void attachBottomFragment(ChatBarEnum enums, boolean isAnimated) {

        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction mTransaction = mFragmentManager.beginTransaction();
        if (isAnimated) {
            mTransaction.setCustomAnimations(R.anim.attach_bottom_in,
                    R.anim.attach_bottom_out);
        }
        switch (enums) {
            case EXPRESSION:
                mChatBarViewHolder.mExpressionFragment = new ChatExpressionFragment();
                mTransaction.add(R.id.send_expression_attachment_container,
                        mChatBarViewHolder.mExpressionFragment);
                break;
            case ATTACHMENT:
                mTransaction.add(R.id.send_expression_attachment_container,
                        mChatBarViewHolder.mAttachmentFragment);
                break;
            case VOICERECORD:
                mTransaction.add(R.id.send_expression_attachment_container,
                        mChatBarViewHolder.mVoiceRecordFragment);
            default:
                break;
        }
        mPanelLayout.setVisibility(View.VISIBLE);
        CatchUtil.transactionCommit(mTransaction);

    }

    public void detachBottomFragment(ChatBarEnum enums, boolean isAnimated) {

        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction mTransaction = mFragmentManager.beginTransaction();
        if (isAnimated) {
            mTransaction.setCustomAnimations(R.anim.attach_bottom_in,
                    R.anim.attach_bottom_out);
        }
        switch (enums) {
            case EXPRESSION:
                mTransaction.remove(mChatBarViewHolder.mExpressionFragment);
                break;
            case ATTACHMENT:
                mTransaction.remove(mChatBarViewHolder.mAttachmentFragment);
                break;
            case VOICERECORD:
                mTransaction.remove(mChatBarViewHolder.mVoiceRecordFragment);
                break;
            default:
                break;
        }

        mPanelLayout.setVisibility(View.GONE);
        CatchUtil.transactionCommit(mTransaction);

    }

    public void processMenuFragment(ChatBarEnum mBarEnum) {

        if (curChatBarEnum == ChatBarEnum.NONE) {

//            moveListToBottom(true);
            attachBottomFragment(mBarEnum, false);
            curChatBarEnum = mBarEnum;

        } else if (curChatBarEnum != mBarEnum) {
            detachBottomFragment(curChatBarEnum, true);
            attachBottomFragment(mBarEnum, true);
            curChatBarEnum = mBarEnum;
        } else {
            detachBottomFragment(mBarEnum, false);
            curChatBarEnum = ChatBarEnum.NONE;
        }

    }

    @Override
    public void onTypeChoose(AttachmentFragment.AttachType mAttachType) {
        switch (mAttachType) {
            case CAMERA:
                mPresenter.openCamera();
                break;
            case PICTURE:
                mPresenter.openGallery();
                break;
        }
    }

    @Override
    public void onChatAppendExpress(String expressRes) {
        if (expressRes.equals(BaseExpressionFragment.TAG_DEL)) {
            LogCore.wtf("delete!");
            delEditText();
        } else if (expressRes.equals(ChatExpressionFragment.TAG_SEND)) {
            if (TextUtils.isEmpty(mChatBarViewHolder.mMsgContentEditText.getText().toString())) {
                Toast.makeText(this, R.string.error_content_not_null,
                        Toast.LENGTH_SHORT).show();
                return;
            }
            mPresenter.send(mChatBarViewHolder.mMsgContentEditText.getText().toString());
            mEditChat.getText().clear();
        } else {
            Spannable mSpannable = EmotionUtil.parseToMsg(this, expressRes);
            mChatBarViewHolder.mMsgContentEditText.getEditableText().insert(mChatBarViewHolder.mMsgContentEditText.getSelectionStart(), mSpannable);
        }
    }

    @Override public void onChatAppendDynamic(String expressRes) {
        mPresenter.send(EmotionUtil.parseToDynamicMsg(expressRes));
        mEditChat.getText().clear();
    }

    public void delEditText() {

        Editable mEditable = mChatBarViewHolder.mMsgContentEditText.getText();
        if (mEditable.length() <= 0) {
            return;
        }
        ImageSpan[] spans = mEditable.getSpans(0, mChatBarViewHolder.mMsgContentEditText.length(),
                ImageSpan.class);
        if (spans.length > 0) {
            int index = mEditable.getSpanEnd(spans[spans.length - 1]);
            if (index == mEditable.length()) {
                int start = mEditable.getSpanStart(spans[spans.length - 1]);
                mEditable.removeSpan(spans[spans.length - 1]);
                mEditable.delete(start, index);
            } else {
                mEditable.delete(mEditable.length() - 1, mEditable.length());
            }
        } else {
            mEditable.delete(mEditable.length() - 1, mEditable.length());
        }

    }

    @Override
    public void onChatDeleteExpress() {

    }



    @Override
    public void onKeyboardShowing(boolean isShowing) {
        isKeyboardShow = isShowing;
        if (isShowing) {
            long delay = curChatBarEnum == ChatBarEnum.NONE ? -1 : 500;
            clickPluseMenuBtn(ChatBarEnum.NONE);

            if (delay == -1) {

//                mChatBarViewHolder.mListView.post(new Runnable() {
//
//                    @Override
//                    public void run() {
//
//                        mChatBarViewHolder.mListView.setSelection(mAdapter.getCount()-1);
//
//                    }
//
//                });

            } else {

//                mChatBarViewHolder.mListView.postDelayed(new Runnable() {
//
//                    @Override
//                    public void run() {
//
//                        mChatBarViewHolder.mListView.setSelection(mAdapter.getCount()-1);
//
//                    }
//
//                },delay);

            }

        }
    }

    @Override public Activity getActivity() {
        return this;
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.handleActivityResult(requestCode, resultCode, data);
    }

    @Override public void scrollToLast() {
        mListViewMain.setSelection(mAdapterMain.getCount() - 1);
    }

    @Override public void setEarplayVisibilityByEarModel() {
        boolean isEarModel = mPresenter.isEarModel();
        AudioManager manager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        if (!isEarModel) {
            manager.setMode(AudioManager.MODE_NORMAL);
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
        } else {
            setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);
            manager.setMode(AudioManager.MODE_IN_CALL);
        }
        if (isEarModel) {
            showEarModelToast(isEarModel);
        }
    }

    private void showEarModelToast(boolean isEarModel) {
        Drawable earDrawable = getResources().getDrawable(isEarModel ? R.drawable.ear_model : R.drawable.im_model_speaker);
        mEarToast = new Toast(this);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int titleHeight = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 49, metrics);
        mEarToast.setGravity(Gravity.TOP, 0, titleHeight);
        mToastText = new TextView(this);
        mToastText.setText(isEarModel ? R.string.ear_model_alert
                : R.string.no_ear_model_alert);
        mToastText.setWidth(metrics.widthPixels * 2);
        mToastText.setHeight(titleHeight);
        mToastText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17);
        mToastText.setTextColor(Color.parseColor("#ffffff"));
        mToastText.setBackgroundColor(Color.parseColor("#666666"));
        mToastText.setPadding(20, 0, 0, 0);
        mToastText.setCompoundDrawablePadding(10);
        mToastText.setGravity(Gravity.CENTER_VERTICAL);
        mToastText.setCompoundDrawablesWithIntrinsicBounds(earDrawable, null,
                null, null);
        mEarToast.setView(mToastText);
        mEarToast.setDuration(Toast.LENGTH_SHORT);
        mEarToast.show();
    }

    @Override public void onVoiceSend(String voicefile, long time) {
        File mVoiceFile = new File(voicefile);
        if (!mVoiceFile.exists()) {
            showToast(R.string.error_voice_record);
        }
        showToast("voiceFile=" + voicefile + ", time=" + time);
        mPresenter.sendVoice(voicefile, time);
//        mChatController.getMessageSender().sendVoiceMessage(mVoiceFile, (int) time / 1000);
    }

    @Override public void onFreshToCancel(boolean isShouldCancel) {
        mChatBarViewHolder.mCancelText.setVisibility(isShouldCancel ? View.VISIBLE : View.GONE);
    }

    @Override public void onVoiceRecordStart() {
        setImChatActivityComponentEnable(false);
    }

    @Override public void onVoiceRecordEnd() {
        setImChatActivityComponentEnable(true);
    }

    public void setImChatActivityComponentEnable(boolean enable) {

        mChatBarViewHolder.mFrameEnabledContainer.setClickable(!enable);
        mChatBarViewHolder.mBottomLeftShowMenuImg.setClickable(enable);
        mChatBarViewHolder.mBottomRightVoiceRecordImg.setClickable(enable);
        mChatBarViewHolder.mExpressImg.setClickable(enable);
        mChatBarViewHolder.mMsgContentEditText.setEnabled(enable);
        titleBuilder.setEnabled(enable);

    }

    @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override public void afterTextChanged(Editable s) {
        if (s.toString().replaceAll(" ", "").replaceAll("\n", "").length() <= 0) {

            mChatBarViewHolder.mBtnSendContent.setVisibility(View.GONE);
            mChatBarViewHolder.mBottomRightVoiceRecordImg
                    .setVisibility(View.VISIBLE);

        } else {

            mChatBarViewHolder.mBtnSendContent.setVisibility(View.VISIBLE);
            mChatBarViewHolder.mBottomRightVoiceRecordImg
                    .setVisibility(View.GONE);

        }
    }

    private enum ChatBarEnum {
        EXPRESSION, ATTACHMENT, VOICERECORD, NONE
    }

    /**
     * 下边栏包括输入框以及发送消息等下关控件
     */
    private class BottomChatBarViewHolder {
        ChatExpressionFragment mExpressionFragment;
        AttachmentFragment mAttachmentFragment;
        VoiceRecordFragment mVoiceRecordFragment;
        ChatEditText mMsgContentEditText;// edttxt_content
        TextView mCancelText;//txt_cancel
        FrameLayout mFrameEnabledContainer;// frame_list
        ImageView mBottomLeftShowMenuImg;// imgbtn_send_menu
        ImageView mBottomRightVoiceRecordImg;// imgbtn_send_voice
        ImageView mExpressImg;// imgbtn_send_express
        Button mBtnSendContent;// btn_send_content
    }


    // -------------------------------------------  eventbus --------------------------------------
    @Subscribe(threadMode = ThreadMode.MAIN) public void OnAsyncDataStatusChangeEvent(AsyncDataStatusChangeEvent<MessageVo> event) {
        LogCore.i("监听到事件, event=" + event);
        mPresenter.update(event.data);
    }


    @Subscribe(threadMode = ThreadMode.MAIN) public void OnAsyncDataProgressChangeEvent(AsyncDataProgressChangeEvent event) {
        LogCore.i("监听到事件, event=" + event);
        mPresenter.updateProgress(event.sid, event.percent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN) public void OnPushHandleEvent(PushHandleEvent<Message> event) {
        mPresenter.onPushHandleEvent(event);
    }

    @Override public boolean handleBackPressed() {
        if (curChatBarEnum != ChatBarEnum.NONE) {
            clickPluseMenuBtn(curChatBarEnum);
        } else {
            finish();
        }
        return true;
    }
}