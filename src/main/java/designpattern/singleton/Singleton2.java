package designpattern.singleton;

public class Singleton2{


    private Singleton2() {

    }

    /**
     * 枚举类型是线程安全的，并且只会装载一次
     */
    private enum Singleton {
        INSTANCE;

        private final Singleton2 instance;

        Singleton() {
            instance = new Singleton2();
        }

        private Singleton2 getInstance() {
            return instance;
        }
    }

    public static Singleton2 getInstance() {

        return Singleton.INSTANCE.getInstance();
    }
}
