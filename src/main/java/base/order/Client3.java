package base.order;

/**
 * @author duosheng
 * @since 2019/9/2
 */
public class Client3 {

    /**
     * 通过子类调用了父类的静态字段，子类不会被初始化
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("main方法执行");
        //用子类去调去父类的静态字段
        System.out.println(Son.father);
    }
}
