package org.yeewoe.mopanet.net;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.yeewoe.mopanet.client.MopaErrno;
import org.yeewoe.mopanet.client.MopaNetConstants;
import org.yeewoe.mopanet.client.MopaNetConstants;
import org.yeewoe.mopanet.net.codec.MessageCodecFactory;
import org.yeewoe.mopanet.net.message.BasicMessage;
import org.yeewoe.mopanet.protos.PBModInfo;
import org.yeewoe.mopanet.protos.PBRoute;

import java.net.InetSocketAddress;
import java.util.HashMap;

//�ͻ���ͨ��ͨ��.
public class ClientTunnel {
    NioSocketConnector connector = null;
    ConnectFuture future = null;
    public enum ConnectionState{
        None,
        Connecting,
        Connected,
        Disconnected,
        Reconnecting,
        Max,


    }
    ConnectionState state = ConnectionState.None;
    IReconnectHandle reconnectHandle = null;

    public ClientTunnel() {
        super();
        // TODO Auto-generated constructor stub
    }

    public int init(IMessageHandle h, IReconnectHandle rh) {
        if (connector == null)
            connector = new NioSocketConnector();
        connector.setConnectTimeoutMillis(MopaNetConstants.CONNECT_TIMEOUT);

        connector.getFilterChain().addLast("codec",
                new ProtocolCodecFilter(
                        new MessageCodecFactory()));

        // connector.getFilterChain().addLast("logger", new LoggingFilter());

        ClientSessionHandler ch = new ClientSessionHandler();
        ch.setMessageHandle(h);
        connector.setHandler(ch);

        future = connector.connect(new InetSocketAddress(
                MopaNetConstants.HOSTNAME, MopaNetConstants.PORT));
        state = ConnectionState.Connecting;
        this.reconnectHandle = rh;
        return 0;
    }


    //����ʹ��
    public boolean awaitConnect() {
        ConnectFuture connectFuture = future.awaitUninterruptibly();
        if(this.isConnected())
            state = ConnectionState.Connected;
        else {
            state = ConnectionState.Disconnected;
        }
        return connectFuture.isConnected();
    }


    public void awaitClose() {
        future.getSession().getCloseFuture().awaitUninterruptibly();
    }

    public boolean isConnected() {
        if (future == null)
            return false;
        return future.isConnected();
    }

    //�򵥷�װ.
    public void send(int srvop, int flag, byte[] buffer) {
        IoSession session = future.getSession();
        BasicMessage bm = new BasicMessage();
        bm.flag = (short) flag;
        bm.mark = MopaNetConstants.mark;
        PBModInfo mdi = new PBModInfo.Builder().rpc(0L).build();
        bm.route = new PBRoute.Builder()
                .srvop(srvop)
                .modinfo(mdi)
                .flag(0L).build();
        bm.payload = buffer;
        session.write(bm);
    }


    private HashMap<Long, BasicMessage> requests = new HashMap<Long, BasicMessage>(); //缓存的消息.
    private long counter = 1024; //序列号计数器

    public boolean sendSync(int srvop, int flag, byte[] buffer, Result res) {
        return sendSync(srvop, flag, buffer, res, 5000);
    }


    public boolean sendSync(int srvop, int flag, byte[] buffer, Result res, int maxSleepTime) {

        BasicMessage bm = new BasicMessage();
        bm.flag = (short) flag;
        bm.mark = MopaNetConstants.mark;

        bm.payload = buffer;
        synchronized (this) {

            PBModInfo mdi = new PBModInfo.Builder().rpc(counter).build();
            bm.route = new PBRoute.Builder()
                    .srvop(srvop)
                    .modinfo(mdi)
                    .flag(0L).build();


            requests.put(counter, bm);
            counter++;
            if(state == ConnectionState.Reconnecting)
            {
                this.awaitConnect(); //等待重连成功
            }

            if(isConnected() == false)
            {
                res.errCode = MopaErrno.MERR_GLOBAL_TIMEOUT.value();
                return false;
            }

            IoSession session = future.getSession();
            session.write(bm);
        }

        int interval = 10;

        while(bm.rsp == null)
        {
            try {
                Thread.sleep(interval);
                maxSleepTime -= interval;
                if(maxSleepTime <= 0 ){
                    boolean bNeedRelogin = false;
                    synchronized (this){
                        if(this.state != ConnectionState.Reconnecting){
                            int retryTime = 5; //总共尝试5次
                            int retryInterval = 3000; //尝试3秒重新连接
                            while(retryTime > 0)
                            {

                                this.reConnect();
                                this.awaitConnect();
                                if(this.isConnected()){
                                    this.state = ConnectionState.Connected;
                                    break;
                                }
                                else{
                                    this.state = ConnectionState.Disconnected;
                                    Thread.sleep(retryInterval);
                                }
                                retryTime --;
                            }

                            bNeedRelogin = true;
                        }
                    }

                    if(bNeedRelogin && this.reconnectHandle != null){
                        if(this.state == ConnectionState.Connected){
                            this.reconnectHandle.OnReconnectOK(); //这里一般执行登录逻辑， 必须同步登录完成
                        } else {
                            this.reconnectHandle.OnReconnectFailed();
                        }
                    }
                    requests.remove(bm.route.modinfo.rpc); //暂时先不负责重传
                    break;
                }

            } catch (Exception e) {
                // TODO: handle exception
                System.out.println("ClientTunnel.sendSync caught excption " + e);
                continue;
            }
        }
        if (maxSleepTime <= 0 && bm.rsp == null) {
            return false;
        }

        res.response = bm.rsp.payload;
        System.out.println("ClientTunnel.sendSync  get a response for req " + bm);
        return true;
    }

    public void onResponse(BasicMessage bm) {
        if (bm.route.modinfo == null || bm.route.modinfo.rpc == null || bm.route.modinfo.rpc == 0)
            return;
        synchronized (this) {
            if (requests.containsKey(bm.route.modinfo.rpc)) {
                BasicMessage req = requests.get(bm.route.modinfo.rpc);
                req.rsp = bm;
                requests.remove(bm.route.modinfo.rpc);
            } else {
                System.out.println("ClientTunnel.onResponse cannot find rpc" + bm.route.modinfo.rpc + " request size " + requests.size());
            }
        }
    }

    void reConnect()
    {
        System.out.print("Begin reconnect ......");
        IMessageHandle h = ((ClientSessionHandler)connector.getHandler()).getMessageHandle();

        connector.dispose();
        connector = new NioSocketConnector();

        connector.setConnectTimeoutMillis(MopaNetConstants.CONNECT_TIMEOUT);
        connector.getFilterChain().addLast( "codec",
                new ProtocolCodecFilter(
                        new MessageCodecFactory()));

        // connector.getFilterChain().addLast("logger", new LoggingFilter());

        ClientSessionHandler ch =  new ClientSessionHandler();
        ch.setMessageHandle(h);
        connector.setHandler(ch);

        future = connector.connect(new InetSocketAddress(
                MopaNetConstants.HOSTNAME, MopaNetConstants.PORT));
        state = ConnectionState.Reconnecting;

    }

    public void close() {
        connector.dispose();
    }

    public void clear() {
        future = null;
        connector = null;
        requests.clear();
    }
}

	
	
	

