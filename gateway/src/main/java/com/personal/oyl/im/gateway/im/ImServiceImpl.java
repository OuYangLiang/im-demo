package com.personal.oyl.im.gateway.im;

import com.personal.oyl.im.gateway.model.ConnectionMgr;
import com.personal.oyl.im.gateway.model.message.Protocol;
import com.personal.oyl.im.gateway.model.message.ProtocolType;
import com.personal.oyl.im.gateway.model.message.ReadNotice;
import com.personal.oyl.im.gateway.model.message.TextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author OuYang Liang
 * @since 2020-11-01
 */
@Component
public class ImServiceImpl implements ImService {

    private ConnectionMgr connectionMgr;
    private MessageMapper messageMapper;

    @Override
    public void onTextMessage(String msgId, TextMessage textMessage) {
        Message message = new Message();
        message.setSender(textMessage.getSenderId());
        message.setReceiver(textMessage.getReceiverId());
        message.setType(MessageType.text);
        message.setContent(textMessage.getContent());
        message.setMsgId(msgId);
        messageMapper.insert(message);

        if (connectionMgr.isUserOnline(textMessage.getReceiverId())) {
            message = messageMapper.queryByKey(message.getId());

            TextMessage param = new TextMessage();

            param.setSenderId(message.getSender());
            param.setReceiverId(message.getReceiver());
            param.setContent(message.getContent());
            param.setCreatedTime(message.getCreatedTime());
            param.setStatus(MessageStatus.initial);

            Protocol protocol = new Protocol();
            protocol.setType(ProtocolType.business);
            protocol.setMsgId(message.getMsgId());
            protocol.setSubType(MessageType.text);
            protocol.setContent(param.json());

            connectionMgr.send(message.getReceiver(), protocol);
        }
    }

    @Override
    public void onAck(String msgId) {
        int i = messageMapper.onAck(msgId);
    }

    private String identification(String s1, String s2) {
        return (s1.compareTo(s2) > 0) ? s1 + "|" + s2 : s2 + "|" + s1;
    }

    @Override
    public List<Protocol> queryLastN(String senderId, String receiverId, int n) {
        List<Message> messages = messageMapper.queryLastN(this.identification(senderId, receiverId), n);

        if (null == messages || messages.isEmpty()) {
            return Collections.emptyList();
        }

        for (Message message : messages) {
            if (MessageStatus.initial.equals(message.getStatus()) && message.getReceiver().equalsIgnoreCase(senderId)) {
                messageMapper.onAck(message.getMsgId());
            }
        }

        return messages.stream().map((m) -> {
            Protocol pro = new Protocol();
            pro.setType(ProtocolType.business);
            pro.setSubType(m.getType());
            pro.setMsgId(m.getMsgId());
            pro.setContent(TextMessage.from(m).json());
            return pro;
        }).collect(Collectors.toList());
    }

    @Override
    public void clearUnread(String receiver, String sender) {
        List<Message> messages = messageMapper.queryUnRead(receiver, sender);

        if (messages == null || messages.isEmpty()) {
            return;
        }

        messageMapper.onRead(messages.stream().map(Message::getId).collect(Collectors.toList()));
        this.noticeIfOnline(receiver, sender);
    }

    @Override
    public void clearUnRead(String receiver, String sender, String msgId) {
        Message message = messageMapper.queryByMsgId(msgId);

        /*if (!message.getSender().equalsIgnoreCase(sender)) {
            //
        }

        if (!message.getReceiver().equalsIgnoreCase(receiver)) {
            //
        }*/

        messageMapper.onRead(Collections.singletonList(message.getId()));
        this.noticeIfOnline(receiver, sender);
    }

    private void noticeIfOnline(String receiver, String sender) {
        if (connectionMgr.isUserOnline(sender)) {
            Protocol protocol = new Protocol();
            protocol.setType(ProtocolType.business);
            protocol.setMsgId(UUID.randomUUID().toString());
            protocol.setSubType(MessageType.read_notice);
            protocol.setContent(new ReadNotice(sender, receiver).json());

            connectionMgr.send(sender, protocol);
        }
    }

    /*@Override
    public void onConnected(String loginId) {
        List<Message> undelivered = messageMapper.queryUndelivered(loginId);
        if (null != undelivered && !undelivered.isEmpty()) {
            for (Message msg : undelivered) {
                connectionMgr.sendTextMessage(msg);
            }
        }
    }*/

    @Autowired
    public void setConnectionMgr(ConnectionMgr connectionMgr) {
        this.connectionMgr = connectionMgr;
    }

    @Autowired
    public void setMessageMapper(MessageMapper messageMapper) {
        this.messageMapper = messageMapper;
    }
}
