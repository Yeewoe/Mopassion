package org.yeewoe.mopanet.task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 1. 线程池管理器（ThreadPoolManager）:用于创建并管理线程池
 * 2. 工作线程（WorkThread）: 线程池中线程
 * 3. 任务接口（Task）:每个任务必须实现的接口，以供工作线程调度任务的执行。
 * 4. 任务队列:用于存放没有处理的任务。提供一种缓冲机制。
 *
 * @author luhua
 *         主要工作:创建线程池，销毁线程池，添加新任务
 */
public class ThreadPoolManager {

    private final int DEFAULT_POOL_SIZE = 3;

    private ExecutorService executorService;

    public ThreadPoolManager() {

    }

    public void createPool() {

        executorService = Executors.newFixedThreadPool(DEFAULT_POOL_SIZE);

    }

    public void createPool(int threadPoolSize) {
        executorService = Executors.newFixedThreadPool(
                threadPoolSize > 0 ?
                        threadPoolSize : DEFAULT_POOL_SIZE);
    }

    public void destoryPool() {

        executorService.shutdown();

    }

    public Future<?> execute(Task task) {
        return executorService.submit(task);
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }


}
