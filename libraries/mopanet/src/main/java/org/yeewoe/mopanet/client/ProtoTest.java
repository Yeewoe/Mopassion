package org.yeewoe.mopanet.client;

import org.yeewoe.mopanet.net.ClientTunnel;
import org.yeewoe.mopanet.net.IReconnectHandle;
import org.yeewoe.mopanet.net.Result;
import org.yeewoe.mopanet.protos.PBAthAlgorithm;
import org.yeewoe.mopanet.protos.PBEntryTimeSwapReq;
import org.yeewoe.mopanet.protos.PBEntryTimeSwapRsp;
import org.yeewoe.mopanet.protos.PBImMsg;
import org.yeewoe.mopanet.protos.PBImMsgClass;
import org.yeewoe.mopanet.protos.PBImMsgContentType;
import org.yeewoe.mopanet.protos.PBImPostReq;
import org.yeewoe.mopanet.protos.PBImPostRsp;
import org.yeewoe.mopanet.protos.PBImSyncReq;
import org.yeewoe.mopanet.protos.PBImSyncRsp;
import org.yeewoe.mopanet.protos.PBLocNearUserReq;
import org.yeewoe.mopanet.protos.PBLocNearUserRsp;
import org.yeewoe.mopanet.protos.PBLocReportReq;
import org.yeewoe.mopanet.protos.PBLocReportRsp;
import org.yeewoe.mopanet.protos.PBLocUser;
import org.yeewoe.mopanet.protos.PBMapPostion;
import org.yeewoe.mopanet.protos.PBMedia;
import org.yeewoe.mopanet.protos.PBMediaType;
import org.yeewoe.mopanet.protos.PBTrdAddReq;
import org.yeewoe.mopanet.protos.PBTrdAddRsp;
import org.yeewoe.mopanet.protos.PBTrdFollowListReq;
import org.yeewoe.mopanet.protos.PBTrdFollowListRsp;
import org.yeewoe.mopanet.protos.PBTrdGetReq;
import org.yeewoe.mopanet.protos.PBTrdGetRsp;
import org.yeewoe.mopanet.protos.PBTrdListUserLastReq;
import org.yeewoe.mopanet.protos.PBTrdListUserLastRsp;
import org.yeewoe.mopanet.protos.PBTrdNearReq;
import org.yeewoe.mopanet.protos.PBTrdNearRsp;
import org.yeewoe.mopanet.protos.PBTrdPullUserReq;
import org.yeewoe.mopanet.protos.PBTrdPullUserRsp;
import org.yeewoe.mopanet.protos.PBTrdRemoveReq;
import org.yeewoe.mopanet.protos.PBTrdRemoveRsp;
import org.yeewoe.mopanet.protos.PBTrends;
import org.yeewoe.mopanet.protos.PBUser;
import org.yeewoe.mopanet.protos.PBUsrAuthReq;
import org.yeewoe.mopanet.protos.PBUsrAuthRsp;
import org.yeewoe.mopanet.protos.PBUsrBatchGetReq;
import org.yeewoe.mopanet.protos.PBUsrBatchGetRsp;
import org.yeewoe.mopanet.protos.PBUsrCreateReq;
import org.yeewoe.mopanet.protos.PBUsrCreateRsp;
import org.yeewoe.mopanet.protos.PBUsrFansListReq;
import org.yeewoe.mopanet.protos.PBUsrFansListRsp;
import org.yeewoe.mopanet.protos.PBUsrLoginReq;
import org.yeewoe.mopanet.protos.PBUsrLoginRsp;
import org.yeewoe.mopanet.protos.PBUsrLogoutReq;
import org.yeewoe.mopanet.protos.PBUsrLogoutRsp;
import org.yeewoe.mopanet.protos.PBUsrTicketReq;
import org.yeewoe.mopanet.protos.PBUsrTicketRsp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;

import okio.ByteString;


public class ProtoTest {
    static Client client = new Client();
    static ClientTunnel tn = new ClientTunnel();
    public static String username = "zjmtest37";
    public static String password = username + "\0";
    static Result r = new Result(); //ģ��ͬ���ӿڡ�
    public static final String sendprefix = ">>>>>";
    public static final String recvprefix = "<<<<<";

    public static void changeUserName(String name) {
        username = name;
        password = username + "\0";
    }

    public static void ticketLogin() throws IOException, NoSuchAlgorithmException {

        //������ticket���ٵ�¼
        byte[] ticket = client.usrAuth.ticket.toByteArray();
        int seed = (int) ((new Date().getTime()) / 1000); //���ֵ����
        Formatter fm = new Formatter();
        fm.format("%d", seed);
        String seedstr = fm.toString();
        byte[] seedbytes = seedstr.getBytes("UTF8");
        byte[] buff = new byte[seedbytes.length + ticket.length];

        System.out.println(seedstr + " len " + seedbytes.length);
        System.arraycopy(ticket, 0, buff, 0, ticket.length);
        System.arraycopy(seedbytes, 0, buff, ticket.length, seedbytes.length);
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] buffmd5 = md.digest(buff);
        ByteString md5 = ByteString.of(buffmd5);
        PBUsrTicketReq utreq = new PBUsrTicketReq.Builder().uid(client.uid).
                algo(PBAthAlgorithm.ATHALGO_MD5.getValue()).
                seed(seedstr).
                verify(md5).
                build();
        System.out.println(sendprefix + utreq);
        tn.sendSync(MopaNetConstants.SRVOP_USR_TICKET_REQ, 0, PBUsrTicketReq.ADAPTER.encode(utreq), r);
        PBUsrTicketRsp usrTicketRsp = PBUsrTicketRsp.ADAPTER.decode(r.response);
        if (usrTicketRsp.result != null && usrTicketRsp.result != 0)
            System.out.println("Tickt error " + MopaErrno.parse(usrTicketRsp.result));
        System.out.println(recvprefix + usrTicketRsp);
    }

    public static void logout() throws IOException {
        //��¼�ɹ����˳���¼
        PBUsrLogoutReq uLogoutReq = new PBUsrLogoutReq.Builder().uid(client.uid).build();
        System.out.println(sendprefix + uLogoutReq);
        tn.sendSync(MopaNetConstants.SRVOP_USR_LOGOUT_REQ, 0, PBUsrLogoutReq.ADAPTER.encode(uLogoutReq), r);
        PBUsrLogoutRsp rsp = PBUsrLogoutRsp.ADAPTER.decode(r.response);
        System.out.println(recvprefix + rsp);
    }

    public static void sendIm() throws UnsupportedEncodingException,
            IOException {
        //����IM��Ϣ
        PBImMsg imMsg = new PBImMsg.Builder().
                imsgid(0L). //���ͷ��������
                from_uid(client.uid).
                to_uid(1L).
                imsgctype(PBImMsgContentType.IMCT_TEXT.getValue()). //TODO ����θ
                imclass(PBImMsgClass.IMCLSS_U2U.getValue()).
                imsg(ByteString.of(("hello ����" + username).getBytes("UTF8"))).build();
        PBImPostReq imPostReq = new PBImPostReq.Builder().imsg(imMsg).build();
        System.out.println(sendprefix + imPostReq);
        tn.sendSync(MopaNetConstants.SRVOP_IM_POST_MSG_REQ, 0, PBImPostReq.ADAPTER.encode(imPostReq), r);
        PBImPostRsp imPostRsp = PBImPostRsp.ADAPTER.decode(r.response);
        System.out.println(recvprefix + imPostRsp);
    }

    public static void syncIm() throws IOException {
        //��¼�ɹ���ͬ������������Ϣ
        PBImSyncReq imSyncReq = new PBImSyncReq.Builder().count(10).start_imsgid(0L).build();
        System.out.println(sendprefix + imSyncReq);
        tn.sendSync(MopaNetConstants.SRVOP_IM_SYNC_REQ, 0, PBImSyncReq.ADAPTER.encode(imSyncReq), r);
        PBImSyncRsp rsp = PBImSyncRsp.ADAPTER.decode(r.response);


        for (PBImMsg msg : rsp.imsgs) {
            System.out.println("Sync IM msg : " + msg.imsg.utf8());
        }
        System.out.println(recvprefix + rsp);
    }

    public static void getUserInfo() throws IOException {
        //��ȡ�û���Ϣ�б�
        ArrayList<Long> uids = new ArrayList<Long>();
        uids.add(1L);
        uids.add(2L);
        uids.add(3L);
        PBUsrBatchGetReq usrBatchGetReq = new PBUsrBatchGetReq.Builder().uids(uids).build();
        System.out.println(sendprefix + usrBatchGetReq);
        tn.sendSync(MopaNetConstants.SRVOP_USR_BATCH_GET_REQ, 0, PBUsrBatchGetReq.ADAPTER.encode(usrBatchGetReq), r);
        PBUsrBatchGetRsp rsp = PBUsrBatchGetRsp.ADAPTER.decode(r.response);
        System.out.println(recvprefix + rsp);
    }

    //��¼
    public static void login() throws InterruptedException,
            NoSuchAlgorithmException, IOException {
        //����ʱ���������
        PBEntryTimeSwapReq req = new PBEntryTimeSwapReq.Builder().clt_time(System.currentTimeMillis() / 1000).build();
        System.out.println(sendprefix + req);
        tn.sendSync(MopaNetConstants.SRVOP_ENTRY_TIME_SWAP_REQ, 0, PBEntryTimeSwapReq.ADAPTER.encode(req), r);
        PBEntryTimeSwapRsp entryTimeSwapRsp = PBEntryTimeSwapRsp.ADAPTER.decode(r.response);
        System.out.println(recvprefix + entryTimeSwapRsp);


        System.out.println(password + " length " + password.length());
        //����ע���û���Ϣ
        ArrayList<PBUser> users = new ArrayList<PBUser>();
        users.add(new PBUser.Builder().uid(0L).account(username).name("zjm2").build());
        PBUsrCreateReq ucreq = new PBUsrCreateReq.Builder().users(users).build();
        System.out.println(sendprefix + ucreq);
        tn.sendSync(MopaNetConstants.SRVOP_USR_CREATE_REQ, 0, PBUsrCreateReq.ADAPTER.encode(ucreq), r);
        PBUsrCreateRsp ucRsp = PBUsrCreateRsp.ADAPTER.decode(r.response);
        System.out.println(recvprefix + ucRsp);


        //��¼�ӿ�
        PBUsrLoginReq ulreq = new PBUsrLoginReq.Builder().account(username).build();
        System.out.println(sendprefix + ulreq);
        tn.sendSync(MopaNetConstants.SRVOP_USR_LOGIN_REQ, 0, PBUsrLoginReq.ADAPTER.encode(ulreq), r);
        PBUsrLoginRsp uLoginRsp = PBUsrLoginRsp.ADAPTER.decode(r.response);
        System.out.println(recvprefix + uLoginRsp);
        client.usrLoginRsp = uLoginRsp;

        //��ս����
        byte[] passwd = password.getBytes("UTF8");
        byte[] seed = client.usrLoginRsp.challenge_seed.toByteArray();
        byte[] merge = new byte[passwd.length + seed.length];
        System.arraycopy(passwd, 0, merge, 0, passwd.length);
        System.arraycopy(seed, 0, merge, passwd.length, seed.length);
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] buff = md.digest(merge);
        ByteString md5 = ByteString.of(buff);

        PBUsrAuthReq uareq = new PBUsrAuthReq.Builder().challenge_response(md5).build();
        System.out.println(sendprefix + uareq);
        tn.sendSync(MopaNetConstants.SRVOP_USR_AUTH_REQ, 0, PBUsrAuthReq.ADAPTER.encode(uareq), r);

        PBUsrAuthRsp rsp = PBUsrAuthRsp.ADAPTER.decode(r.response);
        client.usrAuth = rsp;
        client.uid = rsp.uid;
        System.out.println(recvprefix + rsp);
    }


    //�ϱ�����λ��
    public static void reportLocation(double lon, double lat) throws IOException {
        PBMapPostion mapPostion = new PBMapPostion.Builder().addr("����").lat(lat).lon(lon).build();
        PBLocReportReq locReportReq = new PBLocReportReq.Builder().loc(mapPostion).build();
        System.out.println(sendprefix + locReportReq);
        tn.sendSync(MopaNetConstants.ASRVOP_LOCATION_REPORT_REQ, 0, PBLocReportReq.ADAPTER.encode(locReportReq), r);
        PBLocReportRsp rsp = PBLocReportRsp.ADAPTER.decode(r.response);
        System.out.println(recvprefix + rsp);
    }

    //��ȡ�����û�.
    public static void getNearUser(double lon, double lat) throws IOException {
        PBMapPostion mapPostion = new PBMapPostion.Builder().addr("����").lat(lat).lon(lon).build();
        PBLocNearUserReq req = new PBLocNearUserReq.Builder().center_loc(mapPostion)
                .min_distance(0L).max_distance(100000L).num(10).build();

        System.out.println(sendprefix + req);
        tn.sendSync(MopaNetConstants.ASRVOP_LOCATION_NEAR_USER_REQ, 0, PBLocNearUserReq.ADAPTER.encode(req), r);
        PBLocNearUserRsp rsp = PBLocNearUserRsp.ADAPTER.decode(r.response);
        if (rsp.result != null && rsp.result != 0)
            System.out.println("getNearUser aError: " + MopaErrno.parse(rsp.result));
        for (PBLocUser u : rsp.users) {
            System.out.println(" recv uid " + u.uid + " dist " + u.dist);
        }
        System.out.println(recvprefix + rsp);
    }

    //������̬
    public static long postTrend(double lon, double lat, String content) throws IOException {
        PBMedia media = new PBMedia.Builder().id(0L).content(ByteString.of(content.getBytes("UTF8"))).
                type(PBMediaType.PBMT_TXT.getValue()).size(1024L).build();
        ArrayList<PBMedia> medias = new ArrayList<PBMedia>();
        medias.add(media);

        PBMapPostion mapPostion = new PBMapPostion.Builder().addr("����").lat(lat).lon(lon).build();

        PBTrends trends = new PBTrends.Builder().id(0L).uid(client.uid).contents(medias).
                pubtime(new Date().getTime()).pubpostion(mapPostion).build();
        PBTrdAddReq req = new PBTrdAddReq.Builder().trend(trends).build();

        System.out.println(sendprefix + req);
        tn.sendSync(MopaNetConstants.SRVOP_TRENDS_ADD_REQ, 0, PBTrdAddReq.ADAPTER.encode(req), r);
        PBTrdAddRsp rsp = PBTrdAddRsp.ADAPTER.decode(r.response);
        if (rsp.result != null && rsp.result != 0)
            System.out.println("postTrend Error: " + MopaErrno.parse(rsp.result));

        System.out.println(recvprefix + rsp);
        return rsp.id;

    }

    //ɾ��̬.
    public static void removeTrend(long id) throws IOException {
        PBTrdRemoveReq req = new PBTrdRemoveReq.Builder().id(id).build();

        System.out.println(sendprefix + req);
        tn.sendSync(MopaNetConstants.SRVOP_TRENDS_REMOVE_REQ, 0, PBTrdRemoveReq.ADAPTER.encode(req), r);
        PBTrdRemoveRsp rsp = PBTrdRemoveRsp.ADAPTER.decode(r.response);
        if (rsp.result != null && rsp.result != 0)
            System.out.println("removeTrend Error: " + MopaErrno.parse(rsp.result));

        System.out.println(recvprefix + rsp);

    }

    //��ݶ�̬id��ȡ��̬.
    public static void getTrends(ArrayList<Long> ids) throws IOException {
        PBTrdGetReq req = new PBTrdGetReq.Builder().ids(ids).build();

        System.out.println(sendprefix + req);
        tn.sendSync(MopaNetConstants.SRVOP_TRENDS_GET_REQ, 0, PBTrdGetReq.ADAPTER.encode(req), r);
        PBTrdGetRsp rsp = PBTrdGetRsp.ADAPTER.decode(r.response);
        if (rsp.result != null && rsp.result != 0)
            System.out.println("getTrends Error: " + MopaErrno.parse(rsp.result));
        System.out.println(recvprefix + rsp);

    }

    //��ȡ�ƶ��û���̬
    public static void pullUserTrends(Long uid, long startid, long count)
            throws IOException {
        PBTrdPullUserReq req = new PBTrdPullUserReq.Builder().uid(uid).start_id(startid).count(count).build();

        System.out.println(sendprefix + req);
        tn.sendSync(MopaNetConstants.SRVOP_TRENDS_PULL_USER_REQ, 0, PBTrdPullUserReq.ADAPTER.encode(req), r);
        PBTrdPullUserRsp rsp = PBTrdPullUserRsp.ADAPTER.decode(r.response);
        if (rsp.result != null && rsp.result != 0)
            System.out.println("pullUserTrends Error: " + MopaErrno.parse(rsp.result));
        if (rsp.trends.size() > 0 && rsp.trends.get(0).contents.size() > 0)
            System.out.println(rsp.trends.get(0).contents.get(0).content.utf8());
        System.out.println(recvprefix + rsp);

    }


    //��ȡ��ע�û���̬
    public static void pullFollowUserTrends()
            throws IOException {
        PBTrdFollowListReq req = new PBTrdFollowListReq.Builder().start_id(0L).count(20L).build();

        System.out.println(sendprefix + req);
        tn.sendSync(MopaNetConstants.SRVOP_TRENDS_FOLLOW_LIST_REQ, 0, PBTrdFollowListReq.ADAPTER.encode(req), r);
        PBTrdFollowListRsp rsp = PBTrdFollowListRsp.ADAPTER.decode(r.response);
        if (rsp.result != null && rsp.result != 0)
            System.out.println("pullFollowUserTrends Error: " + MopaErrno.parse(rsp.result));
        System.out.println(recvprefix + rsp);

    }


    //��ȡ�û����¶�̬
    public static void pullUserLatestTrends(ArrayList<Long> uids)
            throws IOException {
        PBTrdListUserLastReq req = new PBTrdListUserLastReq.Builder().uids(uids).build();

        System.out.println(sendprefix + req);
        tn.sendSync(MopaNetConstants.SRVOP_TRENDS_LIST_USER_LAST_REQ, 0, PBTrdListUserLastReq.ADAPTER.encode(req), r);
        PBTrdListUserLastRsp rsp = PBTrdListUserLastRsp.ADAPTER.decode(r.response);
        if (rsp.result != null && rsp.result != 0)
            System.out.println("pullUserLatestTrends Error: " + MopaErrno.parse(rsp.result));
        System.out.println(recvprefix + rsp);

    }


    //��ȡ�������¶�̬
    public static void pullNearTrends(double lon, double lat)
            throws IOException {
        PBMapPostion mapPostion = new PBMapPostion.Builder().addr("����").lat(lat).lon(lon).build();
        PBTrdNearReq req = new PBTrdNearReq.Builder().center_loc(mapPostion).min_distance(0L).
                max_distance(10000000L).num(20).build();

        System.out.println(sendprefix + req);
        tn.sendSync(MopaNetConstants.SRVOP_TRENDS_NEAR_REQ, 0, PBTrdNearReq.ADAPTER.encode(req), r);
        PBTrdNearRsp rsp = PBTrdNearRsp.ADAPTER.decode(r.response);
        if (rsp.result != null && rsp.result != 0)
            System.out.println("pullNearTrends Error: " + MopaErrno.parse(rsp.result));
        System.out.println(recvprefix + rsp);

    }

    //�����ʼ��.
    public static void initNetwork() {
        client.tunnel = tn;
        ClientMessageHandle msgHandle = new ClientMessageHandle();
        msgHandle.setClient(client);
        System.out.println("connect status " + tn.isConnected());
        if (!tn.isConnected()) {

            tn.init(msgHandle, new IReconnectHandle() {

                @Override
                public void OnReconnectOK() {
                    // TODO Auto-generated method stub
                    try {
                        ProtoTest.login();	//重新登录
                    } catch (Exception e) {
                        // TODO: handle exception
                        System.out.println("OnReconnectOK login exception " + e);
                    }

                }

                @Override
                public void OnReconnectFailed() {
                    // TODO Auto-generated method stub
                    System.out.println("OnReconnect Failed ");
                }
            });
        }

        System.out.println("begin connect");
        tn.awaitConnect();
        System.out.println("connect ok, begin send");
    }

    //������������ͨ��״̬.
    public static void Disconnect() {
        tn.close();
        tn.clear();
    }

    //获取关注用户列表
    public static void getFansList() throws IOException
    {

        PBUsrFansListReq req = new PBUsrFansListReq.Builder().skip(0).limit(10).build();
        System.out.println(sendprefix+ req);
        tn.sendSync(MopaNetConstants.SRVOP_USR_FANS_LIST_REQ, 0, PBUsrFansListReq.ADAPTER.encode(req),r);
        PBUsrFansListRsp rsp = PBUsrFansListRsp.ADAPTER.decode(r.response);
        if(rsp.result != null && rsp.result != 0)
            System.out.println("getFansList Error: " + MopaErrno.parse(rsp.result));
        System.out.println(recvprefix+ rsp);
    }

}
