package concurrent.statistics;

import lombok.ToString;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author duosheng
 * @since 2019/8/13
 */
@ToString
public class TotalWords {
    //private long sum = 0;
    //
    //public void sum(int count) {
    //    sum += count;
    //}
    //
    //public long total() {
    //    return sum;
    //}

    private AtomicLong sum = new AtomicLong(0);

    public void sum(int count) {
        sum.addAndGet(count);
    }

    public long total() {
        return sum.get();
    }
}
