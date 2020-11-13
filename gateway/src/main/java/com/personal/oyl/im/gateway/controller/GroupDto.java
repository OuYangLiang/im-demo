package com.personal.oyl.im.gateway.controller;

import java.io.Serializable;
import java.util.List;

/**
 * @author OuYang Liang
 * @since 2020-11-13
 */
public class GroupDto implements Serializable {
    private String groupId;
    private String groupName;
    private String icon;
    List<UserDto> members;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<UserDto> getMembers() {
        return members;
    }

    public void setMembers(List<UserDto> members) {
        this.members = members;
    }
}
