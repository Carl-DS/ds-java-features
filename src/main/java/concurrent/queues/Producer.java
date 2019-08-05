package concurrent.queues;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author duosheng
 * @since 2019/8/5
 */
@Slf4j
public class Producer extends Worker {

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    public Producer(String name, BlockingQueue<Integer> queue) {
        super(name, queue);
    }

    @Override
    public void run() {
        while (enable) {
            try {
                int value = atomicInteger.incrementAndGet();
                queue.put(value);
                log.info("size:{}, put:{}, enable:{}", queue.size(), value, enable);
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                Logger.getAnonymousLogger().log(Level.WARNING, "producer error", e);
            }
        }
        log.info("{} quit", name);
    }
}
