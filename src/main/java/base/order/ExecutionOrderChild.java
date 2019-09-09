package base.order;

/**
 * @author duosheng
 * @since 2019/9/2
 */
public class ExecutionOrderChild extends ExecutionOrder {

    public ExecutionOrderChild() {
        System.out.println("子类构造函数");
    }

    static {
        System.out.println("子类静态代码块");
    }

    //非静态代码块
    {
        System.out.println("子类非静态代码块");
    }

    @Override
    void run() {
        System.out.println("子类重写一般方法执行");
    }

    public static void main(String[] args) {
        System.out.println("子类main 方法执行");
        ExecutionOrderChild child = new ExecutionOrderChild();
        child.run();
    }
}
