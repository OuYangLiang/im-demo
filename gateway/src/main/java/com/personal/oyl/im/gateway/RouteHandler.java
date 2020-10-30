package com.personal.oyl.im.gateway;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author OuYang Liang
 * @since 2020-09-24
 */
@Component
@ChannelHandler.Sharable
public class RouteHandler extends ChannelHandlerAdapter {

    @Autowired
    private TextFrameHandler textFrameHandler;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof FullHttpRequest) {
            if ("/websocket".equalsIgnoreCase(((FullHttpRequest)msg).uri())) {
                ctx.pipeline().addLast(new WebSocketServerProtocolHandler("/websocket"));
                ctx.pipeline().addLast(textFrameHandler);

            } else {
                // ctx.pipeline().addLast(new HttpServerHandler());
            }
        }

        ctx.fireChannelRead(msg);
    }
}
