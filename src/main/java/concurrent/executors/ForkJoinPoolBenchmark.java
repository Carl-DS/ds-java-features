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

    @Test
    public void test() throws InterruptedException {
        AtomicLong atomicLong = new AtomicLong();
        StopWatch stopWatch = new StopWatch();
        log.info("Runtime.getRuntime().availableProcessors(): {}", Runtime.getRuntime().availableProcessors());
        // 当为8 时 normal 反而更快
        ExecutorService normal = Executors.newFixedThreadPool(8);
        ExecutorService forkjoin = Executors.newWorkStealingPool(8);

        stopWatch.start("normal");
        LongStream.rangeClosed(1, 10000000).forEach(__->normal.submit(atomicLong::incrementAndGet));
        normal.shutdown();
        normal.awaitTermination(1, TimeUnit.HOURS);
        stopWatch.stop();
        long r = atomicLong.get();
        stopWatch.start("forkjoin");
        LongStream.rangeClosed(1, 10000000).forEach(__->forkjoin.submit(atomicLong::incrementAndGet));
        forkjoin.shutdown();
        forkjoin.awaitTermination(1, TimeUnit.HOURS);
        stopWatch.stop();
        log.info(stopWatch.prettyPrint());
        log.info("result:{},{}", r, atomicLong.get());
    }
}
