package nettyguide.c23.step;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * NIO 服务端序列图
 * @author duosheng
 * @since 2018/9/28
 */
public class NioServer {

    // public static void main(String[] args) throws IOException {
    //     int port = 8080;
    //     if (args != null && args.length > 1) {
    //         port = Integer.parseInt(args[0]);
    //     }
    //     // 1. 打开ServerSocketChannel, 用于监听客户端的连接，它是所有客户端连接的父管道
    //     ServerSocketChannel acceptorSvr = ServerSocketChannel.open();
    //     // 2. 绑定监听端口，设置连接为非阻塞模式
    //     acceptorSvr.socket().bind(new InetSocketAddress(InetAddress.getByName("IP"), port));
    //     acceptorSvr.configureBlocking(false);
    //     // 3. 创建Reactor线程，创建多路复用器并启动线程
    //     Selector selector = Selector.open();
    //     new Thread(new ReactorTask()).start();
    //     // 4. 将ServerSocketChannel注册到Reactor线程的多路复用器上，监听ACCEPT事件
    //     acceptorSvr.register(selector, SelectionKey.OP_ACCEPT, ioHandler);
    //     // 5. 多路复用器在线程run方法的无限循环体内轮询准备就绪的Key
    //     int num = selector.select();
    //     Set<SelectionKey> selectionKeys = selector.selectedKeys();
    //     Iterator<SelectionKey> iterator = selectionKeys.iterator();
    //     while (iterator.hasNext()) {
    //         SelectionKey next = iterator.next();
    //         // deal with I/O event...
    //     }
    //     // 6. 多路复用器监听到有新的客户端接入，处理新的接入请求，完成TCP三次握手，建立物理链路
    //     SocketChannel channel = svrChannel.accept();
    //     // 7. 设置客户端链路为非阻塞模式
    //     channel.configureBlocking(false);
    //     channel.socket().setReuseAddress(true);
    //     // 8. 将新接入的客户端连接注册到Reactor线程的多路复用器上，监听读操作，读取客户端发送的网络消息
    //     SelectionKey key = channel.register(selector, SelectionKey.OP_READ, ioHandelr);
    //     // 9. 异步读取客户端请求消息到缓冲区
    //     int readNumber = channel.read(receivedBuffer);
    //     // 10. 对ByteBuffer进行编解码，如果有半包消息指针reset，继续读取后续的报文，将解码成功的消息封装成Task,投递到业务线程池中，进行业务逻辑编排
    //     Object message = null;
    //     while (buffer.hasRemain()) {
    //         byteBuffer.mark();
    //         Object message = decode(byteBuffer);
    //         if (message == null) {
    //             byteBuffer.reset();
    //             break;
    //         }
    //         messageList.add(message);
    //     }
    //     if (!byteBuffer.hasRemain()) {
    //         byteBuffer.clear();
    //     } else {
    //         byteBuffer.compact;
    //     }
    //     if (messageList != null & !messageList.isEmpty()) {
    //         for (Object messageE : messageList) {
    //             handlerTask(messageE);
    //         }
    //     }
    //     // 11. 将POJO对象encode成ByteBuffer, 调用SocketChannel的异步write接口，将消息异步发送给客户端
    //     socketChannel.write(buffer);
    // }
}






























