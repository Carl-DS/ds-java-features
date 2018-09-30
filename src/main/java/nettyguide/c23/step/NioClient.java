package nettyguide.c23.step;

import com.sun.org.apache.bcel.internal.generic.Select;

import javax.sound.sampled.Port;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import static com.sun.org.apache.xerces.internal.xinclude.XIncludeHandler.BUFFER_SIZE;

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
        // 4. 判断是否连接成功，如果连接成功，则直接注册状态位到多路复用器中，如果当前
        // 没有连接成功（异步连接，返回false,说明客户端已经发送sync包，服务端没有返回ack 包，物理链路还没有建立）
        if (connected) {
            clientChannel.register(selector, SelectionKey.OP_READ, ioHandler);
        } else {
            clientChannel.register(selector, SelectionKey.OP_CONNECT, ioHandler);
        }
        // 5. 向Reactor 线程的多路复用器注册SelectionKey.OP_CONNECT状态位，监听服务端的TCP ACK 应答
        clientChannel.register(selector, SelectionKey.OP_CONNECT, ioHandler);
        // 6. 创建Reactor 线程，创建多路复用器并启动线程
        Selector selector = Selector.open();
        new Thread(new ReactorTask()).start();
        // 7. 多路复用器在线程run方法的无限循环体内轮询准备就绪的key
        int num = selector.select();
        Set<SelectionKey> selectionKeys = selector.selectedKeys();
        Iterator<SelectionKey> iterator = selectionKeys.iterator();
        while (iterator.hasNext()) {
            SelectionKey next = iterator.next();
            // deal with I/O event....
        }
        // 8. 接收connect 事件进行处理
        if (key.isConnectable) {
            // handlerConnect();
        }
        // 9. 判断连接结果，如果连接成功，注册读事件到多路复用器
        if (channel.finishConect()) {
            registerRead();
        }
        // 10. 注册读事件到多路复用器
        clientChannel.register(selector, SelectionKey.OP_READ, ioHandler);
        // 11. 异步读客户端请求消息到缓冲区
        int readNumber = channel.read(receivedBuffer);
        // 12. 对ByteBuffer 进行编解码，如果有半包消息接收缓冲区Reset,继续读取后续的报文，将解码成功的消息封装成Task,投递
        // 到业务线程池中，进行业务逻辑编排
        Object message = null;
        while (buffer.hasRemain()) {
            byteBuffer.mark();
            Object message = decode(bytebuffer);
            if (message == null) {
                bytebuffer.reset();
                break;
            }
            messageList.add(message);
        }
        if (!byteBuffer.hasRemain()) {
            byteBuffer.clean();
        } else {
            byteBuffer.compact();
        }
        if (messageList != null & !messageList.isEmpty()) {
            for (Object messageE : messageList) {
                handlerTask(messageE);
            }
        }
        // 13. 将POJO对象encode成ByteBuffer, 调用SocketChannel的异步write接口，将消息异步发送给客户端
        socketChannel.write(buffer);
    }
}
