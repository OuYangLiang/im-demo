package com.personal.oyl.im.gateway.model.message;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;

/**
 * @author OuYang Liang
 * @since 2020-11-09
 */
public class ReadNotice implements Serializable {
    private static final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    private String sender;
    private String receiver;

    public ReadNotice() {
    }

    public ReadNotice(String sender, String receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

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

    public String json() {
        return gson.toJson(this);
    }

    public static ReadNotice fromJson(String json) {
        return gson.fromJson(json, ReadNotice.class);
    }
}