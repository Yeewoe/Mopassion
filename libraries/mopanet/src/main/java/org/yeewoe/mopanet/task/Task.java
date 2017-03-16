package org.yeewoe.mopanet.task;


public abstract class Task implements Runnable {

    protected long id;

    public Task(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

}
