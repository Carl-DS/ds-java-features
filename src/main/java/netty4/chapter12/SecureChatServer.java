package netty4.chapter12;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

import javax.net.ssl.SSLException;
import java.net.InetSocketAddress;
import java.security.cert.CertificateException;

/**
 * @author duosheng
 * @since 2018/9/5
 */
public class SecureChatServer extends ChatServer {
    private final SslContext sslContext;

    public SecureChatServer(SslContext sslContext) {
        this.sslContext = sslContext;
    }

    /**
     * 创建 ChatServerInitializer
     *
     * @param group
     * @return
     */
    @Override
    protected ChannelInitializer<Channel> createInitializer(ChannelGroup group) {
        // 返回之前创建的SecureChatServerInitializer 以启用加密
        return new SecureChatServerInitializer(group, sslContext);
    }

    public static void main(String[] args) {
        // if (args.length != 1) {
        //     System.err.println("Please give port as argument");
        //     System.exit(1);
        // }
        // int port = Integer.parseInt(args[0]);
        int port = 9999;
        try {
            SelfSignedCertificate certificate = new SelfSignedCertificate();
            SslContext sslContext = SslContextBuilder.forServer(certificate.certificate(), certificate.privateKey()).build();
            final SecureChatServer endpoint = new SecureChatServer(sslContext);
            ChannelFuture future = endpoint.start(new InetSocketAddress(port));
            Runtime.getRuntime().addShutdownHook(new Thread(() -> endpoint.destroy()));
            future.channel().closeFuture().syncUninterruptibly();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (SSLException e) {
            e.printStackTrace();
        }
    }
}
