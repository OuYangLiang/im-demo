package com.personal.oyl.im.gateway.controller;

import com.personal.oyl.im.gateway.user.User;

import java.util.List;

/**
 * @author OuYang Liang
 * @since 2020-11-17
 */
public class ContactDto {
    private String type;
    private String contactId;
    private String contactName;
    private String icon;
    List<ContactDto> members;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<ContactDto> getMembers() {
        return members;
    }

    public void setMembers(List<ContactDto> members) {
        this.members = members;
    }

    public static ContactDto from(User user) {
        ContactDto rlt = new ContactDto();
        rlt.setType("individual");
        rlt.setContactId(user.getLoginId());
        rlt.setContactName(user.getUserName());
        rlt.setIcon(user.getIcon());

        return rlt;
    }
}
