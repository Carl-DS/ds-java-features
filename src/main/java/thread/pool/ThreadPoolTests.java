package thread.pool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

/**
 * @author duosheng
 * @since 2019/5/13
 */
public class ThreadPoolTests {

    /**
     * 线程资源必须通过线程池提供，不允许在应用中自行显示创建线程
     * 说明：使用线程池的好处是减少在创建和销毁线程上所花的时间以及系统资源的开销,解决资源不足的问题.
     * 如果不使用线程池,有可能造成系统创建大量同类线程而导致消耗完内存或者"过度切换"的问题
     */
    @Test
    public void one() {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("consumer-queue-thread-%d").build();
        ExecutorService pool = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(5), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

        pool.execute(() -> {
            System.out.println("hahahahha");
        });
    }
}
