package org.yeewoe.mopanet.client;
import org.yeewoe.mopanet.net.IMessageHandle;
import org.yeewoe.mopanet.net.message.BasicMessage;
import org.yeewoe.mopanet.protos.PBEntryTimeSwapRsp;
import org.yeewoe.mopanet.protos.PBImMsg;
import org.yeewoe.mopanet.protos.PBImPostReq;
import org.yeewoe.mopanet.protos.PBImPostRsp;
import org.yeewoe.mopanet.protos.PBImSyncRsp;
import org.yeewoe.mopanet.protos.PBUsrAuthRsp;
import org.yeewoe.mopanet.protos.PBUsrBatchGetRsp;
import org.yeewoe.mopanet.protos.PBUsrCreateRsp;
import org.yeewoe.mopanet.protos.PBUsrLoginRsp;
import org.yeewoe.mopanet.protos.PBUsrLogoutRsp;
import org.yeewoe.mopanet.protos.PBUsrTicketRsp;
import android.os.Handler;
import android.os.Message;
import java.io.IOException;

public class ClientMessageHandle implements IMessageHandle {
	
	Client client = null;
	public void setClient(Client client) {
		this.client = client;
	}
	public Client getClient() {
		return client;
	}


	
	@Override
	public void OnMessage(BasicMessage m) {
		// TODO Auto-generated method stub
		System.out.println("ClientMessageHandle route " + m.route + "Constants.SRVOP_IM_POST_MSG_REQ " + MopaNetConstants.SRVOP_IM_POST_MSG_REQ);
		try {
			int srvop = m.route.srvop;
			Object ret = null;
			if(srvop == MopaNetConstants.SRVOP_IM_POST_MSG_REQ) //这里处理推送消息。
			{
				ret = processImPostReqMsg(m);
				OnServerPush(srvop, ret);

			}
			else //这里处理服务器回应.
			{
				client.tunnel.onResponse(m);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	}

	//向UI主线程发出消息.
	void OnServerPush(int srvop, Object push)	{
		Message msg = client.h.obtainMessage();
		msg.what = srvop;
		msg.obj = push;
		client.h.sendMessage(msg);
	}
	//�����������ʱ��
	void processEntryTimeSwap(BasicMessage m) throws IOException
	{	
		PBEntryTimeSwapRsp rsp   = PBEntryTimeSwapRsp.ADAPTER.decode(m.payload);
		System.out.println(rsp);
	}
	
	//��Ӧ�û�����.
	void processUsrCreate(BasicMessage m) throws IOException
	{	
		PBUsrCreateRsp rsp   = PBUsrCreateRsp.ADAPTER.decode(m.payload);
		System.out.println(rsp);
	}
	
	//��Ӧ�û���¼
	void processUsrLogin(BasicMessage m) throws IOException
	{
		PBUsrLoginRsp rsp = PBUsrLoginRsp.ADAPTER.decode(m.payload);
		System.out.println(rsp);
		client.usrLoginRsp = rsp;
		
	}
	
	//��Ӧ�û���ս��Ϣ
	void processUsrAuth(BasicMessage m) throws IOException
	{
		PBUsrAuthRsp rsp = PBUsrAuthRsp.ADAPTER.decode(m.payload);
		client.usrAuth = rsp;
		client.uid = rsp.uid;
		System.out.println(rsp);
		
	}
	
	//��Ӧ�û��ǳ�
	void processLogout(BasicMessage m) throws IOException
	{
		PBUsrLogoutRsp rsp = PBUsrLogoutRsp.ADAPTER.decode(m.payload);
		System.out.println(rsp);
		
	}
	
	//��ӦTicket
	void processTicket(BasicMessage m) throws IOException
	{
		PBUsrTicketRsp rsp = PBUsrTicketRsp.ADAPTER.decode(m.payload);
		System.out.println(rsp);		
	}
	
	//��Ӧ������ȡ�û���Ϣ
	void processUsrBatchGet(BasicMessage m) throws IOException
	{
		PBUsrBatchGetRsp rsp = PBUsrBatchGetRsp.ADAPTER.decode(m.payload);
		System.out.println(rsp);		
	}

	
	//��Ӧ�ͻ��˷�����Ϣ
	void processImPostMsg(BasicMessage m) throws IOException
	{
		PBImPostRsp rsp = PBImPostRsp.ADAPTER.decode(m.payload);
		System.out.println(rsp);		
	}
	
	//�յ��ͻ�������
	Object processImPostReqMsg(BasicMessage m) throws IOException
	{
		PBImPostReq req = PBImPostReq.ADAPTER.decode(m.payload);
		System.out.println(req + " contents is " + req.imsg.imsg.utf8());	
		
		
		//��Ӧһ��
		PBImPostRsp rsp = new PBImPostRsp.Builder().ack_imsgid(req.imsg.imsgid).result(0).build();
		client.tunnel.send(MopaNetConstants.SRVOP_IM_POST_MSG_RSP, 0, PBImPostRsp.ADAPTER.encode(rsp));
		return req;
		
	}
	
	
	
	//��Ӧ��Ϣͬ��
	void processImSync(BasicMessage m) throws IOException
	{
		PBImSyncRsp rsp = PBImSyncRsp.ADAPTER.decode(m.payload);
		
		for(PBImMsg msg : rsp.imsgs)
		{
			System.out.println("Receive IM : " + msg.imsg.utf8());
		}
		System.out.println(rsp);
	}

} 
