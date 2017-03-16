package org.yeewoe.mopanet.client;

import org.yeewoe.mopanet.net.ClientTunnel;
import org.yeewoe.mopanet.net.IReconnectHandle;
import org.yeewoe.mopanet.net.Result;

import android.os.Handler;

/**
 * 网络连接器
 * Created by wyw on 2016/3/22.
 */
public class MopaNetClient {


    private ClientTunnel tn;
    private static MopaNetClient innerInstance;
    private ClientMessageHandle msgHandle;

    private MopaNetClient() {

    }

    public void Init(IReconnectHandle reconnectHandle) {
        tn = new ClientTunnel();
        Client client = new Client();
        client.tunnel = tn;
        msgHandle = new ClientMessageHandle();
        msgHandle.setClient(client);
        tn.init(msgHandle, reconnectHandle); //这里触发连接服务器.
        tn.awaitConnect();
    }

    //设置推送处理的handler
    public void setServerPushHandle(Handler h) {
        msgHandle.getClient().h = h;
    }

    public static MopaNetClient getInstance() {
        if (innerInstance == null) {
            synchronized (MopaNetClient.class) {
                if (innerInstance == null) {
                    innerInstance = new MopaNetClient();
                }
            }
        }

        return innerInstance;
    }

    public boolean isConnected() {
        return tn.isConnected();
    }

    public void disconnet() {
        tn.close();
        tn.awaitClose();
        tn = null;
        innerInstance = null;
    }

    //注意超时单位是毫秒, 如果传入的result为null，则无需等待回复.
    public boolean send(int serverType, short flag, byte[] byteArray, Result mopaResult, int timeoutMs) {
        if (mopaResult == null) {
            tn.send(serverType, flag, byteArray);
            return true;
        }
        return tn.sendSync(serverType, flag, byteArray, mopaResult, timeoutMs);
    }
}
