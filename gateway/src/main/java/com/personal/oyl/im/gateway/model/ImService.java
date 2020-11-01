package com.personal.oyl.im.gateway.model;

/**
 * @author OuYang Liang
 * @since 2020-11-01
 */
public interface ImService {
    void onTextMessage(TextMessage textMessage);
}
