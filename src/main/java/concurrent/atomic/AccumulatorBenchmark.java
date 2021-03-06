package concurrent.atomic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

/**
 * 先来一把性能测试，对比一下AtomicLong（1.5出来的）、LongAdder（1.8出来的）和LongAccumulator（1.8出来的）用于简单累加的性能。
 *
 * 程序逻辑比较简单，可以看到我们在最大并发10的情况下，去做10亿次操作测试：
 * @author duosheng
 * @since 2019/8/23
 */
@Slf4j
public class AccumulatorBenchmark {

    private StopWatch stopWatch = new StopWatch();
    static final int threadCount = 100;
    static final int taskCount = 1000000000;
    static final AtomicLong atomicLong = new AtomicLong();
    static final LongAdder longAdder = new LongAdder();
    static final LongAccumulator longAccumulator = new LongAccumulator(Long::sum, 0L);

    @Test
    public void test() {
        Map<String, IntConsumer> tasks = new HashMap<>();
        tasks.put("atomicLong", i -> atomicLong.incrementAndGet());
        tasks.put("longAdder", i -> longAdder.increment());
        tasks.put("longAccumulator", i -> longAccumulator.accumulate(1L));
        tasks.entrySet().forEach(item -> benchmark(threadCount, taskCount, item.getValue(), item.getKey()));

        log.info(stopWatch.prettyPrint());
        Assertions.assertEquals(taskCount, atomicLong.get());
        Assertions.assertEquals(taskCount, longAdder.longValue());
        Assertions.assertEquals(taskCount, longAccumulator.longValue());
    }

    private void benchmark(int threadCount, int taskCount, IntConsumer task, String name) {
        stopWatch.start(name);
        ForkJoinPool forkJoinPool = new ForkJoinPool(threadCount);
        forkJoinPool.execute(() -> IntStream.rangeClosed(1, taskCount).parallel().forEach(task));
        forkJoinPool.shutdown();
        try {
            forkJoinPool.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stopWatch.stop();
    }
}
