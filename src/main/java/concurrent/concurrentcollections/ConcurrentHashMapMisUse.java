package concurrent.concurrentcollections;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * @author duosheng
 * @since 2019/8/29
 */
@Slf4j
public class ConcurrentHashMapMisUse {

    /**
     * 这段代码显然是有问题的：
     * <p>
     * 第一，诸如size()、containsValue()等（聚合状态的）方法仅仅在没有并发更新的时候是准确的，否则只能作为统计、监控来使用，不能用于控制程序运行逻辑
     * 第二，即使size()是准确的，在计算出gap之后其它线程可能已经往里面添加数据了，虽然putAll()操作这一操作是线程安全的，但是这个这个计算gap，
     * 填补gap的逻辑并不是原子性的，不是说用了ConcurrentHashMap就不需要锁了
     *
     * @throws InterruptedException
     */
    @Test
    public void test() throws InterruptedException {
        int limit = 10000;
        ConcurrentHashMap<String, Long> concurrentHashMap = LongStream.rangeClosed(1, limit - 10)
                .boxed()
                .collect(Collectors.toConcurrentMap(i -> UUID.randomUUID().toString(), Function.identity(), (o1, o2) -> o1, ConcurrentHashMap::new));
        log.info("init size:{} ", concurrentHashMap.size());

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int __ = 0; __ < 10; __++) {
            executorService.execute(() -> {
                int gap = limit - concurrentHashMap.size();
                log.info("gap: {} ", gap);
                concurrentHashMap.putAll(LongStream.rangeClosed(1, gap)
                        .boxed()
                        .collect(Collectors.toMap(i -> UUID.randomUUID().toString(), Function.identity()))
                );
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.HOURS);

        log.info("finish size: {} ", concurrentHashMap.size());
    }

    /**
     * 还有一点算不上误用，只是提一下，ConcurrentHashMap的Key/Value不能是null，
     * 而HashMap是可以的，为什么是这样呢？ 下是ConcurrentHashMap作者的回复：
     * <p>
     * 意思就是如果get(key)返回了null，你搞不清楚这到底是key没有呢还是value就是null。
     * 非并发情况下你可以使用后contains(key)来判断，但是并发情况下不行，你判断的时候可能Map已经修改了。
     */
    @Test
    public void test2() {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put(null, null);
        log.info("hashMap: {} ", hashMap.toString());

        ConcurrentHashMap<String, LongAdder> concurrentHashMap = new ConcurrentHashMap<>();
        // 无法运行
        concurrentHashMap.put(null, new LongAdder());
        log.info("concurrentHashMap: {}", concurrentHashMap.toString());
    }
}
