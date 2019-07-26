package com.centit.demo.netty.http;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;

/**
 * 代码清单 2-1 EchoServerHandler
 *
 * @author <a href="mailto:norman.maurer@gmail.com">Norman Maurer</a>
 */
//标示一个ChannelHandler可以被多个 Channel 安全地共享
@Sharable
public class SimpleHttpServerHandler extends ChannelInboundHandlerAdapter {
    public static void writeResponeToHttpClient(ChannelHandlerContext ctx, String content) throws Exception{
        FullHttpResponse response = new DefaultFullHttpResponse(
            HttpVersion.HTTP_1_1,
            HttpResponseStatus.OK,
            Unpooled.wrappedBuffer(content.getBytes("utf-8")));
        response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/plain;charset=UTF-8");
        response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, response.content().readableBytes());
        response.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
        ctx.write(response);
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object request) throws Exception {
        if (request instanceof HttpRequest) {
            String responseContent = "您访问了" + ((HttpRequest) request).method().toString() +":" + ((HttpRequest) request).uri();
            SimpleHttpServerHandler.writeResponeToHttpClient(ctx, responseContent);
        }

        /*else if (request instanceof HttpContent) {
            HttpContent httpContent = (HttpContent) request;
            ByteBuf byteData = httpContent.content();
            if (byteData instanceof EmptyByteBuf) {
                responseContent = "Content：无数据！";
            } else {
                String content = byteData.toString();
                responseContent = "Content:" + content;
            }
        }*/
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx)
            throws Exception {
        //将未决消息冲刷到远程节点，并且关闭该 Channel
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
                                Throwable cause) {
        //关闭该Channel
        ctx.close();
        //打印异常栈跟踪
        cause.printStackTrace();
    }
}
