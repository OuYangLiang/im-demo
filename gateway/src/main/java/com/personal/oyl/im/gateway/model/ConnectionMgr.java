package com.personal.oyl.im.gateway.model;

import com.personal.oyl.im.gateway.im.Message;
import com.personal.oyl.im.gateway.model.message.ReadNotice;
import io.netty.channel.Channel;

public interface ConnectionMgr {
    String queryUserId(String channelId);

    Channel queryChannel(String userId);

    void markConnected(String userId, Channel channel);

    void channelDisconnected(Channel channel);

    //List<String> onlineUsers();

    boolean isUserOnline(String loginId);

    //void sendTextMessage(String userFrom, String userTo, String message);

    void sendTextMessage(Message message);

    void sendReadNotice(ReadNotice notice);
}
