package org.yeewoe.mopassion.app.file.service;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCancellationSignal;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;

import org.json.JSONObject;
import org.yeewoe.mopanet.client.MopaErrno;
import org.yeewoe.mopassion.app.common.service.BaseService;
import org.yeewoe.mopassion.app.file.model.UploadCallback;
import org.yeewoe.mopassion.utils.LogCore;

import java.io.File;
import java.util.HashMap;

/**
 * 七牛业务接口
 * Created by wyw on 2016/4/21.
 */
public class QiniuService extends BaseService {
    private static final String CLASS_NAME = "QiniuService";

    private UploadManager uploadManager;
    HashMap<String, Boolean> isCancelledMap = new HashMap<>();

    public QiniuService() {
        uploadManager = new UploadManager();
    }

    public void upload(File file, final String key, String token, final UploadCallback uploadCallback) {
        String METHOD_NAME = "upload";
        logInterfaceCall(CLASS_NAME, METHOD_NAME, "file=" + file + ", key=" + key + ", token=" + token);
        // TODO 目前支持记录进度上传，后续需要支持断点续传
        isCancelledMap.put(key, false);

        uploadManager = new UploadManager();
        uploadManager.put(file, key, token, new UpCompletionHandler() {
            // 回调都在主线程
            @Override public void complete(String key, ResponseInfo info, JSONObject response) {
                if (info.isOK()) {
                    LogCore.wtf("qiniu upload success, key=" + key);
                    uploadCallback.success(key);
                } else if (info.isCancelled()) {
                    LogCore.wtf("qiniu upload cancel, key=" + key);
                    uploadCallback.cancel(key);
                } else if (info.isNetworkBroken()) {
                    LogCore.wtf("qiniu upload isNetworkBroken, key=" + key);
                    uploadCallback.fail(key, MopaErrno.LOCAL_NETWORK_ERROR.ordinal());
                } else if (info.isServerError()) {
                    LogCore.wtf("qiniu upload isServerError, key=" + key);
                    uploadCallback.fail(key, MopaErrno.LOCAL_FILE_UPLOAD_SERVER_ERROR.ordinal());
                } else {
                    LogCore.wtf("qiniu upload fail, key=" + key + ", status=" + info.statusCode + ", error=" + info.error);
                    uploadCallback.fail(key, MopaErrno.LOCAL_FILE_UPLOAD_COMMON_ERROR.ordinal());
                }
            }
        }, new UploadOptions(null, null, false, new UpProgressHandler() {
            // 回调都在主线程
            @Override public void progress(String key, double percent) {
                LogCore.wtf("qiniu upload on progress, key=" + key + ", percent=" + percent);
                uploadCallback.progress(key, percent);
            }
        }, new UpCancellationSignal() {
            @Override public boolean isCancelled() {
                if (isCancelledMap.containsKey(key)) {
                    return isCancelledMap.get(key);
                } else {
                    return false;
                }
            }
        }));
    }

    public void cancel(final String key) {
        String METHOD_NAME = "cancel";
        logInterfaceCall(CLASS_NAME, METHOD_NAME, "key=" + key);

        isCancelledMap.put(key, true);
    }
}
