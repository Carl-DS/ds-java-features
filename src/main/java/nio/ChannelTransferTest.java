package nio;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * @author duosheng
 * @since 2018/8/11
 */
public class ChannelTransferTest {

    private static final String FILE_STORAGE = "E:\\IdeaProjects\\practiseProjects\\ds-java-features\\doc\\";

    @Test
    public void transferFrom() throws IOException {
        RandomAccessFile fromFile = new RandomAccessFile(FILE_STORAGE + "fromFile.txt", "rw");
        FileChannel fromChannel = fromFile.getChannel();

        RandomAccessFile toFile = new RandomAccessFile(FILE_STORAGE + "toFile.txt", "rw");
        FileChannel toChannel = toFile.getChannel();

        long position = 0;
        long count = fromChannel.size();

        toChannel.transferFrom(fromChannel, position, count);

        fromChannel.transferTo(position, count, toChannel);
    }
}
