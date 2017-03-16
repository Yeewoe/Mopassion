package org.yeewoe.mopanet.net.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.yeewoe.mopanet.client.MopaNetConstants;
import org.yeewoe.mopanet.net.message.BasicMessage;
import org.yeewoe.mopanet.protos.PBRoute;

public class MessageDecoder extends CumulativeProtocolDecoder {

    public MessageDecoder() {
        super();
        // TODO Auto-generated constructor stub
    }

    public static void printHexString(byte[] b) {
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            System.out.print(hex.toUpperCase() + " ");
        }
        System.out.println("");
    }

    @Override
    protected boolean doDecode(IoSession session, IoBuffer in,
                               ProtocolDecoderOutput out) throws Exception {
        // TODO Auto-generated method stub

        if (in.remaining() < MopaNetConstants.PK_HEADER_LEN)
            return false;
        int oldpos = in.position();
        int oldlimit = in.limit();

        BasicMessage bm = new BasicMessage();
        byte[] temp = new byte[2];

        in.limit(oldpos + 2);
        in.get(temp);
        bm.mark = NumConverter.byteArrayToShort(temp);


        in.limit(oldpos + 4);
        in.get(temp);
        bm.flag = NumConverter.byteArrayToShort(temp);


        temp = new byte[4];
        in.limit(oldpos + 8);
        in.get(temp);
        int total_len = NumConverter.byteArrayToInt(temp);
        System.out.println("MessageDecoder.decode total_len " + total_len + " oldlimit " + oldlimit);
        in.limit(oldlimit);
        if (in.remaining() < total_len - MopaNetConstants.PK_HEADER_LEN) {
            in.position(oldpos);
            in.limit(oldlimit);
            return false;
        } else {
            in.limit(oldpos + 12);
            temp = new byte[4];
            in.get(temp);
            bm.rlen = NumConverter.byteArrayToInt(temp);


            in.limit(oldpos + 12 + bm.rlen);
            temp = new byte[bm.rlen];
            in.get(temp);
            bm.route = PBRoute.ADAPTER.decode(temp);


            in.limit(oldpos + total_len);
            temp = new byte[total_len - 12 - bm.rlen];
            in.get(temp);
            bm.payload = temp;
            in.limit(oldlimit);

            out.write(bm);
            return true;
        }

    }


    @Override
    public void dispose(IoSession session) throws Exception {
        // TODO Auto-generated method stub
        super.dispose(session);

    }

    @Override
    public void finishDecode(IoSession session, ProtocolDecoderOutput out)
            throws Exception {
        // TODO Auto-generated method stub
        super.finishDecode(session, out);

    }


}
