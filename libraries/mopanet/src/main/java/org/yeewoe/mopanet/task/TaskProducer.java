package org.yeewoe.mopanet.task;

import java.util.concurrent.BlockingQueue;

public class TaskProducer extends Producer<Task> {

    public TaskProducer(BlockingQueue<Task> bq) {
        super(bq);
    }

}
