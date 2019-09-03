package base.order;

/**
 * @author duosheng
 * @since 2019/9/2
 */
public class Client4 {

    /**
     * 类作为数组的组件类型不会触发类初始化：
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("main方法执行");
        ExecutionOrder[] orders = new ExecutionOrder[5];
    }
}
