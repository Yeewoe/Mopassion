package org.yeewoe.mopanet.task;

import org.yeewoe.mopanet.task.TaskManager.TaskManagerParams;


public class PoolCache {

    //提供给通常的曾删改查操作使用
    private static TaskManager mTaskManager = null;
    //提供给上传文件操作
    private static TaskManager mFileTaskManager = null;

    private TaskManager mDownloadManager = null;

    private static TaskManager mLogManager = null;

    private static TaskManager mImManager = null;

    private static TaskManager mRequestManager = null;

    private static PoolCache mInstance = null;

    private PoolCache() {
        initTaskManager();

        initFileTaskManager();

        initLogManager();

        initImManager();

        initRequestManager();
    }

    public static PoolCache getInstance() {
        if (mInstance == null) {
            mInstance = new PoolCache();
        }
        return mInstance;
    }

    private void initFileTaskManager() {
        TaskManagerParams params = new TaskManagerParams();
        params.threadPoolSize = 2;
        mFileTaskManager = TaskManager.newInstance("file", params);
    }

    public void initBatchFileUploadManager() {
        TaskManagerParams params = new TaskManagerParams();
        params.threadPoolSize = 3;
        mDownloadManager = TaskManager.newInstance("download", params);
    }

    private void initTaskManager() {
        TaskManagerParams params = new TaskManagerParams();
        params.threadPoolSize = 2;
        mTaskManager = TaskManager.newInstance("common", params);
    }

    private void initLogManager() {
        TaskManagerParams params = new TaskManagerParams();
        params.threadPoolSize = 1;
        mLogManager = TaskManager.newInstance("log", params);
    }

    private void initImManager() {
        TaskManagerParams params = new TaskManagerParams();
        params.threadPoolSize = 1;
        mImManager = TaskManager.newInstance("IM", params);
    }

    private void initRequestManager() {
        TaskManagerParams params = new TaskManagerParams();
        params.threadPoolSize = 5;
        mRequestManager = TaskManager.newInstance("net_request", params);
    }

    public TaskManager getTaskManager() {
        return mTaskManager;
    }

    public TaskManager getFileTaskManager() {
        return mFileTaskManager;
    }

    public TaskManager getLogManager() {
        return mLogManager;
    }

    public TaskManager getImManger() {
        return mImManager;
    }

    public TaskManager getRequestManager(){
        return mRequestManager;
    }

    public TaskManager getBatchFileUploadManager() {
        if(mDownloadManager == null || mDownloadManager.isDestory()){
            initBatchFileUploadManager();
        }
        return mDownloadManager;
    }

}
