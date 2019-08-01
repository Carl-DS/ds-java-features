package concurrent.executors;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.LongStream;

/**
 * @author duosheng
 * @since 2019/7/31
 */
@Slf4j
public class ForkJoinPoolBenchmark {

    /**
     * 从Java 1.8开始，Executors提供了newWorkStealingPool来获得一个ForkJoin线程池。
     * 从命名可以看到，这是一个工作窃取线程池，传统的线程池具有公共的一个任务队列，在任务很多，
     * 任务执行很快（CPU密集型任务）的情况下，会发生比较多的竞争问题，而ForkJoin的话每一个线程都有自己的任务队列，
     * 如果自己的队列没有任务的话可以从其它队列窃取任务，这样确保了吞吐的同时，减少了竞争。
     *
     * @throws InterruptedException
     */
    @Test
    public void test() throws InterruptedException {
        AtomicLong atomicLong = new AtomicLong();
        StopWatch stopWatch = new StopWatch();
        log.info("Runtime.getRuntime().availableProcessors(): {}", Runtime.getRuntime().availableProcessors());
        // 当为8 时 normal 反而更快
        ExecutorService normal = Executors.newFixedThreadPool(8);
        ExecutorService forkjoin = Executors.newWorkStealingPool(8);

        stopWatch.start("normal");
        LongStream.rangeClosed(1, 10000000).forEach(__ -> normal.submit(atomicLong::incrementAndGet));
        normal.shutdown();
        normal.awaitTermination(1, TimeUnit.HOURS);
        stopWatch.stop();
        long r = atomicLong.get();
        stopWatch.start("forkjoin");
        LongStream.rangeClosed(1, 10000000).forEach(__ -> forkjoin.submit(atomicLong::incrementAndGet));
        forkjoin.shutdown();
        forkjoin.awaitTermination(1, TimeUnit.HOURS);
        stopWatch.stop();
        log.info(stopWatch.prettyPrint());
        log.info("result:{},{}", r, atomicLong.get());
    }
}
