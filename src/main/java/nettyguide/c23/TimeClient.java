package nettyguide.c23;

/**
 * NIO 创建的TimeClient
 * @author duosheng
 * @since 2018/9/29
 */
public class TimeClient {

    public static void main(String[] args) {
        int port = 8080;
        if (args != null && args.length > 0) {
            port = Integer.parseInt(args[0]);
        }

        new Thread(new TimeClientHandler("127.0.0.1", port), "TimeClient-001").start();
    }
}
