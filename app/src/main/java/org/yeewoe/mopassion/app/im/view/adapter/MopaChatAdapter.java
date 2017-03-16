package org.yeewoe.mopassion.app.im.view.adapter;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import org.yeewoe.commonutils.common.utils.SdCardUtil;
import org.yeewoe.mopassion.R;
import org.yeewoe.mopassion.app.common.adapter.MopaAdapter;
import org.yeewoe.mopassion.app.common.view.widget.ImChatTextView;
import org.yeewoe.mopassion.app.im.builder.MessageType;
import org.yeewoe.mopassion.app.im.model.MessageVo;
import org.yeewoe.mopassion.app.im.view.iview.IMopaChatView;
import org.yeewoe.mopassion.db.dao.DaoManager;
import org.yeewoe.mopassion.db.po.SendStatus;
import org.yeewoe.mopassion.emotion.EmotionUtil;
import org.yeewoe.mopassion.photo.fresco_lib.HeadThumbSimpleDraweeView;
import org.yeewoe.mopassion.photo.fresco_lib.PhotoThumbSimpleDraweeView;
import org.yeewoe.mopassion.utils.DialogFuncUtil;
import org.yeewoe.mopassion.utils.IntentManager;
import org.yeewoe.mopassion.utils.LinkUtil;
import org.yeewoe.mopassion.utils.LogCore;
import org.yeewoe.mopassion.utils.MopaFileUtil;
import org.yeewoe.mopassion.utils.TimeUtil;

import java.io.File;
import java.sql.SQLException;

import butterknife.Bind;

/**
 * Created by wyw on 2016/3/29.
 */
public class MopaChatAdapter extends MopaAdapter<MessageVo> implements AudioManager.OnAudioFocusChangeListener {

    private static final int VIEW_TYPE_COUNT = 4;

    private static final int VIEW_TYPE_SELF = 0;
    private static final int VIEW_TYPE_OTHER = 1;
    private static final int VIEW_TYPE_VOICE_SELF = 2;
    private static final int VIEW_TYPE_VOICE_OTHER = 3;
    private final IMopaChatView view;
    private CharSequence mCurrentVoicePath;
    private PhotoThumbSimpleDraweeView mCurrentVoiceImage;
    private MediaPlayer mPlayer;
    private AudioManager mAudioManager;
    private MediaPlayer mSpeakNowPlayer;

    public CharSequence getmCurrentVoicePath() {
        return mCurrentVoicePath;
    }

    public void setmCurrentVoicePath(CharSequence mCurrentVoicePath) {
        this.mCurrentVoicePath = mCurrentVoicePath;
    }

    public MopaChatAdapter(IMopaChatView view) {
        super(view.getActivity());
        this.view = view;
        this.mAudioManager = (AudioManager) context
                .getSystemService(Context.AUDIO_SERVICE);
        this.mSpeakNowPlayer = MediaPlayer.create(context,
                R.raw.speak_now);
    }

    @Override public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }

    @Override public int getItemViewType(int position) {
        MessageVo data = getItem(position);
        if (data.getType() == MessageType.VOICE) {
            if (data.isSelf()) {
                return VIEW_TYPE_VOICE_SELF;
            } else {
                return VIEW_TYPE_VOICE_OTHER;
            }
        } else {
            if (data.isSelf()) {
                return VIEW_TYPE_SELF;
            } else {
                return VIEW_TYPE_OTHER;
            }
        }
    }

    @Override protected View getView(ViewGroup parent, int position) {
        switch (getItemViewType(position)) {
            case VIEW_TYPE_SELF:
                return layoutInflater.inflate(R.layout.im_chat_content_self, parent, false);
            case VIEW_TYPE_OTHER:
                return layoutInflater.inflate(R.layout.im_user_chat_content_others, parent, false);
            case VIEW_TYPE_VOICE_SELF:
                return layoutInflater.inflate(R.layout.im_chat_voice_self, parent, false);
            case VIEW_TYPE_VOICE_OTHER:
                return layoutInflater.inflate(R.layout.im_user_chat_voice_others, parent, false);
        }
        return layoutInflater.inflate(R.layout.im_chat_content_self, parent, false);
    }

    @Override public int getItemLayoutId() {
        return 0;
    }

    @Override public MopaViewHolder getViewHolder(View view) {
        return new MopaChatViewHolder(view);
    }

    @Override protected void bindViewHolder(MopaViewHolder mopaViewHolder, @NonNull final MessageVo data) {
        final MopaChatViewHolder viewHolder = (MopaChatViewHolder) mopaViewHolder;


        if (viewHolder.mProgressSending != null) {
            if (data.getSendStatus() == SendStatus.SENDING) {
                viewHolder.mProgressSending.setVisibility(View.VISIBLE);
            } else if (data.getSendStatus() == SendStatus.FAILURE) {
                viewHolder.mProgressSending.setVisibility(View.GONE);
            } else {
                viewHolder.mProgressSending.setVisibility(View.GONE);
            }
        }
        if (viewHolder.mImgViewFail != null) {
            if (data.getSendStatus() == SendStatus.SENDING) {
                viewHolder.mImgViewFail.setVisibility(View.GONE);
            } else if (data.getSendStatus() == SendStatus.FAILURE) {
                viewHolder.mImgViewFail.setVisibility(View.VISIBLE);
            } else {
                viewHolder.mImgViewFail.setVisibility(View.GONE);
            }
        }

        // 设置内容
        switch (data.getType()) {
            case TXT:
                if (EmotionUtil.isDynamicEmotion(data.getMsg())) {
                    /** 动态表情需要特殊处理 **/
                    viewHolder.mTxtContent.setVisibility(View.GONE);
                    viewHolder.mImgViContent.setVisibility(View.VISIBLE);
                    hideBackground(viewHolder);
                    viewHolder.mLLContainer.setOnLongClickListener(null);
                    viewHolder.mImgViContent.setDraweeViewResId(EmotionUtil.parseToDynamicResId(data.getMsg()));
                } else {
                    viewHolder.mTxtContent.setVisibility(View.VISIBLE);
                    viewHolder.mImgViContent.setVisibility(View.GONE);
                    viewHolder.mImgViContent.setOnClickListener(null);
                    viewHolder.mTxtContent.setImText(data.getMsg());
                    showBackground(data, viewHolder);
                    viewHolder.mLLContainer.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override public boolean onLongClick(View v) {
                            DialogFuncUtil.openCopyDialog(context, EmotionUtil.parseToText(viewHolder.mTxtContent.getText().toString(), context));
                            return false;
                        }
                    });
                }
                break;
            case PICTURE:
                viewHolder.mTxtContent.setVisibility(View.GONE);
                viewHolder.mImgViContent.setVisibility(View.VISIBLE);

                viewHolder.mImgViContent.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        IntentManager.Photo.intentToPhotoView(context, data.getImages().get(0));
                    }
                });
                viewHolder.mImgViContent.setImageMedia(data.getImages().get(0));
                showBackground(data, viewHolder);
                viewHolder.mLLContainer.setOnLongClickListener(null);
                viewHolder.mLLContainer.setOnClickListener(null);
                break;
            case VOICE:
                viewHolder.mTxtContent.setText(TimeUtil.parseVoiceRecord(data.getVoice().time));
                viewHolder.mLLContainer.setOnLongClickListener(null);
                viewHolder.mLLContainer.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        reSaveVoiceBytesAndUpdateMessage(data);
                        if (TextUtils.isEmpty(data.getVoice().buffer)) {
                            view.showToast(R.string.error_voice_invalid);
                            return;
                        }
                        boolean flag = handlePlayVoice(data.getVoice().buffer);
                        if (flag) {
                            if (mCurrentVoiceImage != null) {
                                if (mCurrentVoiceImage != viewHolder.mImgViContent) {
                                    AnimationDrawable mAnimationDrawable1 = (AnimationDrawable) mCurrentVoiceImage.getBackground();
                                    if (mAnimationDrawable1.isRunning()) {
                                        mAnimationDrawable1.stop();
                                        mAnimationDrawable1.selectDrawable(0);
                                    }
                                    mCurrentVoiceImage = viewHolder.mImgViContent;
                                    AnimationDrawable mAnimationDrawable = (AnimationDrawable) mCurrentVoiceImage.getBackground();
                                    mAnimationDrawable.start();
                                } else {
                                    AnimationDrawable mAnimationDrawable1 = (AnimationDrawable) mCurrentVoiceImage.getBackground();
                                    if (mAnimationDrawable1.isRunning()) {
                                        mAnimationDrawable1.stop();
                                        mAnimationDrawable1.selectDrawable(0);
                                    }
                                    mCurrentVoiceImage = null;
                                    if (!TextUtils.isEmpty(mCurrentVoicePath) &&
                                            !TextUtils.isEmpty(data.getVoice().buffer) &&
                                            mCurrentVoicePath.equals(data.getVoice().buffer)) {
                                        mCurrentVoiceImage = viewHolder.mImgViContent;
                                        AnimationDrawable mAnimationDrawable = (AnimationDrawable) mCurrentVoiceImage.getBackground();
                                        mAnimationDrawable.start();
                                    }

                                }
                            } else {
                                mCurrentVoiceImage = viewHolder.mImgViContent;
                                AnimationDrawable mAnimationDrawable = (AnimationDrawable) mCurrentVoiceImage.getBackground();
                                mAnimationDrawable.start();
                            }
                        } else {

                            if (mCurrentVoiceImage != null) {
                                AnimationDrawable mAnimationDrawable1 = (AnimationDrawable) mCurrentVoiceImage.getBackground();
                                if (mAnimationDrawable1.isRunning()) {
                                    mAnimationDrawable1.stop();
                                    mAnimationDrawable1.selectDrawable(0);
                                }
                            }

                        }
                    }

                    private void reSaveVoiceBytesAndUpdateMessage(MessageVo vo) {
                        if (vo.getVoice().base64Bytes != null && TextUtils.isEmpty(vo.getVoice().buffer)) {
                            vo.getVoice().buffer = MopaFileUtil.createVoiceCacheFile(vo.getVoice().base64Bytes);
                            try {
                                DaoManager.getMessageDao().updateVoiceBuffer(vo.getId(), vo.getVoice().buffer);
                            } catch (SQLException e) {
                                LogCore.wtf(Log.getStackTraceString(e));
                            }
                            notifyDataSetChanged();
                        }
                    }
                });
                if (!TextUtils.isEmpty(mCurrentVoicePath)) {
                    if (data.getVoice().buffer != null && data.getVoice().buffer.equals(mCurrentVoicePath)) {
                        AnimationDrawable mAnimDrawable = (AnimationDrawable) viewHolder.mImgViContent.getBackground();
                        if (!mAnimDrawable.isRunning()) {
                            mAnimDrawable.start();
                            mCurrentVoiceImage = viewHolder.mImgViContent;
                        }
                    } else {
                        AnimationDrawable mAnimDrawable = (AnimationDrawable) viewHolder.mImgViContent.getBackground();
                        if (mAnimDrawable.isRunning()) {
                            mAnimDrawable.stop();
                            mAnimDrawable.selectDrawable(0);
                        }
                    }
                } else {
                    AnimationDrawable mAnimDrawable = (AnimationDrawable) viewHolder.mImgViContent.getBackground();
                    if (mAnimDrawable.isRunning()) {
                        mAnimDrawable.stop();
                        mAnimDrawable.selectDrawable(0);
                    }
                }

                break;
        }

        if (viewHolder.mCbItemSelect != null) {
            viewHolder.mCbItemSelect.setVisibility(View.GONE);
        }

        viewHolder.mImgViewHead.setImageHead(data.getUser());
        viewHolder.mRLRoot.setOnClickListener(null);
        viewHolder.mRLRoot.setOnLongClickListener(null);
    }

    // 显示背景图
    private void showBackground(@NonNull MessageVo data, MopaChatViewHolder viewHolder) {
        if (data.isSelf()) {
            viewHolder.mLLContainer.setBackgroundResource(R.drawable.im_chat_self_pop);
        } else {
            viewHolder.mLLContainer.setBackgroundResource(R.drawable.im_chat_other_chat_pop);
        }
    }

    // 隐藏背景图
    private void hideBackground(MopaChatViewHolder viewHolder) {
        viewHolder.mLLContainer.setBackgroundResource(R.color.transparent); // 隐藏背景
    }

    public void update(long sid, double percent) {
        // TODO 图片进度
    }

    public boolean playVoice(final String filePath) {
        if (checkVoiceIfValid(filePath)) {
            LogCore.wtf("播放语音:" + filePath);
            mPlayer = MediaPlayer.create(view.getActivity(), Uri.fromFile(new File(filePath)));
            if (mPlayer == null) {
                LogCore.i("语音创建失败!");
                return false;
            }
            view.setEarplayVisibilityByEarModel();
            mAudioManager.requestAudioFocus(MopaChatAdapter.this, AudioManager.STREAM_MUSIC,
                    AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    mAudioManager.abandonAudioFocus(MopaChatAdapter.this);
                    mPlayer.release();
                    mPlayer = null;

                    if (mCurrentVoiceImage != null) {
                        AnimationDrawable mAnimationDrawable = (AnimationDrawable) mCurrentVoiceImage.getBackground();
                        mAnimationDrawable.stop();
                        mAnimationDrawable.selectDrawable(0);
                    }
                    LogCore.i("语音播放完成.");
                    mCurrentVoicePath = null;
                    mCurrentVoiceImage = null;
                }

            });
            mCurrentVoicePath = filePath;
            mSpeakNowPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    if (mPlayer != null) {
                        mPlayer.start();
                        LogCore.i("mSpeakNowPlayer播放完成.");
                    }
                }
            });
        }
        return true;
    }

    public boolean handlePlayVoice(String voicePath) {

        if (TextUtils.isEmpty(mCurrentVoicePath)) {
            boolean flag = playVoice(voicePath);
            if (!flag) {
                return false;
            }
            mSpeakNowPlayer.start();

        } else {
            if (!voicePath.equals(mCurrentVoicePath)) {

                if (mSpeakNowPlayer != null && mSpeakNowPlayer.isPlaying()) {
                    boolean flag = playVoice(voicePath);
                    if (!flag) return false;
                    if (mSpeakNowPlayer != null) {
                        mSpeakNowPlayer.seekTo(0);
                    }
                } else if (mPlayer != null && mPlayer.isPlaying()) {
                    mPlayer.stop();
                    mPlayer.release();
                    mPlayer = null;
                    boolean flag = playVoice(voicePath);
                    if (!flag) return false;
                    if (mSpeakNowPlayer != null) {
                        mSpeakNowPlayer.start();
                    }
                } else if (mPlayer == null) {
                    return false;
                }

            } else {

                mAudioManager.setMode(AudioManager.MODE_NORMAL);
                view.getActivity().setVolumeControlStream(AudioManager.STREAM_MUSIC);
                mAudioManager.abandonAudioFocus(MopaChatAdapter.this);
                if (mSpeakNowPlayer != null && mSpeakNowPlayer.isPlaying()) {
                    mSpeakNowPlayer.setOnCompletionListener(null);
                    mPlayer.release();
                    mPlayer = null;
                    mCurrentVoicePath = null;
                } else if (mPlayer != null && mPlayer.isPlaying()) {
                    mPlayer.stop();
                    mPlayer.release();
                    mPlayer = null;
                    mCurrentVoicePath = null;
                }

            }
        }

        return true;

    }

    public boolean checkVoiceIfValid(String filePath) {
        if (SdCardUtil.getSDCardInfo().isExist && !TextUtils.isEmpty(filePath) && new File(filePath).exists()) {
            return true;
        }
        return false;
    }

    @Override public void onAudioFocusChange(int focusChange) {

    }


    class MopaChatViewHolder extends MopaViewHolder {
        @Bind(R.id.rl_root) RelativeLayout mRLRoot;//
        @Bind(R.id.linear_container) LinearLayout mLLContainer;//
        @Bind(R.id.imgvi_head) HeadThumbSimpleDraweeView mImgViewHead;
        @Bind(R.id.txt_add_content) ImChatTextView mTxtContent;//
        @Bind(R.id.imgvi_content) PhotoThumbSimpleDraweeView mImgViContent;//
        @Nullable @Bind(R.id.imgvi_send_fail) ImageView mImgViewFail;//
        @Nullable @Bind(R.id.probar_ifsend) ProgressBar mProgressSending;//
        @Nullable @Bind(R.id.im_item_checkbox) CheckBox mCbItemSelect;//

        public MopaChatViewHolder(View convertView) {
            super(convertView);
        }
    }
}
