package concurrent.synchronizers;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * CyclicBarrier用来让所有线程彼此等待，等待所有的线程或者说参与方一起到达了汇合点后一起进入下一次等待，不断循环。
 * 在所有线程到达了汇合点后可以由最后一个到达的线程做一下『后处理』操作，
 * 这个后处理操作可以在声明CyclicBarrier的时候传入，也可以通过判断await()的返回来实现。
 *
 * @author duosheng
 * @since 2019/8/30
 */
@Slf4j
public class CyclicBarrierTest {

    /**
     * 一、为什么要用join()方法
     *
     * 主线程生成并启动了子线程，而子线程里要进行大量的耗时的运算(这里可以借鉴下线程的作用)，当主线程处理完其他的事务后，需要用到子线程的处理结果，这个时候就要用到join();方法了。
     *
     * 二、join方法的作用
     *
     * 在网上看到有人说“将两个线程合并”。这样解释我觉得理解起来还更麻烦。不如就借鉴下API里的说法：
     *
     * “等待该线程终止。”
     *
     * 解释一下，是主线程等待子线程的终止。也就是说主线程的代码块中，如果碰到了t.join()方法，此时主线程需要等待（阻塞），等待子线程结束了(Waits for this thread to die.),才能继续执行t.join()之后的代码块。
     * @throws InterruptedException
     */
    @Test
    public void test() throws InterruptedException {
        int playerCount = 5;
        int playCount = 3;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(playerCount);
        List<Thread> threads = IntStream.rangeClosed(1, playerCount).mapToObj(player -> new Thread(() -> IntStream.rangeClosed(1, playCount).forEach(play -> {
            try {
                TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(100));
                log.debug("Player {} arrived for play {}", player, play);
                if (cyclicBarrier.await() == 0) {
                    log.info("Total players {} arrived, let's play {}", cyclicBarrier.getParties(), play);
                    TimeUnit.SECONDS.sleep(2);
                    log.info("Play {} finished", play);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }))).collect(Collectors.toList());

        threads.forEach(Thread::start);
        for (Thread thread : threads) {
            thread.join();
        }
    }
}
