package concurrent.executors;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author duosheng
 * @since 2019/7/31
 */
@Slf4j
public class ScheduledExecutorServiceTest {

    @Test
    public void test() throws InterruptedException {
        // 让任务在固定的频率去执行
        AtomicInteger scheduleAtFixedRateTotal = new AtomicInteger();
        ScheduledExecutorService scheduleAtFixedRateExecutorService = Executors.newSingleThreadScheduledExecutor();
        ScheduledFuture<?> scheduleAtFixedRateTotalFuture = scheduleAtFixedRateExecutorService.scheduleAtFixedRate(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("scheduleAtFixedRate:" + scheduleAtFixedRateTotal.incrementAndGet());
        }, 0, 100, TimeUnit.MILLISECONDS);

        //scheduleAtFixedRateExecutorService.schedule(() -> scheduleAtFixedRateTotalFuture.cancel(false), 1, TimeUnit.SECONDS);
        Executors.newSingleThreadScheduledExecutor().schedule(() -> scheduleAtFixedRateTotalFuture.cancel(false), 1, TimeUnit.SECONDS);

        while (!scheduleAtFixedRateTotalFuture.isDone()) {
            TimeUnit.MILLISECONDS.sleep(1);
        }
        Assertions.assertEquals(11, scheduleAtFixedRateTotal.get());

        // 让任务具有固定的延迟去执行
        AtomicInteger scheduleWithFixedDelayTotal = new AtomicInteger();
        ScheduledExecutorService scheduleWithFixedDelayExecutorService = Executors.newSingleThreadScheduledExecutor();
        ScheduledFuture<?> scheduleWithFixedDelayFuture = scheduleWithFixedDelayExecutorService.scheduleWithFixedDelay(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("scheduleWithFixedDelay:" + scheduleWithFixedDelayTotal.incrementAndGet());
        }, 0, 100, TimeUnit.MILLISECONDS);
        scheduleWithFixedDelayExecutorService.schedule(() -> scheduleWithFixedDelayFuture.cancel(false), 1, TimeUnit.SECONDS);
        while (!scheduleWithFixedDelayFuture.isDone()) TimeUnit.MILLISECONDS.sleep(1);
        Assertions.assertEquals(5, scheduleWithFixedDelayTotal.get());
    }
}
