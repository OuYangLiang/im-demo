package com.personal.oyl.im.gateway.im;

import com.personal.oyl.im.gateway.model.ConnectionMgr;
import com.personal.oyl.im.gateway.model.message.*;
import com.personal.oyl.im.gateway.user.User;
import com.personal.oyl.im.gateway.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author OuYang Liang
 * @since 2020-11-01
 */
@Component
public class ImServiceImpl implements ImService {

    private ConnectionMgr connectionMgr;
    private MessageMapper messageMapper;
    private GroupMessageMapper groupMessageMapper;
    private UserService userService;

    @Override
    public void onTextMessage(String msgId, TextMessage textMessage) {
        if (null != messageMapper.queryByMsgId(msgId)) {
            return;
        }

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
    public void onGroupTextMessage(String msgId, GroupTextMessage textMessage) {
        if (null != groupMessageMapper.queryByMsgId(msgId)) {
            return;
        }

        GroupMessage message = new GroupMessage();
        message.setSender(textMessage.getSenderId());
        message.setGroupId(textMessage.getGroupId());
        message.setType(MessageType.group_text);
        message.setContent(textMessage.getContent());
        message.setMsgId(msgId);
        groupMessageMapper.insert(message);

        List<User> users = userService.queryUserByGroup(textMessage.getGroupId());

        for (User user : users) {
            if (user.getLoginId().equalsIgnoreCase(textMessage.getSenderId())) {
                continue;
            }

            groupMessageMapper.insertRead(textMessage.getGroupId(), msgId, user.getLoginId());
        }

        message = groupMessageMapper.queryByKey(message.getId());
        for (User user : users) {
            if (user.getLoginId().equalsIgnoreCase(textMessage.getSenderId())) {
                continue;
            }

            if (connectionMgr.isUserOnline(user.getLoginId())) {
                GroupTextMessage param = new GroupTextMessage();

                param.setSenderId(message.getSender());
                param.setGroupId(textMessage.getGroupId());
                param.setContent(message.getContent());
                param.setCreatedTime(message.getCreatedTime());
                param.setStatus(MessageStatus.initial);

                Protocol protocol = new Protocol();
                protocol.setType(ProtocolType.business);
                protocol.setMsgId(message.getMsgId());
                protocol.setSubType(MessageType.group_text);
                protocol.setContent(param.json());

                connectionMgr.send(user.getLoginId(), protocol);
            }
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
    public List<Protocol> queryLastGroupN(String sender, String group, int n) {
        List<GroupMessage> messages = groupMessageMapper.queryLastGroupN(group, n);
        messages = messages.stream().sorted((o1, o2) -> o1.getId() <= o2.getId() ? -1 : 1).collect(Collectors.toList());

        List<Protocol> rlt = new LinkedList<>();
        for (GroupMessage message : messages) {
            Protocol protocol = new Protocol();
            protocol.setType(ProtocolType.business);
            protocol.setSubType(message.getType());
            protocol.setMsgId(message.getMsgId());
            GroupTextMessage textMessage = GroupTextMessage.from(message);

            if (sender.equalsIgnoreCase(message.getSender())) {
                List<GroupMessageRead> unreads = groupMessageMapper.queryUnreadGroupMessageReadByMsgId(message.getMsgId());
                if (null == unreads || unreads.isEmpty()) {
                    textMessage.setUnread(Collections.emptyList());
                } else {
                    textMessage.setUnread(unreads.stream().map(GroupMessageRead::getReceiver).collect(Collectors.toList()));
                }
            } else {
                GroupMessageRead groupMessageRead = groupMessageMapper.queryGroupMessageReadByKey(sender, message.getMsgId());
                if (null == groupMessageRead) {
                    textMessage.setStatus(MessageStatus.read);
                } else {
                    textMessage.setStatus(groupMessageRead.getStatus());
                }
            }

            protocol.setContent(textMessage.json());
            rlt.add(protocol);
        }

        return rlt;
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
        messageMapper.onRead(Collections.singletonList(message.getId()));
        this.noticeIfOnline(receiver, sender);
    }

    @Override
    public void clearGroupUnRead(String group, String receiver) {
        List<GroupMessageRead> lists = groupMessageMapper.queryUnreadGroupMessageRead(receiver, group);

        if (null == lists || lists.isEmpty()) {
            return;
        }

        groupMessageMapper.onRead(lists.stream().map(GroupMessageRead::getId).collect(Collectors.toList()));

        List<GroupMessage> messages = groupMessageMapper.queryByMsgIds(lists.stream().map(GroupMessageRead::getMsgId).collect(Collectors.toList()));
        Set<String> senders = messages.stream().map(GroupMessage::getSender).collect(Collectors.toSet());

        for (String sender : senders) {
            if (connectionMgr.isUserOnline(sender)) {
                Protocol protocol = new Protocol();
                protocol.setType(ProtocolType.business);
                protocol.setMsgId(UUID.randomUUID().toString());
                protocol.setSubType(MessageType.group_read_notice);
                protocol.setContent(new GroupReadNotice(receiver, group, sender).json());

                connectionMgr.send(sender, protocol);
            }
        }
    }

    @Override
    public void clearGroupUnRead(String group, String receiver, String msgId) {
        GroupMessageRead groupMessageRead = groupMessageMapper.queryGroupMessageReadByKey(receiver, msgId);

        if (null != groupMessageRead) {
            groupMessageMapper.onRead(Collections.singletonList(groupMessageRead.getId()));

            GroupMessage message = groupMessageMapper.queryByMsgId(msgId);
            if (connectionMgr.isUserOnline(message.getSender())) {
                Protocol protocol = new Protocol();
                protocol.setType(ProtocolType.business);
                protocol.setMsgId(UUID.randomUUID().toString());
                protocol.setSubType(MessageType.group_read_notice);
                protocol.setContent(new GroupReadNotice(receiver, group, message.getSender()).json());

                connectionMgr.send(message.getSender(), protocol);
            }
        }
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

    @Autowired
    public void setGroupMessageMapper(GroupMessageMapper groupMessageMapper) {
        this.groupMessageMapper = groupMessageMapper;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
