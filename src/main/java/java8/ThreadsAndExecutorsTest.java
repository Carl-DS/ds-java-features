package java8;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author duosheng
 * @since 2018/6/26
 */
public class ThreadsAndExecutorsTest {

    @Test
    public void threadsTest() {
        Runnable task = () -> {
            try {
                String name = Thread.currentThread().getName();
                System.out.println("Foo " + name);
                // 睡眠1秒
                TimeUnit.SECONDS.sleep(1);
                System.out.println("Bar " + name);

            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        task.run();
        // 新建线程
        Thread thread = new Thread(task);
        thread.start();

        System.out.println("DONE");
    }

    @Test
    public void executorServiceTest() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("Hello " + threadName);
        });
    }

    public void shutdownTest() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("Hello " + threadName);
        });

        try {
            System.out.println("attempt to shutdown executor");
            // 显示停止进程，会等待正在执行的任务执行完
            executorService.shutdown();
            executorService.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.err.println("tasks interrupted");
        } finally {
            if (!executorService.isTerminated()) {
                System.err.println("cancel non-finished tasks");
            }
            // 终止所有正在执行的任务并立即关闭execuotr
            executorService.shutdownNow();
            System.out.println("shutdown finished");
        }
    }

    @Test
    public void callableTest() throws Exception {
        Callable<Integer> task = () -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                return 123;
            } catch (InterruptedException e) {
                throw new IllegalStateException("task interrupted", e);
            }
        };
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future<Integer> future = executor.submit(task);
        System.out.println("future done? " + future.isDone());
        Integer result = future.get();
        System.out.println("future done? " + future.isDone());
        System.out.println("result: " + result);
        executor.shutdownNow();
        future.get();
    }

    @Test
    public void timeoutTest() throws InterruptedException, ExecutionException, TimeoutException {
        ExecutorService executor = Executors.newFixedThreadPool(1);

        Future<Integer> future = executor.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                return 123;
            } catch (InterruptedException e) {
                throw new IllegalStateException("task interrupted", e);
            }
        });
        // 设置等待时长
        future.get(1, TimeUnit.SECONDS);
    }

    @Test
    public void invokeAllTest() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        List<Callable<String>> callables = Arrays.asList(
                () -> "task1",
                () -> "task2",
                () -> "task3"
        );

        executor.invokeAll(callables)
                .stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (Exception e) {
                        throw new IllegalStateException(e);
                    }
                })
                .forEach(System.out::println);
    }

    @Test
    public void invokeAnyTest() throws ExecutionException, InterruptedException {
        // 返回一个ForkJoinPool类型的executor
        // ForkJoinPools使用一个并行因子数来创建线程池，默认值为主机CPU的可用核心数
        ExecutorService executor = Executors.newWorkStealingPool();

        List<Callable<String>> callables = Arrays.asList(
                callable("task1", 2),
                callable("task2", 1),
                callable("task3", 3)
        );

        // 在等待future对象的过程中，invokeAny这个方法将会阻塞直到第一个callable中止然后返回这一个callable的结果。
        String result = executor.invokeAny(callables);
        System.out.println(result);
    }

    public Callable<String> callable(String result, long sleepSeconds) {
        return () -> {
            TimeUnit.SECONDS.sleep(sleepSeconds);
            return result;
        };
    }

    @Test
    public void scheduledTest() throws InterruptedException {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        Runnable task = () -> System.out.println("\n Scheduling: " + System.nanoTime());
        ScheduledFuture<?> future = executor.schedule(task, 3, TimeUnit.SECONDS);

        TimeUnit.MILLISECONDS.sleep(1337);

        long remainingDelay = future.getDelay(TimeUnit.MILLISECONDS);
        System.out.printf("Remaining Delay: %s", remainingDelay);

        int initialDelay = 0;
        int period = 1;
        // 以固定频率来执行一个任务
        executor.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.SECONDS);
    }

    public static void main(String[] args) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        Runnable task = () -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                System.out.println("Scheduling: " + System.nanoTime());
            } catch (InterruptedException e) {
                System.err.println("task interrupted");
            }
        };

        // 这个例子调度了一个任务，并在一次执行的结束和下一次执行的开始之间设置了一个1秒钟的固定延迟
        executor.scheduleWithFixedDelay(task, 0, 1, TimeUnit.SECONDS);
    }
}
