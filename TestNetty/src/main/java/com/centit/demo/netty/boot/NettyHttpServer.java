package com.centit.demo.netty.boot;

import com.centit.demo.netty.http.SimpleHttpServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

import java.net.InetSocketAddress;

/**
 * 代码清单 2-2 EchoServer 类
 *
 * @author <a href="mailto:norman.maurer@gmail.com">Norman Maurer</a>
 */
public class NettyHttpServer {
    private final int port;

    public NettyHttpServer(int port) {
        this.port = port;
    }

    public static void main(String[] args)
        throws Exception {
        /*if (args.length != 1) {
            System.err.println("Usage: " + EchoServer.class.getSimpleName() +
                " <port>"
            );
            return;
        }*/
        //设置端口值（如果端口参数的格式不正确，则抛出一个NumberFormatException）
        int port = (args.length >= 1) ? Integer.parseInt(args[0]):9999;
        //调用服务器的 start()方法
        new NettyHttpServer(port).start();
    }

    public void start() throws Exception {
        final SimpleHttpServerHandler serverHandler = new SimpleHttpServerHandler();
        //(1) 创建EventLoopGroup
        EventLoopGroup group = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //(2) 创建ServerBootstrap
            ServerBootstrap b = new ServerBootstrap();
            b.group(group, workerGroup)
                //(3) 指定所使用的 NIO 传输 Channel
                .channel(NioServerSocketChannel.class)
                //(4) 使用指定的端口设置套接字地址
                .localAddress(new InetSocketAddress(port))
                //(5) 添加一个EchoServerHandler到于Channel的 ChannelPipeline
                //.childHandler(new HttpPipelineInitializer(false))
                //.handler(new HttpPipelineInitializer(false))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        //EchoServerHandler 被标注为@Shareable，所以我们可以总是使用同样的实例
                        //这里对于所有的客户端连接来说，都会使用同一个 EchoServerHandler，因为其被标注为@Sharable，
                        //这将在后面的章节中讲到。
                        ch.pipeline()
                            .addLast("decoder", new HttpRequestDecoder())
                            .addLast("encoder", new HttpResponseEncoder())
                            .addLast(serverHandler);
                    }
                }).option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);
            //(6) 异步地绑定服务器；调用 sync()方法阻塞等待直到绑定完成
            ChannelFuture f = b.bind().sync();
            System.out.println(NettyHttpServer.class.getName() +
                " started and listening for connections on " + f.channel().localAddress());
            //(7) 获取 Channel 的CloseFuture，并且阻塞当前线程直到它完成
            f.channel().closeFuture().sync();
        } finally {
            //(8) 关闭 EventLoopGroup，释放所有的资源
            group.shutdownGracefully().sync();
        }
    }
}
