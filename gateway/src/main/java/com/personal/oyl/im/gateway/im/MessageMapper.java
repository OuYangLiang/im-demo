package com.personal.oyl.im.gateway.im;

import java.util.List;

public interface MessageMapper {
    //void insert(String sender, String receiver, String identification, MessageType type, String content);

    void insert(Message message);

    Message queryByKey(long id);

    List<Message> queryLastN(String identification, int n);
}
