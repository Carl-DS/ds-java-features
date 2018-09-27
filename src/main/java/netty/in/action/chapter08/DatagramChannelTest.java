package netty.in.action.chapter08;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.oio.OioDatagramChannel;
import org.junit.jupiter.api.Test;

import java.net.InetSocketAddress;

/**
 * @author duosheng
 * @since 2018/9/1
 */
public class DatagramChannelTest {

    @Test
    public void datagramChannelTest() {
        // 创建一个Bootstrap的实例以创建和绑定新的数据报 Channel
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(new OioEventLoopGroup())
                // 指定Channel的实现为OioDatagramChannel
                .channel(OioDatagramChannel.class).handler(
                // 设置用以处理Channel的I/O 以及数据的 ChannelInboundHandler
                new SimpleChannelInboundHandler<DatagramPacket>() {

                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
                        // Do something with the packet
                    }
                }
        );
        // 调用 bind()方法，因为该协议是无连接的
        ChannelFuture future = bootstrap.bind(new InetSocketAddress(0));
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("Channel bound");
                } else {
                    System.err.println("Bind attempt failed");
                    future.cause().printStackTrace();
                }
            }
        });


    }
}
