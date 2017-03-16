package org.yeewoe.mopanet.net.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.yeewoe.mopanet.client.MopaNetConstants;
import org.yeewoe.mopanet.net.message.BasicMessage;
import org.yeewoe.mopanet.protos.PBRoute;

public class MessageEncoder extends ProtocolEncoderAdapter {

    public MessageEncoder() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public void encode(IoSession session, Object message, ProtocolEncoderOutput encodeOut)
            throws Exception {
        // TODO Auto-generated method stub
        if (message instanceof BasicMessage) {
            BasicMessage bm = (BasicMessage) message;

            byte[] route_encoded = PBRoute.ADAPTER.encode(bm.route);
            int rlen = route_encoded.length;
            bm.rlen = rlen;
            int total_len = MopaNetConstants.PK_HEADER_LEN + 4 + rlen + bm.payload.length;
            bm.len = total_len;
            IoBuffer buf = IoBuffer.allocate(total_len).setAutoExpand(true);


            byte[] temp;

            //header
            temp = NumConverter.shortToByteArray(bm.mark);
            buf.put(temp);

            temp = NumConverter.shortToByteArray(bm.flag);
            buf.put(temp);

            temp = NumConverter.intToByteArray(bm.len);
            buf.put(temp);

            //route
            temp = NumConverter.intToByteArray(bm.rlen);
            buf.put(temp);

            buf.put(route_encoded);


            //payload
            buf.put(bm.payload);

            buf.flip();
            System.out.println("MessageEncoder.encode len " + bm.len + "route " + bm.route);
            encodeOut.write(buf);

        } else {

            throw new IllegalArgumentException("message is not a BasicMessage: ");
        }

    }

    @Override
    public void dispose(IoSession session) throws Exception {
        // TODO Auto-generated method stub
        super.dispose(session);
    }


}
