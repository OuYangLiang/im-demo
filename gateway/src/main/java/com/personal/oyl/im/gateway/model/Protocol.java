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
    private String msgId;
    private String content;

    public ProtocolType getType() {
        return type;
    }

    public void setType(ProtocolType type) {
        this.type = type;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
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

    public Protocol toAck() {
        Protocol rlt = new Protocol();
        rlt.setType(this.getType().getAckType());
        rlt.setMsgId(this.getMsgId());
        rlt.setContent(null);

        return rlt;
    }

}
