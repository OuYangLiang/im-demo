package com.personal.oyl.im.gateway.controller;

import java.io.Serializable;

/**
 * @author OuYang Liang
 * @since 2020-10-28
 */
public class CheckParam implements Serializable {
    private String userId;
    private String sec;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSec() {
        return sec;
    }

    public void setSec(String sec) {
        this.sec = sec;
    }
}
