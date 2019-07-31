package concurrent.executors;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author duosheng
 * @since 2019/7/31
 */
@Slf4j
public class PausableThreadPoolExecutorTest {

    @Test
    public void test() throws InterruptedException {
        PausableThreadPoolExecutor threadPool = new PausableThreadPoolExecutor(1, 1, 0, TimeUnit.HOURS, new LinkedBlockingQueue<>());
        IntStream.rangeClosed(1, 5).forEach(i -> threadPool.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("I'm done: {}", i);
        }));

        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.schedule(threadPool::pause, 2, TimeUnit.SECONDS);
        scheduler.schedule(threadPool::resume, 4, TimeUnit.SECONDS);

        /**
         * 平滑的关闭ExecutorService，当此方法被调用时，ExecutorService停止接收新的任务
         * 并且等待已经提交的任务（包含提交正在执行和提交未执行）执行完成。当所有提交任务执行完毕，线程池即被关闭。
         */
        threadPool.shutdown();
        /**
         * 接收人timeout和TimeUnit两个参数，用于设定超时时间及单位。当等待超过设定时间时，
         * 会监测ExecutorService是否已经关闭，若关闭则返回true，否则返回false。一般情况下会和shutdown方法组合使用。
         */
        threadPool.awaitTermination(1, TimeUnit.HOURS);
    }
}
