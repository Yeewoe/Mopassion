package org.yeewoe.mopassion.app.file.model;

/**
 * Created by wyw on 2016/4/21.
 */
public interface UploadCallback {
    void progress(String key, double percent);

    void success(String key);

    void fail(String key, int errorCode);

    void cancel(String key);
}
