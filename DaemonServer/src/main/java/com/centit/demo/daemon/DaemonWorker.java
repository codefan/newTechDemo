package com.centit.demo.daemon;

import org.apache.commons.daemon.DaemonContext;
import org.apache.commons.daemon.DaemonInitException;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.IntConsumer;

public class DaemonWorker /*implements Daemon */{

    private int runCount;
    private ExecutorService executor;
    private final AtomicBoolean running = new AtomicBoolean();
    private CountDownLatch stopLatch;

    private IntConsumer consumer;

    public DaemonWorker(IntConsumer consumer){
        runCount = 0;
        this.consumer = consumer;
    }

    public void init(DaemonContext context) throws DaemonInitException, Exception {
        runCount = 0;
    }


    public void start() throws Exception {
        executor = Executors.newSingleThreadExecutor();
        executor.submit(this::loop);
        running.set(true);
        stopLatch = new CountDownLatch(1);
    }

    private void loop() {
        consumer.accept(runCount);
    }

    public void stop() throws Exception {
        running.set(false);
        stopLatch.await();
        executor.shutdown();
    }

    public void setConsumer(IntConsumer consumer) {
        this.consumer = consumer;
    }

    /*@Override
    public void destroy() {

    }*/
}
