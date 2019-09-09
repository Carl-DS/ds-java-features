package concurrent.synchronizers;

import concurrent.executors.ThreadFactoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Exchanger实现的效果是两个线程在同一时间（会合点）交换数据
 *
 * @author duosheng
 * @since 2019/9/2
 */
@Slf4j
public class ExchangerTest {

    /**
     * 小结
     * 并发容器这块我就不做过多总结了，ConcurrentHashMap实在是太好用太常用，但是务必注意其线程安全的特性
     * 并不是说ConcurrentHashMap怎么用都没有问题，错误使用在业务代码中很常见。
     * 现在我们来举个看表演的例子总结一下几种并发同步器：
     *
     * Semaphore是限制同时看表演的观众人数，有人走了后新人才能进来看
     * CountDownLatch是演职人员人不到齐表演无法开始，演完结束
     * CyclicBarrier是演职人员到期了后才能表演，最后一个到的人是导演，导演会主导整个演出，演出完毕后所有演职人员修整后重新等待大家到期
     * Phaser是每一场演出的演职人员名单可能随时会更改，但是也是要确保所有演职人员到期后才能开演
     *
     * @throws InterruptedException
     */
    @Test
    public void test() throws InterruptedException {
        Random random = new Random();
        Exchanger<Integer> exchanger = new Exchanger<>();
        int count = 10;
        Executors.newFixedThreadPool(1, new ThreadFactoryImpl("producer"))
                .execute(() -> {
                    try {
                        for (int i = 0; i < count; i++) {
                            log.info("sent: {}", i);
                            exchanger.exchange(i);
                            TimeUnit.MILLISECONDS.sleep(random.nextInt(1000));
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });

        ExecutorService executorService = Executors.newFixedThreadPool(1, new ThreadFactoryImpl("consumer"));
        executorService.execute(() -> {
            try {
                for (int i = 0; i < count; i++) {
                    int data = exchanger.exchange(null);
                    log.info("got: {}", data);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.HOURS);
    }
}
