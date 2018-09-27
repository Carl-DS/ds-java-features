package netty.in.action.chapter12;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

/**
 * @author duosheng
 * @since 2018/9/5
 */
public class SecureChatServerInitializer extends ChatServerInitializer {
    private final SslContext sslContext;

    public SecureChatServerInitializer(ChannelGroup group, SslContext sslContext) {
        super(group);
        this.sslContext = sslContext;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        super.initChannel(ch);
        SSLEngine sslEngine = sslContext.newEngine(ch.alloc());
        sslEngine.setUseClientMode(false);
        // 将SslHandler 添加到ChannelPipeline 中
        ch.pipeline().addFirst(new SslHandler(sslEngine));
    }
}
