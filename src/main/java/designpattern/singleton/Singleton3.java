package designpattern.singleton;

/**
 * 静态内部类单例模式
 * 静态内部类单例模式也称单例持有者模式，实例由内部类创建，由于 JVM 在加载外部类的过程中,
 * 是不会加载静态内部类的, 只有内部类的属性/方法被调用时才会被加载, 并初始化其静态属性。
 * 静态属性由static修饰，保证只被实例化一次，并且严格保证实例化顺序。
 *
 */
public class Singleton3 {
    private Singleton3() {
    }

    // 单例持有者
    private static class InstanceHolder{
        private static final Singleton3 instance = new Singleton3();
    }

    public static Singleton3 getInstance() {
        // 调用内部类属性
        return InstanceHolder.instance;
    }

    public String getInfo() {
        return "hello world";
    }

    public static void main(String[] args) {
        System.out.println(Singleton3.getInstance().getInfo());
    }
}
