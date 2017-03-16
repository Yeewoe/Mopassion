package org.yeewoe.mopanet.callback;

import org.yeewoe.mopanet.callback.Callback.CallbackInfo;
import org.yeewoe.mopanet.callback.RefreshCallback.RefreshType;
import org.yeewoe.mopanet.client.MopaErrno;

import java.util.ArrayList;
import java.util.List;

public class CallbackUtils {

    public static void callbackInfoVoid(Callback cb) {
        CallbackInfo<Void> callbackInfo = new CallbackInfo<>();
        cb.callback(callbackInfo);
    }

    public static void callbackInfoInteger(Callback cb, Integer result) {
        if (result == null) {
            result = 0;
        }
        CallbackInfo<Integer> callbackInfo = new CallbackInfo<>();
        callbackInfo.mT = result;
        cb.callback(callbackInfo);
    }

    public static <T> void callbackInfoList(Callback cb, List<T> result) {
        CallbackInfo<T> callbackInfo = new CallbackInfo<>();
        if (result == null) {
            result = new ArrayList<>();
        }
        callbackInfo.mTs = result;
        cb.callback(callbackInfo);
    }

    public static void callbackInfoLong(Callback callback, Long result) {
        if (result == null) {
            result = 0L;
        }

        CallbackInfo<Long> callbackInfo = new CallbackInfo<>();
        callbackInfo.mT = result;
        callback.callback(callbackInfo);
    }

    public static void callbackInfoBoolean(Callback callback, boolean result) {
        CallbackInfo<Boolean> callbackInfo = new CallbackInfo<>();
        callbackInfo.mT = result;
        callback.callback(callbackInfo);
    }

    public static <T> void callbackInfoObject(Callback callback, T result) {
        CallbackInfo<T> callbackInfo = new CallbackInfo<>();
        callbackInfo.mT = result;
        callback.callback(callbackInfo);
    }

    /**
     * 网络错误回调
     * 使用场景,调用网络接口,进行网络判断时判断
     *
     * @param callback Callback
     */
    public static void noNetErrorCallback(Callback callback) {
        errorCallback(callback, MopaErrno.LOCAL_NET_ERROR.value());
    }

    public static void errorCallback(Callback callback, int result) {
        CallbackInfo<Integer> callbackInfo = new CallbackInfo<>();
        callback(callback, result, callbackInfo);
    }

    public static CallbackInfo<?> buildErrorCallbackInfo(int result) {
        CallbackInfo<Integer> callbackInfo = new CallbackInfo<>();
        return buildCallbackInfo(result, callbackInfo);
    }

    public static void buildErrorCallbackInfo(CallbackInfo<?> callbackInfo, int result) {
        buildCallbackInfo(result, callbackInfo);
    }

    private static void callback(Callback callback, int result, CallbackInfo<?> callbackInfo) {
        callbackInfo.bError = true;
        callbackInfo.errorCode = result;
//        if (result == NetError.MERR_DISPATCHER_MESSAGE_ORDER_ERR || result == NetError.MERR_ENTYY_CONNECT_DISPATCHER) {
//            //result == NetError.MERR_ENTYY_CONNECT_DISPATCHER ：没有认证情况调用后台通用模块
//            VerifyProtoNet.mMessageOrderError++;
//            LoginUtils.verifyTicketWithoutConnectInThread();
//        }
        callback.callback(callbackInfo);
    }

    private static CallbackInfo<?> buildCallbackInfo(int result, CallbackInfo<?> callbackInfo) {
        callbackInfo.bError = true;
        callbackInfo.errorCode = result;
//        if (result == NetError.MERR_DISPATCHER_MESSAGE_ORDER_ERR || result == NetError.MERR_ENTYY_CONNECT_DISPATCHER) {
//            //result == NetError.MERR_ENTYY_CONNECT_DISPATCHER ：没有认证情况调用后台通用模块
//            VerifyProtoNet.mMessageOrderError++;
//            LoginUtils.verifyTicketWithoutConnectInThread();
//        }
        return callbackInfo;
    }

    private static void callback(RefreshCallback callback, int result, RefreshCallback.CallbackInfo<?> callbackInfo) {
        callbackInfo.bError = true;
        callbackInfo.errorCode = result;
//        if (result == NetError.MERR_DISPATCHER_MESSAGE_ORDER_ERR || result == NetError.MERR_ENTYY_CONNECT_DISPATCHER) {
//            //result == NetError.MERR_ENTYY_CONNECT_DISPATCHER ：没有认证情况调用后台通用模块
//            VerifyProtoNet.mMessageOrderError++;
//            LoginUtils.verifyTicketWithoutConnectInThread();
//        }
        callback.callback(callbackInfo);
    }

    public static void errorCallback(RefreshCallback callback, RefreshType type, int result) {
        RefreshCallback.CallbackInfo<Integer> callbackInfo = new RefreshCallback.CallbackInfo<>();
        callbackInfo.type = type;
        callback(callback, result, callbackInfo);
    }

    public static void errorCallback(Callback callback, CallbackInfo<?> callbackInfo, int result) {
        callbackInfo.bError = true;
        callbackInfo.errorCode = result;
        callback.callback(callbackInfo);
    }

    public static void invalidCallback(Callback callback) {
        errorCallback(callback, MopaErrno.LOCAL_INVAILD_OPER.value());
    }

    public static void invalidCallback(RefreshCallback callback, RefreshType type) {
        errorCallback(callback, type, MopaErrno.LOCAL_INVAILD_OPER.value());
    }

    public static void jniByteToLocalObjectError(Callback callback) {
        errorCallback(callback, MopaErrno.LOCAL_JNI_OBJECT_PARSE_ERROR.value());
    }

    public static void jniByteToLocalObjectError(RefreshCallback callback,
                                                 RefreshType type) {
        errorCallback(callback, type, MopaErrno.LOCAL_JNI_OBJECT_PARSE_ERROR.value());
    }

    public static void sqlExceptionErrorCallback(Callback callback) {
        errorCallback(callback, MopaErrno.LOCAL_SQLEXCEPTION.value());
    }

    public static void sqlExceptionErrorCallback(RefreshCallback callback, RefreshType type) {
        errorCallback(callback, type, MopaErrno.LOCAL_SQLEXCEPTION.value());
    }

    public static void mopaResultByteArrayInputStreamErrorCallback(Callback callback) {
        errorCallback(callback, MopaErrno.LOCAL_BYTE_ARRAY_INPUTSTREAM_IS_NULL.value());
    }

    public static void connectErrorCallback(Callback callback) {
        errorCallback(callback, MopaErrno.LOCAL_NETWORK_UNAVAILABLE.value());
    }

    /**
     * 线程中断异常
     *
     * @param cb Callback
     */
    public static void threadInterrupted(Callback cb) {
        errorCallback(cb, MopaErrno.LOCAL_THREAD_INTERRUPTED.value());
    }

    /**
     * 线程中断异常
     *
     * @param cb RefreshCallback
     */
    public static void threadInterrupted(RefreshCallback cb, RefreshType type) {
        RefreshCallback.CallbackInfo<Integer> cbi = new RefreshCallback.CallbackInfo<>();
        cbi.bError = true;
        cbi.errorCode = MopaErrno.LOCAL_THREAD_INTERRUPTED.value();
        cbi.type = type;
        cb.callback(cbi);
    }

    public static CallbackInfo<?> buildSqlExceptionCallbackInfo() {
        return buildErrorCallbackInfo(MopaErrno.LOCAL_SQLEXCEPTION.value());
    }

    public static void buildSqlExceptionCallbackInfo(CallbackInfo<?> callbackInfo) {
        buildErrorCallbackInfo(callbackInfo, MopaErrno.LOCAL_SQLEXCEPTION.value());
    }

    public static CallbackInfo<?> buildInvalidCallbackInfo() {
        return buildErrorCallbackInfo(MopaErrno.LOCAL_INVAILD_OPER.value());
    }

    public static void buildInvalidCallbackInfo(CallbackInfo<?> callbackInfo) {
        buildErrorCallbackInfo(callbackInfo, MopaErrno.LOCAL_INVAILD_OPER.value());
    }

    public static CallbackInfo<?> buildJniCallbackInfo() {
        return buildErrorCallbackInfo(MopaErrno.LOCAL_JNI_OBJECT_PARSE_ERROR.value());
    }

    public static CallbackInfo<?> buildThreadInterruptedCallbackInfo() {
        return buildErrorCallbackInfo(MopaErrno.LOCAL_THREAD_INTERRUPTED.value());
    }

    public static void buildThreadInterruptedCallbackInfo(CallbackInfo<?> callbackInfo) {
        buildErrorCallbackInfo(callbackInfo, MopaErrno.LOCAL_THREAD_INTERRUPTED.value());
    }

    public static <T> void buildErrorCallbackInfo(CallbackInfo<?> result, CallbackInfo<T> info) {
        result.bError = true;
        result.errorCode = info.errorCode;
    }
}
