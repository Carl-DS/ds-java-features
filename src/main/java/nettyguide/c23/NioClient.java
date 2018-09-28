package nettyguide.c23;

import javax.sound.sampled.Port;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

/**
 * NIO 客户端序列图
 * @author duosheng
 * @since 2018/9/28
 */
public class NioClient {

    public static void main(String[] args) throws IOException {
        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                // 采用默认值
            }
        }

        // 1. 打开SocketChannel, 绑定客户端本地地址
        SocketChannel clientChannel = SocketChannel.open();
        // 2. 设置SocketChannel 为非阻塞模式，同时设置客户端连接的TCP参数
        clientChannel.configureBlocking(false);
        socket.setReuseAddress(true);
        socket.setReceiveBufferSize(BUFFER_SIZE);
        socket.setSendBufferSize(BUFFER_SIZE);
        // 3. 异步连接服务端
        clientChannel.connect(new InetSocketAddress("ip", port));
        // 4.
    }
}
