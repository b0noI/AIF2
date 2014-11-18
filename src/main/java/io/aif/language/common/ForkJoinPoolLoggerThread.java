package io.aif.language.common;


import org.apache.log4j.Logger;

import java.util.concurrent.ForkJoinPool;

public class ForkJoinPoolLoggerThread extends Thread {

    private static final Logger LOGGER = Logger.getLogger(ForkJoinPoolLoggerThread.class);

    private static final int WAIT_INTERVAL = 1000;

    private static final String POOL_QUEUED_TASK_COUNT_D_THREADS_COUNT_D_STEAL_COUNT_D = "POOL QueuedTaskCount: %d, ThreadsCount: %d, StealCount: %d";

    private final ForkJoinPool forkJoinPool;

    private boolean active = true;

    public ForkJoinPoolLoggerThread(ForkJoinPool forkJoinPool) {
        this.forkJoinPool = forkJoinPool;
    }

    @Override
    public void run() {
        while (active) {
            LOGGER.debug(String.format(POOL_QUEUED_TASK_COUNT_D_THREADS_COUNT_D_STEAL_COUNT_D,
                    forkJoinPool.getQueuedTaskCount(),
                    forkJoinPool.getRunningThreadCount(),
                    forkJoinPool.getStealCount()));
            try {
                Thread.sleep(WAIT_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
                active = false;
            }
        }
    }

    public void stopThread() {
        active = false;
    }

}
