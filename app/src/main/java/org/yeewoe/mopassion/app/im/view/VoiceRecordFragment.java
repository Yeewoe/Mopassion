package org.yeewoe.mopassion.app.im.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import org.yeewoe.commonutils.common.utils.SdCardUtil;
import org.yeewoe.mopassion.R;
import org.yeewoe.mopassion.app.common.view.widget.CircleImageView;
import org.yeewoe.mopassion.utils.AudioRecoderHelper;
import org.yeewoe.mopassion.utils.TimeUtil;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 录音功能界面实现
 *
 * @author crjoe
 */
public class VoiceRecordFragment extends Fragment implements OnTouchListener, OnAudioFocusChangeListener {

    public static final String TAG = "VoiceRecordFragment";

    private int MAX_RECORD_SECOND = 60;

    private TextView mTimeTxt;
    private ImageView mImageVoice;
    private PopupWindow mPopupWindow;
    private CircleImageView mCircleImageView;

    private int mTxtWidth;
    private int mTxtHeight;

    private OnVoiceRecordListener mListener;

    /**
     * 时间计算和处理类
     */
    private Runnable mRunnable = new TimeRunnable();
    private Handler mTimeHandler = new Handler();

    /**
     * 开始时间,结束时间，当前秒数
     */
    private long mVoiceStartTime;
    private long mVoiceEndTime;
    private int mSecond = 0;

    private boolean isSDCardUnmounted = false;
    private boolean isHandlerPost = false;
    private boolean isTouchUp = false;

    private boolean isVoiceComplete = false;

    private AudioRecoderHelper mAudioRecoderHelper;
    private Rect mVoiceRecordRect = new Rect();

    private AudioManager mAudioManager;

    private boolean enabled = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        initPopWindow();
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
    }

    public void requestAudioFocus() {
        mAudioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
    }

    public void setMaxRecordSecond(int second) {
        MAX_RECORD_SECOND = second;
    }

    public void abandonAudioFocus() {
        if (mAudioManager != null) {
            mAudioManager.abandonAudioFocus(this);
        }
    }

    public void init() {
        mAudioRecoderHelper = new AudioRecoderHelper(getActivity());
        mAudioRecoderHelper
                .setOnVolumeChangeListener(new VolumeChangeCallback());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_voice_fragment, null);
        int height = AttachHeightCompute.getAttachHeight(getResources()) + getResources().getDimensionPixelOffset(R.dimen.im_chat_attach_indicator_height);
        view.findViewById(R.id.frame_voice_container).getLayoutParams().height = height;
        mImageVoice = (ImageView) view.findViewById(R.id.img_voice_record);
        mCircleImageView = (CircleImageView) view.findViewById(R.id.img_circle);
        mCircleImageView.setArcPadding(getResources().getDimensionPixelOffset(
                R.dimen.im_chat_circle_padding));
        mImageVoice.setOnTouchListener(this);
        mImageVoice.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.i(TAG, "on focus change: v=" + v + "  hasFocus:" + hasFocus);
            }
        });
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (OnVoiceRecordListener) activity;
    }

    public void initPopWindow() {

        mTimeTxt = new TextView(this.getActivity());
        mTimeTxt.setBackgroundResource(R.drawable.record_time);
        mTimeTxt.setGravity(Gravity.CENTER);
        mTimeTxt.setTextColor(getResources().getColor(R.color.white));
        mTimeTxt.setText("00:00");
        mTimeTxt.measure(
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        mTxtWidth = mTimeTxt.getMeasuredWidth();
        mTxtHeight = mTimeTxt.getMeasuredHeight();
        mTimeTxt.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelOffset(R.dimen.voice_popwindow_font_size));
        mPopupWindow = new PopupWindow(mTimeTxt);
        mPopupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setOutsideTouchable(true);

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "on voice record pause!");
    }

    public void setVoiceRecordEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean onTouch(View arg0, MotionEvent event) {
        if (!enabled) return true;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "touch down");
                if (!SdCardUtil.getSDCardInfo().isExist) {
                    isSDCardUnmounted = true;
                    Toast.makeText(getActivity(), R.string.error_sdcard_exist, Toast.LENGTH_SHORT)
                            .show();
                    return true;
                } else {
                    isSDCardUnmounted = false;
                }
                /**
                 * 播放音效后才开始录音
                 */
                MediaPlayer mStartPlayer = MediaPlayer.create(getActivity(),
                        R.raw.speak_now);
                isTouchUp = false;
                mImageVoice.getLocalVisibleRect(mVoiceRecordRect);
                mStartPlayer.setOnCompletionListener(new OnCompletionListener() {

                    @Override
                    public void onCompletion(MediaPlayer mp) {

                        if (isTouchUp) {
                            Log.i(TAG, "has touch up before record start!");
                            return;
                        }
                        boolean result = mAudioRecoderHelper.startVoiceRecord();
                        if (result) {
                            requestAudioFocus();
                            mVoiceStartTime = System.currentTimeMillis();
                            mListener.onVoiceRecordStart();
                            isVoiceComplete = false;
                            onVoiceRecordStart();
                            mTimeHandler.post(mRunnable);
                            isHandlerPost = true;
                        } else {
                            mVoiceStartTime = -1;
                            if (getActivity() != null) {
                                Toast.makeText(getActivity(), "录音初始化失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                mStartPlayer.start();
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "touch move!");
                if (isSDCardUnmounted || isVoiceComplete) {
                    return true;
                }

                if (!mVoiceRecordRect.contains((int) event.getX(),
                        (int) event.getY())) {
                    mListener.onFreshToCancel(true);
                } else {
                    mListener.onFreshToCancel(false);
                }

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                Log.i(TAG, "touch up!");
                isTouchUp = true;
                mListener.onVoiceRecordEnd();
                mListener.onFreshToCancel(false);
                clickFrequencyControl(1000);
                if (isSDCardUnmounted || isVoiceComplete) {
                    return true;
                }
                onVoiceRecordStop();
                if (isHandlerPost) {
                    mTimeHandler.removeCallbacks(mRunnable);
                    isHandlerPost = false;
                } else {
                    if (mVoiceStartTime != -1) {
                        showTimeToast();
                    }
                    return true;
                }

                // 如果 touchup为 true 时则表示录音还没开始就已经结束了。
                boolean result = mAudioRecoderHelper.stopVoiceRecord();
                MediaPlayer mPlayer = MediaPlayer.create(getActivity(),
                        R.raw.cancel_speak);
                abandonAudioFocus();
                mPlayer.start();

                if (mVoiceEndTime - mVoiceStartTime < 1000) {
                    Log.i(TAG, "record time too low!");
                    mSecond = 0;
                    if (mVoiceRecordRect.contains((int) event.getX(),
                            (int) event.getY()) && mVoiceStartTime != -1) {
                        showTimeToast();
                    }
                    return true;
                }

                if (result) {
                    if (!mVoiceRecordRect.contains((int) event.getX(),
                            (int) event.getY())) {
                        mAudioRecoderHelper.getOutputFile().deleteOnExit();
                        mSecond = 0;
                    } else {
                        Log.i(TAG, "send voice record  time:"
                                + (mVoiceEndTime - mVoiceStartTime));
                        Log.i(TAG, "send void time:" + mSecond);
                        mListener.onVoiceSend(mAudioRecoderHelper.getOutputFile()
                                .getAbsolutePath(), mSecond * 1000);
                        mSecond = 0;
                    }
                } else {
                    if (getActivity() != null) {
                        Toast.makeText(getActivity(), getActivity().getString(R.string.error_voice_record), Toast.LENGTH_SHORT).show();
                    }
                    mAudioRecoderHelper.getOutputFile().deleteOnExit();
                    mSecond = 0;
                }

                break;
        }

        return true;
    }

    public void releaseVoiceRecord() {
        if (mListener != null) {
            mListener.onVoiceRecordEnd();
            mListener.onFreshToCancel(false);
        }
        if (mImageVoice != null) {
            onVoiceRecordStop();
            clickFrequencyControl(1000);
        }
        if (isHandlerPost && mTimeHandler != null) {
            mTimeHandler.removeCallbacks(mRunnable);
            isHandlerPost = false;
        } else {
            isTouchUp = true;
            return;
        }

        if (mAudioRecoderHelper != null) {
            mAudioRecoderHelper.stopVoiceRecord();
            abandonAudioFocus();
            mAudioRecoderHelper.getOutputFile().deleteOnExit();
        }
        mSecond = 0;
    }

    public void showTimeToast() {
        if (getActivity() == null) {
            return;
        }
        Toast.makeText(getActivity(), R.string.error_voice_record_short, Toast.LENGTH_SHORT).show();
    }

    public void onVoiceRecordStart() {
        mImageVoice.setImageResource(R.drawable.microphone_press);
        showPopupWindow();
    }

    public void onVoiceRecordStop() {
        try {
            mVoiceEndTime = System.currentTimeMillis();
            mImageVoice.setImageResource(R.drawable.microphone);
            if (mTimeTxt != null) {
                mTimeTxt.setText("00:00");
            }
            if (mCircleImageView != null) {
                mCircleImageView.setAngle(0);
            }
            dismissPopupWindow();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void showPopupWindow() {
        mVoiceStartTime = System.currentTimeMillis();
        int[] location = new int[2];
        mImageVoice.getLocationInWindow(location);
        mPopupWindow.showAtLocation(mImageVoice, Gravity.NO_GRAVITY,
                location[0] + mImageVoice.getWidth() / 2 - mTxtWidth / 2,
                location[1] - mTxtHeight);
    }

    public void dismissPopupWindow() {
        try {
            if (mPopupWindow.isShowing()) {
                mPopupWindow.dismiss();
            }
        } catch (Exception | Error e) {
            Log.i(TAG, "VoiceRecordFragment", e);
        }
    }

    private void clickFrequencyControl(int intervalTime) {
        Log.i(TAG, "click frequency control :" + intervalTime);
        mImageVoice.setEnabled(false);
        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                if (getActivity() == null) {
                    return;
                }
                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        mImageVoice.setEnabled(true);
                    }
                });

            }
        }, intervalTime);
    }

    @Override
    public void onAudioFocusChange(int focusChange) {

    }

    public interface OnVoiceRecordListener {

        void onVoiceSend(String voicefile, long time);

        void onFreshToCancel(boolean isShouldCancel);

        void onVoiceRecordStart();

        void onVoiceRecordEnd();

    }

    private class TimeRunnable implements Runnable {

        @Override
        public void run() {
            mSecond++;
            String result = TimeUtil.parseVoiceRecord(mSecond * 1000);
            StringBuffer mBuffer = new StringBuffer(result);
            mBuffer.replace(0, 1, "0");
            mTimeTxt.setText(mBuffer.toString());
            Log.i(TAG, "handler time:" + mSecond);
            if (mSecond < MAX_RECORD_SECOND) {
                mTimeHandler.postDelayed(this, 1000);
            } else {
                onVoiceRecordStop();
                mListener.onVoiceRecordEnd();
                boolean resultFlag = mAudioRecoderHelper.stopVoiceRecord();
                if (resultFlag) {
                    mTimeHandler.removeCallbacks(mRunnable);
                    mListener.onVoiceSend(mAudioRecoderHelper.getOutputFile()
                            .getAbsolutePath(), 60000);
                } else {
                    if (getActivity() != null) {
                        Toast.makeText(getActivity(),
                                getActivity().getString(R.string.error_voice_record), Toast.LENGTH_SHORT).show();
                    }
                }
                clickFrequencyControl(1000);
                Log.i(TAG, "send voice 60000");
                isVoiceComplete = true;
                mVoiceStartTime = 0;
                mVoiceEndTime = 0;
                isHandlerPost = false;
                mSecond = 0;
            }
        }

    }

    public class VolumeChangeCallback implements AudioRecoderHelper.OnVolumeChangeListener {

        @Override
        public void onVolumeChange(int level) {
            if (level < 30 && level > 0) {
                int levelback = level - 2;
                if (levelback < 0) {
                    levelback = 0;
                }
                mCircleImageView.setAngle(180 * levelback / 28);

                Log.i(TAG, "volume:" + levelback);
            }
        }

    }

}
