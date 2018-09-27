package netty.in.action.chapter01;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import org.junit.jupiter.api.Test;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * @author duosheng
 * @since 2018/8/15
 */
public class ChannelFutureListenerTest {

    @Test
    public void channelFutureListenerTest() {
        Channel channel = null;
        // 异步的连接到远程节点
        ChannelFuture future = channel.connect(new InetSocketAddress("172.17.20.237", 8065));
        // 注册一个ChannelFutureListener,以便在操作完成时获得通知
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                // 检查操作的状态
                if (future.isSuccess()) {
                    // 如果操作成功，则创建一个ByteBuf 以持有数据
                    ByteBuf buffer = Unpooled.copiedBuffer("Hello", Charset.defaultCharset());
                    // 将数据异步的发送到远程节点。返回一个ChannelFuture
                    future.channel().writeAndFlush(buffer);
                } else {
                    // 如果访问错误则访问描述原因的Throwable
                    Throwable cause = future.cause();
                    cause.printStackTrace();
                }
            }
        });
    }
}
