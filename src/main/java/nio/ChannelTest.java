package nio;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author duosheng
 * @since 2018/8/11
 */
public class ChannelTest {
    private static final String FILE_STORAGE = "E:\\IdeaProjects\\practiseProjects\\ds-java-features\\doc\\";

    @Test
    public void readData2Buffer() throws IOException {
        RandomAccessFile accessFile = new RandomAccessFile(FILE_STORAGE + "data/nio-data.txt", "rw");
        FileChannel fileChannel = accessFile.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(48);
        int read = fileChannel.read(buffer);
        while (read != -1) {
            System.out.println("Read " + read);
            buffer.flip();

            while (buffer.hasRemaining()) {
                System.out.println(buffer.get());
            }

            buffer.clear();
            read = fileChannel.read(buffer);
        }
        accessFile.close();
    }
}
