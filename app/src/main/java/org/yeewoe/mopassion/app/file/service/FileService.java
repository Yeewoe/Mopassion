package org.yeewoe.mopassion.app.file.service;

import android.support.annotation.NonNull;

import org.yeewoe.commonutils.common.assist.Checks;
import org.yeewoe.commonutils.common.io.FileUtils;
import org.yeewoe.commonutils.common.utils.MD5Util;
import org.yeewoe.mopanet.callback.Callback;
import org.yeewoe.mopanet.callback.CallbackUtils;
import org.yeewoe.mopanet.client.MopaErrno;
import org.yeewoe.mopanet.protos.PBFileDownloadInfo;
import org.yeewoe.mopanet.protos.PBFileDownloadRsp;
import org.yeewoe.mopanet.protos.PBFileUploadInfo;
import org.yeewoe.mopanet.protos.PBFileUploadResult;
import org.yeewoe.mopanet.protos.PBFileUploadRsp;
import org.yeewoe.mopassion.app.common.service.BaseService;
import org.yeewoe.mopassion.app.file.model.UploadCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件相关业务接口
 * Created by wyw on 2016/4/21.
 */
public class FileService extends BaseService {
    private static final String CLASS_NAME = "FileService";
    private static final long SEVEN_DAY = 7 * 24 * 3600;
    public static final String NULL = "NULL";
    private final QiniuService qiniuService;

    public FileService() {
        qiniuService = new QiniuService();
    }

    /**
     * 上传文件接口，内部调用了七牛上传逻辑
     * @param file 文件
     * @param callback 主线程完成回调
     */
    public void upload(@NonNull final File file, final UploadCallback callback) {
        final String METHOD_NAME = "upload";
        logInterfaceCall(CLASS_NAME, METHOD_NAME, "file=" + file);
        final String hash = MD5Util.md5Str(file);
        final long size = FileUtils.sizeOf(file);
        if (Checks.check(hash)) {
            runOnUploadFileTemplate(new Runnable() {
                @Override public void run() {
                    ArrayList<PBFileUploadInfo> infos = new ArrayList<>();
                    infos.add(new PBFileUploadInfo.Builder().hash(hash).e_time(SEVEN_DAY).size(size).build());
                    FileProtobufNet.fileUpload(infos, new Callback() {
                        @Override public <T> void callback(CallbackInfo<T> info) {
                            if (info.bError) {
                                callback.fail(hash, info.errorCode);
                                return;
                            }
                            PBFileUploadRsp rsp = (PBFileUploadRsp) info.mT;
                            List<PBFileUploadResult> results = rsp.results;
                            if (Checks.check(results)) {
                                String token = results.get(0).token;
                                qiniuService.upload(file, hash, token, callback);
                            } else {
                                callback.fail(hash, MopaErrno.LOCAL_FILE_UPLOAD_COMMON_ERROR.ordinal());
                            }
                        }
                    });
                }
            }, new Callback() {
                @Override public <T> void callback(CallbackInfo<T> info) {
                    if (info.bError) {
                        callback.fail(hash, info.errorCode);
                    }
                }
            });
        }

    }

    /**
     * 获取下载url
     * @param hash 文件hash
     * @param fop 文件保存类型
     * @param callback
     */
    public void getDownloadUrl(final String hash, final String fop, final Callback callback) {
        final String METHOD_NAME = "download";
        logInterfaceCall(CLASS_NAME, METHOD_NAME, "hash=" + hash);
        if (!Checks.check(hash)) {
            CallbackUtils.invalidCallback(callback);
            return ;
        }

        runOnDownloadFileTemplate(new Runnable() {
            @Override public void run() {
                ArrayList<PBFileDownloadInfo> infos = new ArrayList<>();
                infos.add(new PBFileDownloadInfo.Builder().hash(hash).e_time(SEVEN_DAY).build());
                FileProtobufNet.fileDownload(infos, new Callback() {
                    @Override public <T> void callback(CallbackInfo<T> info) {
                        if (info.bError) {
                            CallbackUtils.errorCallback(callback, info.errorCode);
                            return ;
                        }

                        PBFileDownloadRsp rsp = (PBFileDownloadRsp) info.mT;
                        if (Checks.check(rsp.results)) {
                            logRun(CLASS_NAME, METHOD_NAME, "url=" + rsp.results.get(0).url);
                            CallbackUtils.callbackInfoObject(callback, rsp.results.get(0).url);
                        } else {
                            CallbackUtils.errorCallback(callback, MopaErrno.LOCAL_FILE_DOWNLOAD_COMMON_ERROR.ordinal());
                        }
                    }
                });
            }
        }, callback);
    }
}
