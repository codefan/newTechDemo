package com.centit.demo.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

/**
 * 参见文档
 * https://blog.csdn.net/lianjunzongsiling/article/details/52622864
 */
public interface DaemonProcess {

    void init();

    void start() throws Exception;

    void doTaskOnce();

    void stop() throws Exception;

    void destory();
}
