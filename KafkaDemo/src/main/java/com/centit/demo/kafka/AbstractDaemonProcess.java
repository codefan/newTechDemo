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
public abstract class AbstractDaemonProcess implements DaemonProcess {
    private static final Logger logger = LoggerFactory.getLogger(AbstractDaemonProcess.class);

    private ExecutorService executor;

    private final AtomicBoolean running = new AtomicBoolean();

    private CountDownLatch stopLatch;


    @Override
    public void init() {

    }

    @Override
    public void start() throws Exception {
        executor = Executors.newSingleThreadExecutor();
        executor.submit(this::loop);
        running.set(true);
        stopLatch = new CountDownLatch(1);
    }

    private void loop() {

        logger.info("started");
        do {
            doTaskOnce();
        } while (running.get());

        logger.info("closing process");
        stopLatch.countDown();
    }

    @Override
    public void stop() throws Exception {
        logger.info("stopping");
        running.set(false);
        stopLatch.await();
        executor.shutdown();
        logger.info("stopped");
    }

    @Override
    public void destory() {
        //doOtherJob();
    }


    //public abstract void doOtherJob();
}
