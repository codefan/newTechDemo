package com.centit.demo.daemon;

import org.apache.commons.daemon.Daemon;
import org.apache.commons.daemon.DaemonContext;
import org.apache.commons.daemon.DaemonInitException;

/**
 * 参考文档
 * https://blog.csdn.net/masson32/article/details/51830656#11%E4%B8%BA%E4%BB%80%E4%B9%88%E4%BD%BF%E7%94%A8commons-daemon
 */
public class DaemonApplication implements Daemon {

    private DaemonWorker daemonWorker;

    @Override
    public void init(DaemonContext context) throws DaemonInitException, Exception {
        daemonWorker = new DaemonWorker((i) -> {
            System.out.println("后台第 " + i +" 次执行");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.err.println(e.getLocalizedMessage());
            }
        });

        daemonWorker.init(context);
    }

    @Override
    public void start() throws Exception {
        daemonWorker.start();
    }


    @Override
    public void stop() throws Exception {
        daemonWorker.stop();
    }


    @Override
    public void destroy() {
        System.out.println("Destroying daemon.");
    }
}
