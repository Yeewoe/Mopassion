package org.yeewoe.mopanet.net.codec;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class MessageCodecFactory implements ProtocolCodecFactory {

    private final MessageEncoder encoder;
    private final MessageDecoder decoder;

    public MessageCodecFactory() {
        super();
        encoder = new MessageEncoder();
        decoder = new MessageDecoder();
        // TODO Auto-generated constructor stub
    }

    @Override
    public ProtocolDecoder getDecoder(IoSession session) throws Exception {
        // TODO Auto-generated method stub
        return decoder;
    }

    @Override
    public ProtocolEncoder getEncoder(IoSession session) throws Exception {
        // TODO Auto-generated method stub
        return encoder;
    }


}


