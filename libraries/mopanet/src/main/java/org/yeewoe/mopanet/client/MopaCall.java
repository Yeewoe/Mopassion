package org.yeewoe.mopanet.client;

import org.yeewoe.mopanet.net.Result;

/**
 * Created by acc on 2016/3/22.
 */
public class MopaCall {

    public static final short DEFAULT_FLAG = 0;
    public static final short JSON_FLAG = 0x8;

    /*
     RES_MEMORY_LACK,            //

     RES_CALL_TIMEOUT,
     LOCAL_NETWORK_UNAVAILABLE,    //网络不可用(无网络或还未与Server建立连接)
     RES_NETWORK_ERROR,          //网络发送或接收过程中发生错误
     RES_NOT_LOGIN,              //账号未登陆
     RES_AUTH_FAILED,            //账号认证失败
     LOCAL_INVAILD_OPER,           //非法的操作调用
     RES_NO_MESSAGE,             //暂时没有message(非阻塞模式获取消息可能返回此结果)
     10 							//手机没有网络
  */
    public boolean remoteCall(int severType,
                              byte[] byteArray,
                              Result result) {
        return remoteCall(severType, (short) 0, byteArray, result, 5000);
    }

    public static boolean remoteCall(int severType, byte[] byteArray, Result result, int timeoutMs) {
        return remoteCall(severType, DEFAULT_FLAG, byteArray, result, timeoutMs);
    }


    /**
     * 方法内部实现，任何的改动，可能影响登陆前模块
     *
     * @param severType short
     * @param byteArray byte[]
     * @param result    result
     * @param timeout   int
     * @return int
     */
    public static boolean remoteCall(int severType,
                                     short flag,
                                     byte[] byteArray,
                                     Result result, int timeoutMs) {
        return MopaNetClient.getInstance().send(severType, flag, byteArray, result, timeoutMs);
    }

    public static boolean remteCallTransferJson(int severType,
                                                byte[] byteArray,
                                                Result result) {
        //#define OPFLAG_JSON_DATA  (0x8) //pkt后面装的是json数据
        return remoteCall(severType, JSON_FLAG, byteArray, result, 30000);
    }

    public static boolean remteCallTransferJson(int severType,
                                                byte[] byteArray,
                                                Result result,
                                                int timeoutMs) {
        //#define OPFLAG_JSON_DATA  (0x8) //pkt后面装的是json数据
        return remoteCall(severType, JSON_FLAG, byteArray, result, timeoutMs);
    }
}
