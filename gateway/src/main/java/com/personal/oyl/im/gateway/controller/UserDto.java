package com.personal.oyl.im.gateway.controller;

import com.personal.oyl.im.gateway.user.User;

import java.io.Serializable;

/**
 * @author OuYang Liang
 * @since 2020-11-05
 */
public class UserDto implements Serializable {
    private Long id;
    private String loginId;
    private String userName;
    private String icon;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public static UserDto from(User user) {
        UserDto rlt = new UserDto();
        rlt.setId(user.getId());
        rlt.setLoginId(user.getLoginId());
        rlt.setUserName(user.getUserName());
        rlt.setIcon(user.getIcon());

        return rlt;
    }
}
