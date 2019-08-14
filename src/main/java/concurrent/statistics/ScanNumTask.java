package concurrent.statistics;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author duosheng
 * @since 2019/8/13
 */
@Slf4j
public class ScanNumTask implements Runnable {

    private String path;
    private FilterProcessManager filterProcessManager;

    public ScanNumTask(String path, FilterProcessManager filterProcessManager) {
        this.path = path;
        this.filterProcessManager = filterProcessManager;
    }

    @Override
    public void run() {
        Stream<String> stringStream = null;
        try {
            stringStream = Files.lines(Paths.get(path), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> collect = stringStream.collect(Collectors.toList());
        for (String msg : collect) {
            filterProcessManager.process(msg);
        }
    }
}
