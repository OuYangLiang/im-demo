package com.personal.oyl.im.gateway.controller;

import java.io.Serializable;

/**
 * @author OuYang Liang
 * @since 2020-10-28
 */
public class LoginParam implements Serializable {
    private String userId;
    private String pwd;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
