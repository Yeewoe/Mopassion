package org.yeewoe.mopanet.client;

import org.yeewoe.mopanet.net.ClientTunnel;
import org.yeewoe.mopanet.protos.PBUsrAuthRsp;
import org.yeewoe.mopanet.protos.PBUsrLoginRsp;
import android.os.Handler;

public class Client {
    public long uid = 0;
    public PBUsrLoginRsp usrLoginRsp;
    public PBUsrAuthRsp usrAuth;
    public ClientTunnel tunnel = null;
    public Handler h = null;



}
