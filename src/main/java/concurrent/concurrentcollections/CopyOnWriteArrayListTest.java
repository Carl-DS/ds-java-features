package concurrent.concurrentcollections;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

/**
 * CopyOnWrite的意义在于几乎没有什么修改，而读并发超级高的场景，如果有修改，
 * 我们重起炉灶复制一份，虽然代价很大，但是这样能让99.9%的并发读实现无锁，我们来试试其性能，
 * 先是写的测试，我们比拼一下CopyOnWriteArrayList、手动锁的ArrayList以及synchronizedList包装过的ArrayList：
 *
 * @author duosheng
 * @since 2019/8/29
 */
@Slf4j
public class CopyOnWriteArrayListTest {

    @Test
    public void testWrite() {
        List<Integer> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        List<Integer> arrayList = new ArrayList<>();
        List<Integer> synchronizedList = Collections.synchronizedList(new ArrayList<>());
        StopWatch stopWatch = new StopWatch();
        int loopCount = 100000;
        stopWatch.start("copyOnWriteArrayList");
        IntStream.rangeClosed(1, loopCount).parallel().forEach(__ -> copyOnWriteArrayList.add(ThreadLocalRandom.current().nextInt(loopCount)));
        stopWatch.stop();
        stopWatch.start("arrayList");
        IntStream.rangeClosed(1, loopCount).parallel().forEach(__ -> {
            synchronized (arrayList) {
                arrayList.add(ThreadLocalRandom.current().nextInt(loopCount));
            }
        });
        stopWatch.stop();
        stopWatch.start("synchronizedList");
        IntStream.range(0, loopCount).parallel().forEach(__ -> synchronizedList.add(ThreadLocalRandom.current().nextInt(loopCount)));
        stopWatch.stop();
        log.info(stopWatch.prettyPrint());
    }
}
