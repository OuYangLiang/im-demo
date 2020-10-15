package com.personal.oyl.im.gateway;

import com.personal.oyl.im.gateway.model.ConnectionMgr;
import com.personal.oyl.im.gateway.model.ConnectionMgrImpl;
import com.personal.oyl.im.gateway.model.Protocol;
import com.personal.oyl.im.gateway.model.ProtocolType;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.timeout.IdleStateEvent;

import java.util.UUID;


/**
 * @author OuYang Liang
 * @since 2020-09-23
 */
public class TextFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private static final ConnectionMgr connectionMgr = new ConnectionMgrImpl();

    public static void say(String id, String message) {

        Protocol protocol = new Protocol();
        protocol.setType(ProtocolType.business);
        protocol.setMsgId(UUID.randomUUID().toString());
        protocol.setContent(message);

        connectionMgr.queryChannel(id).writeAndFlush(new TextWebSocketFrame(protocol.toJson()));
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        System.out.println("Received: " + msg.text());

        Protocol protocol = Protocol.fromJson(msg.text());

        if (ProtocolType.heartbeat.equals(protocol.getType())) {
            ctx.writeAndFlush(new TextWebSocketFrame(protocol.toAck().toJson()));
        } else if (ProtocolType.connect.equals(protocol.getType())) {
            connectionMgr.markConnected(protocol.getContent(), ctx.channel());
            ctx.writeAndFlush(new TextWebSocketFrame(protocol.toAck().toJson()));
        } else if (ProtocolType.business.equals(protocol.getType())) {
            ctx.writeAndFlush(new TextWebSocketFrame(protocol.toAck().toJson()));
//            ctx.writeAndFlush(new TextWebSocketFrame(this.reply(ctx, protocol.getContent()).toJson()));
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
        System.out.println("Channel 【" + ctx.channel().id().asLongText() + "】 inactive...");
        connectionMgr.channelDisconnected(ctx.channel());
        super.channelInactive(ctx);
    }
}
