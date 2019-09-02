package concurrent.synchronizers;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author duosheng
 * @since 2019/8/30
 */
@Slf4j
public class CyclicBarrierTest {

    @Test
    public void test() {
        int playerCount = 5;
        int playCount = 3;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(playerCount);
        List<Thread> threads = IntStream.rangeClosed(1, playerCount).mapToObj(player -> new Thread(() -> IntStream.rangeClosed(1, playCount).forEach(play -> {

        }))).collect(Collectors.toList());
    }
}
