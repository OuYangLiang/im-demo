package com.personal.oyl.im.gateway.model;

import io.netty.channel.Channel;

public interface ConnectionMgr {
    String queryUserId(String channelId);

    Channel queryChannel(String userId);

    void markConnected(String userId, Channel channel);

    void channelDisconnected(Channel channel);
}
