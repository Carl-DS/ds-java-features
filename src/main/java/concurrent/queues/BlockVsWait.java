package concurrent.queues;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ArrayBlockingQueue;


/**
 * @author duosheng
 * @since 2019/8/5
 */
@Slf4j
public class BlockVsWait {

    Object locker = new Object();
    ArrayBlockingQueue<Integer> arrayBlockingQueue1 = new ArrayBlockingQueue<>(1);
    ArrayBlockingQueue<Integer> arrayBlockingQueue2 = new ArrayBlockingQueue<>(1);

    /**
     * 在下面的代码里，我们开启了三个线程：
     * <p>
     * 一个是等待锁
     * 一个是等待从队列获取数据
     * 一个是等待加入数据到队列
     * <p>
     * 运行程序之后，我们看一下线程的状态，可以看到：
     * <p>
     * 等待锁的block线程，处于BLOCKED状态
     * 还有两个被阻塞队列阻塞的线程，处于WAITING状态
     * <p>
     * 通俗一点说，BLOCKED就是线程自己想做事情，但是很无奈只能等别人先把事情干完，
     * 所以说是被阻塞，被动的，WAITING就是线程自己主动愿意放弃CPU时间进行等待，
     * 等别人在合适的时候通知自己来继续干活，所以说是等待中，主动的。
     * Blocking Queue其实是让线程Waiting而不是Block。
     *
     * @throws InterruptedException
     */
    @Test
    public void test() throws InterruptedException {
        arrayBlockingQueue1.put(1);

        Thread waitOnTake = new Thread(() -> {
            synchronized (locker) {
                try {
                    arrayBlockingQueue2.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        waitOnTake.setName("waitOnTake");
        waitOnTake.start();

        Thread waitOnPut = new Thread(() -> {
            try {
                arrayBlockingQueue1.put(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        waitOnPut.setName("waitOnPut");
        waitOnPut.start();

        Thread block = new Thread(() -> {
            synchronized (locker) {
                log.info("OK");
            }
        });
        block.setName("block");
        block.start();

        block.join();
    }

}
