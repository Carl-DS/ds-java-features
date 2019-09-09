package base.order;

/**
 * @author duosheng
 * @since 2019/9/2
 */
public class Client5 {

    /**
     * 常量池引用也会导致不初始化类
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("main方法执行");
        System.out.println(ExecutionOrder.color);
    }
}
