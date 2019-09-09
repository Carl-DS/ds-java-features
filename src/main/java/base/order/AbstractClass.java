package base.order;

/**
 * 一个继承了抽象类的普通类的执行顺序是怎样的呢
 *
 * @author duosheng
 * @since 2019/9/3
 */
public abstract class AbstractClass {

    /**
     * 这一句的执行顺序如下：
     * <p>
     * 　　1、先进入到NotAbstractClass的构造函数，然后先去执行其父类的构造函数，父类的构造函数执行print()方法，但是子类实现了这个方法，
     * 所以会去执行System.out.println(num);这时的num还未进行赋值，所以此时num是默认值0.
     * <p>
     * 　　2、执行完父类的构造函数，会去执行子类的成员变量的初始化，这时会执行private int num=1;此时num被赋值为1,
     * <p>
     * 　　3、最后，执行子类构造函数中的代码...
     * <p>
     * 然后main函数执行第二句：n.print();，输出是1.
     * <p>
     * 总结，一个继承了抽象类的普通类的执行顺序是：父类的构造函数---子类成员变量的初始化---子类的构造函数
     *
     * @param args
     */
    public static void main(String[] args) {
        NotAbstractClass n = new NotAbstractClass();
        n.print();
    }

    public abstract void print();

    public AbstractClass() {
        print();
    }
}

/**
 * 实现抽象类的普通类
 */
class NotAbstractClass extends AbstractClass {
    private int num = 1;

    public NotAbstractClass() {
        System.out.println("...");
    }

    @Override
    public void print() {
        System.out.println(num);
    }
}