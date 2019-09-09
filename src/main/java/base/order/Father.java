package base.order;

/**
 * @author duosheng
 * @since 2019/9/2
 */
public class Father {

    public static int father = 100;

    //静态代码块
    static {
        System.out.println("父类静态代码块执行");
    }

}
