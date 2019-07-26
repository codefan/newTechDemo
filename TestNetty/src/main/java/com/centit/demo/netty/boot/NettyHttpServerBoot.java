package com.centit.demo.netty.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 代码清单 2-2 EchoServer 类
 *
 * @author <a href="mailto:norman.maurer@gmail.com">Norman Maurer</a>
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.centit")
public class NettyHttpServerBoot {

    public static void main(String[] args) {
        SpringApplication.run(NettyHttpServerBoot.class, args);
    }

}
