package com.personal.oyl.im.gateway.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author OuYang Liang
 * @since 2020-09-28
 */
public class Protocol {
    private static final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    private ProtocolType type;
    private String content;

    public ProtocolType getType() {
        return type;
    }

    public void setType(ProtocolType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static Protocol fromJson(String json) {
        return gson.fromJson(json, Protocol.class);
    }

    public String toJson() {
        return gson.toJson(this);
    }

    /*public static void main(String[] args) {
        Protocol p = new Protocol();
        p.setType(ProtocolType.business);
        p.setContent("this is the content");

        System.out.println(p.toJson());

        p = Protocol.fromJson(p.toJson());
        System.out.println(p.toJson());
    }*/
}
