package nio;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author duosheng
 * @since 2018/8/11
 */
public class BufferTest {
    private static final String FILE_STORAGE = "E:\\IdeaProjects\\practiseProjects\\ds-java-features\\doc\\";

    @Test
    public void bufferTest() throws IOException {
        RandomAccessFile accessFile = new RandomAccessFile(FILE_STORAGE + "data/nio-data.txt", "rw");
        FileChannel inChannel = accessFile.getChannel();
        ByteBuffer buf = ByteBuffer.allocate(48);

        int read = inChannel.read(buf);
        while (read != -1) {
            buf.flip();

            while (buf.hasRemaining()) {
                System.out.println((char)buf.get());
            }

            buf.clear();
            read = inChannel.read(buf);
        }
        accessFile.close();
    }
}
