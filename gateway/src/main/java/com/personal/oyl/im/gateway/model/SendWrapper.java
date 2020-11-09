package com.personal.oyl.im.gateway.model;

import com.personal.oyl.im.gateway.model.message.Protocol;
import io.netty.channel.Channel;

public interface SendWrapper {
    void send(Channel channel, Protocol protocol);

    void start();

    void stop();

    void clear(String msgId);
}
