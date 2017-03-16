package org.yeewoe.mopanet.task;

import java.util.concurrent.BlockingQueue;

public class Producer<T> {

    private BlockingQueue<T> bq;

    public Producer(BlockingQueue<T> bq) {
        this.bq = bq;
    }

    public void put(T t) throws InterruptedException {
        bq.put(t);
    }

}
