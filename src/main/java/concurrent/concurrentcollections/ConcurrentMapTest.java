package concurrent.concurrentcollections;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;

import java.util.HashMap;
import java.util.concurrent.*;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.IntStream;

/**
 * 我们来测试一下ConcurrentHashMap和ConcurrentSkipListMap的性能。
 * 前者对应的非并发版本是HashMap，后者是跳表实现，
 * Map按照Key顺序排序（当然也可以提供一个Comparator进行排序）。
 * <p>
 * 在这个例子里，我们不是简单的测试Map读写Key的性能，
 * 而是实现一个多线程环境下使用Map最最常见的场景：
 * 统计Key出现频次，我们的Key的范围是1万个，然后循环1亿次（也就是Value平均也在1万左右），10个并发来操作Map：
 *
 * @author duosheng
 * @since 2019/8/27
 */
@Slf4j
public class ConcurrentMapTest {

    int loopCount = 100000000;
    int threadCount = 10;
    int itemCount = 10000;

    @Test
    public void test() throws InterruptedException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("hashmap");
        normal();
        stopWatch.stop();
        stopWatch.start("concurrentHashMap");
        concurrent();
        stopWatch.stop();
        stopWatch.start("concurrentSkipListMap");
        concurrentSkipListMap();
        stopWatch.stop();
        log.info(stopWatch.prettyPrint());
    }

    /**
     * 对于normal的实现，我们全程锁住了HashMap然后进行读写
     *
     * @throws InterruptedException
     */
    private void normal() throws InterruptedException {
        HashMap<String, Long> freqs = new HashMap<>();
        ForkJoinPool forkJoinPool = new ForkJoinPool(threadCount);
        forkJoinPool.execute(() -> IntStream.rangeClosed(1, loopCount).parallel().forEach(i -> {
            String key = "item" + ThreadLocalRandom.current().nextInt(itemCount);
            synchronized (freqs) {
                if (freqs.containsKey(key)) {
                    freqs.put(key, freqs.get(key) + 1);
                } else {
                    freqs.put(key, 1L);
                }
            }
        }));
        forkJoinPool.shutdown();
        forkJoinPool.awaitTermination(1, TimeUnit.HOURS);
    }

    /**
     * 对于ConcurrentHashMap，我们巧妙利用了一个computeIfAbsent()方法，实现了判断Key是否存在，
     * 计算获取Value，put Key Value三步操作，得到一个Value是LongAdder()，
     * 然后因为LongAdder是线程安全的所以直接调用了increase()方法，一行代码实现了5行代码效果
     *
     * @throws InterruptedException
     */
    private void concurrent() throws InterruptedException {
        ConcurrentHashMap<String, LongAdder> freqs = new ConcurrentHashMap<>(itemCount);
        ForkJoinPool forkJoinPool = new ForkJoinPool(threadCount);
        forkJoinPool.execute(() -> IntStream.rangeClosed(1, loopCount).parallel().forEach(i -> {
            String key = "item" + ThreadLocalRandom.current().nextInt(itemCount);
            freqs.computeIfAbsent(key, k -> new LongAdder()).increment();
        }));
        forkJoinPool.shutdown();
        forkJoinPool.awaitTermination(1, TimeUnit.HOURS);
    }

    /**
     * ConcurrentSkipListMap也是一样
     *
     * @throws InterruptedException
     */
    private void concurrentSkipListMap() throws InterruptedException {
        ConcurrentSkipListMap<String, LongAdder> freqs = new ConcurrentSkipListMap<>();
        ForkJoinPool forkJoinPool = new ForkJoinPool(threadCount);
        forkJoinPool.execute(() -> IntStream.rangeClosed(1, loopCount).parallel().forEach(i -> {
            String key = "item" + ThreadLocalRandom.current().nextInt(itemCount);
            freqs.computeIfAbsent(key, k -> new LongAdder()).increment();
        }));
        forkJoinPool.shutdown();
        forkJoinPool.awaitTermination(1, TimeUnit.HOURS);
    }
}
