package com.personal.oyl.im.gateway.controller;

import java.io.Serializable;

/**
 * @author OuYang Liang
 * @since 2020-10-28
 */
public final class WebResult<T> implements Serializable {
    private boolean success;
    private String errCode;
    private String errMsg;
    private T data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static WebResult<Void> success() {
        WebResult<Void> rlt = new WebResult<>();
        rlt.setSuccess(true);
        return rlt;
    }

    public  static <D> WebResult<D> success(D data) {
        WebResult<D> rlt = new WebResult<>();
        rlt.setSuccess(true);
        rlt.setData(data);
        return rlt;
    }

    public static WebResult<Void> fail(String errCode, String errMsg) {
        WebResult<Void> rlt = new WebResult<>();
        rlt.setSuccess(false);
        rlt.setErrCode(errCode);
        rlt.setErrMsg(errMsg);
        return rlt;
    }
}
