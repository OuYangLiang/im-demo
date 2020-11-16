package com.personal.oyl.im.gateway.model.message;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.personal.oyl.im.gateway.im.GroupMessage;
import com.personal.oyl.im.gateway.im.Message;
import com.personal.oyl.im.gateway.im.MessageStatus;

import java.util.Date;
import java.util.List;

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
    private List<String> unread;

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

    public List<String> getUnread() {
        return unread;
    }

    public void setUnread(List<String> unread) {
        this.unread = unread;
    }

    public int getUnreadCnt() {
        return (null == this.getUnread()) ? 0 : this.getUnread().size();
    }

    public String json() {
        return gson.toJson(this);
    }

    public static GroupTextMessage fromJson(String json) {
        return gson.fromJson(json, GroupTextMessage.class);
    }

    public static GroupTextMessage from(GroupMessage message) {
        GroupTextMessage rlt = new GroupTextMessage();
        rlt.setSenderId(message.getSender());
        rlt.setGroupId(message.getGroupId());
        rlt.setContent(message.getContent());
        rlt.setCreatedTime(message.getCreatedTime());
        return rlt;
    }
}
