package base.order;

/**
 * @author duosheng
 * @since 2019/9/2
 */
public class Client {

    public static void main(String[] args) {
        System.out.println("Client main 方法执行");
        ExecutionOrder order = new ExecutionOrder();
        order.run();
    }
}
