//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.yeewoe.mopanet.client;

import java.io.ByteArrayInputStream;

public class MopaResult {
    public ByteArrayInputStream objectBytes = null;
    public int tunnel;
    public boolean isLocal;
    public short localOperType;
    public short serverType;
    public int operType;

    public MopaResult() {
    }

    public boolean addRecord(byte[] buf) {
        if(null == buf) {
            System.out.println("buf==null");
            return false;
        } else {
            System.out.println("addRecord. buf size:" + buf.length);
            this.objectBytes = new ByteArrayInputStream(buf);
            return true;
        }
    }

    public void setOperInfo(int tunn, boolean islocal, short localop, short srv, int op) {
        this.tunnel = tunn;
        this.isLocal = islocal;
        this.localOperType = localop;
        this.serverType = srv;
        this.operType = op;
    }
}
