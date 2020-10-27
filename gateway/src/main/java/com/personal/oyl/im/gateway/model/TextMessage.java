package com.personal.oyl.im.gateway.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author OuYang Liang
 * @since 2020-10-27
 */
public class TextMessage {
    private static final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    private String senderId;
    private String receiverId;
    private String content;

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String json() {
        return gson.toJson(this);
    }

    public static TextMessage fromJson(String json) {
        return gson.fromJson(json, TextMessage.class);
    }
}
