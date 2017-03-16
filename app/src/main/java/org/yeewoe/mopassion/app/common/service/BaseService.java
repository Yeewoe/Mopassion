package org.yeewoe.mopassion.app.common.service;

import org.yeewoe.commonutils.common.test.TestTimeUtils;
import org.yeewoe.mopanet.callback.Callback;
import org.yeewoe.mopanet.callback.CallbackUtils;
import org.yeewoe.mopanet.template.ProtobufNetTemplate;
import org.yeewoe.mopassion.BuildConfig;
import org.yeewoe.mopassion.utils.LogCore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 业务接口基类
 * Created by wyw on 2016/1/13.
 */
public abstract class BaseService {

    private static ExecutorService threadExecutor;

    public BaseService() {
    }

    /**
     * callback的空验证处理
     */
    protected static boolean nullCallback(Callback callback) {
        if (callback == null) {
            if (BuildConfig.DEBUG) {
                throw new IllegalArgumentException("callback 不能为空");
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 日志记录，接口调用时使用
     */
    protected static void logInterfaceCall(String ClassName, String methodName, String param) {
        LogCore.interfaceI(buildLogTag(ClassName, methodName) + "接口调用, " + param);
        TestTimeUtils.getInstance(BuildConfig.DEBUG).tag();
    }

    protected static void logInterfaceReturn(String ClassName, String methodName, Object result) {
        if (BuildConfig.DEBUG) {
            LogCore.interfaceI(buildLogTag(ClassName, methodName) + "接口结束，耗时=" + TestTimeUtils.getInstance(BuildConfig.DEBUG).getTik() + "， 结果=" + result);
        }
    }

    /**
     * 日志记录，运行时使用
     */
    protected static void logRun(String ClassName, String methodName, String param) {
        LogCore.interfaceI(buildLogTag(ClassName, methodName) + "运行中, " + param);
    }



    /**
     * 日志记录，回调成功时使用
     */
    protected static void logCallbackSuccess(String ClassName, String methodName) {
        LogCore.interfaceI(buildLogTag(ClassName, methodName) + "回调成功");
    }

    /**
     * 日志记录，数据库异常时使用
     */
    protected static void logSqlError(String ClassName, String methodName, String sqlMethodName, Exception e) {
        e.printStackTrace();
        LogCore.interfaceI(buildLogTag(ClassName, methodName) + "sql操作异常, 方法为： " + sqlMethodName, e);
    }

    /**
     * 日志记录，pb异常时使用
     */
    protected static void logPbError(String ClassName, String methodName, String pbMethodName, Exception e) {
        e.printStackTrace();
        LogCore.interfaceI(buildLogTag(ClassName, methodName) + "pb操作异常, 方法为： " + pbMethodName, e);
    }


    /**
     * 日志记录，未知异常时使用
     */
    protected static void logUnknown(Exception e) {
        e.printStackTrace();
        LogCore.interfaceI("未知错误", e);
    }

    /**
     * 日志记录，网络请求失败时使用
     */
    protected static void logNetResult(String ClassName, String methodName, String pbMethodName, Callback.CallbackInfo callbackInfo) {
        LogCore.interfaceI(buildLogTag(ClassName, methodName) + "网络接口回调, 方法为： " + pbMethodName + ", 结果：" + callbackInfo);
    }

    /**
     * ProtobufNetTemplate启动
     */
    protected static void runOnNetTemplate(final Runnable runnable, final Callback callback) {
        ProtobufNetTemplate template = new ProtobufNetTemplate() {
            @Override
            public void sendRequest() {
                try {
                    runnable.run();
                } catch (Exception e) {
                    logUnknown(e);
                    if (callback != null) {
                        CallbackUtils.threadInterrupted(callback);
                    }
                }
            }
        };
        template.startThread();
    }


    /**
     * ProtobufNetTemplate启动上传文件处理线程
     */
    protected static void runOnUploadFileTemplate(final Runnable runnable, final Callback callback) {
        ProtobufNetTemplate template = new ProtobufNetTemplate() {
            @Override
            public void sendRequest() {
                try {
                    runnable.run();
                } catch (Exception e) {
                    logUnknown(e);
                    if (callback != null) {
                        CallbackUtils.threadInterrupted(callback);
                    }
                }
            }
        };
        template.startUploadThread(callback);
    }


    /**
     * ProtobufNetTemplate启动下载文件处理线程
     */
    protected static void runOnDownloadFileTemplate(final Runnable runnable, final Callback callback) {
        ProtobufNetTemplate template = new ProtobufNetTemplate() {
            @Override
            public void sendRequest() {
                try {
                    runnable.run();
                } catch (Exception e) {
                    logUnknown(e);
                    if (callback != null) {
                        CallbackUtils.threadInterrupted(callback);
                    }
                }
            }
        };
        template.startDownloadThread(callback);
    }

    /**
     * ProtobufNetTemplate启动Im处理线程
     */
    protected static void runOnImTemplate(final Runnable runnable, final Callback callback) {
        ProtobufNetTemplate template = new ProtobufNetTemplate() {
            @Override
            public void sendRequest() {
                try {
                    runnable.run();
                } catch (Exception e) {
                    logUnknown(e);
                    if (callback != null) {
                        CallbackUtils.threadInterrupted(callback);
                    }
                }
            }
        };
        template.startImThread();
    }

    /**
     * 新Thread启动
     */
    protected static void runOnThread(final Runnable runnable, final Callback callback) {
        if (threadExecutor == null || threadExecutor.isShutdown()) {
            threadExecutor = Executors.newFixedThreadPool(2);
        }
        threadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    runnable.run();
                } catch (Exception e) {
                    logUnknown(e);
                    if (callback != null) {
                        CallbackUtils.threadInterrupted(callback);
                    }
                }
            }
        });
    }

    private static String buildLogTag(String ClassName, String methodName) {
        return "[" + ClassName + "." + methodName + "]";
    }
}
