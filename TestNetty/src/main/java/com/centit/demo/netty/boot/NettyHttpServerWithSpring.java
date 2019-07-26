package com.centit.demo.netty.boot;

import com.centit.demo.netty.http.HttpServerInitializer;
import com.centit.demo.netty.http.SimpleHttpServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.DispatcherServlet;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;

/**
 * 代码清单 2-2 EchoServer 类
 *
 * @author <a href="mailto:norman.maurer@gmail.com">Norman Maurer</a>
 */
@Service
public class NettyHttpServerWithSpring {
    private final int port;

    @Autowired
    private DispatcherServlet servlet;

    public NettyHttpServerWithSpring() {
        this.port = 9999;
    }

    @PostConstruct
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
                .childHandler(new HttpServerInitializer(servlet))
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);
            //(6) 异步地绑定服务器；调用 sync()方法阻塞等待直到绑定完成
            ChannelFuture f = b.bind().sync();
            System.out.println(NettyHttpServerWithSpring.class.getName() +
                " started and listening for connections on " + f.channel().localAddress());
            //(7) 获取 Channel 的CloseFuture，并且阻塞当前线程直到它完成
            f.channel().closeFuture().sync();
        } finally {
            //(8) 关闭 EventLoopGroup，释放所有的资源
            group.shutdownGracefully().sync();
        }
    }
}
