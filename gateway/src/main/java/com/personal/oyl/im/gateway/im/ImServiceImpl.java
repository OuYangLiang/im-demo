package com.personal.oyl.im.gateway.im;

import com.personal.oyl.im.gateway.model.ConnectionMgr;
import com.personal.oyl.im.gateway.model.TextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author OuYang Liang
 * @since 2020-11-01
 */
@Component
public class ImServiceImpl implements ImService {

    private ConnectionMgr connectionMgr;
    private MessageMapper messageMapper;

    @Override
    public void onTextMessage(TextMessage textMessage) {
        messageMapper.insert(textMessage.getSenderId(), textMessage.getReceiverId(), MessageType.text, textMessage.getContent());
        connectionMgr.sendTextMessage(textMessage.getSenderId(), textMessage.getReceiverId(), textMessage.getContent());
    }

    @Autowired
    public void setConnectionMgr(ConnectionMgr connectionMgr) {
        this.connectionMgr = connectionMgr;
    }

    @Autowired
    public void setMessageMapper(MessageMapper messageMapper) {
        this.messageMapper = messageMapper;
    }
}
