package org.yeewoe.mopanet.template;

import org.yeewoe.mopanet.callback.Callback;
import org.yeewoe.mopanet.task.ThreadTemplate;

public abstract class ProtobufNetTemplate extends ThreadTemplate {

    /**
     * 用于启动线程调用MopaNetClient
     * 一般与NetSendRequestTemplate一起使用
     *
     * @param callback callback
     * @return long
     */
    public long startThread(Callback callback) {

        return startNetRequestThread(callback);

    }

    public long startThread() {
        return startThread(null);
    }

}
