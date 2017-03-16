package org.yeewoe.mopassion.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaRecorder;
import android.media.MediaRecorder.OnErrorListener;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import org.yeewoe.mopassion.R;

import java.io.File;
import java.io.IOException;
import java.util.Timer;

@SuppressLint("ShowToast")
public class AudioRecoderHelper {

    private static final String TAG = AudioRecoderHelper.class.getSimpleName();
    boolean isRelease = false;
    private MediaRecorder mRecorder;
    private File mOutputFile;
    private long mRecordTime;
    private Context mContext;
    private Toast mToast;
    private Timer timer = new Timer();
    private int BASE = 600;
    private int SPACE = 100;                        // 间隔取样时间
    private Runnable mUpdateMicStatusTimer = new Runnable() {

        public void run() {
            updateVolumeLevel();
        }

    };

    private OnVolumeChangeListener mVolumeChangeListener;

    public AudioRecoderHelper(Context context) {
        this.mContext = context;
        mToast = Toast.makeText(mContext, "", Toast.LENGTH_SHORT);
        int xoffset = context.getResources().getDimensionPixelOffset(R.dimen.notice_titles_height);
        mToast.setGravity(Gravity.CENTER, 0, xoffset);
    }

    public void showToast(int second) {
        mToast.setText("");
    }

    public void setOnVolumeChangeListener(OnVolumeChangeListener changeListener) {
        this.mVolumeChangeListener = changeListener;
    }

    public void init() {

        try {
            mOutputFile = MopaFileUtil.createVoiceCacheFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean startVoiceRecord() {

        try {
            Log.i(TAG, "开始录音");
            isRelease = false;
            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
        /* ②设置音频文件的编码：AAC/AMR_NB/AMR_MB/Default */
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            init();
            Log.i(TAG, "录音存储位置:" + mOutputFile.getAbsolutePath());
            mRecorder.setOutputFile(mOutputFile.getAbsolutePath());
            Log.i(TAG, "recorder file:" + mOutputFile.getAbsolutePath());
            mRecorder.setOnErrorListener(new OnErrorListener() {

                @Override
                public void onError(MediaRecorder mr, int what, int extra) {
                    Log.i(TAG, "录音出现异常! what:" + what + " extra:" + extra);
                }
            });
            try {
                Log.i(TAG, "start voice record!");
                mRecorder.prepare();
                mRecorder.start();
            } catch (IOException | RuntimeException e) {
                Log.i(TAG, "录音出现异常!" + Log.getStackTraceString(e));
                e.printStackTrace();
                mRecorder.reset();
                return false;
            }

            updateVolumeLevel();
            return true;
        } catch (Exception e) {
            Log.i(TAG, "录音出现异常!");
            if (mRecorder != null) {
                mRecorder.reset();
            }
            return false;
        }
    }

    public File getOutputFile() {
        return mOutputFile;
    }

    public boolean stopVoiceRecord() throws IllegalStateException {
        if (mOutputFile != null && mRecorder != null) {
            try {
                /* ⑤停止录音 */
                mRecorder.stop();
            } catch (IllegalStateException e) {
                Log.i("录音结束出现异常!", Log.getStackTraceString(e));
                e.printStackTrace();
                mRecorder.reset();
                return false;
            } catch (RuntimeException e) {
                Log.i("录音结束出现异常!", Log.getStackTraceString(e));
                e.printStackTrace();
                mRecorder.reset();
                return false;
            }
			/* 将录音文件添加到List中 */
			/* ⑥释放MediaRecorder */
            mRecorder.release();
            if (timer != null) {
                timer.purge();
                timer.cancel();
            }
            // if (count == 9) {
            // mHandler.removeMessages(-1);
            // } else {
            // mHandler.removeMessages(count);
            // }

            mRecorder = null;
            Log.i(TAG, "录音结束" + mOutputFile.length());
            if (mOutputFile.length() > 0) {
                return true;
            } else {
                Log.i(TAG, "录音结束出现异常! 录音文件为空.");
                return false;
            }
        } else {
            Log.i(TAG, "录音结束出现异常! mOutputFile:" + mOutputFile + "  mRecorder:" + mRecorder);
            return false;
        }
    }

    public void updateVolumeLevel() {
        if (mRecorder != null) {
            int ratio = mRecorder.getMaxAmplitude() / BASE;
            int db = 0;// 分贝
            if (ratio > 1)
                db = (int) (20 * Math.log10(ratio));
            if (mVolumeChangeListener != null)
                mVolumeChangeListener.onVolumeChange(db);

            new Handler().postDelayed(mUpdateMicStatusTimer, SPACE);
        }
    }

    public interface OnRecordCompleteListener {

        public void onRecordComplete(File outputFile);

    }

    /**
     * 监听音量大小接口
     *
     * @author crj
     */
    public interface OnVolumeChangeListener {
        public static final int MAX_LEVEL = 7;
        public static final int MIN_LEVEL = 1;

        public void onVolumeChange(int level);
    }

}
