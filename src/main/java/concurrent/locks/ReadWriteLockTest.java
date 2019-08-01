package concurrent.locks;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author duosheng
 * @since 2019/8/1
 */
@Slf4j
public class ReadWriteLockTest {

    /**
     * private ReadWriteLock lock = new ReentrantReadWriteLock(); //互斥锁
     * <p>
     * //读锁
     * try{
     * lock.readLock().lock();
     * ...// 省略的代码
     * }finally{
     * lock.readLock().unlock();
     * }
     * <p>
     * //写锁
     * try{
     * lock.writeLock().lock();
     * ...// 省略的代码
     * }finally{
     * lock.writeLock().unlock();
     * }
     */
    @Test
    public void test() throws InterruptedException {
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch stop = new CountDownLatch(1);

        for (int i = 0; i < 2; i++) {
            Thread thread = new Thread(new RunMethod(start, stop));
            thread.start();
        }

        start.countDown(); // 启动
        stop.await(); // 等待所有结束
        System.out.println("全部运行完毕!!!");

        // 主要为了等最终的入库结果
        Thread.sleep(5000);

        ConcurrentHashMap<String, Integer> concurrentHashMap = Counter.getInst().getTotalCountMap();
        int totalCount = 0;
        System.out.println("=====入库数据汇总=====");
        for (Map.Entry<String, Integer> entry : concurrentHashMap.entrySet()) {
            System.out.println("入库数据汇总|" + entry.getKey() + "|" + entry.getValue());
            totalCount += entry.getValue();
        }
        System.out.println("入库数据汇总|总和|" + totalCount);
    }

    static class RunMethod implements Runnable {
        private CountDownLatch start;
        private CountDownLatch stop;

        public RunMethod(CountDownLatch start, CountDownLatch stop) {
            this.start = start;
            this.stop = stop;
        }

        @Override
        public void run() {
            try {
                start.await();
                for (int i = 0; i < 10000; i++) {
                    Counter.getInst().addClick(RandomStringUtils.random(1, "AB"));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                stop.countDown();
            }
        }
    }

    static class Counter {
        private ConcurrentHashMap<String, AtomicInteger> map = new ConcurrentHashMap<>();
        // 互斥锁
        private ReadWriteLock lock = new ReentrantReadWriteLock();

        static class CounterHolder {
            public static final Counter instance = new Counter();
        }

        public static Counter getInst() {
            return CounterHolder.instance;
        }

        private Counter() {
            //启动定时刷新数据库任务
            //每20毫秒钟进行入库
            ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
            executorService.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    ConcurrentHashMap<String, AtomicInteger> tmpMap = null;
                    try {
                        lock.writeLock().lock();
                        tmpMap = map;
                        map = new ConcurrentHashMap<>();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        lock.writeLock().unlock();
                    }

                    if (!tmpMap.isEmpty()) {
                        System.out.println("==================入库开始==================");
                        for (Map.Entry<String, AtomicInteger> entry : tmpMap.entrySet()) {
                            String key = entry.getKey();
                            int count = entry.getValue().get();
                            System.out.println("入库数据|" + key + "|" + count);

                            Integer tempCount = totalCountMap.get(key);
                            if (tempCount == null) {
                                totalCountMap.put(key, count);
                            } else {
                                totalCountMap.put(key, count + tempCount);
                            }
                        }
                        System.out.println("==================入库结束==================");
                    }
                }
            }, 0, 20, TimeUnit.MILLISECONDS);
        }

        //当用户点击了一次，就对数据点击量加一
        public void addClick(String id) {
            try {
                lock.readLock().lock();
                AtomicInteger atomicIntegerNew = new AtomicInteger(0);
                AtomicInteger atomicInteger = map.putIfAbsent(id, atomicIntegerNew);
                if (atomicInteger == null) {
                    // 说明是新增
                    atomicInteger = atomicIntegerNew;
                }
                atomicInteger.addAndGet(1);
            } finally {
                lock.readLock().unlock();
            }
        }

        //额外记录一下总数，看看是否正确
        private ConcurrentHashMap<String, Integer> totalCountMap = new ConcurrentHashMap<String, Integer>();

        public ConcurrentHashMap<String, Integer> getTotalCountMap() {
            return totalCountMap;
        }
    }
}
