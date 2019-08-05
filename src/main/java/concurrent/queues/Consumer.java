package concurrent.queues;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author duosheng
 * @since 2019/8/5
 */
@Slf4j
public class Consumer extends Worker {
    private static AtomicInteger totalConsumedAfterShutdown = new AtomicInteger();

    public Consumer(String name, BlockingQueue<Integer> queue) {
        super(name, queue);
    }

    public static int totalConsumedAfterShutdown() {
        return totalConsumedAfterShutdown.get();
    }

    @Override
    public void run() {
        while (enable || queue.size() > 0) {
            try {
                Integer item = queue.take();
                log.info("size:{}, got:{}, enable:{}", queue.size(), item, enable);
                if (!enable) {
                    totalConsumedAfterShutdown.incrementAndGet();
                }
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                log.error("consumer error: ", e);
            }
        }
        log.info("{} quit", name);
    }
}
