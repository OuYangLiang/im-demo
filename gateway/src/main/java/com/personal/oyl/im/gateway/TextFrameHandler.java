package com.personal.oyl.im.gateway;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.timeout.IdleStateEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * @author OuYang Liang
 * @since 2020-09-23
 */
public class TextFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private static final Map<String, String> liveConns = new HashMap<>();
    private static final Map<String, Channel> channels = new HashMap<>();

    public static void say(String id, String message) {
        channels.get(id).writeAndFlush(new TextWebSocketFrame(message));
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        if (msg.text().equalsIgnoreCase("heartbeat")) {
            ctx.writeAndFlush(new TextWebSocketFrame("heartbeat ok"));
            return;
        }

        if (msg.text().startsWith("Login:")) {
            String id = msg.text().substring(6);
            liveConns.put(ctx.channel().id().asLongText(), id);
            channels.put(id, ctx.channel());
            ctx.writeAndFlush(new TextWebSocketFrame("Welcome to Netty"));
            return;
        }

        ctx.writeAndFlush(new TextWebSocketFrame(liveConns.get(ctx.channel().id().asLongText()) + ": " + msg.text()));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt.equals(WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE)) {

        } else if (evt.equals(IdleStateEvent.FIRST_ALL_IDLE_STATE_EVENT) || evt.equals(IdleStateEvent.ALL_IDLE_STATE_EVENT)) {
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
        super.channelInactive(ctx);
    }
}
