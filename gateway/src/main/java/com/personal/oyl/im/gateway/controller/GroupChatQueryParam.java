package com.personal.oyl.im.gateway.controller;

import java.io.Serializable;

/**
 * @author OuYang Liang
 * @since 2020-11-05
 */
public class GroupChatQueryParam implements Serializable {
    private String sender;
    private String group;
    private int n = 20;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }
}
