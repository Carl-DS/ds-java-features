package concurrent.atomic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 看一下AtomicInteger的compareAndSet()功能。
 * 首先说明这个程序没有任何意义，只是测试一下功能。在这个程序里，
 * 我们乱序开启10个线程，每一个线程的任务就是按照次序来累加数字。
 * 我们使用AtomicInteger的compareAndSet()来确保乱序的线程也能按照我们要的顺序操作累加。
 *
 * @author duosheng
 * @since 2019/8/27
 */
@Slf4j
public class AtomicIntegerTest {

    @Test
    public void test() throws InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        List<Thread> threadList = IntStream.range(0,10).mapToObj(i-> {
            Thread thread = new Thread(() -> {
                log.info("Wait {}->{}", i, i+1);
                while (!atomicInteger.compareAndSet(i, i + 1)) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.info("Done {}->{}", i, i+1);
            });
            thread.setName(UUID.randomUUID().toString());
            return thread;
        }).sorted(Comparator.comparing(Thread::getName)).collect(Collectors.toList());

        for (Thread thread : threadList) {
            thread.start();
        }
        for (Thread thread : threadList) {
            thread.join();
        }
        log.info("result:{}", atomicInteger.get());
    }
}
