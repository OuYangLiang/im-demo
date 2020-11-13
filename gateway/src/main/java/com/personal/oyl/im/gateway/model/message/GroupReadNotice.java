package com.personal.oyl.im.gateway.model.message;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author OuYang Liang
 * @since 2020-11-14
 */
public class GroupReadNotice {
    private static final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    private String receiver;
    private String group;
    private String sender;

    public GroupReadNotice() {

    }

    public GroupReadNotice(String receiver, String group, String sender) {
        this.receiver = receiver;
        this.group = group;
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String json() {
        return gson.toJson(this);
    }

    public static GroupReadNotice fromJson(String json) {
        return gson.fromJson(json, GroupReadNotice.class);
    }
}
