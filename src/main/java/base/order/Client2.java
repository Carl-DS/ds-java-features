package base.order;

/**
 * @author duosheng
 * @since 2019/9/2
 */
public class Client2 {

    public static void main(String[] args) {
        System.out.println("Client2 main 方法执行");
        ExecutionOrderChild child = new ExecutionOrderChild();
        child.run();
    }
}
