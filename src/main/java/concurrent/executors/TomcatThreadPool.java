package concurrent.executors;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author duosheng
 * @since 19-7-26
 */
@Slf4j
public class TomcatThreadPool extends ThreadPoolExecutor {
    /**
     * The number of tasks submitted but not yet finished. This includes tasks
     * in the queue and tasks that have been handed to a worker thread but the
     * latter did not start executing the task yet.
     * This number is always greater or equal to {@link #getActiveCount()}.
     */
    private final AtomicInteger submittedCount = new AtomicInteger(0);


    public TomcatThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        prestartAllCoreThreads();
    }

    public TomcatThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
        prestartAllCoreThreads();
    }

    public TomcatThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
        prestartAllCoreThreads();
    }

    public TomcatThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        prestartAllCoreThreads();
    }

    public int getSubmittedCount() {
        return submittedCount.get();
    }

    @Override
    public void execute(Runnable command) {
        execute(command, 0, TimeUnit.MILLISECONDS);
    }

    public void execute(Runnable command, long timeout, TimeUnit unit) {
        submittedCount.incrementAndGet();
        try {
            super.execute(command);
        } catch (RejectedExecutionException rx) {
            //出现拒绝异常后的处理策略
            if (super.getQueue() instanceof TomcatTaskQueue) {
                final TomcatTaskQueue queue = (TomcatTaskQueue) super.getQueue();
                try {
                    if (!queue.force(command, timeout, unit)) {
                        submittedCount.decrementAndGet();
                        throw new RejectedExecutionException("threadPoolExecutor.queueFull");
                    } else {
                        log.warn("RejectedExecutionException throw, task {} put into queue again", command.toString());
                    }
                } catch (InterruptedException x) {
                    submittedCount.decrementAndGet();
                    throw new RejectedExecutionException(x);
                }
            } else {
                submittedCount.decrementAndGet();
                throw rx;
            }
        }
    }

    private static class RejectHandler implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            throw new RejectedExecutionException();
        }
    }
}


