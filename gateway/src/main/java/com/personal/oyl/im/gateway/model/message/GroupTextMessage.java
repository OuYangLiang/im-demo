package com.personal.oyl.im.gateway.model.message;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.personal.oyl.im.gateway.im.Message;
import com.personal.oyl.im.gateway.im.MessageStatus;

import java.util.Date;

/**
 * @author OuYang Liang
 * @since 2020-10-27
 */
public class GroupTextMessage {
    private static final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    private String senderId;
    private String receiverId;
    private String groupId;
    private String content;
    private MessageStatus status;
    private Date createdTime;

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

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MessageStatus getStatus() {
        return status;
    }

    public void setStatus(MessageStatus status) {
        this.status = status;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String json() {
        return gson.toJson(this);
    }

    public static GroupTextMessage fromJson(String json) {
        return gson.fromJson(json, GroupTextMessage.class);
    }

    /*public static GroupTextMessage from(Message message) {
        GroupTextMessage rlt = new GroupTextMessage();
        rlt.setSenderId(message.getSender());
        rlt.setReceiverId(message.getReceiver());
        rlt.setContent(message.getContent());
        rlt.setStatus(message.getStatus());
        rlt.setCreatedTime(message.getCreatedTime());
        return rlt;
    }*/
}
