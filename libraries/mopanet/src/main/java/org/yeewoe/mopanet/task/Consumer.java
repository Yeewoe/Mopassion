package org.yeewoe.mopanet.task;

import java.util.concurrent.BlockingQueue;

public class Consumer<T> {

    private BlockingQueue<T> bq;

    public Consumer(BlockingQueue<T> bq) {
        super();
        this.bq = bq;

    }

    /**
     * Retrieves and removes the head of this queue,
     * waiting if necessary until an element becomes
     *
     * @return
     * @throws InterruptedException - if interrupted while waiting
     */
    public T take() throws InterruptedException {
        return bq.take();
    }

}
