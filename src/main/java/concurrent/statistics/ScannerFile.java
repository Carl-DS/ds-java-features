package concurrent.statistics;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author duosheng
 * @since 2019/8/9
 */
@Slf4j
public class ScannerFile {

    public Set<FileInfo> fileInfos = new TreeSet<>();

    public Set<FileInfo> getAllFile(String path) {
        File f = new File(path);
        File[] files = f.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                String directoryPath = file.getPath();
                getAllFile(directoryPath);
            } else {
                String filePath = file.getPath();
                if (!filePath.endsWith(".md")) {
                    continue;
                }
                FileInfo info = new FileInfo(filePath, file.lastModified());
                fileInfos.add(info);
            }
        }
        return fileInfos;
    }

    @ToString
    public final class FileInfo implements Comparable<FileInfo> {
        private String filePath;
        private long modifyTime;

        public FileInfo(String filePath, long modifyTime) {
            this.filePath = filePath;
            this.modifyTime = modifyTime;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public long getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(long modifyTime) {
            this.modifyTime = modifyTime;
        }

        @Override
        public int compareTo(FileInfo o) {
            if (o.modifyTime < o.modifyTime) {
                return -1;
            } else {
                return 1;
            }
        }
    }
}

