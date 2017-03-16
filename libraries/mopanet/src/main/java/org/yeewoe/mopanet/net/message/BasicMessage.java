package org.yeewoe.mopanet.net.message;

import org.yeewoe.mopanet.protos.PBRoute;

public class BasicMessage {
    public BasicMessage rsp; //��Ӧ
    public int len;
    public short mark;
    public short flag;

    public int rlen;
    public PBRoute route;

    public byte[] payload;

    public BasicMessage() {
        super();
        // TODO Auto-generated constructor stub
    }
}
