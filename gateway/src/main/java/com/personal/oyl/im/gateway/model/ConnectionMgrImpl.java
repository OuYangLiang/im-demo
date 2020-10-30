package com.personal.oyl.im.gateway.model;

import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author OuYang Liang
 * @since 2020-09-28
 */
@Component
public class ConnectionMgrImpl implements ConnectionMgr {

    private static final Map<String, String> liveConns = new ConcurrentHashMap<>();
    private static final Map<String, Channel> channels = new ConcurrentHashMap<>();

    @Override
    public String queryUserId(String channelId) {
        return liveConns.get(channelId);
    }

    @Override
    public Channel queryChannel(String userId) {
        return channels.get(userId);
    }

    @Override
    public void markConnected(String userId, Channel channel) {
        liveConns.put(channel.id().asLongText(), userId);
        channels.put(userId, channel);
    }

    @Override
    public void channelDisconnected(Channel channel) {
        String userId = liveConns.get(channel.id().asLongText());
        liveConns.remove(channel.id().asLongText());
        channels.remove(userId);
    }

    @Override
    public List<String> onlineUsers() {
        return new ArrayList<>(channels.keySet());
    }
}
