package com.personal.oyl.im.gateway.controller;

import java.io.Serializable;

/**
 * @author OuYang Liang
 * @since 2020-11-07
 */
public class ClearUnReadParam implements Serializable {
    private String sender;
    private String receiver;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}
