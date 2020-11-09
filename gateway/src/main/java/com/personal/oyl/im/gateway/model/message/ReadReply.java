package com.personal.oyl.im.gateway.model.message;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;

/**
 * @author OuYang Liang
 * @since 2020-11-09
 */
public class ReadReply implements Serializable {
    private static final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    private String sender;
    private String receiver;
    private String msgId;

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

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String json() {
        return gson.toJson(this);
    }

    public static ReadReply fromJson(String json) {
        return gson.fromJson(json, ReadReply.class);
    }
}
