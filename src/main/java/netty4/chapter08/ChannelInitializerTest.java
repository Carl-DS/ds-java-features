package netty4.chapter08;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import org.junit.jupiter.api.Test;

import java.net.InetSocketAddress;

/**
 * @author duosheng
 * @since 2018/9/1
 */
public class ChannelInitializerTest {

    @Test
    public void channelInitializerTest() throws InterruptedException {
        // 创建 ServerBootstrap 以创
        // 建和绑定新的 Channel
        ServerBootstrap bootstrap = new ServerBootstrap();
        // 设置 EventLoopGroup，其将提供用
        // 以处理 Channel 事件的 EventLoop
        bootstrap.group(new NioEventLoopGroup(), new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializerImpl());
        ChannelFuture future = bootstrap.bind(new InetSocketAddress(8080));
        future.sync();
    }

    final class ChannelInitializerImpl extends ChannelInitializer<Channel> {
        /**
         * 将所需的ChannelHandler 添加到ChannelPipeline
         * @param ch
         * @throws Exception
         */
        @Override
        protected void initChannel(Channel ch) throws Exception {
            ChannelPipeline pipeline = ch.pipeline();
            pipeline.addLast(new HttpClientCodec());
            pipeline.addLast(new HttpObjectAggregator(Integer.MAX_VALUE));
        }
    }
}
