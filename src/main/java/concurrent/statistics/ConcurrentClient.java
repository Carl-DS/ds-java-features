package concurrent.statistics;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author duosheng
 * @since 2019/8/13
 */
@Slf4j
public class ConcurrentClient {

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        TotalWords totalWords = new TotalWords();
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        FilterProcessManager filterProcessManager = new FilterProcessManager(totalWords);
        // /home/lds/workspace/worklogs/使用Dockerfile构建Docker镜像.md
        ScannerFile scannerFile = new ScannerFile();
        Set<ScannerFile.FileInfo> allFile = scannerFile.getAllFile("/home/lds/workspace/worklogs/");
        log.info("allFile size=[{}]", allFile.size());
        allFile.stream().forEach(a -> log.info(a.toString()));
        for (ScannerFile.FileInfo fileInfo : allFile) {
            executorService.execute(new ScanNumTask(fileInfo.getFilePath(), filterProcessManager));
        }
        log.info(filterProcessManager.toString());

        executorService.shutdown();
        while (!executorService.awaitTermination(100, TimeUnit.MILLISECONDS)) {
            log.info("worker running");
        }
        long total = totalWords.total();
        long end = System.currentTimeMillis();
        log.info("total sum=[{}],[{}] ms", total, end - start);

    }
}
