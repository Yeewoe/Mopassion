package org.yeewoe.mopanet.client;

import java.io.IOException;
import java.lang.reflect.Method;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Main {

    //������ע����Ҫ���Եķ���.
    static String[] methods = new String[]{
            "userTest", //��IM���ܲ���.
            "ticketTest", //ticket���ܲ���.
            "locationTest", //�ϱ�����λ�á�
            "trendTest", //��̬�ӿڲ���.
    };

    public static void main(String[] args) throws Throwable {

        ProtoTest.initNetwork();

        try {
            for (String name : methods) {
                Method m = Main.class.getMethod(name);
                System.out.println("###############################################################################################");
                System.out.println("===================>Start " + name);
                m.invoke(null);
                System.out.println("<===================Finish " + name);
            }


        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }


    }

    //���ܲ���.
    public static void userTest() throws NoSuchAlgorithmException, InterruptedException, IOException {
        ProtoTest.login();
        ProtoTest.syncIm();
        ProtoTest.getUserInfo();
        ProtoTest.sendIm();
    }

    //ticket����
    public static void ticketTest() throws NoSuchAlgorithmException, InterruptedException, IOException {
        ProtoTest.login();
        ProtoTest.syncIm();
        ProtoTest.getUserInfo();
        ProtoTest.sendIm();
        ProtoTest.Disconnect();
        ProtoTest.initNetwork();
        ProtoTest.ticketLogin();
        ProtoTest.getUserInfo();
    }

    //����λ�ò���
    public static void locationTest() throws NoSuchAlgorithmException, InterruptedException, IOException {
        //��¼��
        String olduser = ProtoTest.username;
        ProtoTest.login();
        ProtoTest.reportLocation(20, 20);
        //���û������ϱ�λ��
        ProtoTest.Disconnect();
        ProtoTest.initNetwork();
        ProtoTest.changeUserName("maintest0");
        ProtoTest.login();
        ProtoTest.reportLocation(21, 22);
        //���û������ϱ�λ��
        ProtoTest.Disconnect();
        ProtoTest.initNetwork();
        ProtoTest.changeUserName("maintest1");
        ProtoTest.login();
        ProtoTest.reportLocation(22, 22);
        //���û������ϱ�λ��
        ProtoTest.Disconnect();
        ProtoTest.initNetwork();
        ProtoTest.changeUserName("maintest2");
        ProtoTest.login();
        ProtoTest.reportLocation(22, 23);

        //��ȡ�����û���Ϣ
        ProtoTest.getNearUser(22.1, 23.1);

        ProtoTest.changeUserName(olduser);

    }

    //���Զ�̬.
    public static void trendTest() throws NoSuchAlgorithmException, InterruptedException, IOException {
        //��¼.
        ProtoTest.login();

        //����һ����̬��
        long id = ProtoTest.postTrend(0, 0, "��һ����̬");
        ArrayList<Long> ids = new ArrayList<Long>();
        ids.add(id);
        ProtoTest.getTrends(ids);

        //��ȡ�ƶ��û���̬.
        ProtoTest.pullUserTrends(ProtoTest.client.uid, 0, 10);

        //��ȡ��ע�û���̬.
        ProtoTest.pullFollowUserTrends();

        //����Ķ�̬.
        ProtoTest.pullNearTrends(0, 0);

        //��ȡ�ƶ��û������¶�̬
        ArrayList<Long> uids = new ArrayList<Long>();
        uids.add(ProtoTest.client.uid);
        ProtoTest.pullUserLatestTrends(uids); //���¶�̬��

        //��ȡ�ƶ��û���̬
        ProtoTest.pullUserTrends(ProtoTest.client.uid, 0, 100);


    }

    //TODO �½����Է���.


}
