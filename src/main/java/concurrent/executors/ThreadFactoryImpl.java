package concurrent.executors;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 为线程池的线程命名: 自定义的线程工厂类来为线程命名
 * 这是一个非常好的实践，对于复杂的项目一定有N多个线程池，
 * 有了明确的容易识别的命名，更容易在出现问题的时候进行排查。
 *
 * @author duosheng
 * @since 19-7-26
 */
public class ThreadFactoryImpl implements ThreadFactory {
    private final AtomicLong threadIndex = new AtomicLong(0);
    private final String threadNamePrefix;
    private final boolean daemon;

    public ThreadFactoryImpl(final String threadNamePrefix) {
        this(threadNamePrefix, false);
    }

    public ThreadFactoryImpl(final String threadNamePrefix, boolean daemon) {
        this.threadNamePrefix = threadNamePrefix;
        this.daemon = daemon;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r, threadNamePrefix + this.threadIndex.incrementAndGet());
        thread.setDaemon(daemon);
        return thread;
    }
}
