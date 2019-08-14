package concurrent.statistics;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author duosheng
 * @since 2019/8/13
 */
@Slf4j
@ToString
public class FilterProcessManager {

    private TotalWords totalWords;

    public FilterProcessManager(TotalWords totalWords) {
        this.totalWords = totalWords;
    }

    private List<FilterProcess> filterProcesses = new ArrayList<>(10);

    public FilterProcessManager() {
        this.addProcess(new HttpFilterProcess())
                .addProcess(new WrapFilterProcess());
    }

    public FilterProcessManager addProcess(FilterProcess filterProcess) {
        filterProcesses.add(filterProcess);
        return this;
    }

    public void process(String msg) {
        for (FilterProcess filterProcess : filterProcesses) {
            msg = filterProcess.process(msg);
        }
        totalWords.sum(msg.toCharArray().length);
    }
}
