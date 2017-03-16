package org.yeewoe.mopanet.task;

import org.yeewoe.mopanet.BuildConfig;
import org.yeewoe.mopanet.utils.LogCore;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;


/**
 * 1. 创建线程池
 * 2. 创建队列
 * 3. 创建任务生产者
 * 4. 创建任务消费者
 *
 * @author luhua
 */

public class TaskManager {

    static final String TAG = "TaskManager";
    String taskManagerName = null;

    private static TaskManager mTaskManager;

    private ThreadPoolManager mThreadPoolManager;

    private BlockingQueue<Task> mqueue = new ArrayBlockingQueue<>(5);
    private TaskProducer mProducer = null;
    private TaskConsumer mConsumer = null;

    private RunConsumer mRunConsumer;

    private Map<Long, Future<?>> futureMap = Collections.synchronizedMap(new HashMap<Long, Future<?>>());

    private boolean isDestory = false;

    private TaskManager(String name) {
        this.taskManagerName = name;
        mProducer = new TaskProducer(mqueue);
        mConsumer = new TaskConsumer(mqueue);

        mThreadPoolManager = new ThreadPoolManager();
        mThreadPoolManager.createPool();

        isDestory = false;

        mRunConsumer = new RunConsumer();
        new Thread(mRunConsumer).start();

    }


    private TaskManager(String name, TaskManagerParams params) {
        this.taskManagerName = name;
        mProducer = new TaskProducer(mqueue);
        mConsumer = new TaskConsumer(mqueue);

        mThreadPoolManager = new ThreadPoolManager();
        mThreadPoolManager.createPool(params.threadPoolSize);

        isDestory = false;

        mRunConsumer = new RunConsumer();
        new Thread(mRunConsumer).start();

    }

    public static TaskManager getInstance(String name) {
        if (mTaskManager == null) {
            mTaskManager = new TaskManager(name);
        }
        return mTaskManager;
    }


    public static TaskManager getInstance(String name, TaskManagerParams params) {
        if (mTaskManager == null) {
            mTaskManager = new TaskManager(name,params);
        }
        return mTaskManager;
    }


    public static TaskManager newInstance(String name) {

        return new TaskManager(name);

    }

    public static TaskManager newInstance(String name, TaskManagerParams params) {

        return new TaskManager(name,params);

    }

    public void putTask(Task task) throws InterruptedException {
        mProducer.put(task);
        if(BuildConfig.DEBUG){
            //Log.i("ThreadPool", String.format("name = %s, add task in queue taskId = %d, queue item count = %s ",taskManagerName, task.getId(), mqueue != null ? mqueue.size() : 0));
        }
    }

    public void destoryThreadPool() {
        mThreadPoolManager.destoryPool();
        isDestory = true;
        mRunConsumer.setCancel(true);
        futureMap.clear();
    }

    /**
     * 判断线程驰池是否销毁
     *
     * @return boolean
     */
    public boolean isDestory() {
        return isDestory;
    }

    public Future<?> getFuture(long id) {

        if (futureMap != null) {
            return futureMap.get(id);
        } else {
            return null;
        }

    }

    public Future<?> removeFuture(long id) {

        /*if (futureMap != null) {
            return futureMap.remove(id);
        } else {
            return null;
        }*/
        return null;
    }

    public static class TaskManagerParams {
        public int threadPoolSize;
    }

    private class RunConsumer implements Runnable {

        private boolean mCancel = false;

        public RunConsumer() {
            super();
        }

        @Override
        public void run() {
            while (!mCancel) {
                try {
                    Task task = mConsumer.take();
                    if(BuildConfig.DEBUG){
                        //Log.e("ThreadPool", String.format("name = %s, get task from queue taskId = %d, queue item count = %s ",taskManagerName, task.getId(), mqueue != null ? mqueue.size() : 0));
                    }
                    Future<?> future = mThreadPoolManager.execute(task);

                    /*try {
                        futureMap.put(task.getId(), future);
                    }catch (OutOfMemoryError e) {
                        if(futureMap != null) {
                            futureMap.clear();
                        }
                    }*/
                } catch (InterruptedException e) {
                    if(futureMap != null) {
                        futureMap.clear();
                    }
                    LogCore.i(android.util.Log.getStackTraceString(e));
                }
            }
        }

        public void setCancel(boolean mCancel) {
            this.mCancel = mCancel;
        }

    }

}
