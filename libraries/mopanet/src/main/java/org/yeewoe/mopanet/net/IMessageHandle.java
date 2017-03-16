package org.yeewoe.mopanet.net;

import org.yeewoe.mopanet.net.message.BasicMessage;

public interface IMessageHandle {
    public void OnMessage(BasicMessage m);
}
