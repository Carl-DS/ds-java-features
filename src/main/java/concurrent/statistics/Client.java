package concurrent.statistics;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author duosheng
 * @since 2019/8/13
 */
@Slf4j
public class Client {

    public static void main(String[] args) throws IOException {
        TotalWords totalWords = new TotalWords();
        FilterProcessManager filterProcessManager = new FilterProcessManager(totalWords);

        // /home/lds/workspace/worklogs/使用Dockerfile构建Docker镜像.md
        ScannerFile scannerFile = new ScannerFile();
        Set<ScannerFile.FileInfo> allFile = scannerFile.getAllFile("/home/lds/workspace/worklogs/");
        log.info("allFile size=[{}]",allFile.size());
        allFile.stream().forEach(a -> log.info(a.toString()));

        for (ScannerFile.FileInfo fileInfo : allFile) {
            Stream<String> stringStream = Files.lines(Paths.get(fileInfo.getFilePath()), StandardCharsets.UTF_8);
            List<String> collect = stringStream.collect(Collectors.toList());
            for (String msg : collect) {
                filterProcessManager.process(msg);
            }
        }
        log.info(filterProcessManager.toString());
    }
}
