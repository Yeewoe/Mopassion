package org.yeewoe.mopassion.app.file.service;

import org.yeewoe.commonutils.common.assist.Checks;
import org.yeewoe.mopanet.callback.Callback;
import org.yeewoe.mopanet.callback.CallbackUtils;
import org.yeewoe.mopanet.client.MopaErrno;
import org.yeewoe.mopanet.protos.PBImMsg;
import org.yeewoe.mopanet.utils.LogCore;
import org.yeewoe.mopassion.app.file.model.UploadCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 文件数据发送管理器
 * Created by wyw on 2016/4/24.
 */
public class PicBatchManager {
    private static final int THREAD_COUNT = 3;

    private static PicBatchManager instance;
    private FileService fileService;
    private Executor executor;

    private PicBatchManager() {
        fileService = new FileService();
        executor = Executors.newFixedThreadPool(THREAD_COUNT);
    }

    public static PicBatchManager getInstance() {
        if (instance == null) {
            synchronized (PicBatchManager.class) {
                if (instance == null) {
                    instance = new PicBatchManager();
                }
            }
        }
        return instance;
    }

    public void call(final File file, final UploadCallback uploadCallback, final Callback callback) {
        List<File> files = new ArrayList<>();
        files.add(file);
        call(files, uploadCallback, callback);
    }

    public void call(final List<File> files, final UploadCallback uploadCallback, final Callback callback) {
        if (!Checks.check(files)) {
            uploadCallback.success("");
            CallbackUtils.callbackInfoVoid(callback);
            return;
        }

        executor.execute(new Runnable() {
            @Override public void run() {
                final List<Long> fileTasks = new ArrayList<>();
                for (File ignored : files) {
                    fileTasks.add(0L);
                }

                LogCore.wtf("PicBatcherManager开始工作，还剩下" + fileTasks.size());

                for (final File file : files) {
                    fileService.upload(file, new UploadCallback() {
                        @Override public void progress(String key, double percent) {
                            uploadCallback.progress(key, percent);
                        }

                        @Override public void success(String key) {
                            fileTasks.remove(0);
                            LogCore.wtf("有一个图片完成，key= " + key + "，还剩下" + fileTasks.size());
                            uploadCallback.success(key);

                            if (fileTasks.isEmpty()) {
                                CallbackUtils.callbackInfoVoid(callback);
                            }
                        }

                        @Override public void fail(String key, int errorCode) {
                            fileTasks.remove(0);
                            LogCore.wtf("有一个图片失败，key= " + key + ", errorCode=" + errorCode + "，还剩下" + fileTasks.size());
                            uploadCallback.fail(key, errorCode);


                            CallbackUtils.errorCallback(callback, errorCode);
                        }

                        @Override public void cancel(String key) {
                            fileTasks.remove(0);
                            LogCore.wtf("有一个图片取消，key= " + key + "，还剩下" + fileTasks.size());
                            uploadCallback.cancel(key);

                            CallbackUtils.errorCallback(callback, MopaErrno.LOCAL_FILE_UPLOAD_CANCEL_ERROR.ordinal());
                        }
                    });
                }
            }
        });
    }

    private void callMsg(PBImMsg data, List<File> file) {
    }
}
