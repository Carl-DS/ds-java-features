package concurrent.executors;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * @author duosheng
 * @since 19-7-25
 */
@Slf4j
public class ThreadPoolExecutorTest {

    @Test
    public void test1() throws InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger();
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(2, 5,
                5, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));
        printStats(threadPool);
        submitTasks(atomicInteger, threadPool);
        threadPool.shutdown();
        threadPool.awaitTermination(1, TimeUnit.HOURS);
    }


    @Test
    public void test2() throws InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger();
        /**
         * 重写队列的offer方法，直接返回false，这样就会让线程池有队列已经满的错觉，offer方法的定义如下：
         */
        BlockingQueue<Runnable> queue = new LinkedBlockingDeque<>(10) {
            @Override
            public boolean offer(Runnable runnable) {
                return false;
            }
        };
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(2, 5, 5, TimeUnit.SECONDS,
                queue, new ThreadFactoryImpl("elastic-pool"), (r, executor) -> {
            try {
                executor.getQueue().put(r);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        threadPool.allowCoreThreadTimeOut(true);
        printStats(threadPool);
        submitTasks(atomicInteger, threadPool);
        threadPool.shutdown();
        threadPool.awaitTermination(1, TimeUnit.HOURS);
    }

    private void printStats(ThreadPoolExecutor threadPool) {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            log.info("=========================");
            log.info("=========================");
            log.info("Pool Size: {}", threadPool.getPoolSize());
            log.info("Active Threads: {}", threadPool.getActiveCount());
            log.info("Number of Tasks Completed: {}", threadPool.getCompletedTaskCount());
            log.info("Number of Tasks in Queue: {}", threadPool.getQueue().size());

            log.info("=========================");

        }, 0, 1, TimeUnit.SECONDS);
    }

    private void submitTasks(AtomicInteger atomicInteger, ThreadPoolExecutor threadPool) {
        IntStream.rangeClosed(1, 20).forEach(i -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int id = atomicInteger.incrementAndGet();
            threadPool.submit(() -> {
                log.info("{} started", id);
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("{} finished", id);
            });
        });
    }
}
