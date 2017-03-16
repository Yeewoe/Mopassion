package org.yeewoe.mopanet.template;

import android.util.Log;

import org.yeewoe.mopanet.callback.Callback;
import org.yeewoe.mopanet.callback.CallbackUtils;
import org.yeewoe.mopanet.client.MopaErrno;
import org.yeewoe.mopanet.client.MopaCall;
import org.yeewoe.mopanet.net.Result;
import org.yeewoe.mopanet.utils.LogCore;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * JNI调用remoteCall方法的封装
 *
 * @author luhua
 */
public class NetSendRequestTemplate<T> {

    private String TAG = "NetSendRequestTemplate";
    private short requestFlag = 0;
    public byte[] setRequestParams() {
        return null;
    }

    public void setFlag(short requestFlag) {
        this.requestFlag = requestFlag;
    }

    public Class getGenericType(int index) {
        Type genType = this.getClass().getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            throw new RuntimeException("Index outof bounds");
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }
        return (Class) params[index];
    }

    public void handleResponse(byte[] bytes, Callback callback) throws IOException {

    }

    public void sendRequest(int serverType, Callback callback) {
        sendRequest(serverType, callback, 60000);
    }

    // 触发回调方法.
    public <RSP> void doCallback(Callback callback, RSP rsp, Integer result)
    {
        if (result == null || result == 0) {
            LogCore.wtf("call success");
            CallbackUtils.callbackInfoObject(callback, rsp);
        } else {
            LogCore.wtf("call error, errorCode=" + result);
            CallbackUtils.errorCallback(callback, result);
        }
    }
    public void sendRequest(int serverType, Callback callback, int timeoutMs) {
        LogCore.wtf("begin call, serverType=" + serverType + ", timeoutMs=" + timeoutMs);
        byte[] b = setRequestParams();

        if (b == null) {
            throw new NullPointerException("request params is null");
        }

        if (timeoutMs == 0) {
            throw new IllegalArgumentException("time out is 0, must > 0");
        }

        final Result mopaResult = new Result();
        boolean result = false;
        if (requestFlag == MopaCall.DEFAULT_FLAG) {
            result = MopaCall.remoteCall(serverType,
                    b,
                    needResponse() ? mopaResult : null,
                    timeoutMs);
        } else if (requestFlag == MopaCall.JSON_FLAG) {
            result = MopaCall.remteCallTransferJson(serverType,
                    b,
                    needResponse() ? mopaResult : null,
                    timeoutMs);
        }

        if (result) {
            byte[] bytes = mopaResult.response;
            if (bytes == null) {
                CallbackUtils.mopaResultByteArrayInputStreamErrorCallback(callback);
                return;
            }

            if (!needResponse()) {
                return;
            }

            callResponse(mopaResult, callback);

        } else {
            CallbackUtils.errorCallback(callback, MopaErrno.LOCAL_CONNECT_FAILED.value());
        }
    }

    private void callResponse(Result result, Callback callback) {
        try {
            handleResponse(result.response, callback);
        } catch (IOException e) {
            LogCore.i(Log.getStackTraceString(e));
            CallbackUtils.jniByteToLocalObjectError(callback);
        }
    }

    /**
     * 是否需要等待返回Response
     *
     * @return true: need, no: no nedd.
     */
    public boolean needResponse() {
        return true;
    }
}
