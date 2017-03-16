package org.yeewoe.mopanet.task;

import java.util.concurrent.BlockingQueue;

public class TaskConsumer extends Consumer<Task> {

    public TaskConsumer(BlockingQueue<Task> bq) {
        super(bq);
    }

}
