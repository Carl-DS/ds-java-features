package nio;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author duosheng
 * @since 2018/8/11
 */
public class SocketChannelTest {

    @Test
    public void SocketChannelTest() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("http://ifeve.com", 80));
        socketChannel.close();

        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress("http://ifeve.com", 80));

        while (!socketChannel.finishConnect()) {
            // do something
        }

        String newData = "New String to write to file..." + System.currentTimeMillis();

        ByteBuffer byteBuffer = ByteBuffer.allocate(48);
        int read = socketChannel.read(byteBuffer);
        byteBuffer.clear();
        byteBuffer.put(newData.getBytes());

        byteBuffer.flip();

        while (byteBuffer.hasRemaining()) {
            socketChannel.write(byteBuffer);
        }

    }
}
