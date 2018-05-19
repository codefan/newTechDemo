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
public class DaemonProcess {
    private static final Logger logger = LoggerFactory.getLogger(DaemonProcess.class);

    private ExecutorService executor;

    private final AtomicBoolean running = new AtomicBoolean();

    private CountDownLatch stopLatch;

    private Consumer<Integer> realProcess;

    public DaemonProcess( Consumer<Integer> realProcess ) {
        this.realProcess = realProcess;
    }


    public void start() throws Exception {
        executor = Executors.newSingleThreadExecutor();
        executor.submit(this::loop);
        running.set(true);
        stopLatch = new CountDownLatch(1);
    }

    private void loop() {

        logger.info("started");
        int i = 0;
        do {
            realProcess.accept(i++);
         } while (running.get());

        logger.info("closing process");
        stopLatch.countDown();
    }

    public void stop() throws Exception {
        logger.info("stopping");
        running.set(false);
        stopLatch.await();
        executor.shutdown();
        logger.info("stopped");
    }
}
