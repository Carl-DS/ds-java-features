package base.order;

/**
 * @author duosheng
 * @since 2019/9/2
 */
public class ExecutionOrder {

    public ExecutionOrder() {
        System.out.println("父类构造函数执行");
    }

    // 新增一段常量，编译阶段后直接放入常量池
    public static final String color = "red";

    // 静态代码块
    static {
        System.out.println("父类静态代码块执行");
    }

    // 非静态代码块
    {
        System.out.println("父类非静态代码块执行");
    }

    // 一般方法
    void run() {
        System.out.println("父类一般方法执行");
    }

    public static void main(String[] args) {
        // 这个一定要写在最上面，程序一进入马上就执行，不然会导致结果不准确
        System.out.println("父类main方法执行");
        ExecutionOrder executionOrder = new ExecutionOrder();
        executionOrder.run();
    }
}
