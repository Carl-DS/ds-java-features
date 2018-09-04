package netty4.chapter08;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.junit.jupiter.api.Test;

import java.net.InetSocketAddress;


/**
 * @author duosheng
 * @since 2018/9/1
 */
public class BootstrapTest {

    @Test
    public void bootstrapTest() {
        EventLoopGroup group = new NioEventLoopGroup();
        // 创建一个Bootstrap 类的实例以创建和连接新的客户端Channel
        Bootstrap bootstrap = new Bootstrap();
        // 设置EventLoopGroup，提供用于处理Channel事件的EventLoop
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new SimpleChannelInboundHandler<ByteBuf>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                        System.out.println("Received data");
                    }
                });

        ChannelFuture future = bootstrap.connect(new InetSocketAddress("www.manning.com", 80));
        future.addListener((ChannelFutureListener) future1 -> {
            if (future1.isSuccess()) {
                System.out.println("Connection established");
            } else {
                System.err.println("Connection attempt failed");
                future1.cause().printStackTrace();
            }
        });
    }
}
