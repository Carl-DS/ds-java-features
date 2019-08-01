package concurrent.executors;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author duosheng
 * @since 2019/8/1
 */
@Slf4j
public class ThreadPoolExceptionTest {

    @BeforeEach
    public void setDefaultUncaughtExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler((Thread t, Throwable e) -> {
            log.warn("Exception in thread {}", t, e);
        });
    }

    @Test
    public void test() throws InterruptedException {
        String prefix = "test";
        ExecutorService threadPool = Executors.newFixedThreadPool(1, new ThreadFactoryImpl(prefix));
        IntStream.rangeClosed(1, 10).forEach(i -> threadPool.execute(() -> {
            if (i == 5) {
                throw new RuntimeException("error");
            }
            log.info("I'm done: {}", i);
            if (i < 5) Assertions.assertEquals(prefix + "1", Thread.currentThread().getName());
            else Assertions.assertEquals(prefix + "2", Thread.currentThread().getName());
        }));

        threadPool.shutdown();
        threadPool.awaitTermination(1, TimeUnit.HOURS);
    }

    @Test
    public void test2() throws InterruptedException {
        String prefix = "test";
        ExecutorService threadPool = Executors.newFixedThreadPool(1, new ThreadFactoryImpl(prefix));
        List<Future> futures = new ArrayList<>();
        IntStream.rangeClosed(1, 10).forEach(i -> threadPool.submit(() -> {
            if (i == 5) {
                throw new RuntimeException("error");
            }
            log.info("I'm done: {}", i);
            //if (i < 5) Assertions.assertEquals(prefix + "1", Thread.currentThread().getName());
            //else Assertions.assertEquals(prefix + "2", Thread.currentThread().getName());
        }));

        for (Future future : futures) {
            try {
                future.get();
            } catch (Exception e) {
                log.warn("future Execution",e);
            }
        }

        threadPool.shutdown();
        threadPool.awaitTermination(1, TimeUnit.HOURS);
    }
}
