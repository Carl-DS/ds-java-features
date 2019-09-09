package concurrent.atomic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 这是一个可见性的问题，AtomicReference以及volatile可以确保线程对数据的更新刷新到内存。
 * 因为我们对于开关的关闭是在另一个定时任务线程做的，如果我们不使用volatile或AtomicReference来定义对象，
 * 那么对象的操作可能无法被其它线程感知到
 *
 * @author duosheng
 * @since 2019/8/27
 */
@Slf4j
public class AtomicReferenceTest {

    private Switch rawValue = new Switch();
    private volatile Switch volatileValue = new Switch();
    private AtomicReference<Switch> atomicValue = new AtomicReference<>(new Switch());

    @Test
    public void test() throws InterruptedException {
        new Thread(() -> {
            log.info("Start:rawValue");
            while (rawValue.get()) {

            }
            log.info("Done:rawValue");
        }).start();

        new Thread(() -> {
            log.info("Start:volatileValue");
            while (volatileValue.get()) {
            }
            log.info("Done:volatileValue");
        }).start();

        new Thread(() -> {
            log.info("Start:atomicValue");
            while (atomicValue.get().get()) {
            }
            log.info("Done:atomicValue");
        }).start();

        Executors.newSingleThreadScheduledExecutor().schedule(rawValue::off, 2, TimeUnit.SECONDS);
        Executors.newSingleThreadScheduledExecutor().schedule(volatileValue::off, 2, TimeUnit.SECONDS);
        Executors.newSingleThreadScheduledExecutor().schedule(atomicValue.get()::off, 2, TimeUnit.SECONDS);

        TimeUnit.HOURS.sleep(1);
    }
}

class Switch {
    private boolean enable = true;

    public boolean get() {
        return enable;
    }

    public void off() {
        enable = false;
    }
}
