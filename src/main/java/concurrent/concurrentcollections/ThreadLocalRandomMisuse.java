package concurrent.concurrentcollections;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author duosheng
 * @since 2019/8/28
 */
@Slf4j
public class ThreadLocalRandomMisuse {

    /**
     * 一句话而言，我们应该每次都ThreadLocalRandom.current().nextInt()这样用而不是实例化了ThreadLocalRandom.current()每次调用nextInt()。
     * 观察一下两次输出可以发现，wrong的那5次得到的随机数都是一样的：
     *
     * @throws InterruptedException
     */
    @Test
    public void test() throws InterruptedException {
        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
        IntStream.rangeClosed(1, 5)
                .mapToObj(i -> new Thread(() -> log.info("wrong:{}", threadLocalRandom.nextInt())))
                .forEach(Thread::start);
        IntStream.rangeClosed(1, 5)
                .mapToObj(i -> new Thread(() -> log.info("ok:{}", ThreadLocalRandom.current().nextInt())))
                .forEach(Thread::start);
        TimeUnit.SECONDS.sleep(1);
    }
}
