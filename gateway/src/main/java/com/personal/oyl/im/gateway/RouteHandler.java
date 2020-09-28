package com.personal.oyl.im.gateway;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

/**
 * @author OuYang Liang
 * @since 2020-09-24
 */
public class RouteHandler extends ChannelHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            if ("/websocket".equalsIgnoreCase(((FullHttpRequest)msg).uri())) {
                ctx.pipeline().addLast(new WebSocketServerProtocolHandler("/websocket"));
                ctx.pipeline().addLast(new TextFrameHandler());

            } else {
                ctx.pipeline().addLast(new HttpServerHandler());
            }
        }

        ctx.fireChannelRead(msg);
    }
}
