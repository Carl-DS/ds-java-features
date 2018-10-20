package nettyguide.ch10;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author duosheng
 * @since 2018/10/20
 */
public class HttpFileServer {
    private static final String DEFAULT_URL = "/src/main/java/netty";

    public void run(final int port, final String url) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 请求消息解码器
                            ch.pipeline().addLast("http-decoder", new HttpRequestDecoder());
                            // 目的是将多个消息转换为单一的request或者response对象
                            ch.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65536));
                            // 响应解码器
                            ch.pipeline().addLast("http-encoder", new HttpResponseEncoder());
                            // 目的是支持异步大文件传输（）
                            ch.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
                            // 业务逻辑 handler
                            ch.pipeline().addLast("fileServerHandler", new HttpFileServerHandler(url));
                        }
                    });
            // 绑定端口，同步等待成功
            ChannelFuture future = b.bind("172.17.20.17", port).sync();
            System.out.println("HTTP 文件目录服务器启动，网址是：" + "http://172.17.20.17:" + port + url);
            // 等待服务端监听端口关闭
            future.channel().closeFuture().sync();
        } finally {
            // 优雅退出，释放服务器资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int port = 8080;
        if (args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        String url = DEFAULT_URL;
        if (args.length > 1) {
            url = args[1];
        }
        new HttpFileServer().run(port, url);
    }
}
