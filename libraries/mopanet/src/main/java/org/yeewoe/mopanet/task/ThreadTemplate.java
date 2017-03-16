package org.yeewoe.mopanet.task;

import android.os.Process;

import org.yeewoe.mopanet.callback.Callback;
import org.yeewoe.mopanet.callback.CallbackUtils;
import org.yeewoe.mopanet.utils.LogCore;

import java.util.concurrent.Future;


public abstract class ThreadTemplate {
    private static final String TAG = "ThreadTemplate";

    private static TaskManager getTaskManager(TaskManagerType type) {
        TaskManager taskManager = null;
        if (type == null) {
            LogCore.i("!!!getTaskManager type is null");
            return null;
        }

        switch (type) {
            case FILE_UPLOAD:
                taskManager = PoolCache.getInstance().getFileTaskManager();
                break;
            case FILE_DOWNLOAD:
                taskManager = PoolCache.getInstance().getBatchFileUploadManager();
                break;
            case COMMON:
                taskManager = PoolCache.getInstance().getTaskManager();
                break;
            case LOG:
                taskManager = PoolCache.getInstance().getLogManager();
                break;
            case IM:
                taskManager = PoolCache.getInstance().getImManger();
                break;
            case NET_REQUEST:
                taskManager = PoolCache.getInstance().getRequestManager();
                break;
            default:
                break;
        }
        return taskManager;
    }

    public static Future<?> getFileFuture(long taskId) {

        return getTaskManager(TaskManagerType.FILE_UPLOAD).getFuture(taskId);

    }

    /**
     * 用于启动线程;
     *
     * @param callback Callback
     * @param  type TaskManagerType
     * @return taskId long
     */
    public long startThread(Callback callback, TaskManagerType type) {

        final TaskManagerType tmType = type ;

        long taskId = ThreadPoolUtils.generateRandom();
        Task task = new Task(taskId) {
            @Override
            public void run() {
                Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
                sendRequest();
                //移除TaskManager中taskId与Future的映射
                TaskManager taskManager = getTaskManager(tmType);
                if(taskManager != null) {
                    taskManager.removeFuture(id) ;
                }
            }
        };

        TaskManager taskManager = getTaskManager(type);
        try {
            if (! taskManager.isDestory()) {
                taskManager.putTask(task);
            } else {
                LogCore.i(String.format("Thread pool is destory, type = %s", type.name()) );
            }
        } catch (InterruptedException e) {
            if (callback != null) {
                CallbackUtils.threadInterrupted(callback);
            } else {
                //callback is null
            }
            //LogCore.i(TAG, Log.getStackTraceString(e));
        }

        return taskId;
    }

    public long startCommonThread() {
        return startCommonThread(null);
    }

    public long startCommonThread(Callback callback) {
        return startThread(callback, TaskManagerType.COMMON);
    }

    public long startUploadThread() {
        return startUploadThread(null);
    }

    public long startUploadThread(Callback callback) {
        return startThread(callback, TaskManagerType.FILE_UPLOAD);
    }

    public long startDownloadThread() {
        return startDownloadThread(null);
    }

    public long startDownloadThread(Callback callback) {
        return startThread(callback, TaskManagerType.FILE_DOWNLOAD);
    }

    public long startImThread() {
        return startThread(null, TaskManagerType.IM);
    }

	public long startNetRequestThread(Callback callback ) {
		return startThread(callback, TaskManagerType.NET_REQUEST) ;
	}

    /**
     * 启动日志线程
     * @return long
     */
    public long startLogThread() {
        return startThread(null, TaskManagerType.LOG);
    }

    public abstract void sendRequest();

    private enum TaskManagerType {
        FILE_UPLOAD,
        COMMON,
        LOG,
        IM,
        FILE_DOWNLOAD, NET_REQUEST
    }
}
