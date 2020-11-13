package com.personal.oyl.im.gateway;

import com.personal.oyl.im.gateway.im.ImService;
import com.personal.oyl.im.gateway.im.MessageType;
import com.personal.oyl.im.gateway.model.*;
import com.personal.oyl.im.gateway.model.message.*;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.timeout.IdleStateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;


/**
 * @author OuYang Liang
 * @since 2020-09-23
 */
@Component
@ChannelHandler.Sharable
public class TextFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {


    private ConnectionMgr connectionMgr;
    private ImService imService;
    private SendWrapper sendWrapper;

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        Protocol protocol = Protocol.fromJson(msg.text());

        if (ProtocolType.heartbeat.equals(protocol.getType())) {
            ctx.writeAndFlush(new TextWebSocketFrame(protocol.toAck().toJson()));

        } else if (ProtocolType.connect.equals(protocol.getType())) {
            connectionMgr.markConnected(protocol.getContent(), ctx.channel());
            ctx.writeAndFlush(new TextWebSocketFrame(protocol.toAck().toJson()));

        } else if (ProtocolType.online_ack.equals(protocol.getType())) {
            sendWrapper.clear(protocol.getMsgId());

        } else if (ProtocolType.offline_ack.equals(protocol.getType())) {
            sendWrapper.clear(protocol.getMsgId());

        } else if (ProtocolType.business_ack.equals(protocol.getType())) {
            sendWrapper.clear(protocol.getMsgId());
            imService.onAck(protocol.getMsgId());

        } else if (ProtocolType.business.equals(protocol.getType())) {
            if (MessageType.text.equals(protocol.getSubType())) {
                TextMessage message = TextMessage.fromJson(protocol.getContent());
                imService.onTextMessage(protocol.getMsgId(), message);

            } else if (MessageType.read_reply.equals(protocol.getSubType())) {
                ReadReply reply = ReadReply.fromJson(protocol.getContent());
                if (null == reply.getMsgId()) {
                    imService.clearUnread(reply.getReceiver(), reply.getSender());
                } else {
                    imService.clearUnRead(reply.getReceiver(), reply.getSender(), reply.getMsgId());
                }

            } else if (MessageType.group_text.equals(protocol.getSubType())) {
                GroupTextMessage message = GroupTextMessage.fromJson(protocol.getContent());
                imService.onGroupTextMessage(protocol.getMsgId(), message);

            } else if (MessageType.group_read_reply.equals(protocol.getSubType())) {
                GroupReadReply reply = GroupReadReply.fromJson(protocol.getContent());
                if (null == reply.getMsgId()) {
                    imService.clearGroupUnRead(reply.getGroup(), reply.getReceiver());
                } else {
                    imService.clearGroupUnRead(reply.getGroup(), reply.getReceiver(), reply.getMsgId());
                }

            }

            ctx.writeAndFlush(new TextWebSocketFrame(protocol.toAck().toJson()));
        }
    }

    private Protocol reply(ChannelHandlerContext ctx, String message) {
        Protocol protocol = new Protocol();
        protocol.setType(ProtocolType.business);
        protocol.setMsgId(UUID.randomUUID().toString());
        protocol.setContent(connectionMgr.queryUserId(ctx.channel().id().asLongText()) + " says: " + message);
        return protocol;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        /*if (evt.equals(WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE)) {

        } else */if (evt.equals(IdleStateEvent.FIRST_ALL_IDLE_STATE_EVENT) || evt.equals(IdleStateEvent.ALL_IDLE_STATE_EVENT)) {
            ctx.channel().close();
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Channel 【" + ctx.channel().id().asLongText() + "】 active...");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        connectionMgr.channelDisconnected(ctx.channel());
        super.channelInactive(ctx);
    }

    @Autowired
    public void setConnectionMgr(ConnectionMgr connectionMgr) {
        this.connectionMgr = connectionMgr;
    }

    @Autowired
    public void setImService(ImService imService) {
        this.imService = imService;
    }

    @Autowired
    public void setSendWrapper(SendWrapper sendWrapper) {
        this.sendWrapper = sendWrapper;
    }
}
