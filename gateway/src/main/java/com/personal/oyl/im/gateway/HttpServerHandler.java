package com.personal.oyl.im.gateway;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

/**
 * @author OuYang Liang
 * @since 2020-09-23
 */
public class HttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {

        if (msg.uri().startsWith("/say")) {
            String[] str = msg.uri().substring(4).split("-");
            TextFrameHandler.say(str[0], str[1]);
        }

        FullHttpResponse resp = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        resp.headers().add("content-type", "text/html;charset=UTF-8");
        ByteBuf buf = Unpooled.copiedBuffer("Hello World !!!".getBytes());
        resp.content().writeBytes(buf);
        buf.release();
        ctx.writeAndFlush(resp).addListener(ChannelFutureListener.CLOSE);
    }
}
