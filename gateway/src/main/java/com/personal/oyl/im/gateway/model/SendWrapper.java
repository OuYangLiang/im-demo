package com.personal.oyl.im.gateway.model;

import io.netty.channel.Channel;

public interface SendWrapper {
    void send(Channel channel, Protocol protocol);

    void start();

    void stop();

    void clear(String msgId);
}
