package com.personal.oyl.im.gateway.controller;

import java.io.Serializable;

/**
 * @author OuYang Liang
 * @since 2020-11-05
 */
public class ChatQueryParam implements Serializable {
    private String loginId1;
    private String loginId2;
    private int n = 20;

    public String getLoginId1() {
        return loginId1;
    }

    public void setLoginId1(String loginId1) {
        this.loginId1 = loginId1;
    }

    public String getLoginId2() {
        return loginId2;
    }

    public void setLoginId2(String loginId2) {
        this.loginId2 = loginId2;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }
}
