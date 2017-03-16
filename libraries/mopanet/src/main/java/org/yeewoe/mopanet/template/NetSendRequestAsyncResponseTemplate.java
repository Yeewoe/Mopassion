package org.yeewoe.mopanet.template;

/**
 * JNI调用remoteCall方法的封装
 * @author luhua
 */
public abstract class NetSendRequestAsyncResponseTemplate extends NetSendRequestTemplate{

    private String TAG = "NetSendRequestAsyncResponseTemplate";

    public boolean asyncResponse(){
        return true;
    }

}
