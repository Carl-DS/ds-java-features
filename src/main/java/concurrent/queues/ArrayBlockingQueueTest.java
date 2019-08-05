package concurrent.queues;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author duosheng
 * @since 2019/8/5
 */
@Slf4j
public class ArrayBlockingQueueTest {

    /**
     * 在这段代码里：
     * <p>
     * 我们使用了容量为50的有界阻塞队列ArrayBlockingQueue作为容器
     * 生产者10个线程
     * 消费者4个线程
     * 2秒后关闭生产者和消费者（这个时候生产者应该不会继续生产，但是消费者还会继续消费）
     * 主线程等待所有生产者消费者执行完成
     * 最后输出关闭后，消费者还能消费多少数据
     */
    @Test
    public void test() throws InterruptedException {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(50, false);
        List<Worker> workers = new ArrayList<>();
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            String name = "Producer" + i;
            Producer worker = new Producer(name, queue);
            workers.add(worker);
            Thread thread = new Thread(worker);
            thread.setName(name);
            threads.add(thread);
            thread.start();
        }
        for (int i = 0; i < 4; i++) {
            String name = "Consumer" + i;
            Consumer worker = new Consumer(name, queue);
            workers.add(worker);
            Thread thread = new Thread(worker);
            thread.setName(name);
            threads.add(thread);
            thread.start();
        }

        Executors.newSingleThreadScheduledExecutor().schedule(() -> {
            for (Worker worker : workers) {
                worker.stop();
            }
        }, 2, TimeUnit.SECONDS);

        for (Thread thread : threads) {
            thread.join();
        }
        log.info("totalConsumedAfterShutdown:{}", Consumer.totalConsumedAfterShutdown());
    }
}
