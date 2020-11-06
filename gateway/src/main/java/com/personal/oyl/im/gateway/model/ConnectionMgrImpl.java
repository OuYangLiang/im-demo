package com.personal.oyl.im.gateway.model;

import com.personal.oyl.im.gateway.im.ImService;
import com.personal.oyl.im.gateway.im.Message;
import com.personal.oyl.im.gateway.im.MessageType;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author OuYang Liang
 * @since 2020-09-28
 */
@Component
public class ConnectionMgrImpl implements ConnectionMgr {

    private static final Map<String, String> liveConns = new ConcurrentHashMap<>();
    private static final Map<String, Channel> channels = new ConcurrentHashMap<>();

    private SendWrapper sendWrapper;
//    private ImService imService;

    @Override
    public String queryUserId(String channelId) {
        return liveConns.get(channelId);
    }

    @Override
    public Channel queryChannel(String userId) {
        return channels.get(userId);
    }

    @Override
    public void markConnected(String userId, Channel channel) {
        liveConns.put(channel.id().asLongText(), userId);
        channels.put(userId, channel);
//        imService.onConnected(userId);

        // 异步
        /*for (String otherId : this.onlineUsers()) {
            if (!otherId.equalsIgnoreCase(userId)) {
                Protocol pro = new Protocol();
                pro.setType(ProtocolType.online);
                pro.setMsgId(UUID.randomUUID().toString());
                pro.setContent(userId);

                //this.queryChannel(otherId).writeAndFlush(new TextWebSocketFrame(pro.toJson()));
                this.sendWrapper.send(this.queryChannel(otherId), pro);
            }
        }*/
    }

    @Override
    public void channelDisconnected(Channel channel) {
        String currentId = liveConns.get(channel.id().asLongText());
        liveConns.remove(channel.id().asLongText());
        channels.remove(currentId);

        /*for (String userId : this.onlineUsers()) {
            if (!userId.equalsIgnoreCase(currentId)) {
                Protocol pro = new Protocol();
                pro.setType(ProtocolType.offline);
                pro.setMsgId(UUID.randomUUID().toString());
                pro.setContent(currentId);

                //this.queryChannel(userId).writeAndFlush(new TextWebSocketFrame(pro.toJson()));
                this.sendWrapper.send(this.queryChannel(userId), pro);
            }
        }*/
    }

    /*@Override
    public List<String> onlineUsers() {
        return new ArrayList<>(channels.keySet());
    }*/

    @Override
    public boolean isUserOnline(String loginId) {
        return channels.containsKey(loginId);
    }

    /*@Override
    public void sendTextMessage(String userFrom, String userTo, String message) {
        TextMessage param = new TextMessage();

        param.setSenderId(userFrom);
        param.setReceiverId(userTo);
        param.setContent(message);

        Protocol protocol = new Protocol();
        protocol.setType(ProtocolType.business);
        protocol.setMsgId(UUID.randomUUID().toString());
        protocol.setSubType(MessageType.text);
        protocol.setContent(param.json());

        //this.queryChannel(userTo).writeAndFlush(new TextWebSocketFrame(protocol.toJson()));
        this.sendWrapper.send(this.queryChannel(userTo), protocol);
    }*/

    @Override
    public void sendTextMessage(Message message) {
        TextMessage param = new TextMessage();

        param.setSenderId(message.getSender());
        param.setReceiverId(message.getReceiver());
        param.setContent(message.getContent());
        param.setCreatedTime(message.getCreatedTime());

        Protocol protocol = new Protocol();
        protocol.setType(ProtocolType.business);
        protocol.setMsgId(message.getMsgId());
        protocol.setSubType(MessageType.text);
        protocol.setContent(param.json());

        //this.queryChannel(userTo).writeAndFlush(new TextWebSocketFrame(protocol.toJson()));
        this.sendWrapper.send(this.queryChannel(message.getReceiver()), protocol);
    }

    @Autowired
    public void setSendWrapper(SendWrapper sendWrapper) {
        this.sendWrapper = sendWrapper;
    }

    /*@Autowired
    public void setImService(ImService imService) {
        this.imService = imService;
    }*/
}
