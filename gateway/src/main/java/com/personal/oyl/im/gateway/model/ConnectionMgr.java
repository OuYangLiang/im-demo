package com.personal.oyl.im.gateway.model;

import com.personal.oyl.im.gateway.im.Message;
import com.personal.oyl.im.gateway.model.message.Protocol;
import com.personal.oyl.im.gateway.model.message.ReadNotice;
import io.netty.channel.Channel;

public interface ConnectionMgr {

    /**
     * 根据channelId查询用户ID
     *
     * @param channelId channelId
     * @return 用户ID
     */
    String queryUserId(String channelId);

    /**
     * 根据用户ID查询channel
     *
     * @param userId 用户ID
     * @return Channel
     */
    Channel queryChannel(String userId);

    /**
     * 标记用户连上长链
     *
     * @param userId 用户ID
     * @param channel channel
     */
    void markConnected(String userId, Channel channel);

    /**
     * 标记长链中断
     *
     * @param channel channel
     */
    void channelDisconnected(Channel channel);

    //List<String> onlineUsers();

    /**
     * 判断用户是否在线（长链）
     *
     * @param loginId 用户ID
     * @return 在线：true，不在线：false
     */
    boolean isUserOnline(String loginId);

    //void sendTextMessage(String userFrom, String userTo, String message);

    /**
     * 发送
     */
    void send(String receiver, Protocol protocol);
}
