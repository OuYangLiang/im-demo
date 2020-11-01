package com.personal.oyl.im.gateway.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author OuYang Liang
 * @since 2020-11-01
 */
@Component
public class ImServiceImpl implements ImService {

    @Autowired
    private ConnectionMgr connectionMgr;

    @Override
    public void onTextMessage(TextMessage textMessage) {
        connectionMgr.sendTextMessage(textMessage.getSenderId(), textMessage.getReceiverId(), textMessage.getContent());
    }
}
