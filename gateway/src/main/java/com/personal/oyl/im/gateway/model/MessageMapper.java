package com.personal.oyl.im.gateway.model;

public interface MessageMapper {
    void insert(String sender, String receiver, MessageType type, String content);
}
