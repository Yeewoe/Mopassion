package org.yeewoe.mopanet.net;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.yeewoe.mopanet.net.message.BasicMessage;

public class ClientSessionHandler extends IoHandlerAdapter {

    IMessageHandle messageHandle;

    public ClientSessionHandler() {

    }

    public void setMessageHandle(IMessageHandle h) {
        messageHandle = h;
    }

    public IMessageHandle getMessageHandle() {
        return messageHandle;
    }

    @Override
    public void sessionOpened(IoSession session) {
        System.out.println("ClientSessionHandler.sessionOpened");
    }

    @Override
    public void messageReceived(IoSession session, Object message) {

        BasicMessage bm = (BasicMessage) message;
        System.out.println("ClientSessionHandler.messageReceived mark " + bm.mark + " flag " + bm.flag + " route " + bm.route.toString());
        if (messageHandle != null) {
            messageHandle.OnMessage(bm);
        } else {
            System.out.println("ClientSessionHandler.messageReceived but message handle is not set !");
        }
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) {
        System.out.println("ClientSessionHandler.exceptionCaught" + cause.toString());
        session.closeNow();
    }

    @Override
    public void inputClosed(IoSession session) throws Exception {
        // TODO Auto-generated method stub
        System.out.println("ClientSessionHandler.inputClosed");
        super.inputClosed(session);
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        // TODO Auto-generated method stub
        System.out.println("ClientSessionHandler.messageSent");
        super.messageSent(session, message);
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        // TODO Auto-generated method stub
        System.out.println("ClientSessionHandler.sessionClosed");
        super.sessionClosed(session);
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        // TODO Auto-generated method stub
        System.out.println("sessionCreated");
        super.sessionCreated(session);
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status)
            throws Exception {
        // TODO Auto-generated method stub
        System.out.println("sessionIdle");
        super.sessionIdle(session, status);
    }


}