package designpattern.singleton;

/**
 * 饿汉式
 */
public class Singleton1 {
    private static final Singleton1 singleton = new Singleton1();

    private Singleton1() {
    }

    public Singleton1 getInstance() {
        return singleton;
    }
}
