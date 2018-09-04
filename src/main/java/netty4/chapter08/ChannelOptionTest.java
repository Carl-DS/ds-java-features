package netty4.chapter08;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import org.junit.jupiter.api.Test;

import java.net.InetSocketAddress;

/**
 * @author duosheng
 * @since 2018/9/1
 */
public class ChannelOptionTest {

    @Test
    public void channelOptionTest() {
        // 创建一个 AttributeKey 以标识该属性
        final AttributeKey<Integer> id = AttributeKey.newInstance("ID");
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new SimpleChannelInboundHandler<ByteBuf>() {
                    @Override
                    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
                        // 使用 AttributeKey 检索属性以及它的值
                        Integer idValue = ctx.channel().attr(id).get();
                        // do something with the idValue
                    }

                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                        System.out.println("Received data");
                    }
                });
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true)
                // 设置 ChannelOption，其将在 connect()或者 bind()方法被调用时被设置到已经创建的 Channel 上
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000);
        // 存储该id 属性
        bootstrap.attr(id, 123456);
        // 使用配置好的 Bootstrap 实例连接到远程主机
        ChannelFuture future = bootstrap.connect(new InetSocketAddress("www.manning.com", 80));
        future.syncUninterruptibly();

    }
}
