package com.personal.oyl.im.gateway.model;

import com.personal.oyl.im.gateway.im.Message;
import io.netty.channel.Channel;

import java.util.List;

public interface ConnectionMgr {
    String queryUserId(String channelId);

    Channel queryChannel(String userId);

    void markConnected(String userId, Channel channel);

    void channelDisconnected(Channel channel);

    List<String> onlineUsers();

    //void sendTextMessage(String userFrom, String userTo, String message);

    void sendTextMessage(Message message);
}
