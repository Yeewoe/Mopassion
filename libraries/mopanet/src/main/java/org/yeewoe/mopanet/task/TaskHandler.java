package org.yeewoe.mopanet.task;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public abstract class TaskHandler extends Task {

    private static final int WHAT = 0x0001;
    private static final String DATA = "byte";

    private long id;

    private DataHandler mHandler = new DataHandler();

    public TaskHandler(long id) {
        super(id);
        this.id = id;
    }

    public long getId() {
        return id;
    }

    @Override
    public void run() {
        byte[] data = request();
        mHandler.setTask(this);
        Message message = mHandler.obtainMessage(WHAT);
        Bundle bundle = new Bundle();
        bundle.putByteArray(DATA, data);
        message.setData(bundle);
        mHandler.sendMessage(message);
    }

    /**
     * @return
     */
    public abstract byte[] request();

    /**
     * @param data
     */
    public abstract void postResponse(byte[] data);

    private static class DataHandler extends Handler {
        private TaskHandler task;

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case WHAT:
                    Bundle bundle = msg.getData();
                    task.postResponse(bundle.getByteArray(DATA));
                    break;

                default:
                    break;
            }
        }

        public void setTask(TaskHandler task) {
            this.task = task;
        }

    }

}
