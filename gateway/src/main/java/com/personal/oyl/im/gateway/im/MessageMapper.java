package com.personal.oyl.im.gateway.im;

import java.util.List;

public interface MessageMapper {
    //void insert(String sender, String receiver, String identification, MessageType type, String content);

    void insert(Message message);

    Message queryByKey(long id);

    Message queryByMsgId(String msgId);

    List<Message> queryLastN(String identification, int n);

    int onAck(String msgId);

    //List<Message> queryUndelivered(String loginId);

    int onRead(List<Long> list);

    List<Message> queryUnRead(String receiver, String sender);
}
