package org.yeewoe.mopassion.app.file.service;

import org.yeewoe.mopanet.callback.Callback;
import org.yeewoe.mopanet.client.MopaNetConstants;
import org.yeewoe.mopanet.protos.PBFileDownloadInfo;
import org.yeewoe.mopanet.protos.PBFileDownloadReq;
import org.yeewoe.mopanet.protos.PBFileDownloadRsp;
import org.yeewoe.mopanet.protos.PBFileUploadInfo;
import org.yeewoe.mopanet.protos.PBFileUploadReq;
import org.yeewoe.mopanet.protos.PBFileUploadRsp;
import org.yeewoe.mopanet.template.NetSendRequestTemplate;

import java.io.IOException;
import java.util.ArrayList;

/**
 * 文件相关网络接口
 * Created by wyw on 2016/4/21.
 */
public class FileProtobufNet {
    public static  void fileUpload(final ArrayList<PBFileUploadInfo> infos, Callback callback) {
        NetSendRequestTemplate template = new NetSendRequestTemplate() {
            @Override
            public byte[] setRequestParams() {
                PBFileUploadReq req = new PBFileUploadReq.Builder().infos(infos).build();
                return PBFileUploadReq.ADAPTER.encode(req);
            }

            @Override
            public void handleResponse(byte[] bytes, Callback callback) throws IOException {
                PBFileUploadRsp rsp = PBFileUploadRsp.ADAPTER.decode(bytes);
                doCallback(callback, rsp, rsp.result);
            }
        };
        template.sendRequest(MopaNetConstants.SRVOP_FILE_UPLOAD_REQ, callback);
    }

    public static  void fileDownload(final ArrayList<PBFileDownloadInfo> infos, Callback callback) {
        NetSendRequestTemplate template = new NetSendRequestTemplate() {
            @Override
            public byte[] setRequestParams() {
                PBFileDownloadReq req = new PBFileDownloadReq.Builder().infos(infos).build();
                return PBFileDownloadReq.ADAPTER.encode(req);
            }

            @Override
            public void handleResponse(byte[] bytes, Callback callback) throws IOException {
                PBFileDownloadRsp rsp = PBFileDownloadRsp.ADAPTER.decode(bytes);
                doCallback(callback, rsp, rsp.result);
            }
        };
        template.sendRequest(MopaNetConstants.SRVOP_FILE_DOWNLOAD_REQ, callback);
    }
}
